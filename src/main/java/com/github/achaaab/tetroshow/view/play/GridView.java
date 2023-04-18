package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.field.Grid;
import com.github.achaaab.tetroshow.view.component.Component;

import java.awt.Graphics2D;

import static com.github.achaaab.tetroshow.model.piece.State.ACTIF;
import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static com.github.achaaab.tetroshow.view.skin.Skin.getCurrentSkin;
import static java.lang.Math.round;
import static java.lang.Math.toIntExact;

/**
 * grid view
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GridView extends Component {

	public static final int CELL_SIZE = scale(20.0f);

	/**
	 * Projects a coordinate from model referential to view referential.
	 *
	 * @param coordinate x or y coordinate in model referential
	 * @return corresponding coordinate in view referential
	 * @since 0.0.0
	 */
	protected static int project(double coordinate) {
		return toIntExact(round(coordinate * CELL_SIZE));
	}

	private final Grid grid;
	private final int gridWidth;
	private final int gridHeight;

	/**
	 * Creates a new grid view.
	 *
	 * @param grid grid to display
	 * @param margin margin (in physical pixels)
	 * @since 0.0.0
	 */
	public GridView(Grid grid, int margin) {

		this.grid = grid;
		this.margin = margin;

		gridWidth = grid.getWidth();
		gridHeight = grid.getHeight();

		width = 2 * margin + CELL_SIZE * gridWidth;
		height = 2 * margin + CELL_SIZE * gridHeight;
	}


	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		var skin = getCurrentSkin();

		for (var x = 0; x < gridWidth; x++) {

			for (var y = 0; y < gridHeight; y++) {

				var cell = grid.getCell(x, y);
				skin.drawCell(graphics, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, cell);
			}
		}

		paintPieces(graphics);
	}

	/**
	 * Paints pieces over this grid.
	 *
	 * @param graphics graphics with which to paint
	 * @since 0.0.0
	 */
	protected void paintPieces(Graphics2D graphics) {

		var skin = getCurrentSkin();

		var pieces = grid.getPieces();

		for (var piece : pieces) {

			var x = piece.getX();
			var y = piece.getY();

			skin.drawPiece(graphics, piece, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, ACTIF);
		}
	}
}
