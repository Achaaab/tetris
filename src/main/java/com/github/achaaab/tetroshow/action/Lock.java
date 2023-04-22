package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.audio.SoundEffect;
import com.github.achaaab.tetroshow.model.Tetroshow;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;

/**
 * action of locking a Tetroshow piece
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Lock extends AbstractAction {

	private final SoundEffect soundEffect;

	private boolean active;
	private int frameCounter;

	/**
	 * Creates a new lock action.
	 *
	 * @param tetroshow Tetroshow in which to apply this lock
	 * @since 0.0.0
	 */
	public Lock(Tetroshow tetroshow) {
		this(tetroshow, getSoundEffect("audio/effect/lock.wav", 1));
	}

	/**
	 * Creates a new lock action with a specified sound effect.
	 *
	 * @param tetroshow Tetroshow in which to apply this lock
	 * @param soundEffect sound effect to play when executing this lock
	 * @since 0.0.0
	 */
	public Lock(Tetroshow tetroshow, SoundEffect soundEffect) {

		super(tetroshow);

		this.soundEffect = soundEffect;

		reset();
	}

	@Override
	public void execute() {

		if (active) {

			frameCounter++;

			var level = tetroshow.getLevel();
			var delay = settings.getLock(level);

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
	 * Starts this lock. It can be cancelled during a configured number of frames.
	 *
	 * @since 0.0.0
	 */
	public void start() {

		if (!active) {

			active = true;
			frameCounter = 0;
		}
	}

	/**
	 * Cancels this lock. It can be started again later.
	 *
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
