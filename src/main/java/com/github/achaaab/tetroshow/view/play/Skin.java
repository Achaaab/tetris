package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.piece.Block;
import com.github.achaaab.tetroshow.model.field.Cell;
import com.github.achaaab.tetroshow.model.piece.Piece;
import com.github.achaaab.tetroshow.model.piece.State;

import java.awt.Graphics;

import static com.github.achaaab.tetroshow.model.piece.State.LOCKED;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface Skin {

	/**
	 * @param graphics
	 * @param x
	 * @param y
	 * @param size
	 * @param block
	 * @param state
	 * @since 0.0.0
	 */
	void drawBlock(Graphics graphics, int x, int y, int size, Block block, State state);

	/**
	 * @param graphics
	 * @param x
	 * @param y
	 * @param size
	 * @param cell
	 * @since 0.0.0
	 */
	default void drawCell(Graphics graphics, int x, int y, int size, Cell cell) {

		cell.getBlock().ifPresent(block ->
				drawBlock(graphics, x, y, size, block, LOCKED));
	}

	/**
	 * Dessine une piece en appliquant le mode de colorisation adéquat.
	 *
	 * @param graphics graphique avec lequel il faut dessiner
	 * @param piece pièce à dessiner
	 * @param x position de la pièce à dessiner sur l'axe horizontal
	 * @param y position de la pièce à dessiner sur l'axe vertical
	 * @param blockSize largeur disponible pour dessiner les carres de la piece (en pixels)
	 * @param state
	 * @since 0.0.0
	 */
	default void drawPiece(Graphics graphics, Piece piece, int x, int y, int blockSize, State state) {

		var blocks = piece.getBlocks();

		for (var block : blocks) {

			var cellX = x + blockSize * block.getX();
			var cellY = y + blockSize * block.getY();

			drawBlock(graphics, cellX, cellY, blockSize, block, state);
		}
	}
}
