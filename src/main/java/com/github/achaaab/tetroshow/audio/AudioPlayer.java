package com.github.achaaab.tetroshow.audio;

import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * An audio player allows to create and manage audios.
 * Audio players are not thread safe.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class AudioPlayer {

	private static final Logger LOGGER = getLogger(AudioPlayer.class);

	private static final Map<String, Track> LOADED_TRACKS = new HashMap<>();
	private static final Map<String, SoundEffect> LOADED_EFFECTS = new HashMap<>();

	/**
	 * Sets track volume.
	 *
	 * @param volume track amplitude ratio (in {@code [0.0, 1.0])}
	 * @since 0.0.0
	 */
	public static void setTrackVolume(double volume) {
		LOADED_TRACKS.values().forEach(track -> track.setVolume(volume));
	}

	/**
	 * Set sound effects volume.
	 *
	 * @param volume sound effects amplitude ratio (in {@code [0.0, 1.0])}
	 * @since 0.0.0
	 */
	public static void setEffectVolume(double volume) {
		LOADED_EFFECTS.values().forEach(effect -> effect.setVolume(volume));
	}

	/**
	 * Gets a track by name.
	 *
	 * @param name track resource name
	 * @return created audio
	 * @since 0.0.0
	 */
	public static Track getTrack(String name) {
		return LOADED_TRACKS.computeIfAbsent(name, AudioPlayer::loadAudio);
	}

	/**
	 * Gets a sound effect by name.
	 *
	 * @param name name of the sound effect resource
	 * @param polyphony maximum number of concurrent instances
	 * @return sound effect
	 */
	public static SoundEffect getSoundEffect(String name, int polyphony) {
		return LOADED_EFFECTS.computeIfAbsent(name, resourceName -> new SoundEffect(resourceName, polyphony));
	}

	/**
	 * Loads an audio from the given path and register it for further usages.
	 *
	 * @param name path of the audio to load
	 * @return loaded and registered audio
	 * @see #LOADED_TRACKS
	 * @since 0.0.0
	 */
	private static Track loadAudio(String name) {

		Track audio = null;

		var extensionIndex = name.lastIndexOf('.') + 1;

		if (extensionIndex == 0 || extensionIndex == name.length()) {

			LOGGER.error("cannot recognize audio without extension: {}", name);

		} else {

			var extension = name.substring(extensionIndex);

			switch (extension) {

				case Mp3Track.EXTENSION -> audio = new Mp3Track(name);
				case WavTrack.EXTENSION -> audio = new WavTrack(name);
				default -> LOGGER.error("unknown audio extension: {}", name);
			}
		}

		return audio;
	}
}
