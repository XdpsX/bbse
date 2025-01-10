package com.bbse.media.util;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.*;

public class MessagesUtils {
    private static final Map<String, ResourceBundle> resourceBundleCache = new HashMap<>();

    public static String getMessage(String errorCode, Object... var2) {
        ResourceBundle messageBundle = getResourceBundle("i18n.messages", LocaleContextHolder.getLocale());
        String message;
        try {
            message = messageBundle.getString(errorCode);
        } catch (MissingResourceException ex) {
            // case message_code is not defined.
            message = errorCode;
        }
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(message, var2);
        return formattingTuple.getMessage();
    }

    private static ResourceBundle getResourceBundle(String baseName, Locale locale) {
        String key = baseName + "_" + locale.toString();
        return resourceBundleCache.computeIfAbsent(key, k -> ResourceBundle.getBundle(baseName, locale));
    }

}
