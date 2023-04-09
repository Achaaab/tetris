package com.github.achaaab.tetris.view;

import com.github.achaaab.tetris.model.Block;
import com.github.achaaab.tetris.model.classic.State;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import static java.awt.Color.WHITE;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GlassSkin implements Skin {

	public static final GlassSkin INSTANCE = new GlassSkin();

	/**
	 * constructeur privé pour forcer l'utilisation du singleton
	 *
	 * @since 0.0.0
	 */
	private GlassSkin() {

	}

	@Override
	public void drawBlock(Graphics graphics, int x, int y, int size, Block block, State state) {

		var graphics2d = (Graphics2D) graphics;
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
		graphics2d.setPaint(gradient);
		graphics2d.fillRect(x, y, size, size);

		var borderWidth = size / 8;
		top = new Point2D.Float(x, y);
		bottom = new Point2D.Float(x, y + size);
		gradient = new GradientPaint(top, baseColor, bottom, darkerColor);
		graphics2d.setPaint(gradient);
		graphics2d.fillRect(x + borderWidth, y + borderWidth, size - 2 * borderWidth, size - 2 * borderWidth);
	}
}
