package com.github.achaaab.tetroshow.codec.wav;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static com.github.achaaab.tetroshow.codec.wav.WavFile.BYTE_ORDER;
import static java.nio.ByteBuffer.wrap;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * WAV chunk
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Chunk {

	private static final Logger LOGGER = getLogger(Chunk.class);

	public static final String DATA = "data";

	protected static final int STRING_SIZE = 4;
	protected static final int INT_SIZE = 4;
	protected static final int SHORT_SIZE = 2;

	protected final String id;
	protected final int size;

	protected ByteBuffer buffer;
	private int offset;

	/**
	 * Creates and read a new WAV chunk.
	 *
	 * @param expectedId expected chunk id, can be {@code null}
	 * @param forcedSize forced size (overriding size field in this chunk), can be {@code null}
	 * @param inputStream input stream from which to read the WAV chunk
	 * @throws IOException if an I/O error occurs while reading the WAV chunk
	 * @since 0.0.0
	 */
	public Chunk(String expectedId, Integer forcedSize, InputStream inputStream) throws IOException {

		var bytes = new byte[STRING_SIZE + INT_SIZE];
		var length = inputStream.read(bytes);

		if (length != STRING_SIZE + INT_SIZE) {
			throw new IOException("not enough data read " + length + "/" + (STRING_SIZE + INT_SIZE));
		}

		buffer = wrap(bytes);
		buffer.order(BYTE_ORDER);
		id = readString();

		if (expectedId != null && !id.equals(expectedId)) {
			LOGGER.warn("unexpected chunk id: {}, it should be {}", id, expectedId);
		}

		size = forcedSize == null ?
				readPositiveInt() :
				forcedSize;

		if (!id.equals(DATA)) {

			bytes = new byte[size];
			length = inputStream.read(bytes);

			if (length != size) {
				throw new IOException("not enough data read " + length + "/" + size);
			}

			buffer = wrap(bytes);
			buffer.order(BYTE_ORDER);
			offset = 0;
		}
	}

	/**
	 * Reads a string from this chunk, at current offset.
	 *
	 * @return read string
	 * @since 0.0.0
	 */
	protected String readString() {

		return String.valueOf(
				(char) buffer.get(offset++)) +
				(char) buffer.get(offset++) +
				(char) buffer.get(offset++) +
				(char) buffer.get(offset++);
	}

	/**
	 * Reads a positive or zero 32 bits integer from this chunk, at current offset.
	 *
	 * @return read integer
	 * @since 0.0.0
	 */
	protected int readPositiveInt() {

		var value = buffer.getInt(offset);
		offset += INT_SIZE;

		if (value < 0) {
			LOGGER.warn("unsupported value: {}, only positive or zero values are supported", value);
		}

		return value;
	}

	/**
	 * Reads a positive or zero 16 bits integer from this chunk, at current offset.
	 *
	 * @return read integer
	 * @since 0.0.0
	 */
	protected short readPositiveShort() {

		var value = buffer.getShort(offset);
		offset += SHORT_SIZE;

		if (value < 0) {
			LOGGER.warn("unsupported value: {}, only positive or zero values are supported", value);
		}

		return value;
	}

	/**
	 * @return chunk id
	 * @since 0.0.0
	 */
	public String id() {
		return id;
	}

	/**
	 * @return chunk size minus 8 (in bytes)
	 * @since 0.0.0
	 */
	public int size() {
		return size;
	}
}
