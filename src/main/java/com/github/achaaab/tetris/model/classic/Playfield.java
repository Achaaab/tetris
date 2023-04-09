package com.github.achaaab.tetris.model.classic;

import com.github.achaaab.tetris.model.Cell;
import com.github.achaaab.tetris.model.Direction;
import com.github.achaaab.tetris.model.Grid;
import com.github.achaaab.tetris.model.Piece;
import com.github.achaaab.tetris.model.Scrap;
import com.github.achaaab.tetris.model.Scrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.achaaab.tetris.model.Direction.DOWN;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

/**
 * Un champ de jeu classique respecte les dimensions officielles du tetris.
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
	 * @param piece
	 * @return whether the given piece can fit in this playfield
	 * @since 0.0.0
	 */
	private boolean fit(Piece piece) {

		return piece.getBlocks().stream().allMatch(block ->
				isFree(piece.getX() + block.getX(), piece.getY() + block.getY()));
	}

	/**
	 * @param x
	 * @param y
	 * @return whether the playfield can be occupied by a block at the given coordinates
	 * @since 0.0.0
	 */
	private boolean isFree(int x, int y) {

		return x >= 0 && x < width && y < height && y > -3 &&
				(y < 0 || getCell(x, y).isEmpty());
	}

	/**
	 * Vide les lignes complètes.
	 *
	 * @return liste des lignes complètes, ordonnées de la plus basse à la plus haute
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
	 * @param y hauteur de la ligne à tester
	 * @return true si la ligne est complete, false sinon
	 * @since 0.0.0
	 */
	private boolean isLine(int y) {

		return stream(cells[y]).
				map(Cell::getBlock).
				allMatch(Optional::isPresent);
	}

	/**
	 * Clears a complete line at given height.
	 *
	 * @param y height of the line to clear
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
	 * @param lines
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
	 * @param y ligne à descendre
	 * @param drop nombre de cellules à descendre
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
	 * La pièce fantôme est à la verticale de la pièce courante, le plus bas possible dans le champ de jeu.
	 *
	 * @return pièce fantôme de la pièce courante ou {@code null} s'il n'y a pas de pièce courante ou bien si la
	 * pièce courante est déjà en bas
	 * @since 0.0.0
	 */
	public Optional<Piece> getGhostPiece() {

		var fallingPiece = getActivePiece();

		return fallingPiece.
				filter(piece -> isMovePossible(piece, DOWN)).
				map(this::getGhost);
	}

	/**
	 * @param piece
	 * @return
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
