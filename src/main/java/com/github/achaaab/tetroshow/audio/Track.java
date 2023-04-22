package com.github.achaaab.tetroshow.audio;

import com.github.achaaab.tetroshow.utility.ResourceUtility;
import org.slf4j.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.MissingResourceException;

import static java.lang.Math.log10;
import static java.lang.Math.round;
import static java.time.Duration.ofMillis;
import static javax.sound.sampled.FloatControl.Type.MASTER_GAIN;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * background music, not intended to be played concurrently or loaded in memory
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class Track extends NamedAudio {

	private static final Logger LOGGER = getLogger(Track.class);

	private static final Duration LINE_BUFFER_DURATION = ofMillis(100);

	/**
	 * @param volume signal amplitude ratio (in {@code [0.0, 1.0]})
	 * @return signal gain (in dB)
	 * @since 0.0.0
	 */
	private static float convertVolumeToGain(double volume) {
		return (float) (20 * log10(volume));
	}

	protected AudioFormat format;
	protected SourceDataLine line;

	/**
	 * Creates a new track.
	 *
	 * @param name resource name
	 * @since 0.0.0
	 */
	public Track(String name) {
		super(name);
	}

	/**
	 * Opens a resource input stream.
	 *
	 * @return input stream
	 * @since 0.0.0
	 */
	protected InputStream openInputStream() {

		var inputStream = ResourceUtility.openInputStream(name);

		if (inputStream == null) {
			throw new MissingResourceException("missing audio resource", name, null);
		}

		return new BufferedInputStream(inputStream);
	}

	/**
	 * Tries to open a new source data line.
	 *
	 * @since 0.0.0
	 */
	protected void openLine() {

		var lineInformations = new DataLine.Info(SourceDataLine.class, format);

		try {

			line = (SourceDataLine) AudioSystem.getLine(lineInformations);
			var byteRate = format.getFrameSize() * format.getFrameRate();
			var bufferDuration = LINE_BUFFER_DURATION.toMillis() / 1_000.0f;
			var bufferSize = round(byteRate * bufferDuration);
			line.open(format, bufferSize);

		} catch (LineUnavailableException lineUnavailableException) {

			LOGGER.error("audio line unavailable", lineUnavailableException);
			line = null;
		}
	}

	@Override
	public void setVolume(double volume) {

		var gainControl = (FloatControl) line.getControl(MASTER_GAIN);
		gainControl.setValue(convertVolumeToGain(volume));
	}
}
