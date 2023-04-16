package com.github.achaaab.tetroshow.codec.wav;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.github.achaaab.tetroshow.codec.wav.Chunk.DATA;
import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * WAV file using the Resource Interchange File Format (RIFF) file structure.
 * It assumes that data chunk is always the last chunk and does not read it.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class WavFile {

	public static final ByteOrder BYTE_ORDER = LITTLE_ENDIAN;
	public static final Charset CHARSET = US_ASCII;

	private final Header header;
	private final Format format;
	private final Chunk data;
	private final List<Chunk> chunks;

	/**
	 * Reads a WAV file from a given input stream.
	 *
	 * @param inputStream input stream from which to read the WAV file
	 * @throws IOException if an I/O error occurs while reading the WAV file
	 * @since 0.0.0
	 */
	public WavFile(InputStream inputStream) throws IOException {

		chunks = new ArrayList<>();

		header = new Header(inputStream);
		chunks.add(header);

		format = new Format(inputStream);
		chunks.add(format);

		Chunk chunk;

		do {

			chunk = new Chunk(null, null, inputStream);
			chunks.add(chunk);


		} while (!chunk.id.equals(DATA));

		data = chunk;
	}

	/**
	 * @return header chunk
	 * @since 0.0.0
	 */
	public Header header() {
		return header;
	}

	/**
	 * @return format chunk
	 * @since 0.0.0
	 */
	public Format format() {
		return format;
	}

	/**
	 * @return data chunk
	 * @since 0.0.0
	 */
	public Chunk data() {
		return data;
	}

	/**
	 * @return all chunks
	 * @since 0.0.0
	 */
	public List<Chunk> chunks() {
		return chunks;
	}
}
