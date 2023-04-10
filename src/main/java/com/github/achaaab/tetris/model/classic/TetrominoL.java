package com.github.achaaab.tetris.model.classic;

import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.model.Direction;

import java.util.List;

import static com.github.achaaab.tetris.audio.AudioFactory.createAudio;
import static java.awt.Color.ORANGE;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoL extends Tetromino {

	private static final Audio SOUND_EFFECT = createAudio("audio/effect/tetromino_l.wav");

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

		super(getRotations(ORANGE, BLOCK_POSITIONS),
				ENTRY_COLUMN, SOUND_EFFECT, CLOCKWISE_WALL_KICKS, COUNTERCLOCKWISE_WALL_KICKS);
	}
}