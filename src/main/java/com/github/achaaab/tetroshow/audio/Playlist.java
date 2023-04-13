package com.github.achaaab.tetroshow.audio;

import com.github.achaaab.tetroshow.configuration.Configuration;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Playlist implements Runnable {

	private final List<Audio> musics;

	/**
	 * @since 0.0.0
	 */
	public Playlist() {

		var cheminMusiques = Configuration.INSTANCE.getMusicResourceNames();

		musics = cheminMusiques.stream().
				map(AudioFactory::createAudio).
				collect(toList());
	}

	@Override
	public void run() {

		while (!musics.isEmpty()) {
			musics.forEach(Audio::playAndWait);
		}
	}
}
