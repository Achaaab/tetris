package com.github.achaaab.tetroshow.model.field;

import com.github.achaaab.tetroshow.model.GamePart;
import com.github.achaaab.tetroshow.model.piece.Piece;

import java.util.Queue;

/**
 * A field is a space containing pieces.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface Field extends GamePart {

	/**
	 * Ajoute une pièce dans le champ.
	 *
	 * @param piece nouvelle pièce
	 * @since 0.0.0
	 */
	void add(Piece piece);

	/**
	 * Supprime une pièce non scellée du champ.
	 *
	 * @param piece pièce à supprimer
	 * @since 0.0.0
	 */
	void remove(Piece piece);

	/**
	 * @return file des pièces du champ
	 * @since 0.0.0
	 */
	Queue<Piece> getPieces();
}
