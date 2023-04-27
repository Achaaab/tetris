package com.github.achaaab.tetroshow.utility;

import com.github.achaaab.tetroshow.view.message.Messages;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import static java.lang.Character.isISOControl;
import static java.lang.Character.isWhitespace;
import static java.lang.Character.toUpperCase;

/**
 * key utility methods
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class KeyUtility {

	/**
	 * @param keyCode key code
	 * @param keyCharacter key character, can be {@code null}
	 * @return key text
	 * @since 0.0.0
	 */
	public static String getKeyText(int keyCode, Character keyCharacter) {

		String keyText;

		if (Messages.containsKeyText(keyCode)) {
			keyText = Messages.getKeyText(keyCode);
		} else if (isDisplayable(keyCharacter)) {
			keyText = toString(keyCharacter);
		} else {
			keyText = KeyEvent.getKeyText(keyCode);
		}

		return keyText;
	}

	/**
	 * Determines if a key character can be displayed.
	 *
	 * @param keyCharacter key character, can be {@code null}
	 * @return whether the given key character is displayable
	 * @since 0.0.0
	 */
	private static boolean isDisplayable(Character keyCharacter) {

		return keyCharacter != null && keyCharacter != CHAR_UNDEFINED &&
				!isWhitespace(keyCharacter) && !isISOControl(keyCharacter);
	}

	/**
	 * @param keyCharacter displayable key character
	 * @return string representation of the given key character
	 * @since 0.0.0
	 */
	private static String toString(Character keyCharacter) {

		if (keyCharacter >= 'a' && keyCharacter <= 'z') {
			keyCharacter = toUpperCase(keyCharacter);
		}

		return Character.toString(keyCharacter);
	}
}
