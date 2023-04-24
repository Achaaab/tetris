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
	 * @param tetroshow
	 * @since 0.0.0
	 */
	public Scoring(Tetroshow tetroshow) {
		super(tetroshow);
	}

	/**
	 * @param softDropCount
	 * @since 0.0.0
	 */
	public abstract void pieceLocked(int softDropCount);
}
