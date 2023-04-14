package com.github.achaaab.tetroshow.audio;

import com.github.achaaab.tetroshow.settings.Settings;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Playlist implements Runnable {

	private final List<Audio> tracks;

	/**
	 * @since 0.0.0
	 */
	public Playlist() {

		var trackNames = Settings.getDefaultInstance().getTracks();

		tracks = trackNames.stream().
				map(AudioFactory::createAudio).
				collect(toList());
	}

	@Override
	public void run() {

		while (!tracks.isEmpty()) {
			tracks.forEach(Audio::playAndWait);
		}
	}
}
