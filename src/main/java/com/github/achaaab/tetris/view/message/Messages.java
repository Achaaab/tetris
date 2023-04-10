package com.github.achaaab.tetris.view.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Messages {

	public static final String BUNDLE_NAME = "messages";

	public static final String LEVEL = "level";
	public static final String OPTIONS = "options";
	public static final String PLAY = "play";
	public static final String QUIT = "quit";
	public static final String QUIT_CONFIRM = "quit_confirm";
	public static final String SCORE = "score";
	public static final String TIME = "time";

	private static final List<LocaleListener> LISTENERS = new ArrayList<>();

	private static Locale locale;
	private static ResourceBundle bundle = getBundle(BUNDLE_NAME);

	/**
	 * @param locale
	 * @since 0.0.0
	 */
	public static void setLocale(Locale locale) {

		if (locale != Messages.locale) {

			bundle = getBundle(BUNDLE_NAME, locale);
			Messages.locale = locale;

			LISTENERS.forEach(LocaleListener::localeChanged);
		}
	}

	/**
	 * @param listener
	 * @since 0.0.0
	 */
	public static void register(LocaleListener listener) {
		LISTENERS.add(listener);
	}

	/**
	 * @param key
	 * @return
	 * @since 0.0.0
	 */
	public static String getMessage(String key) {
		return bundle.getString(key);
	}
}
