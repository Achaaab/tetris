package com.github.achaaab.tetroshow.audio;

import com.github.achaaab.tetroshow.audio.wav.Format;
import com.github.achaaab.tetroshow.audio.wav.WavFile;
import org.slf4j.Logger;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;

import static com.github.achaaab.tetroshow.audio.wav.WavFile.BYTE_ORDER;
import static java.nio.ByteOrder.BIG_ENDIAN;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * WAV background track
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class WavTrack extends Track {

	private static final Logger LOGGER = getLogger(WavTrack.class);
	public static final String EXTENSION = "wav";
	private static final boolean SIGNED = true;

	/**
	 * Creates a new WAV resource.
	 *
	 * @param name WAV resource name
	 * @since 0.2.0
	 */
	public WavTrack(String name) {

		super(name);

		try (var inputStream = openInputStream()) {

			var file = new WavFile(inputStream);
			Format fileFormat = file.format();

			var frameRate = fileFormat.frameRate();
			var channelCount = fileFormat.channelCount();
			var sampleSize = fileFormat.sampleSize();

			format = new AudioFormat(frameRate, sampleSize, channelCount, SIGNED, BYTE_ORDER == BIG_ENDIAN);
			openLine();

		} catch (IOException exception) {

			LOGGER.error("WAV decoding error: {}", name, exception);
			line = null;
		}
	}

	@Override
	public void play() {

		if (line != null) {

			try (var inputStream = openInputStream()) {

				line.start();

				new WavFile(inputStream);
				var buffer = new byte[4096];

				int length;

				while ((length = inputStream.read(buffer)) != -1) {
					line.write(buffer, 0, length);
				}

			} catch (IOException ioException) {

				LOGGER.error("error while playing {}", name, ioException);

			} finally {

				line.drain();
				line.stop();
			}
		}
	}
}
