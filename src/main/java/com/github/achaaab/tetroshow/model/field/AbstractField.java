package com.github.achaaab.tetroshow.model.field;

import com.github.achaaab.tetroshow.model.piece.Piece;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import static java.util.Optional.ofNullable;

/**
 * Implémente le stockage des pièces.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class AbstractField implements Field {

	protected final Queue<Piece> pieces;

	/**
	 * @since 0.0.0
	 */
	public AbstractField() {
		pieces = new LinkedList<>();
	}

	@Override
	public void add(Piece piece) {
		pieces.add(piece);
	}

	@Override
	public void remove(Piece piece) {
		pieces.remove(piece);
	}

	@Override
	public Queue<Piece> getPieces() {
		return pieces;
	}

	/**
	 * @return active piece
	 * @since 0.0.0
	 */
	public Optional<Piece> getActivePiece() {
		return ofNullable(pieces.peek());
	}

	@Override
	public void reset() {
		pieces.clear();
	}
}
