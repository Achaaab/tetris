package com.github.achaaab.tetris.action;

import com.github.achaaab.tetris.model.Piece;
import com.github.achaaab.tetris.model.classic.Tetris;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Hold extends Action {

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public Hold(Tetris tetris) {
		super(tetris);
	}

	@Override
	public void execute() {

		tetris.getFallingPiece().ifPresentOrElse(
				this::apply,
				this::apply);
	}

	/**
	 * Applies this hold when there is not currently falling piece.
	 * It allows to pre-move hold (Initial Hold System).
	 *
	 * @since 0.0.0
	 */
	public void apply() {
		tetris.setInitialHold(this);
	}

	/**
	 * Holds the given piece into the storage.
	 *
	 * @param piece piece to hold
	 * @since 0.0.0
	 */
	private void apply(Piece piece) {
		tetris.hold();
	}
}
