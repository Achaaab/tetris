package com.github.achaaab.tetroshow.view.skin;

import com.github.achaaab.tetroshow.model.field.Cell;
import com.github.achaaab.tetroshow.model.piece.Block;
import com.github.achaaab.tetroshow.model.piece.State;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import static com.github.achaaab.tetroshow.model.piece.State.ACTIF;
import static com.github.achaaab.tetroshow.model.piece.State.LOCKED;
import static com.github.achaaab.tetroshow.model.piece.State.OFF;
import static java.lang.Math.round;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Lcd implements Skin {

	public static final Lcd INSTANCE = new Lcd();

	private static final Color BACKGROUND_COLOR = new Color(158, 173, 134);
	private static final Color OFF_COLOR = new Color(135, 147, 114);
	private static final Color ON_COLOR = new Color(0, 0, 0);

	/**
	 * private constructor ensuring singleton usage
	 *
	 * @see #INSTANCE
	 * @since 0.0.0
	 */
	private Lcd() {

	}

	@Override
	public Color getBackgroundColor() {
		return BACKGROUND_COLOR;
	}

	@Override
	public Color getBorderColor() {
		return ON_COLOR;
	}

	@Override
	public void drawBlock(Graphics2D graphics, int x, int y, int size, Block block, State state) {
		drawCell(graphics, x, y, size, state);
	}

	@Override
	public void drawCell(Graphics2D graphics, int x, int y, int size, Cell cell) {

		var state = cell.isEmpty() ?
				OFF :
				LOCKED;

		drawCell(graphics, x, y, size, state);
	}

	/**
	 * Draws an LCD cell.
	 *
	 * @param graphics graphics with which to draw
	 * @param x
	 * @param y
	 * @param size cell width and height (in physical pixels)
	 * @param state state of the cell
	 * @since 0.0.0
	 */
	private void drawCell(Graphics2D graphics, int x, int y, int size, State state) {

		graphics.setColor(state == OFF ? OFF_COLOR : ON_COLOR);

		var previousStroke = graphics.getStroke();
		var stroke = new BasicStroke(0.08f * size);
		graphics.setStroke(stroke);

		var outerMargin = round(0.12f * size);
		var innerMargin = round(0.23f * size);

		graphics.drawRect(
				x + outerMargin,
				y + outerMargin,
				size - 2 * outerMargin,
				size - 2 * outerMargin);

		graphics.setStroke(previousStroke);

		graphics.setColor(state == ACTIF || state == LOCKED ? ON_COLOR : OFF_COLOR);

		graphics.fillRect(
				x + innerMargin,
				y + innerMargin,
				size - 2 * innerMargin,
				size - 2 * innerMargin);
	}
}
