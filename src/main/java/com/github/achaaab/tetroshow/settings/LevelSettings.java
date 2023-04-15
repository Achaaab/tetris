package com.github.achaaab.tetroshow.settings;

import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.binarySearch;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class LevelSettings {

	private static final Logger LOGGER = getLogger(LevelSettings.class);

	private static final String SETTINGS_RESOURCE_SUFFIX = ".yaml";

	private final int[] keyLevels;
	private final int[] keyValues;

	/**
	 * @param type settings type
	 * @param style settings style
	 * @param defaultValue default value to return for every level, if requested settings cannot be found or loaded
	 * @since 0.0.0
	 */
	public LevelSettings(String type, String style, int defaultValue) {

		var resourceName = style + "/" + type + SETTINGS_RESOURCE_SUFFIX;
		var classLoader = LevelSettings.class.getClassLoader();

		Map<Integer, Integer> values;

		try (var inputStream = classLoader.getResourceAsStream(resourceName)) {

			var yaml = new Yaml();
			values = yaml.load(inputStream);

		} catch (IOException cause) {

			LOGGER.error("unable to load {} {} settings", style, type, cause);
			values = Map.of(0, defaultValue);
		}

		keyLevels = values.keySet().stream().mapToInt(Integer::intValue).sorted().toArray();
		var keyLevelCount = keyLevels.length;
		keyValues = new int[keyLevelCount];

		for (var keyLevelIndex = 0; keyLevelIndex < keyLevelCount; keyLevelIndex++) {

			var keyLevel = keyLevels[keyLevelIndex];
			keyValues[keyLevelIndex] = values.get(keyLevel);
		}
	}

	/**
	 * Gets the value at given level, basing on configured key levels.
	 *
	 * @param level wanted level
	 * @return value at given level
	 * @since 0.0.0
	 */
	public int getValue(int level) {

		var index = binarySearch(keyLevels, level);

		if (index < 0) {
			index = min(keyValues.length - 1, max(0, -index - 2));
		}

		return keyValues[index];
	}
}
