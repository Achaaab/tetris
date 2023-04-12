package com.github.achaaab.tetroshow.model.piece;

import java.util.List;

/**
 * Une pièce est un ensemble de carrés. Elle est positionnée et orientée dans un champ.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface Piece {

	/**
	 * @return position de la pièce dans le champ (axe horizontal)
	 * @since 0.0.0
	 */
	int getX();

	/**
	 * @param x position de la pièce dans le champ (axe horizontal)
	 * @since 0.0.0
	 */
	void setX(int x);

	/**
	 * @return position de la pièce dans le champ de jeu (axe vertical)
	 * @since 0.0.0
	 */
	int getY();

	/**
	 * @param y position de la pièce dans le champ de jeu (axe vertical)
	 * @since 0.0.0
	 */
	void setY(int y);

	/**
	 * @param rotation index de la rotation voulue
	 * @since 0.0.0
	 */
	void setRotation(int rotation);

	/**
	 * @return liste des carrés constituant la pièce
	 * @since 0.0.0
	 */
	List<Block> getBlocks();

	/**
	 * Move this piece once in the given direction.
	 *
	 * @param direction direction du déplacement
	 * @since 0.0.0
	 */
	default void move(Direction direction) {
		move(direction, 1);
	}

	/**
	 * Déplace la pièce dans le champ, ne tient pas compte des obstacles ni des limites du champ :
	 * tous les déplacements sont autorisés.
	 *
	 * @param direction direction du déplacement
	 * @param distance nombre de répétitions du déplacement
	 * @since 0.0.0
	 */
	void move(Direction direction, int distance);

	/**
	 * @return colonne d'apparition de la pièce
	 * @since 0.0.0
	 */
	int getEnteringColumn();

	/**
	 * @return wall kicks possibles lors d'une rotation vers la droite
	 * @since 0.0.0
	 */
	List<Direction> getClockwiseWallKicks();

	/**
	 * @return wall kicks possibles lors d'une rotation vers la gauche
	 * @since 0.0.0
	 */
	List<Direction> getCounterClockwiseWallKicks();

	/**
	 * Joue le son d'apparition de la pièce.
	 *
	 * @since 0.0.0
	 */
	void playEnterSound();

	/**
	 * @return clone de la pièce
	 */
	Piece copy();
}
