package com.github.achaaab.tetroshow.audio;

import com.github.achaaab.tetroshow.codec.wav.Format;
import com.github.achaaab.tetroshow.codec.wav.WavFile;
import org.slf4j.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

import static com.github.achaaab.tetroshow.codec.wav.WavFile.BYTE_ORDER;
import static java.lang.Math.round;
import static java.nio.ByteOrder.BIG_ENDIAN;
import static java.time.Duration.ofMillis;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * WAV resource
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class WavResource extends AudioResource implements Runnable {

	private static final Logger LOGGER = getLogger(WavResource.class);

	public static final String EXTENSION = "wav";

	/**
	 * We keep up to 1 MiB of data in memory, resources bigger than that are read from the disk everytime.
	 */
	private static final int MEMORY_THRESHOLD = 1024 * 1024;

	/**
	 * buffer duration
	 */
	private static final Duration BUFFER_DURATION = ofMillis(100);

	private static final boolean SIGNED = true;

	private Format format;
	private byte[] data;

	/**
	 * Creates a new WAV resource.
	 *
	 * @param name WAV resource name
	 * @since 0.2.0
	 */
	public WavResource(String name) {
		super(name);
	}

	@Override
	public void run() {

		if (data != null) {

			playMemoryData();

		} else {

			try (var inputStream = openInputStream()) {

				var file = new WavFile(inputStream);
				format = file.format();
				var dataSize = file.data().size();

				if (dataSize > MEMORY_THRESHOLD) {

					playInputStream(inputStream);

				} else {

					data = new byte[dataSize];
					var length = inputStream.read(data);

					if (length != dataSize) {
						throw new IOException("not enough data read " + length + "/" + dataSize);
					}

					playMemoryData();
				}

			} catch (IOException exception) {

				LOGGER.error("WAV decoding error: {}", name, exception);
			}
		}
	}

	/**
	 * PLays WAV data from given input stream until the end.
	 *
	 * @param inputStream input stream from which to read WAV data
	 * @since 0.0.0
	 */
	private void playInputStream(InputStream inputStream) {

		var frameRate = format.frameRate();
		var frameSize = format.frameSize();
		var milliseconds = BUFFER_DURATION.toMillis();
		var frameCount = round(milliseconds * frameRate / 1000.0f);

		var buffer = new byte[frameCount * frameSize];

		try (var line = openLine(format)) {

			int length;

			while ((length = inputStream.read(buffer)) != -1) {
				line.write(buffer, 0, length);
			}

			line.drain();

		} catch (LineUnavailableException | IOException exception) {

			LOGGER.error("WAV playing error: {}", name, exception);
		}
	}

	/**
	 * Plays in memory WAV data (for small resources).
	 *
	 * @since 0.0.0
	 */
	private void playMemoryData() {

		try (var line = openLine(format)) {

			line.write(data, 0, data.length);
			line.drain();

		} catch (LineUnavailableException exception) {

			LOGGER.error("WAV playing error: {}", name, exception);
		}
	}

	@Override
	public void playAndWait() {
		run();
	}

	@Override
	public void play() {
		new Thread(this, "WAV playback").start();
	}

	/**
	 * Ensures there is a source data line able to read data.
	 *
	 * @param format WAV format chunk
	 * @return open line
	 * @throws LineUnavailableException error while getting an available line or while opening it
	 * @since 0.0.0
	 */
	private SourceDataLine openLine(Format format) throws LineUnavailableException {

		var mixer = AudioSystem.getMixer(AudioSystem.getMixerInfo()[0]);
		mixer.getSourceLines();

		var frameRate = format.frameRate();
		var channelCount = format.channelCount();
		var sampleSize = format.sampleSize();
		var audioFormat = new AudioFormat(frameRate, sampleSize, channelCount, SIGNED, BYTE_ORDER == BIG_ENDIAN);
		var lineInformation = new DataLine.Info(SourceDataLine.class, audioFormat);

		var line = (SourceDataLine) AudioSystem.getLine(lineInformation);
		line.open(audioFormat);
		line.start();
		return line;
	}
}
