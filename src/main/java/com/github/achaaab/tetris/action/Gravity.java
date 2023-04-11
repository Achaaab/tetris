package com.github.achaaab.tetris.action;

import com.github.achaaab.tetris.model.Piece;
import com.github.achaaab.tetris.model.classic.Tetris;

import static com.github.achaaab.tetris.model.Direction.DOWN;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Gravity extends Action {

	public static final int ROW = 256;

	private int cumulatedForce;

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public Gravity(Tetris tetris) {

		super(tetris);

		reset();
	}

	@Override
	public void execute() {
		tetris.getFallingPiece().ifPresent(this::apply);
	}

	/**
	 * @param piece
	 * @since 0.0.0
	 */
	private void apply(Piece piece) {

		var levelSpeed = tetris.getLevelSpeed();
		cumulatedForce += configuration.getGravity(levelSpeed);

		while (cumulatedForce >= ROW && playfield.isMovePossible(piece, DOWN)) {

			piece.move(DOWN);
			cumulatedForce -= ROW;
		}

		if (cumulatedForce >= ROW) {

			tetris.startLocking();
			cumulatedForce = 0;
		}
	}

	@Override
	public void reset() {
		cumulatedForce = 0;
	}
}
