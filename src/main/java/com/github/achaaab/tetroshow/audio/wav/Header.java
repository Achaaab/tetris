package com.github.achaaab.tetroshow.audio.wav;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * WAV file header chunk
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Header extends Chunk {

	private static final Logger LOGGER = getLogger(Header.class);

	public static final int SIZE = STRING_SIZE;
	public static final String ID = "RIFF";
	public static final String TYPE = "WAVE";

	private final String type;

	/**
	 * Creates a new WAV header chunk.
	 *
	 * @param inputStream input stream from which to read the header
	 * @throws IOException if an I/O error occurs while reading the header
	 * @since 0.0.0
	 */
	public Header(InputStream inputStream) throws IOException {

		super(ID, SIZE, inputStream);

		type = readString();

		if (!type.equals(TYPE)) {
			LOGGER.warn("unexpected type: {}, it should be {}", type, TYPE);
		}
	}

	/**
	 * Header chunk size is not the chunk size but the whole file size minus 8.
	 *
	 * @return file size minus 8 (in bytes)
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * @return header type, should be {@link #TYPE}
	 * @since 0.0.0
	 */
	public String getType() {
		return type;
	}
}
