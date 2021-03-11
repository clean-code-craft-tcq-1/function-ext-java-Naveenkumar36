package vitals.language;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
enum LanguageSupported {
    ENGLISH(Language.fetchResourceBasedOnLanguage(Locale.ENGLISH)),
    GERMAN(Language.fetchResourceBasedOnLanguage(Locale.GERMAN));
    private final ResourceBundle userMessages;

    LanguageSupported(ResourceBundle userMessages) {
        this.userMessages = userMessages;
    }

    ResourceBundle fetchResourceBundle() {
        return userMessages;
    }
}
