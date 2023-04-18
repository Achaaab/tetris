package com.github.achaaab.tetroshow.model.field;

import com.github.achaaab.tetroshow.model.piece.Piece;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * A storage is a grid that can hold a single piece.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Storage extends Grid {

	public static final int WIDTH = 4;
	public static final int HEIGHT = 2;

	/**
	 * Creates a new storage.
	 *
	 * @since 0.0.0
	 */
	public Storage() {
		super(WIDTH, HEIGHT);
	}

	/**
	 * Holds a piece in this storage, resetting its rotation.
	 *
	 * @param incomingPiece piece to hold
	 * @return piece that was previously hold in this storage
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
