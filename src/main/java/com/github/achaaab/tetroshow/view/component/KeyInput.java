package com.github.achaaab.tetroshow.view.component;

import com.github.achaaab.tetroshow.audio.SoundEffect;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.util.Map;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.utility.KeyUtility.getKeyText;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scaleFloat;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static java.awt.Font.BOLD;
import static java.awt.Font.DIALOG;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.lang.Math.round;
import static java.lang.Math.toIntExact;

/**
 * input component to set a key
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class KeyInput extends Input {

	private static final Font SYMBOL_FONT = new Font(DIALOG, BOLD, scale(22));
	private static final int SELECTED_SHIFT = scale(10.0f);
	private static final SoundEffect KEY_SET_SOUND_EFFECT = getSoundEffect("audio/effect/move.wav", 6);

	private static final Color SELECTED_NAME_COLOR = new Color(255, 255, 255);
	private static final Color UNSELECTED_NAME_COLOR = new Color(128, 128, 128);
	private static final Color SELECTED_VALUE_COLOR = new Color(0, 96, 192);
	private static final Color UNSELECTED_VALUE_COLOR = new Color(0, 48, 96);
	private static final Color SELECTED_SYMBOL_COLOR = new Color(255, 32, 16);
	private static final Color UNSELECTED_SYMBOL_COLOR = new Color(128, 16, 8);

	private static final Color KEY_OUTER_LIGHT_COLOR = new Color(192, 192, 192);
	private static final Color KEY_OUTER_DARK_COLOR = new Color(64, 64, 64);
	private static final Color KEY_INNER_LIGHT_COLOR = new Color(224, 224, 224);
	private static final Color KEY_INNER_DARK_COLOR = new Color(128, 128, 128);

	private static final int KEY_OUTER_MARGIN = scale(10);
	private static final int KEY_UPPER_MARGIN = scale(8);
	private static final int KEY_INNER_MARGIN = scale(5);
	private static final int KEY_OUTER_ARC = scale(10);
	private static final int KEY_INNER_ARC = scale(5);

	private static final float MINIMUM_KEY_WIDTH = scaleFloat(6);
	private static final float MINIMUM_KEY_HEIGHT = scaleFloat(8);

	private static final String KEY_INVITATION_TEXT = " ";

	private final char symbol;
	private final String actionKey;
	private final Map<String, Integer> keyCodes;
	private final int keyTextX;
	private int keyCode;
	private Character keyCharacter;
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
		this.actionKey = actionKey;
		this.keyCodes = keyCodes;
		this.keyTextX = keyTextX;

		var keyCode = keyCodes.get(actionKey);
		setKey(keyCode);
	}

	/**
	 * @param keyCode key code
	 * @since 0.0.0
	 */
	private void setKey(int keyCode) {
		setKey(keyCode, null);
	}

	/**
	 * @param keyCode key code
	 * @param keyCharacter key character
	 * @since 0.0.0
	 */
	private void setKey(int keyCode, Character keyCharacter) {

		this.keyCode = keyCode;
		this.keyCharacter = keyCharacter;

		editing = false;
		keyCodes.put(actionKey, keyCode);
		KEY_SET_SOUND_EFFECT.play();
	}

	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		var text = getMessage(textKey);
		var keyText = getKeyText(keyCode, keyCharacter);

		graphics.setColor(selected ? SELECTED_NAME_COLOR : UNSELECTED_NAME_COLOR);
		graphics.drawString(text, selected ? SELECTED_SHIFT : 0, 0);

		drawKey(graphics, keyText);

		var fontMetrics = graphics.getFontMetrics();
		var textWidth = fontMetrics.stringWidth(text + " ");
		graphics.setFont(SYMBOL_FONT);
		graphics.setColor(selected ? SELECTED_SYMBOL_COLOR : UNSELECTED_SYMBOL_COLOR);
		graphics.drawString(Character.toString(symbol), selected ? SELECTED_SHIFT + textWidth : textWidth, 0);
	}

	/**
	 * Draws a key.
	 *
	 * @param graphics graphics with which to draw
	 * @param keyText text of the key
	 * @since 0.0.0
	 */
	private void drawKey(Graphics2D graphics, String keyText) {

		var font = graphics.getFont();
		var renderContext = graphics.getFontRenderContext();
		var layout = new TextLayout(editing ? KEY_INVITATION_TEXT : keyText, font, renderContext);
		var bounds = layout.getBounds();

		var x = keyTextX + bounds.getX();
		var y = bounds.getY();
		var width = bounds.getWidth();
		var height = bounds.getHeight();

		if (width < MINIMUM_KEY_WIDTH) {

			x -= (MINIMUM_KEY_WIDTH - width) / 2;
			width = MINIMUM_KEY_WIDTH;
		}

		if (height < MINIMUM_KEY_HEIGHT) {

			y -= (MINIMUM_KEY_HEIGHT - height) / 2;
			height = MINIMUM_KEY_HEIGHT;
		}

		var topLeft = new Point2D.Double(
				x - KEY_OUTER_MARGIN,
				y - KEY_UPPER_MARGIN);

		var bottomRight = new Point2D.Double(
				x + width + KEY_OUTER_MARGIN + KEY_OUTER_MARGIN,
				y + height + KEY_UPPER_MARGIN + KEY_OUTER_MARGIN);

		var gradient = new GradientPaint(
				topLeft, editing ? KEY_OUTER_LIGHT_COLOR.brighter() : KEY_OUTER_LIGHT_COLOR,
				bottomRight, editing ? KEY_OUTER_DARK_COLOR.brighter() : KEY_OUTER_DARK_COLOR);

		graphics.setPaint(gradient);

		graphics.fillRoundRect(
				toIntExact(round(x)) - KEY_OUTER_MARGIN,
				toIntExact(round(y)) - KEY_UPPER_MARGIN,
				toIntExact(round(width)) + KEY_OUTER_MARGIN + KEY_OUTER_MARGIN,
				toIntExact(round(height)) + KEY_UPPER_MARGIN + KEY_OUTER_MARGIN,
				KEY_OUTER_ARC, KEY_OUTER_ARC);

		topLeft = new Point2D.Double(
				x - KEY_INNER_MARGIN,
				y - KEY_INNER_MARGIN);

		bottomRight = new Point2D.Double(
				x + width + KEY_INNER_MARGIN,
				y + height + KEY_INNER_MARGIN);

		gradient = new GradientPaint(
				topLeft, editing ? KEY_INNER_DARK_COLOR.brighter() : KEY_INNER_DARK_COLOR,
				bottomRight, editing ? KEY_INNER_LIGHT_COLOR.brighter() : KEY_INNER_LIGHT_COLOR);

		graphics.setPaint(gradient);

		graphics.fillRoundRect(
				toIntExact(round(x)) - KEY_INNER_MARGIN,
				toIntExact(round(y)) - KEY_INNER_MARGIN,
				toIntExact(round(width)) + KEY_INNER_MARGIN + KEY_INNER_MARGIN,
				toIntExact(round(height)) + KEY_INNER_MARGIN + KEY_INNER_MARGIN,
				KEY_INNER_ARC, KEY_INNER_ARC);

		graphics.setColor(selected ? SELECTED_VALUE_COLOR : UNSELECTED_VALUE_COLOR);

		graphics.drawString(
				editing ? KEY_INVITATION_TEXT : keyText,
				keyTextX,
				0);
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

		var keyCode = keyEvent.getExtendedKeyCode();
		var keyCharacter = keyEvent.getKeyChar();

		if (editing) {

			if (keyCode == VK_ESCAPE) {
				editing = false;
			} else {
				setKey(keyCode, keyCharacter);
			}

		} else {

			switch (keyCode) {
				case VK_ENTER, VK_SPACE -> editing = true;
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
