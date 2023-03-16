package pro.jazzman.odmiana.helpers;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.Control.FORMAT_PROPERTIES;
import static java.util.ResourceBundle.Control.getNoFallbackControl;
import static java.util.ResourceBundle.getBundle;

public class Localized {
    private static final String BUNDLE_NAME = "messages";
    private final Locale locale;

    public Localized(String code) {
        locale = code == null || code.isEmpty() ? null : Locale.forLanguageTag(code);
    }

    public String message(String messageCode, Object... arguments) {
        ResourceBundle bundle;
        if (locale == null) {
            bundle = getBundle(BUNDLE_NAME, Locale.ROOT);
        } else {
            try {
                bundle = getBundle(
                    BUNDLE_NAME,
                    locale,
                    getNoFallbackControl(FORMAT_PROPERTIES));
            } catch (MissingResourceException e) {
                bundle = getBundle(BUNDLE_NAME, Locale.ROOT);
            }
        }
        String message = bundle.getString(messageCode);

        return MessageFormat.format(message, arguments);
    }
}
