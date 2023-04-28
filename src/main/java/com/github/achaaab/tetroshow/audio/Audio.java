package com.github.achaaab.tetroshow.audio;

/**
 * audio
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface Audio {

	double VOLUME_SCALE = 10.0;

	/**
	 * @return name of this audio
	 * @since 0.0.0
	 */
	String name();

	/**
	 * @param volume in {@code [0, 10]}
	 * @since 0.0.0
	 */
	void setVolume(int volume);

	/**
	 * Plays this audio.
	 *
	 * @since 0.0.0
	 */
	void play();
}
