package com.github.achaaab.tetroshow.view.skin;

import com.github.achaaab.tetroshow.model.field.Cell;
import com.github.achaaab.tetroshow.model.piece.Block;
import com.github.achaaab.tetroshow.model.piece.Piece;
import com.github.achaaab.tetroshow.model.piece.State;
import com.github.achaaab.tetroshow.settings.Settings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;

import static com.github.achaaab.tetroshow.model.piece.State.LOCKED;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static java.awt.Color.GRAY;
import static java.awt.Color.ORANGE;
import static java.awt.Color.WHITE;
import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface Skin {

	int FONT_SIZE = scale(20.0f);
	Font FONT = new Font(MONOSPACED, PLAIN, FONT_SIZE);
	Color[] TITLE_COLORS = new Color[] { WHITE, WHITE, WHITE.darker(), WHITE, WHITE };
	Color[] VALUE_COLORS = new Color[] { ORANGE, ORANGE, ORANGE.darker(), ORANGE, ORANGE };
	float[] TEXT_GRADIENT_FRACTIONS = new float[] { 0.0f, 0.6f, 0.75f, 0.9f, 1.0f };

	Color DEFAULT_BRACKGROUND_COLOR = new Color(0, 0, 16);
	Color DEFAULT_BORDER_COLOR = GRAY;

	String ELECTRONIKA_60 = "Electronika 60";
	String GLASS = "Glass";
	String BRICK_GAME = "Brick game";
	String[] SKINS = { ELECTRONIKA_60, GLASS, BRICK_GAME };

	/**
	 * @return current skin
	 * @since 0.0.0
	 */
	static Skin getCurrentSkin() {

		var name = Settings.getDefaultInstance().getGraphics().getSkin();
		return get(name);
	}

	/**
	 * @param name skin name
	 * @return skin
	 * @since 0.0.0
	 */
	static Skin get(String name) {

		return switch (name) {

			case ELECTRONIKA_60 -> Electronika60.INSTANCE;
			case BRICK_GAME -> BrickGame.INSTANCE;
			default -> Glass.INSTANCE;
		};
	}

	/**
	 * @return background color
	 * @since 0.0.0
	 */
	default Color getBackgroundColor() {
		return DEFAULT_BRACKGROUND_COLOR;
	}

	/**
	 * @return border color
	 * @since 0.0.0
	 */
	default Color getBorderColor() {
		return DEFAULT_BORDER_COLOR;
	}

	/**
	 * Draws a block.
	 *
	 * @param graphics graphics with which to draw
	 * @param x x position of the block (in pixels)
	 * @param y y position of the block (in pixels)
	 * @param size with and height of the block (in pixels)
	 * @param block block to draw
	 * @param state block state
	 * @since 0.0.0
	 */
	void drawBlock(Graphics2D graphics, int x, int y, int size, Block block, State state);

	/**
	 * Draws a cell.
	 *
	 * @param graphics graphics with which to draw
	 * @param x x position of the cell (in pixels)
	 * @param y y position of the cell (in pixels)
	 * @param size with and height of the cell (in pixels)
	 * @param cell cell to draw
	 * @since 0.0.0
	 */
	default void drawCell(Graphics2D graphics, int x, int y, int size, Cell cell) {

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
	 * @param state piece state
	 * @since 0.0.0
	 */
	default void drawPiece(Graphics2D graphics, Piece piece, int x, int y, int blockSize, State state) {

		var blocks = piece.getBlocks();

		for (var block : blocks) {

			var cellX = x + blockSize * block.getX();
			var cellY = y + blockSize * block.getY();

			if (piece.getY() + block.getY() >= 0) {
				drawBlock(graphics, cellX, cellY, blockSize, block, state);
			}
		}
	}

	/**
	 * Draws a title.
	 *
	 * @param graphics graphics with which to draw
	 * @param title title to draw
	 * @param x x position of the title (in pixels)
	 * @param y y position of the title (in pixels)
	 * @since 0.0.0
	 */
	default void drawTitle(Graphics2D graphics, String title, int x, int y) {

		graphics.setFont(FONT);
		var fontMetrics = graphics.getFontMetrics();
		var height = fontMetrics.getHeight();
		var top = new Point2D.Float(0, y);
		var bottom = new Point2D.Float(0, y + height);
		var gradient = new LinearGradientPaint(top, bottom, TEXT_GRADIENT_FRACTIONS, TITLE_COLORS);

		graphics.setPaint(gradient);
		graphics.drawString(title, x, y + height);
	}

	/**
	 * Draws a value.
	 *
	 * @param graphics graphics with which to draw
	 * @param value value to draw
	 * @param x x position of the value (in pixels)
	 * @param y y position of the value (in pixels)
	 * @since 0.0.0
	 */
	default void drawValue(Graphics2D graphics, String value, int x, int y) {

		graphics.setFont(FONT);

		var fontMetrics = graphics.getFontMetrics();
		var height = fontMetrics.getHeight();
		var top = new Point2D.Float(0, y);
		var bottom = new Point2D.Float(0, y + height);
		var gradient = new LinearGradientPaint(top, bottom, TEXT_GRADIENT_FRACTIONS, VALUE_COLORS);

		graphics.setPaint(gradient);
		graphics.drawString(value, x, y + height);
	}
}
