package com.github.achaaab.tetris.model.classic;

import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.model.Direction;

import java.util.List;

import static com.github.achaaab.tetris.audio.AudioFactory.createAudio;
import static java.awt.Color.GREEN;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoS extends Tetromino {

	private static final Audio SOUND_EFFECT = createAudio("audio/effect/tetromino_s.wav");

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
	 * @since 0.0.0
	 */
	public TetrominoS() {

		super(getRotations(GREEN, BLOCK_POSITIONS),
				ENTRY_COLUMN, SOUND_EFFECT, CLOCKWISE_WALL_KICKS, COUNTERCLOCKWISE_WALL_KICKS);
	}
}
