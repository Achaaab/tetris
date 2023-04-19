package com.github.achaaab.tetroshow.model.piece;

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

	protected static final ResourceBundle WALL_KICKS_PROPERTIES = getBundle("srs/wall_kicks");

	/**
	 * @param color color of the tetromino
	 * @param blockPositions block positions for each rotation
	 * @return rotations
	 * @since 0.0.0
	 */
	protected static List<List<Block>> getRotations(Color color, int[][] blockPositions) {

		return stream(blockPositions).
				map(positions -> getBlocks(color, positions)).
				toList();
	}

	/**
	 * Transforms a block position array into a list of blocks.
	 *
	 * @param color color of the blocks to create
	 * @param rotation position of each block
	 * @return created blocks
	 * @since 0.0.0
	 */
	private static List<Block> getBlocks(Color color, int[] rotation) {

		return stream(rotation).
				mapToObj(position -> new Block(color, position % 4, position / 4)).
				toList();
	}

	/**
	 * @param letter letter representing a tetromino
	 * @return clockwise wall kicks allowed for the specified tetromino
	 * @since 0.0.0
	 */
	protected static List<List<Direction>> getClockwiseWallKicks(char letter) {
		return getWallKicks(letter, "clockwise");
	}

	/**
	 * @param letter letter representing a tetromino
	 * @return counterclockwise wall kicks allowed for the specified tetromino
	 * @since 0.0.0
	 */
	protected static List<List<Direction>> getCounterclockwiseWallKicks(char letter) {
		return getWallKicks(letter, "counterclockwise");
	}

	/**
	 * @param letter letter representing a tetromino
	 * @param direction rotation
	 * @return allowed wall kicks for each rotation
	 * @since 0.0.0
	 */
	private static List<List<Direction>> getWallKicks(char letter, String direction) {

		var wallKicks = new ArrayList<List<Direction>>();

		for (var rotation = 0; rotation < 4; rotation++) {

			var wallKicksString = WALL_KICKS_PROPERTIES.getString(letter + "_" + direction + "_" + rotation);
			wallKicks.add(parseWallKicks(wallKicksString));
		}

		return wallKicks;
	}

	/**
	 * @param wallKicksString string representation of wall kicks
	 * @return parsed wall kicks
	 * @since 0.0.0
	 */
	private static List<Direction> parseWallKicks(String wallKicksString) {

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
	 * Creates a new tetromino.
	 *
	 * @param rotations blocks of the tetromino to create, for each rotation
	 * @param entryColumn entry column in the playfield
	 * @param clockwiseWallKicks allowed clockwise wall kicks
	 * @param counterclockwiseWallKicks allowed counterclockwise wall kicks
	 * @since 0.0.0
	 */
	protected Tetromino(List<List<Block>> rotations, int entryColumn,
			List<List<Direction>> clockwiseWallKicks, List<List<Direction>> counterclockwiseWallKicks) {

		super(rotations, entryColumn, clockwiseWallKicks, counterclockwiseWallKicks);
	}
}
