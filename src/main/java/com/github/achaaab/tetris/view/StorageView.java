package com.github.achaaab.tetris.view;

import com.github.achaaab.tetris.model.Storage;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class StorageView extends GridView {

	private static final int MARGIN = 10;

	/**
	 * @param storage modèle de la réserve
	 * @since 0.0.0
	 */
	public StorageView(Storage storage) {
		super(storage, DEFAULT_BORDER, MARGIN, DEFAULT_CELL_SIZE);
	}
}
