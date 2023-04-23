package com.github.achaaab.tetroshow.view.component;

import java.awt.Graphics2D;

import static java.awt.geom.AffineTransform.getTranslateInstance;

/**
 * UI component as simple as possible, we don't need much
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class Component {

	protected int x;
	protected int y;
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
		margin = 0;
	}

	/**
	 * Paints this component.
	 *
	 * @param graphics graphics with which to paint
	 * @since 0.0.0
	 */
	public void paint(Graphics2D graphics) {

		var translation = getTranslateInstance(x + margin, y + margin);
		graphics.setTransform(translation);
	}

	/**
	 * @return x relative position of this component in his parent (in pixels)
	 * @since 0.0.0
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x x relative position of this component in his parent (in pixels)
	 * @since 0.0.0
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return y relative position of this component in his parent (in pixels)
	 * @since 0.0.0
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y y relative position of this component in his parent (in pixels)
	 * @since 0.0.0
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return width of this component (in pixels)
	 * @since 0.0.0
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width width of this component (in pixels)
	 * @since 0.0.0
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return height of this component (in pixels)
	 * @since 0.0.0
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height height of this component (in pixels)
	 * @since 0.0.0
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return inner margin of this component (in pixels)
	 * @since 0.0.0
	 */
	public int getMargin() {
		return margin;
	}

	/**
	 * @param margin inner margin of this component (in pixels)
	 * @since 0.0.0
	 */
	public void setMargin(int margin) {
		this.margin = margin;
	}
}
