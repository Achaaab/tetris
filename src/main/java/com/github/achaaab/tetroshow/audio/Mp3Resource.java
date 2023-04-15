package com.github.achaaab.tetroshow.audio;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.DecoderException;
import javazoom.jl.decoder.SampleBuffer;
import org.slf4j.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;

import static javax.sound.sampled.AudioSystem.getLine;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * MP3 resource
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Mp3Resource extends AudioResource implements Runnable {

	private static final Logger LOGGER = getLogger(Mp3Resource.class);

	public static final String EXTENSION = "mp3";
	private static final boolean SIGNED = true;
	private static final boolean BIG_ENDIAN = false;
	private static final int SAMPLE_SIZE = 16;

	private final Decoder decoder;

	private Bitstream bitstream;
	private SampleBuffer frame;
	private SourceDataLine line;
	private byte[] lineBuffer;

	/**
	 * Creates a new MP3 resource.
	 *
	 * @param name MP3 resource name
	 * @since 0.0.0
	 */
	public Mp3Resource(String name) {

		super(name);

		decoder = new Decoder();
	}

	/**
	 * Plays this MP3 and waits until it ends.
	 *
	 * @since 0.0.0
	 */
	public void playAndWait() {
		run();
	}

	@Override
	public void play() {
		new Thread(this, "MP3 playback").start();
	}

	@Override
	public void run() {

		try (var inputStream = openInputStream()) {

			bitstream = new Bitstream(inputStream);

			while (readFrame()) {
				playFrame();
			}

			bitstream.close();
			line.close();

		} catch (BitstreamException | DecoderException | LineUnavailableException | IOException exception) {

			LOGGER.error("MP3 decoding error: {}", name, exception);
		}
	}

	/**
	 * Reads and decodes an MP3 frame.
	 *
	 * @return whether a frame was read and decoded
	 * @throws BitstreamException error while reading an MP3 frame from this resource
	 * @throws DecoderException error while decoding an MP3 frame read from this resource
	 * @since 0.0.0
	 */
	private boolean readFrame() throws BitstreamException, DecoderException {

		var header = bitstream.readFrame();

		if (header == null) {
			frame = null;
		} else {
			frame = (SampleBuffer) decoder.decodeFrame(header, bitstream);
		}

		return frame != null;
	}

	/**
	 * Decodes an MP3 trame and writes it to the line buffer.
	 *
	 * @throws LineUnavailableException
	 * @since 0.0.0
	 */
	private void playFrame() throws LineUnavailableException {

		ensureLine(frame);

		var samples = frame.getBuffer();
		var sampleCount = frame.getBufferLength();
		var byteCount = sampleCount * 2;
		ensureLineBuffer(byteCount);

		var lineBufferIndex = 0;
		var sampleIndex = 0;

		while (sampleIndex < sampleCount) {

			var sample = samples[sampleIndex++];
			lineBuffer[lineBufferIndex++] = (byte) sample;
			lineBuffer[lineBufferIndex++] = (byte) (sample >>> 8);
		}

		line.write(lineBuffer, 0, sampleCount * 2);
		bitstream.closeFrame();
	}

	/**
	 * Ensures a source data line able to read MP3
	 * Ouvre une ligne capable de lire la trame, ne fait rien si une telle ligne est déjà ouverte.
	 *
	 * @param frame MP3 frame to play
	 * @throws LineUnavailableException error while getting an available line or while opening it
	 * @since 0.0.0
	 */
	private void ensureLine(SampleBuffer frame) throws LineUnavailableException {

		if (line == null) {

			var frameRate = frame.getSampleFrequency();
			var channelCount = frame.getChannelCount();
			var format = new AudioFormat(frameRate, SAMPLE_SIZE, channelCount, SIGNED, BIG_ENDIAN);
			var informationLigne = new Info(SourceDataLine.class, format);

			line = (SourceDataLine) getLine(informationLigne);
			line.open(format);
			line.start();
		}
	}

	/**
	 * Ensures the line buffer is created and long enough.
	 *
	 * @param length necessary length
	 * @since 0.0.0
	 */
	private void ensureLineBuffer(int length) {

		if (lineBuffer == null || lineBuffer.length < length) {
			lineBuffer = new byte[length];
		}
	}
}
