package com.github.achaaab.tetroshow.model;

import java.awt.Color;
import java.util.List;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.sin;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Scrapper {

	private static final int FRACTION = 10;

	private final double scrapSize;
	private final double[][] vx;
	private final double[][] vy;

	/**
	 * @since 0.0.0
	 */
	public Scrapper() {

		scrapSize = 1.0 / FRACTION;
		vx = new double[FRACTION][FRACTION];
		vy = new double[FRACTION][FRACTION];

		for (var i = 0; i < FRACTION; i++) {

			var x = (i + 0.5) * scrapSize;

			for (var j = 0; j < FRACTION; j++) {

				var y = (j + 0.5) * scrapSize;

				var distance = hypot(0.5 - x, 0.5 - y);
				var angle = atan2(0.5 - y, 0.5 - x);
				var velocity = distance;

				vx[i][j] = velocity * cos(angle);
				vy[i][j] = velocity * sin(angle);
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param color
	 * @param scraps
	 * @since 0.0.0
	 */
	public void addScraps(int x, int y, Color color, List<Scrap> scraps) {

		for (var i = 0; i < FRACTION; i++) {

			for (var j = 0; j < FRACTION; j++) {

				var scrapX = x + i * scrapSize;
				var scrapY = y + j * scrapSize;

				var scrap = new Scrap(scrapX, scrapY, scrapSize, color);
				scrap.setVelocity(vx[i][j], vy[i][j]);

				scraps.add(scrap);
			}
		}
	}
}
