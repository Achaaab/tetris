package com.github.achaaab.tetroshow.view.component;

import com.github.achaaab.tetroshow.audio.SoundEffect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;
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

	private static final SoundEffect OPTION_CHANGED_SOUND_EFFECT = getSoundEffect("audio/effect/move.wav", 6);
	private static final int SELECTED_SHIFT = scale(10.0f);

	private static final Color SELECTED_NAME_COLOR = WHITE;
	private static final Color UNSELECTED_NAME_COLOR = GRAY;
	private static final Color SELECTED_VALUE_COLOR = new Color(96, 224, 255);
	private static final Color UNSELECTED_VALUE_COLOR = new Color(0, 101, 189);

	private String action;
	private final String keyKey;
	private final Map<String, Integer> keyCodes;
	private final int keyTextX;
	private String keyText;
	private boolean editing;

	/**
	 * Creates a new key input.
	 *
	 * @param action action description
	 * @param keyKey key of the key
	 * @param keyCodes key codes to adjust
	 * @param keyTextX distance between action and key text (in pixels)
	 * @since 0.0.0
	 */
	public KeyInput(String action, String keyKey, Map<String, Integer> keyCodes, int keyTextX) {

		this.action = action;
		this.keyKey = keyKey;
		this.keyCodes = keyCodes;
		this.keyTextX = keyTextX;

		setKeyCode(keyCodes.get(keyKey));
	}

	/**
	 * @param keyCode key code
	 * @since 0.0.0
	 */
	private void setKeyCode(int keyCode) {

		keyText = getKeyText(keyCode);
		editing = false;

		keyCodes.put(keyKey, keyCode);
	}

	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		graphics.setColor(selected ? SELECTED_NAME_COLOR : UNSELECTED_NAME_COLOR);
		graphics.drawString(action, selected ? SELECTED_SHIFT : 0, 0);
		graphics.setColor(editing ? SELECTED_VALUE_COLOR : UNSELECTED_VALUE_COLOR);
		graphics.drawString(editing ? "???" : keyText, keyTextX, 0);
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
