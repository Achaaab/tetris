package com.github.achaaab.tetroshow.audio;

import com.github.achaaab.tetroshow.settings.Settings;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static java.time.Duration.ofSeconds;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * playlist, should be run in its own thread
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Playlist implements Runnable {

	private static final Logger LOGGER = getLogger(Playlist.class);

	private static final Duration SILENCE_BETWEEN_TRACKS = ofSeconds(2);

	private final List<Audio> tracks;

	/**
	 * Creates the playlist.
	 *
	 * @since 0.0.0
	 */
	public Playlist() {

		var audioSettings = Settings.getDefaultInstance().getAudio();
		var trackNames = audioSettings.getTracks();

		tracks = trackNames.stream().
				map(AudioPlayer::getTrack).
				collect(toList());
	}

	@Override
	public void run() {

		while (!tracks.isEmpty()) {

			for (var track : tracks) {

				LOGGER.info("playing {}", track.name());
				track.play();

				try {

					sleep(SILENCE_BETWEEN_TRACKS);

				} catch (InterruptedException interruptedException) {

					LOGGER.error("interrupted", interruptedException);
					currentThread().interrupt();
				}
			}
		}
	}
}
