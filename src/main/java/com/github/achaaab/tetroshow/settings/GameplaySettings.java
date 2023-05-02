package com.github.achaaab.tetroshow.settings;

/**
 * gameplay settings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GameplaySettings {

	private int holdLimit;
	private String rules;

	private LevelSettings gravitySettings;
	private LevelSettings areSettings;
	private LevelSettings lineAreSettings;
	private LevelSettings dasSettings;
	private LevelSettings lockSettings;
	private LevelSettings clearSettings;

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

	/**
	 * @return name of the rule set
	 * @since 0.0.0
	 */
	public String getRules() {
		return rules;
	}

	/**
	 * @param rules name of the rule set
	 * @since 0.0.0
	 */
	public void setRules(String rules) {

		this.rules = rules;

		gravitySettings = new LevelSettings("gravity", rules, 4);
		areSettings = new LevelSettings("are", rules, 27);
		lineAreSettings = new LevelSettings("line-are", rules, 27);
		dasSettings = new LevelSettings("das", rules, 16);
		lockSettings = new LevelSettings("lock", rules, 30);
		clearSettings = new LevelSettings("clear", rules, 40);
	}

	/**
	 * @param level Tetroshow level
	 * @return gravity value at given level in (frame per second) / 256
	 * @since 0.0.0
	 */
	public int getGravity(int level) {
		return gravitySettings.getValue(level);
	}

	/**
	 * @param level Tetroshow level
	 * @return entry delay at given delay (in frames)
	 */
	public int getAre(int level) {
		return areSettings.getValue(level);
	}

	/**
	 * @param level Tetroshow level
	 * @return entry delay after a line clear at given level (in frames)
	 * @since 0.0.0
	 */
	public int getLineAre(int level) {
		return lineAreSettings.getValue(level);
	}

	/**
	 * @param level Tetroshow level
	 * @return DAS at given level (in frames)
	 * @since 0.0.0
	 */
	public int getDas(int level) {
		return dasSettings.getValue(level);
	}

	/**
	 * @param level Tetroshow level
	 * @return lock delay at given level (in frames)
	 * @since 0.0.0
	 */
	public int getLock(int level) {
		return lockSettings.getValue(level);
	}

	/**
	 * @param level Tetroshow level
	 * @return clear delay at given level (in frames)
	 * @since 0.0.0
	 */
	public int getClear(int level) {
		return clearSettings.getValue(level);
	}
}
