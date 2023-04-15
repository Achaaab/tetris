package com.github.achaaab.tetroshow.model.field;

import com.github.achaaab.tetroshow.model.piece.Piece;

import static java.util.Arrays.setAll;
import static java.util.Arrays.stream;

/**
 * field implementation based on a grid
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Grid extends AbstractField {

	protected int width;
	protected int height;
	protected Cell[][] cells;

	/**
	 * Creates a new grid.
	 *
	 * @param width column count
	 * @param height row count
	 * @since 0.0.0
	 */
	public Grid(int width, int height) {

		this.width = width;
		this.height = height;

		cells = new Cell[height][width];
		stream(cells).forEach(row -> setAll(row, x -> new Cell()));
	}

	/**
	 * Tests if a piece can be locked in this grid.
	 *
	 * @param piece piece to lock
	 * @return whether the given piece can be locked
	 * @since 0.0.0
	 */
	public boolean canLock(Piece piece) {

		return piece.getBlocks().stream().allMatch(block ->
				isEmptyCell(piece.getX() + block.getX(), piece.getY() + block.getY()));
	}

	/**
	 * Tests if a cell is empty.
	 *
	 * @param x column index
	 * @param y row index
	 * @return whether the cell at given position is empty
	 * @since 0.0.0
	 */
	private boolean isEmptyCell(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height && getCell(x, y).isEmpty();
	}

	/**
	 * Locks a piece in the stack.
	 *
	 * @param piece piece from this grid
	 * @return whether the given piece could be locked
	 * @since 0.0.0
	 */
	public boolean lock(Piece piece) {

		var lockable = canLock(piece);

		if (lockable) {

			remove(piece);

			var blocks = piece.getBlocks();

			var pieceX = piece.getX();
			var pieceY = piece.getY();

			for (var block : blocks) {

				var blockX = block.getX();
				var blockY = block.getY();

				var cellX = pieceX + blockX;
				var cellY = pieceY + blockY;

				getCell(cellX, cellY).setBlock(block);
			}
		}

		return lockable;
	}

	/**
	 * @return grid height, in rows
	 * @since 0.0.0
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return grid width, in columns
	 * @since 0.0.0
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param x column index
	 * @param y row index
	 * @return cell at the given position
	 * @since 0.0.0
	 */
	public Cell getCell(int x, int y) {
		return cells[y][x];
	}

	@Override
	public void reset() {

		super.reset();

		stream(cells).forEach(row -> stream(row).forEach(Cell::clear));
	}
}
