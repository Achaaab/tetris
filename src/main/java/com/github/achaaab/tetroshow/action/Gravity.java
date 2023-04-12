package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.model.piece.Piece;

import static com.github.achaaab.tetroshow.model.piece.Direction.DOWN;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Gravity extends AbstractAction {

	public static final int ROW = 256;

	private int cumulatedForce;

	/**
	 * @param tetroshow
	 * @since 0.0.0
	 */
	public Gravity(Tetroshow tetroshow) {

		super(tetroshow);

		reset();
	}

	@Override
	public void execute() {
		tetroshow.getFallingPiece().ifPresent(this::apply);
	}

	/**
	 * @param piece
	 * @since 0.0.0
	 */
	private void apply(Piece piece) {

		var levelSpeed = tetroshow.getLevelSpeed();
		cumulatedForce += configuration.getGravity(levelSpeed);

		while (cumulatedForce >= ROW && playfield.isMovePossible(piece, DOWN)) {

			piece.move(DOWN);
			cumulatedForce -= ROW;
		}

		if (cumulatedForce >= ROW) {

			tetroshow.startLocking();
			cumulatedForce = 0;
		}
	}

	@Override
	public void reset() {
		cumulatedForce = 0;
	}
}
