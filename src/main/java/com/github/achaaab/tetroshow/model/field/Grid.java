package com.github.achaaab.tetroshow.model.field;

import com.github.achaaab.tetroshow.model.piece.Piece;

import static java.util.Arrays.setAll;
import static java.util.Arrays.stream;

/**
 * Implémente un champ à l'aide d'une matrice.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Grid extends AbstractField {

	protected int width;
	protected int height;
	protected Cell[][] cells;

	/**
	 * Crée un champ.
	 *
	 * @param width nombre de carrés disposables en largeur dans le champ
	 * @param height nombre de carrés disposables en hauteur dans le champ
	 * @since 0.0.0
	 */
	public Grid(int width, int height) {

		this.width = width;
		this.height = height;

		cells = new Cell[height][width];
		stream(cells).forEach(row -> setAll(row, x -> new Cell()));
	}

	/**
	 * Determines if a piece can be locked in this grid.
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
	 * @param x
	 * @param y
	 * @return
	 * @since 0.0.0
	 */
	private boolean isEmptyCell(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height && getCell(x, y).isEmpty();
	}

	/**
	 * Verrouille une pièce dans le tas.
	 *
	 * @param piece pièce du champ
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
	 * @return hauteur du champ, c'est-à-dire, le nombre de carrés disposables en hauteur dans le champ
	 * @since 0.0.0
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return la largeur du champ, c'est-à-dire, le nombre de carrés disposables en largeur dans le champ
	 * @since 0.0.0
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param x position d'un carré dans le champ (axe horizontal)
	 * @param y position d'un carré dans le champ (axe vertical)
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
