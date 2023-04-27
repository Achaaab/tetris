package com.github.achaaab.tetroshow.view.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Localized messages control center.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Messages {

	public static final String BUNDLE_NAME = "messages/messages";

	public static final String BACK = "back";
	public static final String CONTROLS = "controls";
	public static final String CREDITS = "credits";
	public static final String HARD_DROP = "hard_drop";
	public static final String HOLD = "hold";
	public static final String LANGUAGE = "language";
	public static final String LEFT = "left";
	public static final String LEVEL = "level";
	public static final String MUSIC = "music";
	public static final String OPTIONS = "options";
	public static final String PAUSE = "pause";
	public static final String PLAY = "play";
	public static final String RIGHT = "right";
	public static final String ROTATE = "rotate";
	public static final String QUIT = "quit";
	public static final String QUIT_CONFIRM = "quit_confirm";
	public static final String SCORE = "score";
	public static final String SKIN = "skin";
	public static final String SOFT_DROP = "soft_drop";
	public static final String SOUND_EFFECT = "sound_effect";
	public static final String TIME = "time";

	private static final String KEY_TEXT_PREFIX = "key_";

	private static final List<LocaleListener> LISTENERS = new ArrayList<>();

	private static Locale locale = Locale.getDefault();
	private static ResourceBundle bundle = getBundle(BUNDLE_NAME);

	/**
	 * @return current locale
	 * @since 0.0.0
	 */
	public static Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale new locale to apply
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
	 * Registers a locale change listener.
	 *
	 * @param listener locale change listener to register
	 * @since 0.0.0
	 */
	public static void register(LocaleListener listener) {
		LISTENERS.add(listener);
	}

	/**
	 * @param key message key
	 * @return localized message value
	 * @since 0.0.0
	 */
	public static String getMessage(String key) {
		return bundle.getString(key);
	}

	/**
	 * @param keyCode code of the key to test
	 * @return whether a message exists for the given key
	 * @since 0.0.0
	 */
	public static boolean containsKeyText(int keyCode) {
		return bundle.containsKey(KEY_TEXT_PREFIX + keyCode);
	}

	/**
	 * @param keyCode key code
	 * @return corresponding key text
	 * @since 0.0.0
	 */
	public static String getKeyText(int keyCode) {
		return getMessage(KEY_TEXT_PREFIX + keyCode);
	}
}
