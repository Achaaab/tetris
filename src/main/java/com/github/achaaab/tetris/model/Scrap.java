package com.github.achaaab.tetris.model;

import java.awt.Color;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Scrap {

	private static final int LIFESPAN = 64;
	private static final double GRAVITY = 0.01;

	private final int red;
	private final int green;
	private final int blue;

	private final double size;

	private double x;
	private double y;

	private double vx;
	private double vy;

	private int age;

	/**
	 * @param x
	 * @param y
	 * @param size
	 * @param color
	 * @since 0.0.0
	 */
	public Scrap(double x, double y, double size, Color color) {

		this.x = x;
		this.y = y;
		this.size = size;

		red = color.getRed();
		green = color.getGreen();
		blue = color.getBlue();

		age = 0;
	}

	/**
	 * @return color of this scrap
	 * @since 0.0.0
	 */
	public Color getColor() {

		var alpha = 255 - age * 255 / LIFESPAN;
		return new Color(red, green, blue, alpha);
	}

	/**
	 * @param vx
	 * @param vy
	 * @since 0.0.0
	 */
	public void setVelocity(double vx, double vy) {

		this.vx = vx;
		this.vy = vy;
	}

	/**
	 * Updates this scrap.
	 *
	 * @return whether this scrap is still active
	 * @since 0.0.0
	 */
	public boolean update() {

		x += vx;
		y += vy;
		vy += GRAVITY;

		return ++age >= LIFESPAN;
	}

	/**
	 * @return width and height of this scrap
	 * @since 0.0.0
	 */
	public double getSize() {
		return size;
	}

	/**
	 * @return x coordinate of this scrap in the playfield referential
	 * @since 0.0.0
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return y coordinate of this scrap in the playfield referential
	 * @since 0.0.0
	 */
	public double getY() {
		return y;
	}
}
