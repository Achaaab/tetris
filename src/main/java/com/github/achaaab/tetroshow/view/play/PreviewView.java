package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.field.Preview;
import com.github.achaaab.tetroshow.view.component.Component;

import java.awt.Graphics2D;

import static com.github.achaaab.tetroshow.model.piece.State.ACTIF;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.play.GridView.CELL_SIZE;
import static com.github.achaaab.tetroshow.view.skin.Skin.getCurrentSkin;

/**
 * preview view
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class PreviewView extends Component {

	private static final int BLOCK_SIZE = scale(25.0f);
	private static final int MARGIN = scale(10.0f);
	public static final int HEIGHT = 2 * MARGIN + 2 * BLOCK_SIZE;

	private final Preview preview;

	/**
	 * Creates a new preview view.
	 *
	 * @param preview preview to display
	 * @since 0.0.0
	 */
	public PreviewView(Preview preview) {

		this.preview = preview;

		width = 0;
		height = HEIGHT;
		margin = MARGIN;
	}

	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		var pieces = preview.getPieces();

		var skin = getCurrentSkin();

		var x = 2 * CELL_SIZE;
		var y = 0;
		var blockSize = BLOCK_SIZE;

		for (var piece : pieces) {

			skin.drawPiece(graphics, piece, x, y, blockSize, ACTIF);
			x += 5 * blockSize;
			blockSize = blockSize * 2 / 3;
		}
	}
}
