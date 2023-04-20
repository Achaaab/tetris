package com.github.achaaab.tetroshow.model;

import com.github.achaaab.tetroshow.action.Clear;
import com.github.achaaab.tetroshow.action.Gravity;
import com.github.achaaab.tetroshow.action.Hold;
import com.github.achaaab.tetroshow.action.Keyboard;
import com.github.achaaab.tetroshow.action.Lock;
import com.github.achaaab.tetroshow.action.Move;
import com.github.achaaab.tetroshow.model.field.Playfield;
import com.github.achaaab.tetroshow.model.field.Preview;
import com.github.achaaab.tetroshow.model.field.Storage;
import com.github.achaaab.tetroshow.model.piece.Piece;
import com.github.achaaab.tetroshow.settings.Settings;
import org.slf4j.Logger;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Tetroshow implements GameComponent {

	private static final Logger LOGGER = getLogger(Tetroshow.class);

	private final Playfield playfield;
	private final Storage storage;
	private final Preview preview;
	private final Lock lock;
	private final Gravity gravity;
	private final Keyboard keyboard;
	private final Clear clear;

	private Piece fallingPiece;
	private Move initialRotation;
	private Hold initialHold;

	private double time;
	private boolean paused;
	private int level;
	private int holdCount;
	private int lineCount;
	private int dropBonus;
	private int fallingPieceAge;
	private int score;

	private Runnable exitListener;

	/**
	 * @since 0.0.0
	 */
	public Tetroshow() {

		playfield = new Playfield();
		storage = new Storage();
		preview = new Preview(3);
		keyboard = new Keyboard(this);
		lock = new Lock(this);
		clear = new Clear(this);
		gravity = new Gravity(this);

		reset();
	}

	/**
	 * @param exitListener
	 * @since 0.0.0
	 */
	public void setExitListener(Runnable exitListener) {
		this.exitListener = exitListener;
	}

	@Override
	public void reset() {

		LOGGER.info("reset");

		playfield.reset();
		storage.reset();
		preview.reset();
		keyboard.reset();
		gravity.reset();
		lock.reset();
		clear.reset();

		fallingPiece = null;
		initialRotation = null;
		initialHold = null;

		time = 0;
		paused = false;
		level = 0;
		holdCount = 0;
		lineCount = 0;
		dropBonus = 0;
		fallingPieceAge = 0;
		score = 0;
	}

	/**
	 * @param deltaTime
	 * @since 0.0.0
	 */
	public void update(double deltaTime) {

		if (paused) {

			keyboard.executePaused();

		} else {

			time += deltaTime;

			if (fallingPiece != null) {
				fallingPieceAge++;
			}

			keyboard.execute();
			gravity.execute();
			lock.execute();
			clear.execute();
		}
	}

	/**
	 * Inverts the pause status:
	 * <ul>
	 *     <li>If this Tetroshow is paused: unpauses it.</li>
	 *     <li>If this Tetroshow is not paused: pauses it.</li>
	 * </ul>
	 *
	 * @since 0.0.0
	 */
	public void pause() {
		paused = !paused;
	}

	/**
	 * stops this Tetroshow
	 *
	 * @since 0.0.0
	 */
	public void exit() {

		if (exitListener != null) {
			exitListener.run();
		}
	}

	/**
	 * @return time in seconds
	 * @since 0.0.0
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public Keyboard getKeyboard() {
		return keyboard;
	}

	/**
	 * Introduit une nouvelle pièce dans le jeu. La nouvelle pièce est prise dans la prévisualisation.
	 *
	 * @since 0.0.0
	 */
	public void newPiece() {

		var newPiece = preview.getNextPiece();
		setFallingPiece(newPiece);

		if (initialHold != null) {

			initialHold = null;
			initialRotation = null;
			hold();

		} else {

			if (initialRotation != null) {

				var direction = initialRotation.getDirection();

				if (playfield.isMovePossible(newPiece, direction)) {
					newPiece.move(direction);
				}

				initialRotation = null;
			}

			holdCount = 0;
		}

		gravity.execute();
	}

	/**
	 * Introduit une nouvelle pièce dans le jeu.
	 *
	 * @param fallingPiece pièce à introduire dans le jeu
	 * @since 0.0.0
	 */
	public void setFallingPiece(Piece fallingPiece) {

		this.fallingPiece = fallingPiece;

		var colonneApparition = fallingPiece.getEnteringColumn();
		fallingPiece.setX(colonneApparition);
		fallingPiece.setY(-2);

		playfield.add(fallingPiece);

		dropBonus = 0;
		fallingPieceAge = 0;
	}

	/**
	 * Augmente de 1 le bonus de descente.
	 *
	 * @since 0.0.0
	 */
	public void increaseDropBonus() {
		dropBonus++;
	}

	/**
	 * Locks the falling piece.
	 *
	 * @return whether the falling piece could be locked
	 * @since 0.0.0
	 */
	public boolean lockFallingPiece() {

		var locked = playfield.lock(fallingPiece);
		cancelLocking();

		fallingPiece = null;
		level++;

		return locked;
	}

	/**
	 * @since 0.0.0
	 */
	public void startLocking() {
		lock.start();
	}

	/**
	 * @since 0.0.0
	 */
	public void cancelLocking() {
		lock.cancel();
	}

	/**
	 * Place la pièce courante dans la réserve.
	 *
	 * @since 0.0.0
	 */
	public void hold() {

		if (holdCount < Settings.getDefaultInstance().getGameplay().getHoldLimit()) {

			holdCount++;

			playfield.remove(fallingPiece);

			storage.hold(fallingPiece).ifPresentOrElse(
					this::setFallingPiece,
					this::newPiece);

			cancelLocking();
		}
	}

	/**
	 * @return champ de jeu
	 * @since 0.0.0
	 */
	public Playfield getPlayfield() {
		return playfield;
	}

	/**
	 * @return level courant
	 * @since 0.0.0
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level level courant
	 * @since 0.0.0
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return score courant
	 * @since 0.0.0
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score score courant
	 * @since 0.0.0
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Positionne la rotation initiale pendant le délai entre 2 pièces.
	 *
	 * @param initialRotation rotation initiale
	 * @since 0.0.0
	 */
	public void setInitialRotation(Move initialRotation) {
		this.initialRotation = initialRotation;
	}

	/**
	 * Prévoit la réservation de la pièce suivante pendant le délai entre 2 pièces.
	 *
	 * @param holdInitiale réservation initiale
	 * @since 0.0.0
	 */
	public void setInitialHold(Hold holdInitiale) {
		this.initialHold = holdInitiale;
	}

	/**
	 * @return nombre de lignes complétées
	 * @since 0.0.0
	 */
	public int getLineCount() {
		return lineCount;
	}

	/**
	 * @param lineCount nombre de lignes complétées
	 * @since 0.0.0
	 */
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	/**
	 * @return bonus de descente forcée
	 * @since 0.0.0
	 */
	public int dropBonus() {
		return dropBonus;
	}

	/**
	 * @return durée de la pièce courante
	 * @since 0.0.0
	 */
	public int getFallingPieceAge() {
		return fallingPieceAge;
	}

	/**
	 * @return storage
	 * @since 0.0.0
	 */
	public Storage getStorage() {
		return storage;
	}

	/**
	 * @return prévisualisation
	 * @since 0.0.0
	 */
	public Preview getPreview() {
		return preview;
	}

	/**
	 * @return pièce courante
	 * @since 0.0.0
	 */
	public Optional<Piece> getFallingPiece() {
		return ofNullable(fallingPiece);
	}
}
