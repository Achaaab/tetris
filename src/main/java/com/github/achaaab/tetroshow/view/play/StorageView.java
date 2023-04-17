package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.field.Storage;

import static com.github.achaaab.tetroshow.view.Scaler.scale;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class StorageView extends GridView {

	public static final int MARGIN = scale(5.0f);

	/**
	 * @param storage modèle de la réserve
	 * @since 0.0.0
	 */
	public StorageView(Storage storage) {
		super(storage, DEFAULT_BORDER, MARGIN, DEFAULT_CELL_SIZE);
	}
}
