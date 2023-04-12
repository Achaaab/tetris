package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.model.piece.Direction;
import com.github.achaaab.tetroshow.model.piece.Piece;

import static com.github.achaaab.tetroshow.model.piece.Direction.CLOCKWISE;
import static com.github.achaaab.tetroshow.model.piece.Direction.COUNTERCLOCKWISE;
import static com.github.achaaab.tetroshow.model.piece.Direction.DOWN;

/**
 * Action de déplacement d'un tetromino.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Move extends AbstractAction {

	private final Direction direction;
	private final int distance;

	/**
	 * @param tetroshow
	 * @param direction
	 * @param distance
	 * @since 0.0.0
	 */
	public Move(Tetroshow tetroshow, Direction direction, int distance) {

		super(tetroshow);

		this.direction = direction;
		this.distance = distance;
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

			piece.move(direction, distance);

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
				correctedDirection = direction.combine(wallKick);
				movePossible = playfield.isMovePossible(piece, correctedDirection);
			}

			if (movePossible) {

				piece.move(correctedDirection, distance);
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

	/**
	 * @return distance
	 * @since 0.0.0
	 */
	public int getDistance() {
		return distance;
	}
}
