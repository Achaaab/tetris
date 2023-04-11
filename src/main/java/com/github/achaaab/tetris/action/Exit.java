package com.github.achaaab.tetris.action;

import com.github.achaaab.tetris.model.classic.Tetris;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Exit extends Action {

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public Exit(Tetris tetris) {
		super(tetris);
	}

	@Override
	public void execute() {
		tetris.exit();
	}
}
