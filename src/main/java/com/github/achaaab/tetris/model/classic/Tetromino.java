package com.github.achaaab.tetris.model.classic;

import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.model.Block;
import com.github.achaaab.tetris.model.Direction;
import com.github.achaaab.tetris.model.AbstractPiece;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;
import static java.util.ResourceBundle.getBundle;

/**
 * Un tetromino est une pièce classique composée de 4 carrés.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Tetromino extends AbstractPiece {

	protected static final ResourceBundle CONFIGURATION_WALL_KICKS = getBundle("wall_kicks");

	/**
	 * @param color
	 * @param rotations tableau contenant les différentes rotations d'un tetromino
	 * @return liste des rotations
	 * @since 0.0.0
	 */
	protected static List<List<Block>> getRotations(Color color, int[][] rotations) {

		int nombreRotations = rotations.length;

		var listeRotations = new ArrayList<List<Block>>(nombreRotations);

		for (var rotationTetromino : rotations) {
			listeRotations.add(getBlocks(color, rotationTetromino));
		}

		return listeRotations;
	}

	/**
	 * Permet de convertir un tableau de positions de carrés en une liste de carrés.
	 *
	 * @param color
	 * @param rotationTetromino tableau contenant la position des carrés d'un tetromino
	 * @return liste des carrés du tetromino
	 * @since 0.0.0
	 */
	private static List<Block> getBlocks(Color color, int[] rotationTetromino) {

		var blockCount = rotationTetromino.length;
		var blocks = new ArrayList<Block>(blockCount);

		for (var position : rotationTetromino) {
			blocks.add(new Block(color, position % 4, position / 4));
		}

		return blocks;
	}

	/**
	 * @param lettre
	 * @return
	 * @since 0.0.0
	 */
	protected static List<List<Direction>> getWallKicksDroite(char lettre) {
		return getWallKicks(lettre, "droite");
	}

	/**
	 * @param lettre
	 * @return
	 * @since 0.0.0
	 */
	protected static List<List<Direction>> getWallKicksGauche(char lettre) {
		return getWallKicks(lettre, "gauche");
	}

	/**
	 * @param lettre
	 * @param sens
	 * @return
	 * @since 0.0.0
	 */
	private static List<List<Direction>> getWallKicks(char lettre, String sens) {

		var wallKicksDroite = new ArrayList<List<Direction>>();

		for (var indexRotation = 0; indexRotation < 4; indexRotation++) {

			var wallKicksString = CONFIGURATION_WALL_KICKS.getString(lettre + "_" + sens + "_" + indexRotation);
			var wallKicks = getWallKicks(wallKicksString);
			wallKicksDroite.add(wallKicks);
		}

		return wallKicksDroite;
	}

	/**
	 * @param wallKicksString chaîne de caractères contenant une liste de wall kicks
	 * @return
	 * @since 0.0.0
	 */
	private static List<Direction> getWallKicks(String wallKicksString) {

		var wallKicks = new ArrayList<Direction>();
		var wallKicksStrings = wallKicksString.split("\\|");

		for (var indexRotation = 0; indexRotation < 4; indexRotation++) {

			var wallKickString = wallKicksStrings[indexRotation];
			var wallKickXY = wallKickString.split(",");
			var xString = wallKickXY[0];
			var yString = wallKickXY[1];
			var x = parseInt(xString);
			var y = parseInt(yString);

			var wallKick = new Direction(x, y, 0);
			wallKicks.add(wallKick);
		}

		return wallKicks;
	}

	/**
	 * Crée un tetromino initialement positionné en (0, 0).
	 *
	 * @param rotations tableau contenant les différentes rotations du tetromino
	 * @param colonneApparition colonne d'apparition du tetromino dans le champ de jeu
	 * @param wallKicksDroite wall kicks possibles lors d'une rotation vers la droite
	 * @param wallKicksGauche wall kicks possibles lors d'une rotation vers la gauche
	 * @param soundEffect son d'apparition du tetromino
	 * @since 0.0.0
	 */
	protected Tetromino(List<List<Block>> rotations, int colonneApparition,
			List<List<Direction>> wallKicksDroite, List<List<Direction>> wallKicksGauche, Audio soundEffect) {

		super(rotations, colonneApparition, soundEffect, wallKicksDroite, wallKicksGauche);
	}
}
