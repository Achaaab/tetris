package com.github.achaaab.tetris.audio;

import org.slf4j.Logger;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static java.lang.Thread.currentThread;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioSystem.getClip;
import static javax.sound.sampled.LineEvent.Type.STOP;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class WavResource extends AudioResource {

	private static final Logger LOGGER = getLogger(WavResource.class);

	public static final String EXTENSION = "wav";

	private Clip clip;

	/**
	 * @param name
	 * @since 0.0.0
	 */
	public WavResource(String name) {

		super(name);

		try (var stream = openInputStream()) {

			var audioStream = getAudioInputStream(stream);

			clip = getClip();
			clip.open(audioStream);
			clip.addLineListener(this::update);

		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException exception) {

			LOGGER.error("WAV decoding error: {}", name, exception);
		}
	}

	@Override
	public synchronized void playAndWait() {

		play();

		try {

			wait();

		} catch (InterruptedException interruptedException) {

			LOGGER.error("interrupted", interruptedException);
			currentThread().interrupt();
		}
	}

	@Override
	public void play() {

		if (clip != null) {

			LOGGER.debug("playing {}", name);

			clip.setFramePosition(0);
			clip.loop(0);
		}
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	private void update(LineEvent event) {

		if (event.getType() == STOP) {
			notify();
		}
	}
}
