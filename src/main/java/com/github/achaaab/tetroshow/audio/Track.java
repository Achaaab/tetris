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
	 * @param volume volume (in {@code [0, 10]})
	 * @return signal gain (in dB)
	 * @since 0.0.0
	 */
	private static float convertVolumeToGain(int volume) {
		return (float) (20 * log10(volume / VOLUME_SCALE));
	}

	protected AudioFormat format;
	protected SourceDataLine line;
	protected float gain;

	/**
	 * Creates a new track.
	 *
	 * @param name resource name
	 * @param volume volume (in {@code [0, 10]})
	 * @since 0.0.0
	 */
	public Track(String name, int volume) {

		super(name);

		setVolume(volume);
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
			applyGain();

		} catch (LineUnavailableException lineUnavailableException) {

			LOGGER.error("audio line unavailable", lineUnavailableException);
			line = null;
		}
	}

	@Override
	public void setVolume(int volume) {

		gain = convertVolumeToGain(volume);
		applyGain();
	}

	/**
	 * Applies gain to open line.
	 *
	 * @since 0.0.0
	 */
	private void applyGain() {

		if (line != null) {

			var gainControl = (FloatControl) line.getControl(MASTER_GAIN);
			gainControl.setValue(gain);
		}
	}
}
