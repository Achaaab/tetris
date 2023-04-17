package com.github.achaaab.tetroshow.audio;

import com.github.achaaab.tetroshow.codec.wav.Format;
import com.github.achaaab.tetroshow.codec.wav.WavFile;
import org.slf4j.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;

import static com.github.achaaab.tetroshow.codec.wav.WavFile.BYTE_ORDER;
import static java.nio.ByteOrder.BIG_ENDIAN;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * WAV resource
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class WavResource extends AudioResource {

	private static final Logger LOGGER = getLogger(WavResource.class);

	public static final String EXTENSION = "wav";

	/**
	 * We keep up to 1 MiB of data in memory, resources bigger than that are read from the disk everytime.
	 */
	private static final int MEMORY_THRESHOLD = 1024 * 1024;

	private static final boolean SIGNED = true;

	private AudioFormat audioFormat;
	private byte[] data;

	/**
	 * Creates a new WAV resource.
	 *
	 * @param name WAV resource name
	 * @since 0.2.0
	 */
	public WavResource(String name) {

		super(name);

		try (var inputStream = openInputStream()) {

			var file = new WavFile(inputStream);
			Format format = file.format();

			var frameRate = format.frameRate();
			var channelCount = format.channelCount();
			var sampleSize = format.sampleSize();
			audioFormat = new AudioFormat(frameRate, sampleSize, channelCount, SIGNED, BYTE_ORDER == BIG_ENDIAN);

			var dataSize = file.data().size();

			if (dataSize <= MEMORY_THRESHOLD) {

				data = new byte[dataSize];
				var length = inputStream.read(data);

				if (length != dataSize) {
					throw new IOException("not enough data read " + length + "/" + dataSize);
				}
			}

		} catch (IOException exception) {

			LOGGER.error("WAV decoding error: {}", name, exception);
		}
	}

	@Override
	public AudioFormat getFormat() {
		return audioFormat;
	}

	@Override
	public void play(SourceDataLine line) {

		if (data == null) {

			try (var inputStream = openInputStream()) {

				new WavFile(inputStream);
				var buffer = new byte[4096];

				int length;

				while ((length = inputStream.read(buffer)) != -1) {
					line.write(buffer, 0, length);
				}

			} catch (IOException ioException) {

				LOGGER.error("error while playing {}", name, ioException);
			}

		} else {

			line.write(data, 0, data.length);
		}
	}
}
