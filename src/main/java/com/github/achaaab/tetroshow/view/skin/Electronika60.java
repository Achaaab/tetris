package com.github.achaaab.tetroshow.view.skin;

import com.github.achaaab.tetroshow.model.piece.Block;
import com.github.achaaab.tetroshow.model.piece.State;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import static com.github.achaaab.tetroshow.model.piece.State.GHOST;
import static java.awt.BasicStroke.CAP_SQUARE;
import static java.awt.BasicStroke.JOIN_MITER;
import static java.awt.Color.GREEN;
import static java.lang.Math.round;

/**
 * Represents the blocks by a pair of square brackets.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Electronika60 implements Skin {

	public static final Electronika60 INSTANCE = new Electronika60();

	private static final Color BASE_COLOR = GREEN;
	private static final Color STACK_COLOR = BASE_COLOR.darker();

	/**
	 * @param size block size in physical pixels
	 * @return default stroke to draw blocks
	 * @since 0.0.0
	 */
	private static Stroke getDefaultStroke(int size) {
		return new BasicStroke(0.08f * size);
	}

	/**
	 * @param size block size in physical pixels
	 * @return default stroke to draw blocks
	 * @since 0.0.0
	 */
	private static Stroke getGhostStroke(int size) {

		return new BasicStroke(
				0.03f * size,
				CAP_SQUARE, JOIN_MITER,
				1.0f,
				new float[] { 0.08f * size, 0.16f * size },
				0.0f);
	}

	/**
	 * private constructor ensuring singleton usage
	 *
	 * @see #INSTANCE
	 * @since 0.0.0
	 */
	private Electronika60() {

	}

	@Override
	public void drawBlock(Graphics graphics, int x, int y, int size, Block block, State state) {

		var graphics2d = (Graphics2D) graphics;

		var color = switch (state) {

			case GHOST, LOCKED -> STACK_COLOR;
			default -> BASE_COLOR;
		};

		var stroke = state == GHOST ?
				getGhostStroke(size) :
				getDefaultStroke(size);

		graphics2d.setStroke(stroke);
		graphics2d.setColor(color);

		var x0 = x + round(0.15f * size);
		var x1 = x + round(0.38f * size);
		var x2 = x - round(0.38f * size) + size;
		var x3 = x - round(0.15f * size) + size;

		var y0 = y + round(0.15f * size);
		var y1 = y - round(0.15f * size) + size;

		var leftBracketX = new int[] { x1, x0, x0, x1 };
		var leftBracketY = new int[] { y0, y0, y1, y1 };
		var rightBracketX = new int[] { x2, x3, x3, x2 };
		var rightBracketY = new int[] { y0, y0, y1, y1 };

		graphics2d.drawPolyline(leftBracketX, leftBracketY, 4);
		graphics2d.drawPolyline(rightBracketX, rightBracketY, 4);
	}
}
