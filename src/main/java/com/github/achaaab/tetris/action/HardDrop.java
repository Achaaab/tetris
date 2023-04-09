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

	private static final Audio SOUND_EFFECT = createAudio("audio/effect/hard_drop.wav");

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public HardDrop(Tetris tetris) {
		super(tetris);
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
			tetris.incrementerBonusDescente();
		}

		if (tetris.lockFallingPiece()) {
			SOUND_EFFECT.play();
		} else {
			tetris.stop();
		}
	}
}
