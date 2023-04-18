package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.field.Storage;

import static com.github.achaaab.tetroshow.view.Scaler.scale;

/**
 * storage view
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class StorageView extends GridView {

	private static final int MARGIN = scale(10.0f);
	public static final int WIDTH = 2 * MARGIN + Storage.WIDTH * CELL_SIZE;
	public static final int HEIGHT = 2 + MARGIN + Storage.HEIGHT * CELL_SIZE;

	/**
	 * Creates a new storage view.
	 *
	 * @param storage storage to display
	 * @since 0.0.0
	 */
	public StorageView(Storage storage) {
		super(storage, MARGIN);
	}
}
