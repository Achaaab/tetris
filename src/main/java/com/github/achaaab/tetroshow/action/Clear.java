package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.action.scoring.Scoring;
import com.github.achaaab.tetroshow.audio.SoundEffect;
import com.github.achaaab.tetroshow.model.Scrap;
import com.github.achaaab.tetroshow.model.Tetroshow;

import java.util.List;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;

/**
 * Action of clearing lines of a Tetroshow.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Clear extends AbstractAction {

	private static final SoundEffect CLEAR_SOUND_EFFECT = getSoundEffect("audio/effect/clear.wav", 1);
	private static final SoundEffect LINE_DROP_SOUND_EFFECT = getSoundEffect("audio/effect/line_drop.wav", 1);

	private final Scoring scoring;

	private boolean active;
	private int age;
	private int delay;
	private List<Integer> lines;

	/**
	 * Creates a new clear action.
	 *
	 * @param tetroshow Tetroshow on which to execute this action
	 * @since 0.0.0
	 */
	public Clear(Tetroshow tetroshow) {

		super(tetroshow);

		scoring = tetroshow.getScoring();

		active = false;
		age = 0;
	}

	@Override
	public void execute() {

		updateScraps();

		if (active) {

			if (age == delay) {

				dropLines();
				tetroshow.newPiece();
			}

		} else if (tetroshow.getFallingPiece().isEmpty()) {

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
		var level = tetroshow.getLevel();

		if (lines.isEmpty()) {

			delay = settings.getAre(level);

		} else {

			delay = settings.getClear(level);
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

		tetroshow.setLineCount(lineCount);
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

	@Override
	public void reset() {

		active = false;
		age = 0;
	}
}
