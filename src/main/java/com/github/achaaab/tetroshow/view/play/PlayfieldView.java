package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.Scrap;
import com.github.achaaab.tetroshow.model.field.Playfield;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.skin.Skin;

import java.awt.Graphics;

import static com.github.achaaab.tetroshow.model.piece.State.GHOST;
import static java.lang.Math.round;
import static java.lang.Math.toIntExact;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class PlayfieldView extends GridView {

	private final Playfield playfield;

	/**
	 * @param playfield
	 * @since 0.0.0
	 */
	public PlayfieldView(Playfield playfield) {

		super(playfield);

		this.playfield = playfield;
	}


	@Override
	public void paint(Graphics graphics) {

		super.paint(graphics);

		var skin = Skin.get(Settings.getDefaultInstance().getGraphics().getSkin());

		playfield.getGhostPiece().ifPresent(ghostPiece -> {

			var x = border + margin + cellSize * ghostPiece.getX();
			var y = border + margin + cellSize * ghostPiece.getY();

			skin.drawPiece(graphics, ghostPiece, x, y, cellSize, GHOST);
		});

		for (var scrap : playfield.getScraps()) {
			drawScrap(graphics, scrap);
		}
	}

	/**
	 * @param graphics
	 * @param scrap
	 * @since 0.0.0
	 */
	private void drawScrap(Graphics graphics, Scrap scrap) {

		var color = scrap.getColor();
		graphics.setColor(color);

		var x = scrap.getX();
		var y = scrap.getY();
		var size = toIntExact(round(cellSize * scrap.getSize()));

		graphics.fillRect(project(x), project(y), size, size);
	}
}
