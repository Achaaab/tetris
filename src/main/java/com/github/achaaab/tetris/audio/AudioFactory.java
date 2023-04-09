package com.github.achaaab.tetris.audio;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class AudioFactory {

	/**
	 * @param path
	 * @return son créé
	 * @since 0.0.0
	 */
	public static Audio createAudio(String path) {

		Audio audio;

		if (path.endsWith(Mp3Resource.EXTENSION)) {
			audio = new Mp3Resource(path);
		} else if (path.endsWith(WavResource.EXTENSION)) {
			audio = new WavResource(path);
		} else {
			audio = null;
		}

		return audio;
	}
}
