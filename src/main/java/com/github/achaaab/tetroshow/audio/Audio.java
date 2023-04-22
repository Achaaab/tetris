package com.github.achaaab.tetroshow.audio;

/**
 * audio
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface Audio {

	/**
	 * @return name of this audio
	 * @since 0.0.0
	 */
	String name();

	/**
	 * @param volume in {@code [0.0, 1.0]}
	 * @since 0.0.0
	 */
	void setVolume(double volume);

	/**
	 * Plays this audio.
	 *
	 * @since 0.0.0
	 */
	void play();
}
