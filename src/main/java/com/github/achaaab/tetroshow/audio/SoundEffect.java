package com.github.achaaab.tetroshow.audio;

import com.adonax.audiocue.AudioCue;
import org.slf4j.Logger;

import static com.adonax.audiocue.AudioCue.makeStereoCue;
import static com.github.achaaab.tetroshow.utility.ResourceUtility.getResourceUrl;
import static java.util.stream.IntStream.range;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * A sound effect is an audio fully loaded in memory.
 * Playing a sound effect is non-blocking.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class SoundEffect extends NamedAudio {

	private static final Logger LOGGER = getLogger(SoundEffect.class);

	private final int polyphony;

	private int volume;
	private AudioCue cue;
	private Long lastPlayTime;
	private long minimumDelay;

	/**
	 * Creates a new sound effect.
	 *
	 * @param name sound effect resource name
	 * @param polyphony maximum number of concurrent instances
	 * @param volume volume in {@code [0, 10]}
	 * @since 0.0.0
	 */
	public SoundEffect(String name, int polyphony, int volume) {

		super(name);

		this.polyphony = polyphony;
		this.volume = volume;

		try {

			var url = getResourceUrl(name);
			cue = makeStereoCue(url, polyphony);
			cue.open();
			lastPlayTime = null;

			var microseconds = cue.getMicrosecondLength();
			minimumDelay = microseconds * 1_000 / polyphony;

		} catch (Exception exception) {

			LOGGER.error("error while loading sound effect {}", name, exception);
			cue = null;
		}
	}

	@Override
	public void setVolume(int volume) {

		if (cue != null) {

			this.volume = volume;

			range(0, polyphony).
					filter(instanceId -> cue.getIsActive(instanceId)).
					forEach(instanceId -> cue.setVolume(instanceId, volume / VOLUME_SCALE));
		}
	}

	@Override
	public void play() {

		if (cue != null) {

			var nanoTime = System.nanoTime();

			if (lastPlayTime == null || nanoTime - lastPlayTime > minimumDelay) {

				lastPlayTime = nanoTime;
				cue.play(volume / VOLUME_SCALE);
			}
		}
	}
}
