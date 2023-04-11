package com.github.achaaab.tetris.model;

import com.github.achaaab.tetris.model.classic.TetrominoGenerator;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Preview extends AbstractField {

	private final int size;
	private final PieceGenerator pieceGenerator;

	/**
	 * @param size nombre de pièces prévisualisables
	 * @since 0.0.0
	 */
	public Preview(int size) {

		this.size = size;

		pieceGenerator = new TetrominoGenerator();
		fill();
	}

	/**
	 * Ajoute des pièces dans la prévisualisation jusqu'à ce que la taille soit atteinte.
	 *
	 * @since 0.0.0
	 */
	private void fill() {

		while (pieces.size() < size) {
			addPiece();
		}
	}

	/**
	 * Ajoute une pièce.
	 *
	 * @since 0.0.0
	 */
	private void addPiece() {

		var piece = pieceGenerator.getPiece();
		pieces.offer(piece);
	}

	/**
	 * @return pièce suivante
	 * @since 0.0.0
	 */
	public Piece getNextPiece() {

		var nextPiece = pieces.poll();
		addPiece();

		return nextPiece;
	}

	@Override
	public void clear() {

		super.clear();

		fill();
	}
}
