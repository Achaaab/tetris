package com.github.achaaab.tetroshow.model.piece;

import com.github.achaaab.tetroshow.audio.Audio;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static java.util.ResourceBundle.getBundle;

/**
 * A tetromino is a polyomino made of four square blocks.
 * The spelling "tetromino" is standard among mathematicians.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Tetromino extends AbstractPiece {

	protected static final ResourceBundle WALL_KICKS_PROPERTIES = getBundle("srs_wall_kicks");

	/**
	 * @param color
	 * @param blockPositions tableau contenant les différentes rotations d'un tetromino
	 * @return liste des rotations
	 * @since 0.0.0
	 */
	protected static List<List<Block>> getRotations(Color color, int[][] blockPositions) {

		return stream(blockPositions).
				map(positions -> getBlocks(color, positions)).
				toList();
	}

	/**
	 * Permet de convertir un tableau de positions de carrés en une liste de carrés.
	 *
	 * @param color
	 * @param rotation tableau contenant la position des carrés d'un tetromino
	 * @return liste des carrés du tetromino
	 * @since 0.0.0
	 */
	private static List<Block> getBlocks(Color color, int[] rotation) {

		return stream(rotation).
				mapToObj(position -> new Block(color, position % 4, position / 4)).
				toList();
	}

	/**
	 * @param letter
	 * @return
	 * @since 0.0.0
	 */
	protected static List<List<Direction>> getClockwiseWallKicks(char letter) {
		return getWallKicks(letter, "clockwise");
	}

	/**
	 * @param letter
	 * @return
	 * @since 0.0.0
	 */
	protected static List<List<Direction>> getCounterclockwiseWallKicks(char letter) {
		return getWallKicks(letter, "counterclockwise");
	}

	/**
	 * @param letter
	 * @param direction
	 * @return
	 * @since 0.0.0
	 */
	private static List<List<Direction>> getWallKicks(char letter, String direction) {

		var wallKicksDroite = new ArrayList<List<Direction>>();

		for (var rotation = 0; rotation < 4; rotation++) {

			var wallKicksString = WALL_KICKS_PROPERTIES.getString(letter + "_" + direction + "_" + rotation);
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

		for (var index = 0; index < 4; index++) {

			var wallKickString = wallKicksStrings[index];
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
	 * @param entryColumn colonne d'apparition du tetromino dans le champ de jeu
	 * @param soundEffect son d'apparition du tetromino
	 * @param clockwiseWallKicks wall kicks possibles lors d'une rotation vers la droite
	 * @param counterclockwiseWallKicks wall kicks possibles lors d'une rotation vers la gauche
	 * @since 0.0.0
	 */
	protected Tetromino(List<List<Block>> rotations, int entryColumn, Audio soundEffect,
						List<List<Direction>> clockwiseWallKicks, List<List<Direction>> counterclockwiseWallKicks) {

		super(rotations, entryColumn, soundEffect, clockwiseWallKicks, counterclockwiseWallKicks);
	}
}
