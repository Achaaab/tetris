package com.github.achaaab.tetris.action;

import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.model.Piece;
import com.github.achaaab.tetris.model.classic.Tetris;

import static com.github.achaaab.tetris.audio.AudioFactory.createAudio;
import static com.github.achaaab.tetris.model.Direction.DOWN;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class HardDrop extends Action {

	private final Audio soundEffect;

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public HardDrop(Tetris tetris) {
		this(tetris, createAudio("audio/effect/hard_drop.wav"));
	}

	/**
	 * @param tetris
	 * @param soundEffect
	 * @since 0.0.0
	 */
	public HardDrop(Tetris tetris, Audio soundEffect) {

		super(tetris);

		this.soundEffect = soundEffect;
	}

	@Override
	public void execute() {
		tetris.getFallingPiece().ifPresent(this::apply);
	}

	/**
	 * Instantly (between 2 frames) drops and locks the given piece.
	 *
	 * @param piece piece to drop
	 * @since 0.0.0
	 */
	private void apply(Piece piece) {

		while (playfield.isMovePossible(piece, DOWN)) {

			piece.move(DOWN);
			tetris.increaseDropBonus();
		}

		if (tetris.lockFallingPiece()) {
			soundEffect.play();
		} else {
			tetris.exit();
		}
	}
}
