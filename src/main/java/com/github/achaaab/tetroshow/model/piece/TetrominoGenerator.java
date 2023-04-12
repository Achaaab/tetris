package com.github.achaaab.tetroshow.model.piece;

import java.util.LinkedList;

import static java.util.Collections.shuffle;

/**
 * Le générateur de tetrominos dispose les 7 pièces dans un sac, le mélange et distribue les pièces à la demande. Quand
 * le sac est vide, un nouveau cycle commence. Cette façon de faire permet de limiter la malchance et d'avoir une
 * distribution équitable des tetrominos.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoGenerator implements PieceGenerator {

	private final LinkedList<Piece> bag;

	/**
	 * Crée un nouveau générateur de Tetrominos.
	 *
	 * @since 0.0.0
	 */
	public TetrominoGenerator() {
		bag = new LinkedList<>();
	}

	/**
	 * Remplit le sac avec un exemplaire de chacun des 7 Tetrominos puis mélange le sac.
	 *
	 * @since 0.0.0
	 */
	private void fillBag() {

		bag.add(new TetrominoI());
		bag.add(new TetrominoJ());
		bag.add(new TetrominoL());
		bag.add(new TetrominoO());
		bag.add(new TetrominoS());
		bag.add(new TetrominoT());
		bag.add(new TetrominoZ());

		shuffle(bag);
	}

	@Override
	public Piece getPiece() {

		if (bag.isEmpty()) {
			fillBag();
		}

		return bag.poll();
	}

	@Override
	public void reset() {

		bag.clear();
		fillBag();
	}
}
