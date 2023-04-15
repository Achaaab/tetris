package com.github.achaaab.tetroshow.model.field;

import com.github.achaaab.tetroshow.model.piece.Piece;
import com.github.achaaab.tetroshow.model.piece.PieceGenerator;
import com.github.achaaab.tetroshow.model.piece.TetrominoGenerator;

/**
 * A preview is a field containing next pieces to come. Its size is configurable.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Preview extends AbstractField {

	private final int size;
	private final PieceGenerator pieceGenerator;

	/**
	 * Creates a new preview.
	 *
	 * @param size number of pieces this preview can hold
	 * @since 0.0.0
	 */
	public Preview(int size) {

		this.size = size;

		pieceGenerator = new TetrominoGenerator();
		fill();
	}

	/**
	 * Adds pieces in this preview until it is full.
	 *
	 * @since 0.0.0
	 */
	private void fill() {

		while (pieces.size() < size) {
			addPiece();
		}
	}

	/**
	 * Adds a piece in this preview.
	 *
	 * @since 0.0.0
	 */
	private void addPiece() {

		var piece = pieceGenerator.getPiece();
		pieces.offer(piece);
	}

	/**
	 * Removes and returns the next piece of this preview.
	 *
	 * @return next piece
	 * @since 0.0.0
	 */
	public Piece getNextPiece() {

		var nextPiece = pieces.poll();
		addPiece();

		return nextPiece;
	}

	@Override
	public void reset() {

		super.reset();

		pieceGenerator.reset();
		fill();
	}
}
