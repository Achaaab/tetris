package com.github.achaaab.tetroshow.model.piece;

import java.awt.Color;
import java.util.List;

/**
 * L-shape tetromino
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoL extends Tetromino {

	private static final Color COLOR = new Color(255, 121, 0);

	private static final int[][] BLOCK_POSITIONS = {
			{ 2, 4, 5, 6 },
			{ 1, 5, 9, 10 },
			{ 4, 5, 6, 8 },
			{ 0, 1, 5, 9 } };

	private static final int ENTRY_COLUMN = 3;
	private static final char LETTER = 'l';

	private static final List<List<Direction>> CLOCKWISE_WALL_KICKS = getClockwiseWallKicks(LETTER);
	private static final List<List<Direction>> COUNTERCLOCKWISE_WALL_KICKS = getCounterclockwiseWallKicks(LETTER);

	/**
	 * @since 0.0.0
	 */
	public TetrominoL() {

		super(getRotations(COLOR, BLOCK_POSITIONS),
				ENTRY_COLUMN, CLOCKWISE_WALL_KICKS, COUNTERCLOCKWISE_WALL_KICKS);
	}
}