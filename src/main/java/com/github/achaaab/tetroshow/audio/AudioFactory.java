package com.github.achaaab.tetroshow.audio;

import com.github.achaaab.tetroshow.audio.wav.WavResource;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * audio factory, not thread-safe
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class AudioFactory {

	private static final Logger LOGGER = getLogger(AudioFactory.class);

	private static final Map<String, Audio> LOADED_AUDIOS = new HashMap<>();

	/**
	 * Gets an audio from the given path.
	 *
	 * @param path audio path
	 * @return created audio
	 * @since 0.0.0
	 */
	public static Audio getAudio(String path) {
		return LOADED_AUDIOS.computeIfAbsent(path, AudioFactory::loadAudio);
	}

	/**
	 * Loads an audio from the given path and register it for further usages.
	 *
	 * @param path path of the audio to load
	 * @return loaded and registered audio
	 * @see #LOADED_AUDIOS
	 * @since 0.0.0
	 */
	private static Audio loadAudio(String path) {

		Audio audio = null;

		var extensionIndex = path.lastIndexOf('.') + 1;

		if (extensionIndex == 0 || extensionIndex == path.length()) {

			LOGGER.error("cannot recognize audio without extension: {}", path);

		} else {

			var extension = path.substring(extensionIndex);

			switch (extension) {

				case Mp3Resource.EXTENSION -> audio = new Mp3Resource(path);
				case WavResource.EXTENSION -> audio = new WavResource(path);
				default -> LOGGER.error("unknown audio extension: {}", path);
			}
		}

		return audio;
	}
}
