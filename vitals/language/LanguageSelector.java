package vitals.language;

import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public final class LanguageSelector {

    private static ResourceBundle selectedLanguage;

    private LanguageSelector() {
    }

    public static void setPreference(String language) throws IllegalArgumentException {
        String notNullLanguageValue = Optional.ofNullable(language)
              .orElseThrow(() -> new IllegalArgumentException("Argument passed cannot be null"));
        selectedLanguage = Arrays.stream(LanguagesSupported.values())
              .filter(languagesSupported -> languagesSupported.name().equalsIgnoreCase(notNullLanguageValue))
              .findFirst()
              .map(LanguagesSupported::fetchResourceBundle)
              .orElseThrow(() -> new IllegalArgumentException("Currently English and German language are supported"));
    }

    public static ResourceBundle getSelectedLanguage() {
        return selectedLanguage;
    }
}
