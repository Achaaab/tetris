package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.settings.Settings;
import org.slf4j.Logger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
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

	private static final int KEY_COUNT = 1024;

	private final Move rotateClockwise;
	private final Move rotateCounterclockwise;
	private final Move shiftLeft;
	private final Move shiftRight;
	private final Move softDrop;
	private final HardDrop hardDrop;
	private final Hold hold;
	private final Pause pause;
	private final Exit exit;

	private final boolean[] pressedKeys;
	private final int[] frame;
	private final Map<Action, Integer> keyMapping;

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
		keyMapping = new HashMap<>();

		rotateClockwise = new Move(tetroshow, CLOCKWISE);
		rotateCounterclockwise = new Move(tetroshow, COUNTERCLOCKWISE);
		shiftLeft = new Move(tetroshow, LEFT);
		shiftRight = new Move(tetroshow, RIGHT);
		softDrop = new Move(tetroshow, DOWN);
		hardDrop = new HardDrop(tetroshow);
		hold = new Hold(tetroshow);
		pause = new Pause(tetroshow);
		exit = new Exit(tetroshow);

		var keys = Settings.getDefaultInstance().getKeys();

		var clockwiseKey = keys.get("clockwise");
		var counterclockwiseKey = keys.get("counterclockwise");
		var leftKey = keys.get("left");
		var rightKey = keys.get("right");
		var softDropKey = keys.get("soft_drop");
		var hardDropKey = keys.get("hard_drop");
		var holdKey = keys.get("hold");
		var pauseKey = keys.get("pause");
		var exitKey = keys.get("exit");

		keyMapping.put(rotateClockwise, clockwiseKey);
		keyMapping.put(rotateCounterclockwise, counterclockwiseKey);
		keyMapping.put(shiftLeft, leftKey);
		keyMapping.put(shiftRight, rightKey);
		keyMapping.put(softDrop, softDropKey);
		keyMapping.put(hardDrop, hardDropKey);
		keyMapping.put(hold, holdKey);
		keyMapping.put(pause, pauseKey);
		keyMapping.put(exit, exitKey);

		reset();
	}

	@Override
	public void reset() {

		fill(pressedKeys, false);
		fill(frame, -1);
		frameCounter = 0;
	}

	/**
	 * @param key code de la touche à tester
	 * @param repeatable indique que la touche est répétable passé un certain délai
	 * @return {@code true} si la touche est effective ou {@code false} sinon
	 * @since 0.0.0
	 */
	private boolean isEffective(int key, boolean repeatable) {

		boolean effective;

		var duration = frameCounter - frame[key];

		if (duration == 0) {

			effective = true;

		} else if (repeatable) {

			var keyPressed = pressedKeys[key];
			var level = tetroshow.getLevel();
			var delayAuto = Settings.getDefaultInstance().getDas(level);
			var autoRepeat = duration >= delayAuto;
			effective = keyPressed && autoRepeat;

		} else {

			effective = false;
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

		if (!execute(rotateClockwise, false)) {
			execute(rotateCounterclockwise, false);
		}

		var shiftRightDone = execute(shiftRight, false);
		var shiftLeftDone = !shiftRightDone && execute(shiftLeft, false);

		if (!shiftRightDone && !shiftLeftDone) {

			if (!execute(shiftRight, true)) {
				execute(shiftLeft, true);
			}
		}

		execute(softDrop, true);
		execute(hardDrop, false);
		execute(hold, false);
		execute(pause, false);
		execute(exit, false);

		frameCounter++;
	}

	/**
	 * @param action action à exécuter
	 * @param repeatable indique si l'action est répétable lorsque la touche est maintenue enfoncée
	 * @return {@code true} si l'action a été exécutée, {@code false} sinon
	 * @since 0.0.0
	 */
	private boolean execute(Action action, boolean repeatable) {

		var key = keyMapping.get(action);
		var effective = isEffective(key, repeatable);

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
		event.consume();
	}
}
