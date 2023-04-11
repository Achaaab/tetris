package com.github.achaaab.tetris.action;

import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.model.classic.Tetris;

import static com.github.achaaab.tetris.audio.AudioFactory.createAudio;

/**
 * Action de verrouillage d'une pièce de Tetris.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Lock extends Action {

	private final Audio soundEffect;

	private boolean active;
	private int frameCounter;

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public Lock(Tetris tetris) {
		this(tetris, createAudio("audio/effect/lock.wav"));
	}

	/**
	 * @param tetris
	 * @param soundEffect
	 * @since 0.0.0
	 */
	public Lock(Tetris tetris, Audio soundEffect) {

		super(tetris);

		this.soundEffect = soundEffect;

		reset();
	}

	@Override
	public void execute() {

		if (active) {

			frameCounter++;

			var levelSpeed = tetris.getLevelSpeed();
			var delay = configuration.getLockDelay(levelSpeed);

			if (frameCounter == delay) {

				frameCounter = 0;

				if (tetris.lockFallingPiece()) {
					soundEffect.play();
				} else {
					tetris.exit();
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
