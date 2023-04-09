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

	private static final Audio SOUND_EFFECT = createAudio("audio/effect/lock.wav");

	private boolean active;
	private int age;

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public Lock(Tetris tetris) {

		super(tetris);

		age = 0;
		active = false;
	}

	@Override
	public void execute() {

		if (active) {

			age++;

			var levelSpeed = tetris.getLevelSpeed();
			var delay = configuration.getLockDelay(levelSpeed);

			if (age == delay) {

				age = 0;

				if (tetris.lockFallingPiece()) {
					SOUND_EFFECT.play();
				} else {
					tetris.stop();
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
			age = 0;
		}
	}

	/**
	 * @since 0.0.0
	 */
	public void cancel() {
		active = false;
	}
}
