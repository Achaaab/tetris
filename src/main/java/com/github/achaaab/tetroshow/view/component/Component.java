package com.github.achaaab.tetroshow.view.component;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

import static com.github.achaaab.tetroshow.view.Scaler.scaleFloat;
import static com.github.achaaab.tetroshow.view.skin.Skin.getCurrentSkin;
import static java.awt.geom.AffineTransform.getTranslateInstance;
import static java.lang.Math.round;

/**
 * UI component as simple as possible, we don't need much
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class Component {

	private static final float BORDER = scaleFloat(4.0f);
	protected static final int HALF_BORDER = round(BORDER / 2);
	private static final Stroke DEFAULT_STROKE = new BasicStroke(1.0f);
	private static final Stroke BORDER_STROKE = new BasicStroke(BORDER);

	private int x;
	private int y;
	protected int width;
	protected int height;
	protected int margin;

	/**
	 * Creates a new component.
	 *
	 * @since 0.0.0
	 */
	public Component() {

		x = 0;
		y = 0;
		width = 0;
		height = 0;
		margin = HALF_BORDER;
	}

	/**
	 * Paints this component.
	 *
	 * @param graphics graphics with which to paint
	 * @since 0.0.0
	 */
	public void paint(Graphics2D graphics) {

		var translation = getTranslateInstance(x, y);
		graphics.setTransform(translation);

		var skin = getCurrentSkin();

		graphics.setStroke(BORDER_STROKE);
		graphics.setColor(skin.getBorderColor());
		graphics.drawRect(0, 0, width, height);
		graphics.setStroke(DEFAULT_STROKE);

		translation = getTranslateInstance(x + margin, y + margin);
		graphics.setTransform(translation);
	}

	/**
	 * @return x relative position of this component in his parent (in physical pixels)
	 * @since 0.0.0
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x x relative position of this component in his parent (in physical pixels)
	 * @since 0.0.0
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return y relative position of this component in his parent (in physical pixels)
	 * @since 0.0.0
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y y relative position of this component in his parent (in physical pixels)
	 * @since 0.0.0
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return width of this component (in physical pixels)
	 * @since 0.0.0
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width width of this component (in physical pixels)
	 * @since 0.0.0
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return height of this component (in physical pixels)
	 * @since 0.0.0
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height height of this component (in physical pixels)
	 * @since 0.0.0
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
