package com.github.achaaab.tetris.action;

import com.github.achaaab.tetris.model.classic.Tetris;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Pause extends Action {

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public Pause(Tetris tetris) {
		super(tetris);
	}

	@Override
	public void execute() {
		tetris.pause();
	}
}
