package com.github.achaaab.tetroshow.action.scoring;

import com.github.achaaab.tetroshow.model.Tetroshow;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GbScoring extends Scoring {

	private static final int[] MULTIPLIER = { 0, 40, 100, 300, 1200 };
	private static final int LINES_PER_LEVEL = 10;

	private int totalLineCount;

	/**
	 * Creates a new Game Boy scoring action.
	 *
	 * @param tetroshow Tetroshow in which to apply this scoring
	 * @since 0.0.0
	 */
	public GbScoring(Tetroshow tetroshow) {

		super(tetroshow);

		reset();
	}

	@Override
	public void execute() {

		var level = tetroshow.getLevel();
		var lineCount = tetroshow.getLineCount();
		var score = tetroshow.getScore();

		score += MULTIPLIER[lineCount] * (level + 1);
		tetroshow.setScore(score);

		totalLineCount += lineCount;
		tetroshow.setLevel(totalLineCount / LINES_PER_LEVEL);
	}

	@Override
	public void pieceLocked(int softDropCount) {
		tetroshow.setScore(tetroshow.getScore() + softDropCount);
	}

	@Override
	public void reset() {
		totalLineCount = 0;
	}
}
