package com.github.achaaab.tetroshow.model.piece;

import java.awt.Color;
import java.util.List;

/**
 * S-shape tetromino
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoS extends Tetromino {

	private static final Color COLOR = new Color(105, 190, 40);

	private static final int[][] BLOCK_POSITIONS = {
			{ 1, 2, 4, 5 },
			{ 1, 5, 6, 10 },
			{ 5, 6, 8, 9 },
			{ 0, 4, 5, 9 } };

	private static final int ENTRY_COLUMN = 3;
	private static final char LETTER = 's';

	private static final List<List<Direction>> CLOCKWISE_WALL_KICKS = getClockwiseWallKicks(LETTER);
	private static final List<List<Direction>> COUNTERCLOCKWISE_WALL_KICKS = getCounterclockwiseWallKicks(LETTER);

	/**
	 * Creates a new S-shape tetromino.
	 *
	 * @since 0.0.0
	 */
	public TetrominoS() {

		super(getRotations(COLOR, BLOCK_POSITIONS),
				ENTRY_COLUMN, CLOCKWISE_WALL_KICKS, COUNTERCLOCKWISE_WALL_KICKS);
	}
}
