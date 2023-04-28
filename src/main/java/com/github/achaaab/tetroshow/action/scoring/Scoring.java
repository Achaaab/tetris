package com.github.achaaab.tetroshow.action.scoring;

import com.github.achaaab.tetroshow.action.AbstractAction;
import com.github.achaaab.tetroshow.model.Tetroshow;

/**
 * Action of scoring.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class Scoring extends AbstractAction {

	/**
	 * @param tetroshow Tetroshow in which to apply scoring
	 * @since 0.0.0
	 */
	public Scoring(Tetroshow tetroshow) {
		super(tetroshow);
	}

	/**
	 * @param softDropCount number of soft drop rows for the locked piece
	 * @since 0.0.0
	 */
	public abstract void pieceLocked(int softDropCount);
}
