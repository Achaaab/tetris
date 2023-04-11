package com.github.achaaab.tetris.model;

import java.util.Queue;

/**
 * Un champ est un espace dans lequel on trouve des pièces.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface Field {

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

	/**
	 * Clears this field.
	 *
	 * @since 0.0.0
	 */
	void clear();
}
