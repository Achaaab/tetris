package com.github.achaaab.tetroshow.view.skin;

import com.github.achaaab.tetroshow.model.piece.Block;
import com.github.achaaab.tetroshow.model.piece.State;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import static java.awt.Color.WHITE;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Glass implements Skin {

	public static final Glass INSTANCE = new Glass();

	/**
	 * private constructor ensuring singleton usage
	 *
	 * @see #INSTANCE
	 * @since 0.0.0
	 */
	private Glass() {

	}

	@Override
	public void drawBlock(Graphics2D graphics, int x, int y, int size, Block block, State state) {

		var color = block.getColor();

		var baseColor = switch (state) {

			case GHOST -> new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
			case LOCKED -> new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() / 2);
			default -> color;
		};

		var darkerColor = baseColor.darker();

		var top = new Point2D.Float(x - size / 3.0f, y - size / 3.0f);
		var bottom = new Point2D.Float(x + size, y + size);
		var gradient = new GradientPaint(top, WHITE, bottom, darkerColor);
		graphics.setPaint(gradient);
		graphics.fillRect(x, y, size, size);

		var borderWidth = size / 8;
		top = new Point2D.Float(x, y);
		bottom = new Point2D.Float(x, y + size);
		gradient = new GradientPaint(top, baseColor, bottom, darkerColor);
		graphics.setPaint(gradient);
		graphics.fillRect(x + borderWidth, y + borderWidth, size - 2 * borderWidth, size - 2 * borderWidth);
	}
}
