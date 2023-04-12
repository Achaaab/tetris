package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.field.Preview;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;

import static com.github.achaaab.tetroshow.model.piece.State.ACTIF;
import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static com.github.achaaab.tetroshow.view.play.GridView.DEFAULT_CELL_SIZE;
import static java.awt.Color.BLACK;
import static java.awt.Color.GRAY;
import static javax.swing.BorderFactory.createLineBorder;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class PreviewView extends JComponent {

	private static final int BORDER = scale(2.5f);
	private static final int MARGIN = scale(5.0f);
	private static final int BLOCK_SIZE = scale(25.0f);

	private final Preview preview;
	private final Skin skin;

	/**
	 * @param preview preview
	 * @since 0.0.0
	 */
	public PreviewView(Preview preview) {

		this.preview = preview;

		skin = GlassSkin.INSTANCE;

		setBackground(BLACK);
		setPreferredSize(new Dimension(0, BORDER + MARGIN + 2 * BLOCK_SIZE + MARGIN + BORDER));
		setBorder(createLineBorder(GRAY, BORDER));
	}

	@Override
	public void paintComponent(Graphics graphics) {

		var pieces = preview.getPieces();

		var x = BORDER + MARGIN + 2 * DEFAULT_CELL_SIZE;
		var y = BORDER + MARGIN;
		var blockSize = BLOCK_SIZE;

		graphics.setColor(getBackground());
		graphics.fillRect(0, 0, getWidth(), getHeight());

		for (var piece : pieces) {

			skin.drawPiece(graphics, piece, x, y, blockSize, ACTIF);
			x += 5 * blockSize;
			blockSize = blockSize * 2 / 3;
		}
	}
}
