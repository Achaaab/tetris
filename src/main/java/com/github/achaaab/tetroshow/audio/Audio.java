package com.github.achaaab.tetroshow.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

/**
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
	 * @return format of this audio, {@code null} if the audio could not be decoded
	 * @since 0.0.0
	 */
	AudioFormat getFormat();

	/**
	 * Plays this audio.
	 *
	 * @param line line on which to play
	 * @since 0.0.0
	 */
	void play(SourceDataLine line);
}
