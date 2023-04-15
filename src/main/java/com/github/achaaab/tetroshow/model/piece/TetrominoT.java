package com.github.achaaab.tetroshow.model.piece;

import java.awt.Color;
import java.util.List;

/**
 * T-shape tetromino
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoT extends Tetromino {

	private static final Color COLOR = new Color(149, 45, 152);

	private static final int[][] BLOCK_POSITIONS = {
			{ 1, 4, 5, 6 },
			{ 1, 5, 6, 9 },
			{ 4, 5, 6, 9 },
			{ 1, 4, 5, 9 } };

	private static final int ENTRY_COLUMN = 3;
	private static final char LETTER = 't';

	private static final List<List<Direction>> CLOCKWISE_WALL_KICKS = getClockwiseWallKicks(LETTER);
	private static final List<List<Direction>> COUNTERCLOCKWISE_WALL_KICKS = getCounterclockwiseWallKicks(LETTER);

	/**
	 * Creates a new T-shape tetromino.
	 *
	 * @since 0.0.0
	 */
	public TetrominoT() {

		super(getRotations(COLOR, BLOCK_POSITIONS),
				ENTRY_COLUMN, CLOCKWISE_WALL_KICKS, COUNTERCLOCKWISE_WALL_KICKS);
	}
}
