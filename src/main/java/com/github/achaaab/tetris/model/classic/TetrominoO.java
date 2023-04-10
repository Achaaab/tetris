package com.github.achaaab.tetris.model.classic;

import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.model.Direction;

import java.util.List;

import static com.github.achaaab.tetris.audio.AudioFactory.createAudio;
import static java.awt.Color.YELLOW;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoO extends Tetromino {

	private static final Audio SOUND_EFFECT = createAudio("audio/effect/tetromino_o.wav");

	private static final int[][] BLOCK_POSITIONS = {
			{ 1, 2, 5, 6 },
			{ 1, 2, 5, 6 },
			{ 1, 2, 5, 6 },
			{ 1, 2, 5, 6 } };

	private static final int ENTRY_COLUMN = 3;
	private static final char LETTER = 'o';

	private static final List<List<Direction>> CLOCKWISE_WALL_KICKS = getClockwiseWallKicks(LETTER);
	private static final List<List<Direction>> COUNTERCLOCKWISE_WALL_KICKS = getCounterclockwiseWallKicks(LETTER);

	/**
	 * @since 0.0.0
	 */
	public TetrominoO() {

		super(getRotations(YELLOW, BLOCK_POSITIONS),
				ENTRY_COLUMN, SOUND_EFFECT, CLOCKWISE_WALL_KICKS, COUNTERCLOCKWISE_WALL_KICKS);
	}
}
