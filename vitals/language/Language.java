package vitals.language;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public final class Language {

    private static ResourceBundle selectedLanguage;

    private Language() {
    }

    public static void setPreference(String language) throws IllegalArgumentException {
        String notNullLanguageValue = Optional.ofNullable(language)
              .orElseThrow(() -> new IllegalArgumentException("Argument passed cannot be null"));
        selectedLanguage = Arrays.stream(LanguageSupported.values())
              .filter(languageSupported -> languageSupported.name().equalsIgnoreCase(notNullLanguageValue))
              .findFirst()
              .map(LanguageSupported::fetchResourceBundle)
              .orElseThrow(() -> new IllegalArgumentException("Currently English and German language are supported"));
    }

    public static ResourceBundle getSelectedLanguage() {
        return selectedLanguage;
    }

    static ResourceBundle fetchResourceBasedOnLanguage(Locale language) {
        return ResourceBundle.getBundle("message", language);
    }
}
