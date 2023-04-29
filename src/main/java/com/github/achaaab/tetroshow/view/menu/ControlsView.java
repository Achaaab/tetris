package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.component.Button;
import com.github.achaaab.tetroshow.view.component.KeyInput;
import com.github.achaaab.tetroshow.view.component.Label;

import java.awt.event.KeyEvent;

import static com.github.achaaab.tetroshow.action.Keyboard.CLOCKWISE_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.COUNTERCLOCKWISE_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.HARD_DROP_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.HOLD_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.LEFT_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.PAUSE_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.RIGHT_KEY;
import static com.github.achaaab.tetroshow.action.Keyboard.SOFT_DROP_KEY;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.BACK;
import static com.github.achaaab.tetroshow.view.message.Messages.CONTROLS;
import static com.github.achaaab.tetroshow.view.message.Messages.HARD_DROP;
import static com.github.achaaab.tetroshow.view.message.Messages.HOLD;
import static com.github.achaaab.tetroshow.view.message.Messages.LEFT;
import static com.github.achaaab.tetroshow.view.message.Messages.PAUSE;
import static com.github.achaaab.tetroshow.view.message.Messages.RIGHT;
import static com.github.achaaab.tetroshow.view.message.Messages.ROTATE;
import static com.github.achaaab.tetroshow.view.message.Messages.SOFT_DROP;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_UP;

/**
 * view to display and change controls
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class ControlsView extends MenuView {

	private static final int INPUT_HEIGHT = scale(40);
	private static final int VALUE_X = scale(200);
	private static final int INPUT_X = scale(75.0f);
	private static final int FIRST_INPUT_Y = scale(95.0f);

	private final Button back;

	/**
	 * Creates a view to display and change controls.
	 *
	 * @since 0.0.0
	 */
	public ControlsView() {

		var title = new Label(CONTROLS);
		title.setFont(DEFAULT_TITLE_FONT);

		var keys = Settings.getDefaultInstance().getKeys();

		var left = new KeyInput(LEFT, '⏴', LEFT_KEY, keys, VALUE_X);
		var right = new KeyInput(RIGHT, '⏵', RIGHT_KEY, keys, VALUE_X);
		var clockwise = new KeyInput(ROTATE, '↶', CLOCKWISE_KEY, keys, VALUE_X);
		var counterClockwise = new KeyInput(ROTATE, '↷', COUNTERCLOCKWISE_KEY, keys, VALUE_X);
		var softDrop = new KeyInput(SOFT_DROP, '⇣', SOFT_DROP_KEY, keys, VALUE_X);
		var hardDrop = new KeyInput(HARD_DROP, '↓', HARD_DROP_KEY, keys, VALUE_X);
		var hold = new KeyInput(HOLD, '↸', HOLD_KEY, keys, VALUE_X);
		var pause = new KeyInput(PAUSE, '⏸', PAUSE_KEY, keys, VALUE_X);

		back = new Button(BACK);

		add(title);
		add(left);
		add(right);
		add(clockwise);
		add(counterClockwise);
		add(softDrop);
		add(hardDrop);
		add(hold);
		add(pause);
		add(back);

		selectFirstInput();

		title.setX(scale(130));
		title.setY(scale(35));

		var y = FIRST_INPUT_Y;

		left.setX(INPUT_X);
		left.setY(y);

		y += INPUT_HEIGHT;
		right.setX(INPUT_X);
		right.setY(y);

		y += INPUT_HEIGHT;
		clockwise.setX(INPUT_X);
		clockwise.setY(y);

		y += INPUT_HEIGHT;
		counterClockwise.setX(INPUT_X);
		counterClockwise.setY(y);

		y += INPUT_HEIGHT;
		softDrop.setX(INPUT_X);
		softDrop.setY(y);

		y += INPUT_HEIGHT;
		hardDrop.setX(INPUT_X);
		hardDrop.setY(y);

		y += INPUT_HEIGHT;
		hold.setX(INPUT_X);
		hold.setY(y);

		y += INPUT_HEIGHT;
		pause.setX(INPUT_X);
		pause.setY(y);

		y += INPUT_HEIGHT + INPUT_HEIGHT / 2;
		back.setX(INPUT_X);
		back.setY(y);

		setFocusTraversalKeysEnabled(false);
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

		var keyCode = keyEvent.getExtendedKeyCode();
		var selectedInput = getSelectedInput();

		if (selectedInput instanceof KeyInput keyInput && keyInput.isEditing()) {

			keyInput.keyTyped(keyEvent);

		} else {

			switch (keyCode) {

				case VK_UP -> selectPreviousInput();
				case VK_DOWN -> selectNextInput();
				case VK_ESCAPE -> back.executeAction();
				default -> selectedInput.keyTyped(keyEvent);
			}
		}
	}
}
