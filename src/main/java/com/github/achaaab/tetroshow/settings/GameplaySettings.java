package com.github.achaaab.tetroshow.settings;

/**
 * gameplay settings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GameplaySettings {

	private int holdLimit;
	private String gravity;
	private String are;
	private String lineAre;
	private String das;
	private String lock;
	private String clear;

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
	 * @return gravity style
	 * @since 0.0.0
	 */
	public String getGravity() {
		return gravity;
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
	 * @param gravity gravity style
	 * @since 0.0.0
	 */
	public void setGravity(String gravity) {

		this.gravity = gravity;

		gravitySettings = new LevelSettings("gravity", gravity, 4);
	}

	/**
	 * @return ARE style
	 * @since 0.0.0
	 */
	public String getAre() {
		return are;
	}

	/**
	 * @param level Tetroshow level
	 * @return entry delay at given delay (in frames)
	 */
	public int getAre(int level) {
		return areSettings.getValue(level);
	}

	/**
	 * @param are ARE style
	 * @since 0.0.0
	 */
	public void setAre(String are) {

		this.are = are;

		areSettings = new LevelSettings("are", are, 27);
	}

	/**
	 * @return line ARE style
	 * @since 0.0.0
	 */
	public String getLineAre() {
		return lineAre;
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
	 * @param lineAre line ARE style
	 * @since 0.0.0
	 */
	public void setLineAre(String lineAre) {

		this.lineAre = lineAre;

		lineAreSettings = new LevelSettings("line-are", lineAre, 27);
	}

	/**
	 * @return DAS style (Delayed Auto Shift)
	 * @since 0.0.0
	 */
	public String getDas() {
		return das;
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
	 * @param das DAS style (Delayed Auto Shift)
	 * @since 0.0.0
	 */
	public void setDas(String das) {

		this.das = das;

		dasSettings = new LevelSettings("das", das, 16);
	}

	/**
	 * @return lock style
	 * @since 0.0.0
	 */
	public String getLock() {
		return lock;
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
	 * @param lock lock style
	 * @since 0.0.0
	 */
	public void setLock(String lock) {

		this.lock = lock;

		lockSettings = new LevelSettings("lock", lock, 30);
	}

	/**
	 * @return clear style
	 * @since 0.0.0
	 */
	public String getClear() {
		return clear;
	}

	/**
	 * @param level Tetroshow level
	 * @return clear delay at given level (in frames)
	 * @since 0.0.0
	 */
	public int getClear(int level) {
		return clearSettings.getValue(level);
	}

	/**
	 * @param clear clear style
	 * @since 0.0.0
	 */
	public void setClear(String clear) {

		this.clear = clear;

		clearSettings = new LevelSettings("clear", clear, 40);
	}
}
