package vitals.language;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
enum LanguagesSupported {
    ENGLISH(fetchResourceBasedOnLanguage(Locale.ENGLISH)),
    GERMAN(fetchResourceBasedOnLanguage(Locale.GERMAN));
    private final ResourceBundle userMessages;

    LanguagesSupported(ResourceBundle userMessages) {
        this.userMessages = userMessages;
    }

    ResourceBundle fetchResourceBundle() {
        return userMessages;
    }

    private static ResourceBundle fetchResourceBasedOnLanguage(Locale language) {
        return ResourceBundle.getBundle("message", language);
    }
}
