package com.github.achaaab.tetris.action;

import com.github.achaaab.tetris.configuration.Configuration;
import com.github.achaaab.tetris.model.classic.Playfield;
import com.github.achaaab.tetris.model.classic.Tetris;

/**
 * Une action, initiée par l'utilisateur ou non, ayant une influence sur le tetris.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class Action {

	protected final Tetris tetris;
	protected final Playfield playfield;
	protected final Configuration configuration;

	/**
	 * @param tetris tetris dans lequel sera exécutée l'action
	 * @since 0.0.0
	 */
	public Action(Tetris tetris) {

		this.tetris = tetris;

		configuration = tetris.getConfiguration();
		playfield = tetris.getPlayfield();
	}

	/**
	 * @since 0.0.0
	 */
	public abstract void execute();

	/**
	 * @since 0.0.0
	 */
	public void reset() {

	}
}
