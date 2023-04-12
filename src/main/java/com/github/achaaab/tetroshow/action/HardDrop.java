package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.audio.Audio;
import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.model.piece.Piece;

import static com.github.achaaab.tetroshow.audio.AudioFactory.createAudio;
import static com.github.achaaab.tetroshow.model.piece.Direction.DOWN;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class HardDrop extends AbstractAction {

	private final Audio soundEffect;

	/**
	 * @param tetroshow
	 * @since 0.0.0
	 */
	public HardDrop(Tetroshow tetroshow) {
		this(tetroshow, createAudio("audio/effect/hard_drop.wav"));
	}

	/**
	 * @param tetroshow
	 * @param soundEffect
	 * @since 0.0.0
	 */
	public HardDrop(Tetroshow tetroshow, Audio soundEffect) {

		super(tetroshow);

		this.soundEffect = soundEffect;
	}

	@Override
	public void execute() {
		tetroshow.getFallingPiece().ifPresent(this::apply);
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
			tetroshow.increaseDropBonus();
		}

		if (tetroshow.lockFallingPiece()) {
			soundEffect.play();
		} else {
			tetroshow.exit();
		}
	}
}
