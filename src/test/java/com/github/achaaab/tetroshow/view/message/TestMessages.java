package com.github.achaaab.tetroshow.view.message;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.achaaab.tetroshow.view.message.Language.ENGLISH;
import static com.github.achaaab.tetroshow.view.message.Language.FRENCH;
import static com.github.achaaab.tetroshow.view.message.Language.HINDI;
import static com.github.achaaab.tetroshow.view.message.Language.JAPANESE;
import static com.github.achaaab.tetroshow.view.message.Messages.getLocale;
import static com.github.achaaab.tetroshow.view.message.Messages.register;
import static com.github.achaaab.tetroshow.view.message.Messages.setLocale;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * unit tests of {@link Messages}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
@ExtendWith(MockitoExtension.class)
class TestMessages {

	@Mock
	private LocaleListener localeListener;

	@Test
	void testCurrentLocale() {

		var locale = HINDI.getLocale();
		setLocale(locale);
		assertEquals(locale, getLocale());

		locale = ENGLISH.getLocale();
		setLocale(locale);
		assertEquals(locale, getLocale());
	}

	@Test
	void testLocaleListener() {

		setLocale(FRENCH.getLocale());
		register(localeListener);
		setLocale(JAPANESE.getLocale());

		verify(localeListener).localeChanged();
	}

	@Test
	void testSetSameLocale() {

		setLocale(FRENCH.getLocale());
		register(localeListener);
		setLocale(FRENCH.getLocale());

		verify(localeListener, never()).localeChanged();
	}
}
