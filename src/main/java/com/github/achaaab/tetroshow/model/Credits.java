package com.github.achaaab.tetroshow.model;

import com.github.achaaab.tetroshow.scene.CreditsScene;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.FileSystems.newFileSystem;
import static java.nio.file.Files.readAllLines;
import static java.util.Collections.emptyMap;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Credits implements GameComponent {

	private static final Logger LOGGER = getLogger(Credits.class);

	private static final String RESOURCE_NAME = "credits.txt";
	private static final List<String> LINES = readCredits();

	/**
	 * @return credits, line by line, or {@code null} if credits are missing or cannot be read
	 * @since 0.0.0
	 */
	private static List<String> readCredits() {

		List<String> credits;

		var classLoader = CreditsScene.class.getClassLoader();
		var resource = classLoader.getResource(RESOURCE_NAME);

		if (resource == null) {

			LOGGER.error("missing credits {}", RESOURCE_NAME);
			credits = null;

		} else {

			try {

				var uri = resource.toURI();
				var scheme = uri.getScheme();

				if (scheme.equals("jar")) {
					newFileSystem(uri, emptyMap());
				}

				var path = Path.of(resource.toURI());
				credits = readAllLines(path, UTF_8);

			} catch (URISyntaxException | IOException cause) {

				LOGGER.error("error while reading credits {}", RESOURCE_NAME, cause);
				credits = null;
			}
		}

		return credits;
	}

	private double time;

	/**
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
