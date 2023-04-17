package com.github.achaaab.tetroshow.audio;

import org.slf4j.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.System.nanoTime;
import static javax.sound.sampled.FloatControl.Type.MASTER_GAIN;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * audio player, not thread-safe
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class AudioPlayer {

	private static final Logger LOGGER = getLogger(AudioPlayer.class);

	public static final AudioPlayer BACKGROUND = new AudioPlayer();
	public static final AudioPlayer SOUND_EFFECT = new AudioPlayer();
	private static final long MINIMUM_DELAY_BETWEEN_REPETITION = 20_000_000;

	private final List<SourceDataLine> lines;
	private final Map<Audio, Long> lastPlayed;
	private float gain;

	/**
	 * Creates a new audio player.
	 *
	 * @see #SOUND_EFFECT
	 * @since 0.0.0
	 */
	private AudioPlayer() {

		lines = new ArrayList<>();
		lastPlayed = new HashMap<>();
		gain = 0.0f;
	}

	/**
	 * @param gain gain in dB
	 * @since 0.0.0
	 */
	public void setGain(float gain) {

		this.gain = gain;

		lines.forEach(this::applyGain);
	}

	/**
	 * @param line line on which to apply gain
	 * @since 0.0.0
	 */
	private void applyGain(SourceDataLine line) {

		var gainControl = (FloatControl) line.getControl(MASTER_GAIN);
		gainControl.setValue(gain);
	}

	/**
	 * @param audio
	 * @param wait
	 * @since 0.0.0
	 */
	public void play(Audio audio, boolean wait) {

		if (shouldPlay(audio)) {

			var format = audio.getFormat();

			getAvailableLine(format).ifPresent(line -> {

				if (wait) {
					play(audio, line);
				} else {
					new Thread(() -> play(audio, line)).start();
				}
			});
		}
	}

	/**
	 * Tests if a play request should be honored.
	 *
	 * @param audio audio requested to be played
	 * @return whether it should be played
	 * @since 0.0.0
	 */
	private boolean shouldPlay(Audio audio) {

		var shouldPlay = false;

		if (audio != null && audio.getFormat() != null) {

			var lastPlayedTime = lastPlayed.get(audio);
			var time = nanoTime();

			if (lastPlayedTime == null || time - lastPlayedTime > MINIMUM_DELAY_BETWEEN_REPETITION) {

				lastPlayed.put(audio, time);
				shouldPlay = true;
			}
		}

		return shouldPlay;
	}

	/**
	 * @param audio
	 * @param line
	 * @since 0.0.0
	 */
	private void play(Audio audio, SourceDataLine line) {

		audio.play(line);
		line.drain();
	}

	/**
	 * @param format
	 * @return
	 * @since 0.0.0
	 */
	public Optional<SourceDataLine> getAvailableLine(AudioFormat format) {

		return lines.stream().
				filter(this::isAvailable).
				filter(line -> hasFormat(line, format)).
				findFirst().
				or(() -> openLine(format));
	}

	/**
	 * Tries to open a new source data line.
	 *
	 * @param format desired audio format
	 * @return opened source data line, or empty if there is no available source data line for the given format
	 * @since 0.0.0
	 */
	private Optional<SourceDataLine> openLine(AudioFormat format) {

		var lineInformations = new DataLine.Info(SourceDataLine.class, format);

		try {

			var line = (SourceDataLine) AudioSystem.getLine(lineInformations);
			line.open(format);
			applyGain(line);
			line.start();
			lines.add(line);

			LOGGER.info("there are now {} lines open", lines.size());

			return Optional.of(line);

		} catch (LineUnavailableException lineUnavailableException) {

			LOGGER.error("audio line unavailable", lineUnavailableException);
			return Optional.empty();
		}
	}

	/**
	 * @param line any line
	 * @return whether the given line is available for playback
	 * @since 0.0.0
	 */
	private boolean isAvailable(SourceDataLine line) {
		return line.available() == line.getBufferSize();
	}

	/**
	 * @param line any line
	 * @param format any audio format
	 * @return whether the given line has the given format
	 * @since 0.0.0
	 */
	private boolean hasFormat(SourceDataLine line, AudioFormat format) {

		var lineFormat = line.getFormat();

		return lineFormat.getSampleRate() == format.getSampleRate() &&
				lineFormat.getSampleSizeInBits() == format.getSampleSizeInBits() &&
				lineFormat.getChannels() == format.getChannels() &&
				lineFormat.getEncoding().equals(format.getEncoding()) &&
				lineFormat.isBigEndian() == format.isBigEndian();
	}
}
