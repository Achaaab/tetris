package com.github.achaaab.tetroshow.view.segment;

import java.awt.Polygon;

/**
 * LCD-like segment
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Segment extends Polygon {

	private boolean on;

	/**
	 * Creates a new LCD-like segement.
	 *
	 * @param xs
	 * @param ys
	 */
	public Segment(int[] xs, int[] ys) {

		super(xs, ys, xs.length);

		on = false;
	}

	/**
	 * @return whether this display is on
	 * @since 0.0.0
	 */
	public boolean isOn() {
		return on;
	}

	public void turnOn() {
		on = true;
	}

	public void turnOff() {
		on = false;
	}

	/**
	 * @param on whether this display is on
	 * @since 0.0.0
	 */
	public void setOn(boolean on) {
		this.on = on;
	}
}
