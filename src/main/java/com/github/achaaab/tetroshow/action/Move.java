package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.audio.SoundEffect;
import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.model.piece.Direction;
import com.github.achaaab.tetroshow.model.piece.Piece;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.model.piece.Direction.CLOCKWISE;
import static com.github.achaaab.tetroshow.model.piece.Direction.COUNTERCLOCKWISE;
import static com.github.achaaab.tetroshow.model.piece.Direction.DOWN;

/**
 * piece moving action
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Move extends AbstractAction {

	private final Direction direction;
	private final SoundEffect soundEffect;

	/**
	 * Creates a new move action.
	 *
	 * @param tetroshow Tetroshow in which to apply this move
	 * @param direction move direction
	 * @since 0.0.0
	 */
	public Move(Tetroshow tetroshow, Direction direction) {

		super(tetroshow);

		this.direction = direction;

		soundEffect = getSoundEffect("audio/effect/move.wav", 6);
	}

	@Override
	public final void execute() {

		tetroshow.getFallingPiece().ifPresentOrElse(
				this::apply,
				this::apply);
	}

	/**
	 * Applies this move when there is not currently falling piece.
	 * It allows to pre-move rotations (Initial Rotation System).
	 *
	 * @since 0.0.0
	 */
	private void apply() {

		if (direction == CLOCKWISE || direction == COUNTERCLOCKWISE) {
			tetroshow.setInitialRotation(this);
		}
	}

	/**
	 * Applies this move on the given piece.
	 *
	 * @param piece piece to move
	 * @since 0.0.0
	 */
	private void apply(Piece piece) {

		var movePossible = playfield.isMovePossible(piece, direction);

		if (movePossible) {

			piece.move(direction);
			soundEffect.play();

			if (direction == DOWN) {
				tetroshow.increaseDropBonus();
			}

			tetroshow.cancelLocking();

		} else if (direction == DOWN) {

			tetroshow.startLocking();

		} else if (direction == CLOCKWISE || direction == COUNTERCLOCKWISE) {

			/*
			 * On ne peut pas effectuer la rotation de manière conventionnelle. On peut exécuter des "wall kicks"
			 * qui nous permettent de translater la piece automatiquement pour rester dans la zone de jeu ou pour
			 * éviter la collision avec d'autres carrés.
			 */

			var wallKicks = direction == CLOCKWISE ?
					piece.getClockwiseWallKicks() :
					piece.getCounterClockwiseWallKicks();

			var wallKickIterator = wallKicks.iterator();

			Direction correctedDirection = null;

			while (!movePossible && wallKickIterator.hasNext()) {

				var wallKick = wallKickIterator.next();
				correctedDirection = direction.add(wallKick);
				movePossible = playfield.isMovePossible(piece, correctedDirection);
			}

			if (movePossible) {

				piece.move(correctedDirection);
				tetroshow.cancelLocking();
			}
		}
	}

	/**
	 * @return direction
	 * @since 0.0.0
	 */
	public Direction getDirection() {
		return direction;
	}
}
