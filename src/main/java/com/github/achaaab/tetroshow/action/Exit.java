package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Tetroshow;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Exit extends AbstractAction {

	/**
	 * @param tetroshow
	 * @since 0.0.0
	 */
	public Exit(Tetroshow tetroshow) {
		super(tetroshow);
	}

	@Override
	public void execute() {
		tetroshow.exit();
	}
}
