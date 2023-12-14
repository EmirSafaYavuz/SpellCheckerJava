import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SpellChecker {
    private int size;
    private String[][] dictionary;

    public SpellChecker(int size) {
        this.size = size;
        this.dictionary = new String[26][size / 26];
    }

    private int hashFunction(String word) {
        char[] chars = word.toLowerCase().toCharArray();
        int hash = 0;
        for (char c : chars) {
            hash += (int) c - 'a';
        }
        return hash % dictionary.length;
    }
    public void insertWord(String word) {
        int index = hashFunction(word);
        int subIndex = index % (size / 26);
        while (dictionary[index][subIndex] != null && !dictionary[index][subIndex].equals(word)) {
            subIndex = (subIndex + 1) % (size / 26);
        }
        dictionary[index][subIndex] = word;
    }

    public void deleteWord(String word) {
        int index = hashFunction(word);
        int subIndex = index % (size / 26);
        while (dictionary[index][subIndex] != null) {
            if (dictionary[index][subIndex].equals(word)) {
                dictionary[index][subIndex] = null;
                break;
            }
            subIndex = (subIndex + 1) % (size / 26);
        }
    }

    public boolean searchWord(String word) {
        int index = Math.abs(hashFunction(word));
        int subIndex = index % (size / 26);
        while (dictionary[index][subIndex] != null) {
            if (dictionary[index][subIndex].equalsIgnoreCase(word)) {
                return true;
            }
            subIndex = (subIndex + 1) % (size / 26);
        }
        return false;
    }

    public void loadDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/emirsafayavuz/IdeaProjects/SpellChecker/src/dict.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\b\\W+\\b");
                for (String word : words) {
                    if (!word.isEmpty() && Character.isLetter(word.charAt(0))) {
                        insertWord(word);
                    }
                }
                System.out.println(line);
            }
            System.out.println("Dictionary loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> spellCheckFile() {
        Set<String> misspelledWords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/emirsafayavuz/IdeaProjects/SpellChecker/src/textfile.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\b\\W+\\b");
                for (String word : words) {
                    if (!word.isEmpty() && Character.isLetter(word.charAt(0)) && !searchWord(word)) {
                        misspelledWords.add(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return misspelledWords;
    }
}
