package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.field.Preview;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.skin.Skin;

import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static com.github.achaaab.tetroshow.model.piece.State.ACTIF;
import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static com.github.achaaab.tetroshow.view.play.GridView.DEFAULT_CELL_SIZE;
import static java.lang.Math.round;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class PreviewView extends JComponent {

	private static final int BORDER = scale(2.5f);
	private static final int MARGIN = scale(5.0f);
	private static final int BLOCK_SIZE = scale(25.0f);

	private final Preview preview;

	/**
	 * @param preview preview
	 * @since 0.0.0
	 */
	public PreviewView(Preview preview) {

		this.preview = preview;

		setPreferredSize(new Dimension(0, BORDER + MARGIN + 2 * BLOCK_SIZE + MARGIN + BORDER));
	}

	@Override
	public void paint(Graphics graphics) {

		var pieces = preview.getPieces();

		var graphics2d = (Graphics2D) graphics;
		var skin = Skin.get(Settings.getDefaultInstance().getGraphics().getSkin());
		graphics2d.setColor(skin.getBackgroundColor());
		graphics2d.fillRect(0, 0, getWidth(), getHeight());

		var previousStroke = graphics2d.getStroke();
		graphics.setColor(skin.getBorderColor());
		graphics2d.setStroke(new BasicStroke(BORDER));

		graphics.drawRect(
				round(BORDER / 2.0f),
				round(BORDER / 2.0f),
				getWidth() - BORDER,
				getHeight() - BORDER);

		graphics2d.setStroke(previousStroke);

		var x = BORDER + MARGIN + 2 * DEFAULT_CELL_SIZE;
		var y = BORDER + MARGIN;
		var blockSize = BLOCK_SIZE;

		for (var piece : pieces) {

			skin.drawPiece(graphics2d, piece, x, y, blockSize, ACTIF);
			x += 5 * blockSize;
			blockSize = blockSize * 2 / 3;
		}
	}
}
