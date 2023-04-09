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

	private static final int[][] INDICES_CARRES = {
			{ 4, 5, 6, 7 },
			{ 2, 6, 10, 14 },
			{ 8, 9, 10, 11 },
			{ 1, 5, 9, 13 } };

	private static final int COLONNE_APPARITION = 3;
	private static final char LETTRE = 'i';

	private static final List<List<Direction>> WALL_KICKS_DROITE = getWallKicksDroite(LETTRE);
	private static final List<List<Direction>> WALL_KICKS_GAUCHE = getWallKicksGauche(LETTRE);

	/**
	 * @since 0.0.0
	 */
	public TetrominoI() {

		super(getRotations(CYAN, INDICES_CARRES),
				COLONNE_APPARITION, WALL_KICKS_DROITE, WALL_KICKS_GAUCHE, SOUND_EFFECT);
	}
}
