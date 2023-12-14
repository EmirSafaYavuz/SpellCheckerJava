import java.util.Set;

public class Main {
    public static void main(String[] args) {
        SpellChecker spellChecker = new SpellChecker(250000);

        spellChecker.loadDictionary();

        Set<String> misspelled = spellChecker.spellCheckFile();
        System.out.println("Misspelled words: " + misspelled);
    }
}