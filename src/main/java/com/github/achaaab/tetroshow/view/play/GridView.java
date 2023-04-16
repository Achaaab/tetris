package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.field.Grid;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.skin.Skin;

import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static com.github.achaaab.tetroshow.model.piece.State.ACTIF;
import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static java.lang.Math.round;
import static java.lang.Math.toIntExact;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GridView extends JComponent {

	public static final int DEFAULT_BORDER = scale(2.5f);
	public static final int DEFAULT_CELL_SIZE = scale(20.0f);
	protected static final int DEFAULT_MARGIN = scale(0.0f);

	protected final int border;
	protected final int margin;
	protected final int cellSize;

	private final Grid grid;
	private final int gridWidth;
	private final int gridHeight;

	/**
	 * @param grid modèle du champ
	 * @since 0.0.0
	 */
	public GridView(Grid grid) {
		this(grid, DEFAULT_BORDER, DEFAULT_MARGIN, DEFAULT_CELL_SIZE);
	}

	/**
	 * Creates a new grid view.
	 *
	 * @param grid grid to display
	 * @param border border width (in physical pixels)
	 * @param margin left, right, top and bottom margin (in physical pixels)
	 * @param cellSize cell width and height (in physical pixels)
	 * @since 0.0.0
	 */
	public GridView(Grid grid, int border, int margin, int cellSize) {

		this.grid = grid;
		this.border = border;
		this.margin = margin;
		this.cellSize = cellSize;

		gridWidth = grid.getWidth();
		gridHeight = grid.getHeight();

		var preferredWidth = border + margin + cellSize * gridWidth + margin + border;
		var preferredHeight = border + margin + cellSize * gridHeight + margin + border;
		var preferredSize = new Dimension(preferredWidth, preferredHeight);

		setPreferredSize(preferredSize);
	}

	@Override
	public void paint(Graphics graphics) {

		var skin = Skin.get(Settings.getDefaultInstance().getGraphics().getSkin());

		graphics.setColor(skin.getBackgroundColor());
		graphics.fillRect(0, 0, getWidth(), getHeight());

		var graphics2d = (Graphics2D) graphics;
		var previousStroke = graphics2d.getStroke();
		graphics.setColor(skin.getBorderColor());
		graphics2d.setStroke(new BasicStroke(border));

		graphics.drawRect(
				round(border / 2.0f),
				round(border / 2.0f),
				getWidth() - border,
				getHeight() - border);

		graphics2d.setStroke(previousStroke);

		for (var x = 0; x < gridWidth; x++) {

			for (var y = 0; y < gridHeight; y++) {

				var cell = grid.getCell(x, y);
				skin.drawCell(graphics, project(x), project(y), cellSize, cell);
			}
		}

		var pieces = grid.getPieces();

		for (var piece : pieces) {

			var x = piece.getX();
			var y = piece.getY();

			skin.drawPiece(graphics, piece, project(x), project(y), cellSize, ACTIF);
		}
	}

	/**
	 * @param gridCoordinate coordinate in grid model
	 * @return coordinate on this view
	 * @since 0.0.0
	 */
	protected int project(double gridCoordinate) {
		return toIntExact(round(border + margin + gridCoordinate * cellSize));
	}
}
