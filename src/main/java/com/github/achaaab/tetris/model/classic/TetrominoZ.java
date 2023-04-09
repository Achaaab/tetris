package com.github.achaaab.tetris.model.classic;

import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.model.Direction;

import java.util.List;

import static com.github.achaaab.tetris.audio.AudioFactory.createAudio;
import static java.awt.Color.RED;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrominoZ extends Tetromino {

	private static final Audio SOUND_EFFECT = createAudio("audio/effect/tetromino_z.wav");

	private static final int[][] INDICES_CARRES = {
			{ 0, 1, 5, 6 },
			{ 2, 5, 6, 9 },
			{ 4, 5, 9, 10 },
			{ 1, 4, 5, 8 } };

	private static final int COLONNE_APPARITION = 3;
	private static final char LETTRE = 'z';

	private static final List<List<Direction>> WALL_KICKS_DROITE = getWallKicksDroite(LETTRE);
	private static final List<List<Direction>> WALL_KICKS_GAUCHE = getWallKicksGauche(LETTRE);

	/**
	 * @since 0.0.0
	 */
	public TetrominoZ() {

		super(getRotations(RED, INDICES_CARRES),
				COLONNE_APPARITION, WALL_KICKS_DROITE, WALL_KICKS_GAUCHE, SOUND_EFFECT);
	}
}
