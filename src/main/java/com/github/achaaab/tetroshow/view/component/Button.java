package com.github.achaaab.tetroshow.view.component;

import com.github.achaaab.tetroshow.audio.SoundEffect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
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

	private Runnable action;

	/**
	 * Creates a new text button.
	 *
	 * @param textKey key of the text
	 * @since 0.0.0
	 */
	public Button(String textKey) {
		super(textKey);
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

		var text = getMessage(textKey);

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
	public void keyTyped(KeyEvent keyEvent) {

		switch (keyEvent.getKeyCode()) {
			case VK_ENTER, VK_SPACE -> executeAction();
		}
	}
}
