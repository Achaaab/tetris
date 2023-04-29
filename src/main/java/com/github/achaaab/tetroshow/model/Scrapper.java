package com.github.achaaab.tetroshow.model;

import com.github.achaaab.tetroshow.settings.Settings;

import java.awt.Color;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * A scrapper is responsible for scraps creation after a block explosion.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Scrapper {

	/**
	 * maximum initial scrap velocity (in block per second)
	 */
	private static final double MAXIMUM_SCRAP_VELOCITY = 1.0;

	/**
	 * Adds scraps after a block explosion.
	 *
	 * @param x block column
	 * @param y block row
	 * @param color block color
	 * @param scraps list in which to add new scraps
	 * @since 0.0.0
	 */
	public void addScraps(int x, int y, Color color, Queue<Scrap> scraps) {

		var settings = Settings.getDefaultInstance();
		var graphicsSettings = settings.getGraphics();
		var particleLevel = graphicsSettings.getParticleLevel();

		var fraction = switch (particleLevel) {
			case LOW -> 5;
			case MEDIUM -> 10;
			case HIGH -> 15;
			case VERY_HIGH -> 20;
			case INSANE -> 30;
		};

		var size = 1.0 / fraction;

		var random = ThreadLocalRandom.current();

		for (var i = 0; i < fraction; i++) {
			for (var j = 0; j < fraction; j++) {

				var scrapX = x + i * size;
				var scrapY = y + j * size;

				var scrap = new Scrap(scrapX, scrapY, size, color);
				var velocity = MAXIMUM_SCRAP_VELOCITY * random.nextDouble();
				var angle = PI * random.nextDouble();

				scrap.setVelocity(
						velocity * cos(angle),
						-velocity * sin(angle));

				scraps.offer(scrap);
			}
		}
	}
}
