package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Tetroshow;

/**
 * Action of pausing the Tetroshow.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Pause extends AbstractAction {

	/**
	 * Creates a new pause action.
	 *
	 * @param tetroshow Tetroshow to pause
	 * @since 0.0.0
	 */
	public Pause(Tetroshow tetroshow) {
		super(tetroshow);
	}

	@Override
	public void execute() {
		tetroshow.pause();
	}
}
