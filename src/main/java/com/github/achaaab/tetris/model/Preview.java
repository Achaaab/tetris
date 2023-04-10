package com.github.achaaab.tetris.model;

import com.github.achaaab.tetris.model.classic.TetrominoGenerator;
import com.github.achaaab.tetris.view.play.PreviewView;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Preview extends AbstractField {

	private final int size;
	private final PieceGenerator pieceGenerator;
	private final PreviewView presentation;

	/**
	 * @param size nombre de pièces prévisualisables
	 * @since 0.0.0
	 */
	public Preview(int size) {

		this.size = size;

		presentation = new PreviewView(this);
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
	 *
	 * @since 0.0.0
	 */
	public Piece getNextPiece() {

		var nextPiece = pieces.poll();
		addPiece();

		return nextPiece;
	}

	/**
	 * @return présentation du champ
	 * @since 0.0.0
	 */
	public PreviewView getPresentation() {
		return presentation;
	}
}
