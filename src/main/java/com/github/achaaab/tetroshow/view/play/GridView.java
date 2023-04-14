package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.field.Grid;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.skin.Skin;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;

import static com.github.achaaab.tetroshow.model.piece.State.ACTIF;
import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static java.awt.Color.BLACK;
import static java.awt.Color.GRAY;
import static java.lang.Math.round;
import static java.lang.Math.toIntExact;
import static javax.swing.BorderFactory.createLineBorder;

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
	 * @param grid
	 * @param border
	 * @param margin
	 * @param cellSize
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
		setBackground(BLACK);
		setBorder(createLineBorder(GRAY, border));
	}

	@Override
	public void paintComponent(Graphics graphics) {

		var skin = Skin.get(Settings.getDefaultInstance().getGraphics().getSkin());

		graphics.setColor(getBackground());
		graphics.fillRect(0, 0, getWidth(), getHeight());

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
