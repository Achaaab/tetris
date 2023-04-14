package com.github.achaaab.tetroshow.settings;

import com.github.achaaab.tetroshow.Application;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Settings {

	private static final String RESOURCE_NAME = "tetroshow.yaml";

	private static final Settings DEFAULT_INSTANCE = load();

	/**
	 * @return default settings
	 * @since 0.0.0
	 */
	public static Settings getDefaultInstance() {
		return DEFAULT_INSTANCE;
	}

	/**
	 * Loads default settings.
	 *
	 * @return default settings
	 * @since 0.0.0
	 */
	private static Settings load() {

		var yaml = new Yaml();
		var classLoader = Application.class.getClassLoader();
		var settingsInputStream = classLoader.getResourceAsStream(RESOURCE_NAME);
		return yaml.loadAs(settingsInputStream, Settings.class);
	}

	private Map<String, Integer> keys;
	private GraphicsSettings graphics;
	private GameplaySettings gameplay;
	private List<String> tracks;
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
	 * private constructor
	 *
	 * @see #DEFAULT_INSTANCE
	 * @since 0.0.0
	 */
	private Settings() {

	}

	/**
	 * @return configured keys
	 * @since 0.0.0
	 */
	public Map<String, Integer> getKeys() {
		return keys;
	}

	/**
	 * @param keys configured keys
	 * @since 0.0.0
	 */
	public void setKeys(Map<String, Integer> keys) {
		this.keys = keys;
	}

	/**
	 * @return graphics settings
	 * @since 0.0.0
	 */
	public GraphicsSettings getGraphics() {
		return graphics;
	}

	/**
	 * @param graphics graphics settings
	 * @since 0.0.0
	 */
	public void setGraphics(GraphicsSettings graphics) {
		this.graphics = graphics;
	}

	/**
	 * @return gameplay settings
	 * @since 0.0.0
	 */
	public GameplaySettings getGameplay() {
		return gameplay;
	}

	/**
	 * @param gameplay gameplay settings
	 * @since 0.0.0
	 */
	public void setGameplay(GameplaySettings gameplay) {
		this.gameplay = gameplay;
	}

	/**
	 * @return playback tracks
	 * @since 0.0.0
	 */
	public List<String> getTracks() {
		return tracks;
	}

	/**
	 * @param tracks playback tracks
	 * @since 0.0.0
	 */
	public void setTracks(List<String> tracks) {
		this.tracks = tracks;
	}

	/**
	 * @return gravity style
	 * @since 0.0.0
	 */
	public String getGravity() {
		return gravity;
	}

	/**
	 * @param level
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
	 * @param level
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
	 * @param level
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
	 * @param level
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
	 * @param level
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
	 * @param level
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
