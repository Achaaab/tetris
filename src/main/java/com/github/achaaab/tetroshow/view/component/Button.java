package com.github.achaaab.tetroshow.view.component;

import com.github.achaaab.tetroshow.audio.SoundEffect;

import java.awt.Color;
import java.awt.Graphics2D;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_SPACE;

/**
 * simple text button
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Button extends Input {

	private static final SoundEffect EXECUTION_SOUND_EFFECT = getSoundEffect("audio/effect/hard_drop.wav", 2);
	private static final int SELECTED_SHIFT = scale(10.0f);

	private static final Color SELECTED_COLOR = WHITE;
	private static final Color UNSELECTED_COLOR = GRAY;

	private String text;
	private Runnable action;

	/**
	 * Creates a new text button.
	 *
	 * @param text text to display as a button
	 * @since 0.0.0
	 */
	public Button(String text) {
		this.text = text;
	}

	/**
	 * @param text text to display
	 * @since 0.0.0
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param action action to execute
	 * @since 0.0.0
	 */
	public void setAction(Runnable action) {
		this.action = action;
	}

	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		graphics.setColor(selected ? SELECTED_COLOR : UNSELECTED_COLOR);
		graphics.drawString(text, selected ? SELECTED_SHIFT : 0, 0);
	}

	/**
	 * Executes the action associated to this button.
	 *
	 * @since 0.0.0
	 */
	public void executeAction() {

		EXECUTION_SOUND_EFFECT.play();
		action.run();
	}

	@Override
	public void keyTyped(int keyCode) {

		switch (keyCode) {
			case VK_ENTER, VK_SPACE -> executeAction();
		}
	}
}
