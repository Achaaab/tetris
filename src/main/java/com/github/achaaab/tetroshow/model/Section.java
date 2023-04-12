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
	private final long condition;

	private double startTime;
	private double duration;
	private boolean started;
	private boolean cleared;

	/**
	 * @param startLevel
	 * @param endLevel
	 * @param condition durée conditionnelle de la section, exprimée en secondes
	 * @since 0.0.0
	 */
	public Section(int startLevel, int endLevel, int condition) {

		this.startLevel = startLevel;
		this.endLevel = endLevel;
		this.condition = condition;

		startTime = 0;
		duration = 0;
		started = false;
		cleared = false;
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

		if (started && !cleared && level >= endLevel) {

			duration = time - startTime;
			cleared = true;
			LOGGER.info("end section {}: {}s", this, round(duration));
		}
	}

	/**
	 * @return whether this section time is below or equals to the time condition
	 * @since 0.0.0
	 */
	public boolean isCool() {
		return cleared && duration <= condition;
	}

	/**
	 * @return whether this section time is above to the time condition
	 * @since 0.0.0
	 */
	public boolean isRegret() {
		return cleared && duration >= condition;
	}

	@Override
	public int compareTo(Section section) {
		return startLevel - section.startLevel;
	}

	/**
	 * @return whether this section is cleared
	 * @since 0.0.0
	 */
	public boolean isCleared() {
		return cleared;
	}

	@Override
	public String toString() {
		return startLevel + " - " + endLevel;
	}
}
