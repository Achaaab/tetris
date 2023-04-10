package com.github.achaaab.tetris.model.classic;

import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.model.Direction;

import java.util.List;

import static com.github.achaaab.tetris.audio.AudioFactory.createAudio;
import static java.awt.Color.CYAN;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoI extends Tetromino {

	private static final Audio SOUND_EFFECT = createAudio("audio/effect/tetromino_i.wav");

	private static final int[][] BLOCK_POSITIONS = {
			{ 4, 5, 6, 7 },
			{ 2, 6, 10, 14 },
			{ 8, 9, 10, 11 },
			{ 1, 5, 9, 13 } };

	private static final int ENTRY_COLUMN = 3;
	private static final char LETTER = 'i';

	private static final List<List<Direction>> CLOCKWISE_WALL_KICKS = getClockwiseWallKicks(LETTER);
	private static final List<List<Direction>> COUNTERCLOCKWISE_WALL_KICKS = getCounterclockwiseWallKicks(LETTER);

	/**
	 * @since 0.0.0
	 */
	public TetrominoI() {

		super(getRotations(CYAN, BLOCK_POSITIONS),
				ENTRY_COLUMN, SOUND_EFFECT, CLOCKWISE_WALL_KICKS, COUNTERCLOCKWISE_WALL_KICKS);
	}
}
