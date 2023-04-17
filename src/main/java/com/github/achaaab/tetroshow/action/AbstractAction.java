package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.model.field.Playfield;
import com.github.achaaab.tetroshow.settings.Settings;

/**
 * Action executable on a Tetroshow.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class AbstractAction implements Action {

	protected final Tetroshow tetroshow;
	protected final Playfield playfield;
	protected Settings settings;

	/**
	 * Creates a new action.
	 *
	 * @param tetroshow Tetroshow in which this action wille be executed
	 * @since 0.0.0
	 */
	public AbstractAction(Tetroshow tetroshow) {

		this.tetroshow = tetroshow;

		playfield = tetroshow.getPlayfield();
		settings = Settings.getDefaultInstance();
	}

	@Override
	public void reset() {

	}
}
