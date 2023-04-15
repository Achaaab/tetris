package com.github.achaaab.tetroshow.audio;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class AudioFactory {

	private static final Logger LOGGER = getLogger(AudioFactory.class);

	/**
	 * Creates an audio from the given path.
	 *
	 * @param path audio path
	 * @return created audio, {@link Silence#INSTANCE} if the given path is not recognized
	 * @since 0.0.0
	 */
	public static Audio createAudio(String path) {

		Audio audio;

		var extensionIndex = path.lastIndexOf('.') + 1;

		if (extensionIndex == 0 || extensionIndex == path.length()) {

			LOGGER.error("cannot recognize audio without extension: {}", path);
			audio = Silence.INSTANCE;

		} else {

			var extension = path.substring(extensionIndex);

			switch (extension) {

				case Mp3Resource.EXTENSION -> audio = new Mp3Resource(path);
				case WavResource.EXTENSION -> audio = new WavResource(path);
				case MidiResource.EXTENSION -> audio = new MidiResource(path);

				default -> {
					LOGGER.error("unknown audio extension: {}", path);
					audio = Silence.INSTANCE;
				}
			}
		}

		return audio;
	}
}
