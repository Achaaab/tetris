package com.github.achaaab.tetris.model.classic;

import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.model.Direction;

import java.util.List;

import static com.github.achaaab.tetris.audio.AudioFactory.createAudio;
import static java.awt.Color.MAGENTA;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoT extends Tetromino {

	private static final Audio SOUND_EFFECT = createAudio("audio/effect/tetromino_t.wav");

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
	 * @since 0.0.0
	 */
	public TetrominoT() {

		super(getRotations(MAGENTA, BLOCK_POSITIONS),
				ENTRY_COLUMN, SOUND_EFFECT, CLOCKWISE_WALL_KICKS, COUNTERCLOCKWISE_WALL_KICKS);
	}
}
