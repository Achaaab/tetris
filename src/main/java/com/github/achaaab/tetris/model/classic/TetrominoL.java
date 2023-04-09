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

	private static final int[][] INDICES_CARRES = {
			{ 2, 4, 5, 6 },
			{ 1, 5, 9, 10 },
			{ 4, 5, 6, 8 },
			{ 0, 1, 5, 9 } };

	private static final int COLONNE_APPARITION = 3;
	private static final char LETTRE = 'l';

	private static final List<List<Direction>> WALL_KICKS_DROITE = getWallKicksDroite(LETTRE);
	private static final List<List<Direction>> WALL_KICKS_GAUCHE = getWallKicksGauche(LETTRE);

	/**
	 * @since 0.0.0
	 */
	public TetrominoL() {

		super(getRotations(ORANGE, INDICES_CARRES),
				COLONNE_APPARITION, WALL_KICKS_DROITE, WALL_KICKS_GAUCHE, SOUND_EFFECT);
	}
}