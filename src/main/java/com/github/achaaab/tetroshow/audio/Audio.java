package com.github.achaaab.tetroshow.audio;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface Audio {

	/**
	 * @return name of this audio
	 * @since 0.0.0
	 */
	String getName();

	/**
	 * Plays this sound asynchronously.
	 *
	 * @since 0.0.0
	 */
	void play();

	/**
	 * Plays this sound synchronously.
	 *
	 * @since 0.0.0
	 */
	void playAndWait();
}
