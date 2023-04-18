package com.github.achaaab.tetroshow.audio.wav;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * WAV format chunk
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Format extends Chunk {

	private static final Logger LOGGER = getLogger(Format.class);

	public static final String ID = "fmt ";

	public static final Set<Short> VALID_SAMPLE_SIZES = Set.of(
			(short) 8,
			(short) 16,
			(short) 24,
			(short) 32);

	/**
	 * (Pulse Code Modulation) = linear quantization
	 */
	public static final short PCM = 1;

	private final short compressionCode;
	private final short channelCount;
	private final int frameRate;
	private final int byteRate;
	private final short frameSize;
	private final short sampleSize;

	/**
	 * Creates a new WAV format chunk.
	 *
	 * @param inputStream input stream from which to read the format
	 * @throws IOException if an I/O error occurs while reading the format
	 * @since 0.0.0
	 */
	public Format(InputStream inputStream) throws IOException {

		super(ID, null, inputStream);

		compressionCode = readPositiveShort();
		if (compressionCode != PCM) {
			LOGGER.error("unsupported compression code: {}, only {} is currently supported", compressionCode, PCM);
		}

		channelCount = readPositiveShort();
		frameRate = readPositiveInt();
		byteRate = readPositiveInt();
		frameSize = readPositiveShort();

		if (frameSize * frameRate != byteRate) {
			LOGGER.error("inconsistent byte rate: {}, it must be {}", byteRate, frameSize * frameRate);
		}

		sampleSize = readPositiveShort();

		if (!VALID_SAMPLE_SIZES.contains(sampleSize)) {
			LOGGER.error("invalid sample size: {}, it must be one of {}", sampleSize, VALID_SAMPLE_SIZES);
		}

		if (sampleSize / 8 * channelCount != frameSize) {
			LOGGER.error("inconsistent frame size: {}, it must be {}", frameSize, sampleSize / 8 * channelCount);
		}
	}

	/**
	 * Currently, the only supported compression code is {@link #PCM}.
	 *
	 * @return compression code
	 * @since 0.0.0
	 */
	public short compressionCode() {
		return compressionCode;
	}

	/**
	 * @return number of channels
	 * @since 0.0.0
	 */
	public short channelCount() {
		return channelCount;
	}

	/**
	 * @return number of frames per second (in Hertz)
	 * @since 0.0.0
	 */
	public int frameRate() {
		return frameRate;
	}

	/**
	 * @return number of bytes per second
	 * @since 0.0.0
	 */
	public int byteRate() {
		return byteRate;
	}

	/**
	 * @return frame size (in bytes)
	 * @since 0.0.0
	 */
	public short frameSize() {
		return frameSize;
	}

	/**
	 * @return sample size (in bits), it must be one of {@link #VALID_SAMPLE_SIZES}
	 * @since 0.0.0
	 */
	public short sampleSize() {
		return sampleSize;
	}
}
