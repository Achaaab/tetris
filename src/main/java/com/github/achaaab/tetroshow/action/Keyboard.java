package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Tetroshow;
import org.slf4j.Logger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import static com.github.achaaab.tetroshow.model.piece.Direction.CLOCKWISE;
import static com.github.achaaab.tetroshow.model.piece.Direction.COUNTERCLOCKWISE;
import static com.github.achaaab.tetroshow.model.piece.Direction.DOWN;
import static com.github.achaaab.tetroshow.model.piece.Direction.LEFT;
import static com.github.achaaab.tetroshow.model.piece.Direction.RIGHT;
import static java.util.Arrays.fill;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * A keyboard can listen for key events and bind actions to them.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Keyboard extends AbstractAction implements KeyListener {

	private static final Logger LOGGER = getLogger(Keyboard.class);

	public static final String LEFT_KEY = "left";
	public static final String RIGHT_KEY = "right";
	public static final String CLOCKWISE_KEY = "clockwise";
	public static final String COUNTERCLOCKWISE_KEY = "counterclockwise";
	public static final String SOFT_DROP_KEY = "soft_drop";
	public static final String HARD_DROP_KEY = "hard_drop";
	public static final String HOLD_KEY = "hold";
	public static final String PAUSE_KEY = "pause";
	public static final String EXIT_KEY = "exit";

	private static final int KEY_COUNT = 1024;

	private final Move rotateClockwise;
	private final Move rotateCounterclockwise;
	private final Move moveLeft;
	private final Move moveRight;
	private final Move softDrop;
	private final HardDrop hardDrop;
	private final Hold hold;
	private final Pause pause;
	private final Exit exit;

	private final Map<Action, String> actions;
	private final boolean[] pressedKeys;
	private final int[] frame;

	private int frameCounter;

	/**
	 * Creates a new keyboard.
	 *
	 * @param tetroshow Tetroshow on which to apply actions bound to key events
	 * @since 0.0.0
	 */
	public Keyboard(Tetroshow tetroshow) {

		super(tetroshow);

		pressedKeys = new boolean[KEY_COUNT];
		frame = new int[KEY_COUNT];

		moveLeft = new Move(tetroshow, LEFT);
		moveRight = new Move(tetroshow, RIGHT);
		rotateClockwise = new Move(tetroshow, CLOCKWISE);
		rotateCounterclockwise = new Move(tetroshow, COUNTERCLOCKWISE);
		softDrop = new Move(tetroshow, DOWN);
		hardDrop = new HardDrop(tetroshow);
		hold = new Hold(tetroshow);
		pause = new Pause(tetroshow);
		exit = new Exit(tetroshow);

		actions = Map.of(
				moveLeft, LEFT_KEY,
				moveRight, RIGHT_KEY,
				rotateClockwise, CLOCKWISE_KEY,
				rotateCounterclockwise, COUNTERCLOCKWISE_KEY,
				softDrop, SOFT_DROP_KEY,
				hardDrop, HARD_DROP_KEY,
				hold, HOLD_KEY,
				pause, PAUSE_KEY,
				exit, EXIT_KEY);

		reset();
	}

	@Override
	public void reset() {

		fill(pressedKeys, false);
		fill(frame, -1);
		frameCounter = 0;
	}

	/**
	 * @param action action
	 * @return key code associated with this action
	 * @since 0.0.0
	 */
	private int getKey(Action action) {

		var keyKey = actions.get(action);
		return settings.getKeys().get(keyKey);
	}

	/**
	 * @param action to test
	 * @param repeatable whether the given action is repeatable after a delay without releasing the key
	 * @return whether the action is effective
	 * @since 0.0.0
	 */
	private boolean isEffective(Action action, boolean repeatable) {

		var key = getKey(action);
		var effective = false;
		var pressed = pressedKeys[key];

		if (pressed) {

			var duration = frameCounter - frame[key];

			if (duration == 0) {

				effective = true;

			} else if (repeatable) {

				var level = tetroshow.getLevel();
				var das = settings.getGameplay().getDas(level);
				effective = duration >= das;
			}
		}

		return effective;
	}

	/**
	 * Executes this keyboard while paused.
	 *
	 * @since 0.0.0
	 */
	public void executePaused() {

		execute(pause, false);
		execute(exit, false);

		frameCounter++;
	}

	@Override
	public void execute() {

		executeRotate();
		executeTranslate();
		execute(softDrop, true);
		execute(hardDrop, false);
		execute(hold, false);
		execute(pause, false);
		execute(exit, false);

		frameCounter++;
	}

	/**
	 * Executes clockwise or counterclockwise rotation. If both keys were pressed in the last frame,
	 * clockwise has priority.
	 *
	 * @since 0.0.0
	 */
	private void executeRotate() {

		if (!execute(rotateClockwise, false)) {
			execute(rotateCounterclockwise, false);
		}
	}

	/**
	 * Executes left or right move. If both keys were pressed in the last frame, right move has priority.
	 *
	 * @since 0.0.0
	 */
	private void executeTranslate() {

		if (execute(moveRight, false)) {

			// we just pressed right key, we force left key release
			pressedKeys[getKey(moveLeft)] = false;

		} else if (execute(moveLeft, false)) {

			// we just pressed left key, we force right key release
			pressedKeys[getKey(moveRight)] = false;

		} else {

			if (isEffective(moveRight, true)) {

				if (isEffective(moveLeft, true)) {

					if (frame[getKey(moveLeft)] > frame[getKey(moveRight)]) {
						moveLeft.execute();
					} else {
						moveRight.execute();
					}

				} else {

					moveRight.execute();
				}

			} else if (isEffective(moveLeft, true)) {

				moveLeft.execute();
			}
		}
	}

	/**
	 * @param action action à exécuter
	 * @param repeatable indique si l'action est répétable lorsque la touche est maintenue enfoncée
	 * @return {@code true} si l'action a été exécutée, {@code false} sinon
	 * @since 0.0.0
	 */
	private boolean execute(Action action, boolean repeatable) {

		var effective = isEffective(action, repeatable);

		if (effective) {
			action.execute();
		}

		return effective;
	}

	@Override
	public void keyPressed(KeyEvent event) {

		var keyCode = event.getKeyCode();

		LOGGER.debug("key pressed: {}", keyCode);

		if (!pressedKeys[keyCode]) {

			pressedKeys[keyCode] = true;
			frame[keyCode] = frameCounter;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {

		var keyCode = event.getKeyCode();
		LOGGER.debug("key released: {}", keyCode);
		pressedKeys[keyCode] = false;
	}

	@Override
	public void keyTyped(KeyEvent event) {

	}
}
