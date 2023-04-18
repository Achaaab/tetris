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
	 * Creates a new block.
	 *
	 * @param color block preferred color
	 * @param x x block position in its container (piece or field for example)
	 * @param y y block position in its container (piece or field for example)
	 * @since 0.0.0
	 */
	public Block(Color color, int x, int y) {

		this.color = color;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return block preferred color
	 * @since 0.0.0
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color block preferred color
	 * @since 0.0.0
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return x block position in its container (piece or field for example)
	 * @since 0.0.0
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x x block position in its container (piece or field for example)
	 * @since 0.0.0
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return y block position in its container (piece or field for example)
	 * @since 0.0.0
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y y block position in its container (piece or field for example)
	 * @since 0.0.0
	 */
	public void setY(int y) {
		this.y = y;
	}
}
