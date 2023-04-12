package com.github.achaaab.tetroshow.model.piece;

import com.github.achaaab.tetroshow.audio.Audio;

import java.awt.Color;
import java.util.List;

import static com.github.achaaab.tetroshow.audio.AudioFactory.createAudio;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoI extends Tetromino {

	private static final Color COLOR = new Color(0, 159, 218);
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

		super(getRotations(COLOR, BLOCK_POSITIONS),
				ENTRY_COLUMN, SOUND_EFFECT, CLOCKWISE_WALL_KICKS, COUNTERCLOCKWISE_WALL_KICKS);
	}
}
