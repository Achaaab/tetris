package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.model.Tetroshow;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Scoring extends AbstractAction {

	private int combo;

	/**
	 * @param tetroshow
	 * @since 0.0.0
	 */
	public Scoring(Tetroshow tetroshow) {

		super(tetroshow);

		reset();
	}

	@Override
	public void execute() {

		var lineCount = tetroshow.getLineCount();
		var levelBefore = tetroshow.getLevel();
		var score = tetroshow.getScore();
		var dropBonus = tetroshow.dropBonus();
		var pieceFrames = tetroshow.getFallingPieceAge();

		if (lineCount == 0) {
			combo = 1;
		} else {
			combo += 2 * (lineCount - 1);
		}

		var levelAfter = levelBefore + lineCount;

		score += ((levelBefore + lineCount + 3) / 4 + dropBonus) * lineCount * combo
				+ (levelAfter + 1) / 2;

		var lockDelay = Settings.getDefaultInstance().getLock(levelBefore);
		var speedBonus = lockDelay - pieceFrames;

		if (speedBonus > 0) {
			score += speedBonus;
		}

		tetroshow.setLevel(levelAfter);
		tetroshow.setScore(score);
	}

	@Override
	public void reset() {
		combo = 1;
	}
}
