package com.github.achaaab.tetroshow.model.piece;

import java.awt.Color;

/**
 * square block
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Block {

	private Color color;
	private int x;
	private int y;

	/**
	 * @param color
	 * @param x
	 * @param y
	 * @since 0.0.0
	 */
	public Block(Color color, int x, int y) {

		this.color = color;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 * @since 0.0.0
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 * @since 0.0.0
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 * @since 0.0.0
	 */
	public void setY(int y) {
		this.y = y;
	}
}
