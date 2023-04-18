package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.Scrap;
import com.github.achaaab.tetroshow.model.field.Playfield;

import java.awt.Graphics;
import java.awt.Graphics2D;

import static com.github.achaaab.tetroshow.model.piece.State.GHOST;
import static com.github.achaaab.tetroshow.view.skin.Skin.getCurrentSkin;

/**
 * playfield view
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class PlayfieldView extends GridView {

	private static final int MARGIN = HALF_BORDER;
	public static final int WIDTH = 2 * MARGIN + Playfield.WIDTH * CELL_SIZE;
	public static final int HEIGHT = 2 + MARGIN + Playfield.HEIGHT * CELL_SIZE;

	private final Playfield playfield;

	/**
	 * Creates a new playfield view.
	 *
	 * @param playfield playfield to display
	 * @since 0.0.0
	 */
	public PlayfieldView(Playfield playfield) {

		super(playfield, MARGIN);

		this.playfield = playfield;
	}

	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		var skin = getCurrentSkin();

		playfield.getGhostPiece().ifPresent(ghostPiece -> {

			var x = CELL_SIZE * ghostPiece.getX();
			var y = CELL_SIZE * ghostPiece.getY();

			skin.drawPiece(graphics, ghostPiece, x, y, CELL_SIZE, GHOST);
		});

		paintPieces(graphics);

		for (var scrap : playfield.getScraps()) {
			drawScrap(graphics, scrap);
		}
	}

	/**
	 * Draws a scrap.
	 *
	 * @param graphics graphics with which to draw
	 * @param scrap scrap to draw
	 * @since 0.0.0
	 */
	private void drawScrap(Graphics graphics, Scrap scrap) {

		var color = scrap.getColor();
		graphics.setColor(color);

		var x = scrap.getX();
		var y = scrap.getY();
		var size = scrap.getSize();

		graphics.fillRect(project(x), project(y), project(size), project(size));
	}
}
