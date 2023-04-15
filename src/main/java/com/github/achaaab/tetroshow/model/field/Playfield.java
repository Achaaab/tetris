package com.github.achaaab.tetroshow.model.field;

import com.github.achaaab.tetroshow.model.Scrap;
import com.github.achaaab.tetroshow.model.Scrapper;
import com.github.achaaab.tetroshow.model.piece.Direction;
import com.github.achaaab.tetroshow.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.achaaab.tetroshow.model.piece.Direction.DOWN;
import static java.util.Arrays.stream;

/**
 * playfield for Tetroshow
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Playfield extends Grid {

	public static final int WIDTH = 10;
	public static final int HEIGHT = 20;

	private final Scrapper scrapper;
	private final List<Scrap> scraps;

	/**
	 * Create a new playfield.
	 *
	 * @since 0.0.0
	 */
	public Playfield() {

		super(WIDTH, HEIGHT);

		scrapper = new Scrapper();
		scraps = new ArrayList<>();
	}

	/**
	 * Determines whether a piece can move in this field, in a given direction.
	 *
	 * @param piece piece to move
	 * @param direction move direction
	 * @return whether the given move is possible
	 * @since 0.0.0
	 */
	public boolean isMovePossible(Piece piece, Direction direction) {

		var copy = piece.copy();
		copy.move(direction);

		return fit(copy);
	}

	/**
	 * Tests if a piece can fit in this playfield.
	 * Note that it can entirely or partially occupy the 2 rows over this grid.
	 *
	 * @param piece piece to test
	 * @return whether the given piece can fit in this playfield
	 * @since 0.0.0
	 */
	private boolean fit(Piece piece) {

		return piece.getBlocks().stream().allMatch(block ->
				isFree(piece.getX() + block.getX(), piece.getY() + block.getY()));
	}

	/**
	 * Note that 2 rows over this grid can be occupied by blocks.
	 *
	 * @param x column index
	 * @param y row index
	 * @return whether the playfield can be occupied by a block at the given coordinates
	 * @since 0.0.0
	 */
	private boolean isFree(int x, int y) {

		return x >= 0 && x < width && y < height && y > -3 &&
				(y < 0 || getCell(x, y).isEmpty());
	}

	/**
	 * Clears lines.
	 *
	 * @return cleared line indices
	 * @since 0.0.0
	 */
	public List<Integer> clearLines() {

		var clearedLines = new ArrayList<Integer>();
		var y = height;

		while (y-- > 0) {

			if (isLine(y)) {

				clearLine(y);
				clearedLines.add(y);
			}
		}

		return clearedLines;
	}

	/**
	 * Tests if a row is a line. Lines are rows in which every cell contains a block.
	 *
	 * @param y row to test
	 * @return whether the given row is a line
	 * @since 0.0.0
	 */
	private boolean isLine(int y) {

		return stream(cells[y]).
				map(Cell::getBlock).
				allMatch(Optional::isPresent);
	}

	/**
	 * Clears a line.
	 *
	 * @param y row index
	 * @since 0.0.0
	 */
	private void clearLine(int y) {

		for (var x = 0; x < width; x++) {

			var cell = getCell(x, y);
			var block = cell.getBlock().orElseThrow();
			var color = block.getColor();

			scrapper.addScraps(x, y, color, scraps);
			cell.clear();
		}
	}

	/**
	 * @return scraps resulting from cleared lines
	 * @since 0.0.0
	 */
	public List<Scrap> getScraps() {
		return scraps;
	}

	/**
	 * Drops rows after a line clear.
	 *
	 * @param lines row indices of cleared lines
	 * @since 0.0.0
	 */
	public void dropRows(List<Integer> lines) {

		var lineIterator = lines.iterator();

		var line = lineIterator.next();
		var drop = 0;

		for (var y = height - 1; y >= 0; y--) {

			if (y == line) {

				drop++;

				if (lineIterator.hasNext()) {
					line = lineIterator.next();
				}

			} else if (drop > 0) {

				dropRow(y, drop);
			}
		}
	}

	/**
	 * @param y row to drop
	 * @param drop drop height
	 * @since 0.0.0
	 */
	private void dropRow(int y, int drop) {

		for (var x = 0; x < width; x++) {

			var topCell = getCell(x, y);
			var bottomCell = getCell(x, y + drop);
			topCell.moveBlock(bottomCell);
		}
	}

	/**
	 * The ghost piece is vertical to the active piece, as low as possible in this playfield.
	 * It is empty if there is no active piece or if the active piece is already on the stack.
	 *
	 * @return ghost piece
	 * @since 0.0.0
	 */
	public Optional<Piece> getGhostPiece() {

		var fallingPiece = getActivePiece();

		return fallingPiece.
				filter(piece -> isMovePossible(piece, DOWN)).
				map(this::getGhost);
	}

	/**
	 * A ghost piece is a copy of a piece, vertical to it, as low as possible in this playfield.
	 *
	 * @param piece any piece
	 * @return ghost of the given piece
	 * @since 0.0.0
	 */
	private Piece getGhost(Piece piece) {

		var ghost = piece.copy();

		do {
			ghost.move(DOWN);
		} while (isMovePossible(ghost, DOWN));

		return ghost;
	}
}
