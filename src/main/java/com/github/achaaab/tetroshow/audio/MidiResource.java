package com.github.achaaab.tetroshow.audio;

import org.slf4j.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import java.io.IOException;

import static java.lang.Thread.currentThread;
import static javax.sound.midi.MidiSystem.getSequence;
import static javax.sound.midi.MidiSystem.getSequencer;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * MIDI resource
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MidiResource extends AudioResource {

	private static final Logger LOGGER = getLogger(MidiResource.class);

	public static final String EXTENSION = "mid";
	private static final int END_OF_TRACK = 0x2F;

	private Sequence sequence;

	/**
	 * Creates a new MIDI resource.
	 *
	 * @param name MIDI resource name
	 * @since 0.0.0
	 */
	public MidiResource(String name) {

		super(name);

		try (var inputStream = openInputStream()) {
			sequence = getSequence(inputStream);
		} catch (IOException | InvalidMidiDataException exception) {
			LOGGER.error("MIDI sequence error: {}", name, exception);
		}
	}

	@Override
	public void play() {

		if (sequence != null) {

			try {

				var sequencer = getSequencer();
				sequencer.open();
				sequencer.addMetaEventListener(this::receiveMeta);
				sequencer.setSequence(sequence);
				sequencer.start();

			} catch (MidiUnavailableException midiUnavailableException) {

				LOGGER.error("cannot get a MIDI sequencer", midiUnavailableException);

			} catch (InvalidMidiDataException invalidMidiDataException) {

				LOGGER.error("invalid MIDI sequence: {}", name, invalidMidiDataException);
			}
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

	/**
	 * @param metaMessage MIDI message containing metadata
	 * @since 0.0.0
	 */
	public synchronized void receiveMeta(MetaMessage metaMessage) {

		if (metaMessage.getType() == END_OF_TRACK) {
			notify();
		}
	}
}
