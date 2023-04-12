package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.model.piece.Piece;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Hold extends AbstractAction {

	/**
	 * @param tetroshow
	 * @since 0.0.0
	 */
	public Hold(Tetroshow tetroshow) {
		super(tetroshow);
	}

	@Override
	public void execute() {

		tetroshow.getFallingPiece().ifPresentOrElse(
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
		tetroshow.setInitialHold(this);
	}

	/**
	 * Holds the given piece into the storage.
	 *
	 * @param piece piece to hold
	 * @since 0.0.0
	 */
	private void apply(Piece piece) {
		tetroshow.hold();
	}
}
