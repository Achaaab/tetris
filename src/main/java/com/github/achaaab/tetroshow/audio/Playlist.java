package com.github.achaaab.tetroshow.audio;

import com.github.achaaab.tetroshow.settings.Settings;
import org.slf4j.Logger;

import java.util.List;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static java.time.Duration.ofSeconds;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Playlist implements Runnable {

	private static final Logger LOGGER = getLogger(Playlist.class);

	private final List<Audio> tracks;

	/**
	 * @since 0.0.0
	 */
	public Playlist() {

		var trackNames = Settings.getDefaultInstance().getTracks();

		tracks = trackNames.stream().
				map(AudioFactory::getAudio).
				collect(toList());
	}

	@Override
	public void run() {

		while (!tracks.isEmpty()) {

			for (var track : tracks) {

				LOGGER.info("playing {}", track.name());
				//AudioPlayer.BACKGROUND.play(track, true);

				try {

					sleep(ofSeconds(2));

				} catch (InterruptedException interruptedException) {

					LOGGER.error("interrupted", interruptedException);
					currentThread().interrupt();
				}
			}
		}
	}
}
