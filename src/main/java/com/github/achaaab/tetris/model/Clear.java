package com.github.achaaab.tetris.model;

import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.configuration.Configuration;
import com.github.achaaab.tetris.action.Scoring;
import com.github.achaaab.tetris.action.Action;
import com.github.achaaab.tetris.model.classic.Tetris;

import java.util.List;

import static com.github.achaaab.tetris.audio.AudioFactory.createAudio;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Clear extends Action {

	private static final Audio CLEAR_SOUND_EFFECT = createAudio("audio/effect/clear.wav");
	private static final Audio LINE_DROP_SOUND_EFFECT = createAudio("audio/effect/line_drop.wav");

	private final Scoring scoring;

	private boolean active;
	private int age;
	private int delay;
	private List<Integer> lines;

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public Clear(Tetris tetris) {

		super(tetris);

		scoring = new Scoring(tetris);

		active = false;
		age = 0;
	}

	@Override
	public void execute() {

		updateScraps();

		if (active) {

			if (age == delay) {

				dropLines();
				tetris.newPiece();
			}

		} else if (tetris.getFallingPiece().isEmpty()) {

			clearLines();
		}
	}

	/**
	 * Clears complete lines.
	 *
	 * @since 0.0.0
	 */
	private void clearLines() {

		active = true;
		age = 0;

		lines = playfield.clearLines();
		var speedLevel = tetris.getLevelSpeed();

		if (lines.isEmpty()) {

			delay = Configuration.INSTANCE.getDelaiTetromino(speedLevel);

		} else {

			delay = Configuration.INSTANCE.getDelaiNettoyage(speedLevel);
			CLEAR_SOUND_EFFECT.play();
		}
	}

	/**
	 * Drops lines once complete lines are cleared.
	 *
	 * @since 0.0.0
	 */
	private void dropLines() {

		active = false;

		var lineCount = lines.size();

		if (lineCount > 0) {

			playfield.dropRows(lines);
			LINE_DROP_SOUND_EFFECT.play();
		}

		tetris.setLineCount(lineCount);
		scoring.execute();
	}

	/**
	 * Update scraps.
	 *
	 * @since 0.0.0
	 */
	private void updateScraps() {

		var scraps = playfield.getScraps();
		scraps.removeIf(Scrap::update);
		age++;
	}
}
