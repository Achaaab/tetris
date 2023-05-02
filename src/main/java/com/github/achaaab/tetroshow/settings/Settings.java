package com.github.achaaab.tetroshow.settings;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Map;

import static com.github.achaaab.tetroshow.utility.ResourceUtility.openInputStream;
import static java.lang.System.getProperty;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.isRegularFile;
import static java.nio.file.Files.newBufferedWriter;
import static java.nio.file.Files.newInputStream;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Tetroshow settings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Settings {

	private static final Logger LOGGER = getLogger(Settings.class);

	private static final String FILE_NAME = "settings.yaml";
	private static final String USER_HOME_PROPERTY = getProperty("user.home");
	private static final String APPLICATION_DIRECTORY_NAME = ".tetroshow";

	private static final Path USER_HOME_DIRECTORY = Path.of(USER_HOME_PROPERTY);
	private static final Path APPLICATION_DIRECTORY = USER_HOME_DIRECTORY.resolve(APPLICATION_DIRECTORY_NAME);
	private static final Path USER_CONFIGURATION_FILE = APPLICATION_DIRECTORY.resolve(FILE_NAME);

	private static final Settings DEFAULT_INSTANCE = load();

	/**
	 * @return default settings
	 * @since 0.0.0
	 */
	public static Settings getDefaultInstance() {
		return DEFAULT_INSTANCE;
	}

	/**
	 * Loads user (in priority) or default settings.
	 *
	 * @return default settings
	 * @since 0.0.0
	 */
	private static Settings load() {

		Settings settings;

		if (isRegularFile(USER_CONFIGURATION_FILE)) {

			try {

				settings = loadUserSettings();
				LOGGER.info("user configuration loaded from: {}", USER_CONFIGURATION_FILE);

			} catch (IOException ioException) {

				LOGGER.error("unable to load user configuration file: {}", USER_CONFIGURATION_FILE, ioException);
				settings = loadDefaultSettings();
			}

		} else {

			settings = loadDefaultSettings();
		}

		return settings;
	}

	/**
	 * Loads user settings.
	 *
	 * @return loaded user settings
	 * @throws IOException if an I/O error occurs while loading user settings
	 * @since 0.0.0
	 */
	private static Settings loadUserSettings() throws IOException {

		try (var inputStream = newInputStream(USER_CONFIGURATION_FILE)) {

			var yaml = new Yaml();
			return yaml.loadAs(inputStream, Settings.class);
		}
	}

	/**
	 * Loads default settings.
	 *
	 * @return loaded default settings
	 * @since 0.0.0
	 */
	private static Settings loadDefaultSettings() {

		try (var inputStream = openInputStream(FILE_NAME)) {

			var yaml = new Yaml();
			return yaml.loadAs(inputStream, Settings.class);

		} catch (IOException cause) {

			LOGGER.error("unable to load default configuration file: {}", FILE_NAME);
			throw new UncheckedIOException(cause);
		}
	}

	private String language;
	private Map<String, Integer> keys;
	private GraphicsSettings graphics;
	private GameplaySettings gameplay;
	private AudioSettings audio;

	/**
	 * private constructor
	 *
	 * @see #DEFAULT_INSTANCE
	 * @since 0.0.0
	 */
	private Settings() {

	}

	/**
	 * Save settings in user configuration file.
	 *
	 * @since 0.0.0
	 */
	public void save() {

		try {

			createDirectories(APPLICATION_DIRECTORY);

			try (var writer = newBufferedWriter(USER_CONFIGURATION_FILE)) {

				var objectMapper = new YAMLMapper();
				objectMapper.writeValue(writer, this);

				LOGGER.info("settings saved to {}", USER_CONFIGURATION_FILE);
			}

		} catch (IOException cause) {

			LOGGER.error("unable to save user configuration file: {}", USER_CONFIGURATION_FILE, cause);
		}
	}

	/**
	 * @return language
	 * @since 0.0.0
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language language
	 * @since 0.0.0
	 */
	public void setLanguage(String language) {
		this.language = language;
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
	 * @return audio settings
	 * @since 0.0.0
	 */
	public AudioSettings getAudio() {
		return audio;
	}

	/**
	 * @param audio audio settings
	 * @since 0.0.0
	 */
	public void setAudio(AudioSettings audio) {
		this.audio = audio;
	}
}
