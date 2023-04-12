package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.configuration.Configuration;
import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.model.field.Playfield;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class AbstractAction implements Action{

	protected final Tetroshow tetroshow;
	protected final Playfield playfield;
	protected final Configuration configuration;

	/**
	 * @param tetroshow Tetroshow dans lequel sera exécutée l'action
	 * @since 0.0.0
	 */
	public AbstractAction(Tetroshow tetroshow) {

		this.tetroshow = tetroshow;

		configuration = tetroshow.getConfiguration();
		playfield = tetroshow.getPlayfield();
	}

	@Override
	public void reset() {

	}
}
