package com.github.achaaab.tetroshow.model;

import java.util.List;

import static com.github.achaaab.tetroshow.utility.ResourceUtility.readLines;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Tetroshow credits
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Credits implements GamePart {

	private static final String RESOURCE_NAME = "credits.txt";
	private static final List<String> LINES = readLines(RESOURCE_NAME, UTF_8);

	private double time;

	/**
	 * Creates Tetroshow credits.
	 *
	 * @since 0.0.0
	 */
	public Credits() {
		reset();
	}

	@Override
	public void reset() {
		time = 0;
	}

	/**
	 * @param deltaTime time elapsed since previous update (in seconds)
	 * @since 0.0.0
	 */
	public void update(double deltaTime) {
		time += deltaTime;
	}

	/**
	 * @return credits time in seconds
	 * @since 0.0.0
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @return credits, line by line
	 * @since 0.0.0
	 */
	public List<String> getLines() {
		return LINES;
	}
}
