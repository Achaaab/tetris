package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.audio.SoundEffect;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.component.Button;
import com.github.achaaab.tetroshow.view.component.Input;
import com.github.achaaab.tetroshow.view.component.KeyInput;
import com.github.achaaab.tetroshow.view.message.Messages;
import com.github.achaaab.tetroshow.view.play.TetroshowView;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import static com.github.achaaab.tetroshow.action.Keyboard.CLOCKWISE_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.COUNTERCLOCKWISE_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.HARD_DROP_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.HOLD_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.LEFT_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.PAUSE_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.RIGHT_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.SOFT_DROP_KEY;
import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.utility.SwingUtility.hideCursor;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.BACK;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static java.awt.Font.DIALOG;
import static java.awt.Font.PLAIN;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_UP;

/**
 * view to display and change controls
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class ControlsView extends JComponent implements KeyListener {

	private static final Color BACKGROUND_COLOR = new Color(0, 0, 16);
	private static final int FONT_SIZE = 18;
	private static final Font FONT = new Font(DIALOG, PLAIN, scale(FONT_SIZE));
	private static final int INPUT_HEIGHT = scale(40);
	private static final int VALUE_X = scale(200);
	private static final SoundEffect SELECTION_SOUND_EFFECT = getSoundEffect("audio/effect/move.wav", 6);

	private final Button back;

	private final List<Input> inputs;
	private final int inputCount;
	private int selectedInputIndex;

	/**
	 * Creates a view to display and change controls.
	 *
	 * @since 0.0.0
	 */
	public ControlsView() {

		Messages.register(this::localeChanged);

		var keys = Settings.getDefaultInstance().getKeys();

		var left = new KeyInput("Left", LEFT_KEY, keys, VALUE_X);
		var right = new KeyInput("Right", RIGHT_KEY, keys, VALUE_X);
		var clockwise = new KeyInput("Clockwise", CLOCKWISE_KEY, keys, VALUE_X);
		var counterClockwise = new KeyInput("Counterclockwise", COUNTERCLOCKWISE_KEY, keys, VALUE_X);
		var softDrop = new KeyInput("Soft drop", SOFT_DROP_KEY, keys, VALUE_X);
		var hardDrop = new KeyInput("Hard drop", HARD_DROP_KEY, keys, VALUE_X);
		var hold = new KeyInput("Hold", HOLD_KEY, keys, VALUE_X);
		var pause = new KeyInput("Pause", PAUSE_KEY, keys, VALUE_X);

		back = new Button(getMessage(BACK));

		inputs = List.of(
				left,
				right,
				clockwise,
				counterClockwise,
				softDrop,
				hardDrop,
				hold,
				pause,
				back);

		inputCount = inputs.size();
		left.setSelected(true);
		selectedInputIndex = 0;

		var x = scale(75.0f);
		var y = scale(75.0f);

		left.setX(x);
		left.setY(y);

		y += INPUT_HEIGHT;
		right.setX(x);
		right.setY(y);

		y += INPUT_HEIGHT;
		clockwise.setX(x);
		clockwise.setY(y);

		y += INPUT_HEIGHT;
		counterClockwise.setX(x);
		counterClockwise.setY(y);

		y += INPUT_HEIGHT;
		softDrop.setX(x);
		softDrop.setY(y);

		y += INPUT_HEIGHT;
		hardDrop.setX(x);
		hardDrop.setY(y);

		y += INPUT_HEIGHT;
		hold.setX(x);
		hold.setY(y);

		y += INPUT_HEIGHT;
		pause.setX(x);
		pause.setY(y);

		y += INPUT_HEIGHT;
		back.setX(x);
		back.setY(y);

		addKeyListener(this);
		hideCursor(this);
		setPreferredSize(TetroshowView.DIMENSION);
	}

	@Override
	public void paint(Graphics graphics) {

		var graphics2d = (Graphics2D) graphics;

		graphics2d.setColor(BACKGROUND_COLOR);
		graphics2d.fillRect(0, 0, getWidth(), getHeight());

		graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		graphics.setFont(FONT);
		inputs.forEach(input -> input.paint(graphics2d));
		graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
	}

	/**
	 * @since 0.0.0
	 */
	private void localeChanged() {
		back.setText(getMessage(BACK));
	}

	/**
	 * Selects an input.
	 *
	 * @param index input index
	 * @since 0.0.0
	 */
	public void selectInput(int index) {

		inputs.get(selectedInputIndex).setSelected(false);
		selectedInputIndex = index;
		inputs.get(selectedInputIndex).setSelected(true);
		SELECTION_SOUND_EFFECT.play();
	}

	/**
	 * @param action back action
	 * @since 0.0.0
	 */
	public void onBack(Runnable action) {
		back.setAction(action);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

		var keyCode = keyEvent.getKeyCode();

		var selectedInput = inputs.get(selectedInputIndex);

		if (selectedInput instanceof KeyInput keyInput && keyInput.isEditing()) {

			keyInput.keyTyped(keyCode);

		} else {

			switch (keyCode) {

				case VK_UP -> selectInput(((selectedInputIndex - 1) + inputCount) % inputCount);
				case VK_DOWN -> selectInput((selectedInputIndex + 1) % inputCount);
				case VK_ESCAPE -> back.executeAction();
				default -> selectedInput.keyTyped(keyCode);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {

	}
}
