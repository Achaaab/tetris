package com.github.achaaab.tetroshow.view.message;

import java.util.Locale;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public enum Language {

	ENGLISH(Locale.ENGLISH, "English"),
	SPANISH(Locale.of("es"), "Español"),
	FRENCH(Locale.FRENCH, "Français"),
	HINDI(Locale.of("hi"), "हिंदी"),
	JAPANESE(Locale.JAPANESE, "日本語"),
	RUSSIAN(Locale.of("ru"), "Русский"),
	CHINESE(Locale.SIMPLIFIED_CHINESE, "中文");

	private static final Map<String, Language> LANGUAGES_BY_CODE = stream(values()).
			collect(toMap(Language::getCode, identity()));

	private static final Map<String, Language> LANGUAGES_BY_NAME = stream(values()).
			collect(toMap(Language::toString, identity()));

	/**
	 * @param code ISO 639 language code
	 * @return corresponding language
	 * @since 0.0.0
	 */
	public static Language getLanguageByCode(String code) {
		return LANGUAGES_BY_CODE.get(code);
	}

	/**
	 * @param name localized language name
	 * @return corresponding language
	 * @since 0.0.0
	 */
	public static Language getLanguageByName(String name) {
		return LANGUAGES_BY_NAME.get(name);
	}

	private final Locale locale;
	private final String localizedName;

	/**
	 * @param locale locale
	 * @param localizedName language localized name
	 * @since 0.0.0
	 */
	Language(Locale locale, String localizedName) {

		this.locale = locale;
		this.localizedName = localizedName;
	}

	/**
	 * @return ISO 639 code
	 * @since 0.0.0
	 */
	public String getCode() {
		return locale.getLanguage();
	}

	/**
	 * @return locale
	 * @since 0.0.0
	 */
	public Locale getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		return localizedName;
	}
}
