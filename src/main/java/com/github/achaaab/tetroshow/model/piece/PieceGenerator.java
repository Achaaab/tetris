package com.github.achaaab.tetroshow.model.piece;

/**
 * Un générateur de pièces permet de générer des pièces de manière aléatoire ou non.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface PieceGenerator {

	/**
	 * @return pièce générée
	 * @since 0.0.0
	 */
	Piece getPiece();

	/**
	 * Resets this piece generator.
	 *
	 * @since 0.0.0
	 */
	void reset();
}
