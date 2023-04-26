package com.github.achaaab.tetroshow.view.component;

import com.github.achaaab.tetroshow.audio.SoundEffect;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Map;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static java.awt.Font.BOLD;
import static java.awt.Font.DIALOG;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.getKeyText;

/**
 * input component to set a key
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class KeyInput extends Input {

	private static final Font SYMBOL_FONT = new Font(DIALOG, BOLD, scale(18));
	private static final int SELECTED_SHIFT = scale(10.0f);
	private static final SoundEffect KEY_SET_SOUND_EFFECT = getSoundEffect("audio/effect/move.wav", 6);

	private static final Color SELECTED_NAME_COLOR = new Color(255, 255, 255);
	private static final Color UNSELECTED_NAME_COLOR = new Color(128, 128, 128);
	private static final Color SELECTED_VALUE_COLOR = new Color(96, 224, 255);
	private static final Color UNSELECTED_VALUE_COLOR = new Color(0, 101, 189);
	private static final Color SELECTED_SYMBOL_COLOR = new Color(255, 32, 16);
	private static final Color UNSELECTED_SYMBOL_COLOR = new Color(128, 16, 8);

	private final char symbol;
	private final String keyKey;
	private final Map<String, Integer> keyCodes;
	private final int keyTextX;
	private String keyText;
	private boolean editing;

	/**
	 * Creates a new key input.
	 *
	 * @param textKey key of the action description
	 * @param symbol single character representing the action in a more visual way
	 * @param actionKey key of the action
	 * @param keyCodes key codes to adjust
	 * @param keyTextX distance between action and key text (in pixels)
	 * @since 0.0.0
	 */
	public KeyInput(String textKey, char symbol, String actionKey, Map<String, Integer> keyCodes, int keyTextX) {

		super(textKey);

		this.symbol = symbol;
		this.keyKey = actionKey;
		this.keyCodes = keyCodes;
		this.keyTextX = keyTextX;

		var keyCode = keyCodes.get(actionKey);
		setKeyCode(keyCode);
	}

	/**
	 * @param keyCode key code
	 * @since 0.0.0
	 */
	private void setKeyCode(int keyCode) {

		keyText = getKeyText(keyCode);
		editing = false;

		keyCodes.put(keyKey, keyCode);

		KEY_SET_SOUND_EFFECT.play();
	}

	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		var text = getMessage(textKey);

		graphics.setColor(selected ? SELECTED_NAME_COLOR : UNSELECTED_NAME_COLOR);
		graphics.drawString(text, selected ? SELECTED_SHIFT : 0, 0);

		graphics.setColor(editing ? SELECTED_VALUE_COLOR : UNSELECTED_VALUE_COLOR);
		graphics.drawString(editing ? "???" : keyText, keyTextX, 0);

		var fontMetrics = graphics.getFontMetrics();
		var textWidth = fontMetrics.stringWidth(text + " ");
		graphics.setFont(SYMBOL_FONT);
		graphics.setColor(selected ? SELECTED_SYMBOL_COLOR : UNSELECTED_SYMBOL_COLOR);
		graphics.drawString(Character.toString(symbol), selected ? SELECTED_SHIFT + textWidth : textWidth, 0);
	}

	@Override
	public void keyTyped(int keyCode) {

		if (editing) {

			if (keyCode == VK_ESCAPE) {
				editing = false;
			} else {
				setKeyCode(keyCode);
			}

		} else {

			switch (keyCode) {

				case VK_ENTER, VK_SPACE -> editing = true;
				default -> setKeyCode(keyCode);
			}
		}
	}

	/**
	 * @return whether this key input is being edited
	 * @since 0.0.0
	 */
	public boolean isEditing() {
		return editing;
	}
}
