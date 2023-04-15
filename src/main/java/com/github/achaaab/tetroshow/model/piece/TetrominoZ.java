package com.github.achaaab.tetroshow.model.piece;

import java.awt.Color;
import java.util.List;

/**
 * Z-shape tetromino
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoZ extends Tetromino {

	private static final Color COLOR = new Color(237, 41, 57);

	private static final int[][] BLOCK_POSITIONS = {
			{ 0, 1, 5, 6 },
			{ 2, 5, 6, 9 },
			{ 4, 5, 9, 10 },
			{ 1, 4, 5, 8 } };

	private static final int ENTRY_COLUMN = 3;
	private static final char LETTER = 'z';

	private static final List<List<Direction>> CLOCKWISE_WALL_KICKS = getClockwiseWallKicks(LETTER);
	private static final List<List<Direction>> COUNTERCLOCKWISE_WALL_KICKS = getCounterclockwiseWallKicks(LETTER);

	/**
	 * Creates a new Z-shape tetromino.
	 *
	 * @since 0.0.0
	 */
	public TetrominoZ() {

		super(getRotations(COLOR, BLOCK_POSITIONS),
				ENTRY_COLUMN, CLOCKWISE_WALL_KICKS, COUNTERCLOCKWISE_WALL_KICKS);
	}
}
