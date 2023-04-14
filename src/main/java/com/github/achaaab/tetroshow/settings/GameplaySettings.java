package com.github.achaaab.tetroshow.settings;

/**
 * gameplay settings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GameplaySettings {

	private int holdLimit;

	/**
	 * @return number of times a piece can be hold
	 * @since 0.0.0
	 */
	public int getHoldLimit() {
		return holdLimit;
	}

	/**
	 * @param holdLimit number of times a piece can be hold
	 * @since 0.0.0
	 */
	public void setHoldLimit(int holdLimit) {
		this.holdLimit = holdLimit;
	}
}
