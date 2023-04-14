package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.audio.Audio;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.model.Tetroshow;

import static com.github.achaaab.tetroshow.audio.AudioFactory.createAudio;

/**
 * action of locking a Tetroshow piece
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Lock extends AbstractAction {

	private final Audio soundEffect;

	private boolean active;
	private int frameCounter;

	/**
	 * @param tetroshow
	 * @since 0.0.0
	 */
	public Lock(Tetroshow tetroshow) {
		this(tetroshow, createAudio("audio/effect/lock.wav"));
	}

	/**
	 * @param tetroshow
	 * @param soundEffect
	 * @since 0.0.0
	 */
	public Lock(Tetroshow tetroshow, Audio soundEffect) {

		super(tetroshow);

		this.soundEffect = soundEffect;

		reset();
	}

	@Override
	public void execute() {

		if (active) {

			frameCounter++;

			var level = tetroshow.getLevel();
			var delay = Settings.getDefaultInstance().getLock(level);

			if (frameCounter == delay) {

				frameCounter = 0;

				if (tetroshow.lockFallingPiece()) {
					soundEffect.play();
				} else {
					tetroshow.exit();
				}
			}
		}
	}

	/**
	 * @since 0.0.0
	 */
	public void start() {

		if (!active) {

			active = true;
			frameCounter = 0;
		}
	}

	/**
	 * @since 0.0.0
	 */
	public void cancel() {
		active = false;
	}

	@Override
	public void reset() {

		frameCounter = 0;
		active = false;
	}
}
