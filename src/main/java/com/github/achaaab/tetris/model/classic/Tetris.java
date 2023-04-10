package com.github.achaaab.tetris.model.classic;

import com.github.achaaab.tetris.action.Gravity;
import com.github.achaaab.tetris.action.Hold;
import com.github.achaaab.tetris.action.Keyboard;
import com.github.achaaab.tetris.action.Lock;
import com.github.achaaab.tetris.action.Move;
import com.github.achaaab.tetris.configuration.Configuration;
import com.github.achaaab.tetris.game.AbstractGame;
import com.github.achaaab.tetris.model.Clear;
import com.github.achaaab.tetris.model.Piece;
import com.github.achaaab.tetris.model.Preview;
import com.github.achaaab.tetris.model.Storage;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Tetris extends AbstractGame {

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

	private int level;
	private int bonusNiveau;
	private int holdCount;
	private int lineCount;
	private int bonusDescente;
	private int fallingPieceAge;
	private int score;

	/**
	 * @since 0.0.0
	 */
	public Tetris() {

		playfield = new Playfield();
		storage = new Storage();
		preview = new Preview(3);
		keyboard = new Keyboard(this);
		lock = new Lock(this);
		clear = new Clear(this);
		gravity = new Gravity(this);

		initialRotation = null;
		initialHold = null;

		level = 0;
		bonusNiveau = 0;
		score = 0;
		lineCount = 0;
		holdCount = 0;
	}

	/**
	 * @return tetris configuration
	 * @since 0.0.0
	 */
	public Configuration getConfiguration() {
		return Configuration.INSTANCE;
	}

	@Override
	public void update() {

		if (paused) {

			keyboard.executePaused();

		} else {

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

		var nouvellePiece = preview.getNextPiece();
		setFallingPiece(nouvellePiece);

		if (initialHold != null) {

			initialHold = null;
			initialRotation = null;
			hold();

		} else {

			if (initialRotation != null) {

				var direction = initialRotation.getDirection();

				if (playfield.isMovePossible(nouvellePiece, direction)) {
					nouvellePiece.move(direction);
				}

				initialRotation = null;
			}

			holdCount = 0;
		}

		nouvellePiece.playEnterSound();
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

		bonusDescente = 0;
		fallingPieceAge = 0;
	}

	/**
	 * Augmente de 1 le bonus de descente.
	 *
	 * @since 0.0.0
	 */
	public void increaseDropBonus() {
		bonusDescente++;
	}

	/**
	 * Locks the falling piece.
	 *
	 * @return whether the falling piece could be locked
	 * @since 0.0.0
	 */
	public boolean lockFallingPiece() {

		var locked = playfield.lock(fallingPiece);

		fallingPiece = null;
		cancelLocking();

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

		if (holdCount < Configuration.INSTANCE.getHoldLimit()) {

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
	 * @return level courant + bonus de vitesse obtenu en réalisant des sections cool
	 * @since 0.0.0
	 */
	public int getLevelSpeed() {
		return level + bonusNiveau;
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
		return bonusDescente;
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

	/**
	 * @return bonus level
	 * @since 0.0.0
	 */
	public int getBonusNiveau() {
		return bonusNiveau;
	}

	/**
	 * @param bonusNiveau bonus level
	 * @since 0.0.0
	 */
	public void setBonusNiveau(int bonusNiveau) {
		this.bonusNiveau = bonusNiveau;
	}
}
