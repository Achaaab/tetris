package com.github.achaaab.tetris.model;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Storage extends Grid {

	private static final int WIDTH = 4;
	private static final int HEIGHT = 2;

	/**
	 * Crée une réserve pouvant contenir temporairement une pièce.
	 *
	 * @since 0.0.0
	 */
	public Storage() {
		super(WIDTH, HEIGHT);
	}

	/**
	 * Si la réserve est pleine, retourne la première pièce de la réserve.
	 *
	 * @param incomingPiece pièce à ajouter à la reserve
	 * @return piece éventuellement retirée de la reserve
	 * @since 0.0.0
	 */
	public Optional<Piece> hold(Piece incomingPiece) {

		var outgoingPiece = pieces.poll();
		pieces.offer(incomingPiece);

		incomingPiece.setX(0);
		incomingPiece.setY(0);
		incomingPiece.setRotation(0);

		return ofNullable(outgoingPiece);
	}
}
