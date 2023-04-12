package com.github.achaaab.tetroshow.model;

import org.slf4j.Logger;

import static java.lang.Math.round;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Section implements Comparable<Section> {

	private static final Logger LOGGER = getLogger(Section.class);

	private final int startLevel;
	private final int endLevel;
	private final long threshold;

	private double startTime;
	private double endTime;
	private boolean started;
	private boolean ended;

	/**
	 * @param startLevel
	 * @param endLevel
	 * @param threshold durée conditionnelle de la section, exprimée en secondes
	 * @since 0.0.0
	 */
	public Section(int startLevel, int endLevel, int threshold) {

		this.startLevel = startLevel;
		this.endLevel = endLevel;
		this.threshold = threshold;

		startTime = 0;
		endTime = 0;
		started = false;
		ended = false;
	}

	/**
	 * @param level
	 * @param time
	 * @since 0.0.0
	 */
	public void setTime(int level, double time) {

		if (!started && level >= startLevel) {

			LOGGER.info("start section {}", this);

			startTime = time;
			started = true;
		}

		if (started && !ended && level >= endLevel) {

			LOGGER.info("end section {}: {}s", this, round(time));

			endTime = time;
			ended = true;
		}
	}

	/**
	 * @return {@code true} si la durée effective de la section est inférieure ou égale à la durée conditionnelle
	 * @since 0.0.0
	 */
	public boolean isCool() {
		return ended && (endTime - startTime) <= threshold;
	}

	/**
	 * @return {@code true} si la durée effective de la section est supérieure ou égale à la durée conditionnelle
	 * @since 0.0.0
	 */
	public boolean isRegret() {
		return ended && (endTime - startTime) >= threshold;
	}

	@Override
	public int compareTo(Section section) {
		return startLevel - section.startLevel;
	}

	/**
	 * @return {@code true} si la section est terminée
	 */
	public boolean isEnded() {
		return ended;
	}

	@Override
	public String toString() {
		return startLevel + " - " + endLevel;
	}
}
