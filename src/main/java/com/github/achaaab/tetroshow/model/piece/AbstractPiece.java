package com.github.achaaab.tetroshow.model.piece;

import java.util.List;

/**
 * Implémente une pièce avec une liste de carrés pour chaque rotation.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class AbstractPiece implements Piece {

	protected final List<List<Block>> rotations;
	protected final List<List<Direction>> clockwiseWallKicks;
	protected final List<List<Direction>> counterClockwiseWallKicks;

	protected int x;
	protected int y;
	protected int enteringColumn;
	private int rotation;

	/**
	 * @param rotations
	 * @param enteringColumn
	 * @param clockwiseWallKicks
	 * @param counterClockwiseWallKicks
	 * @since 0.0.0
	 */
	public AbstractPiece(List<List<Block>> rotations, int enteringColumn,
						 List<List<Direction>> clockwiseWallKicks, List<List<Direction>> counterClockwiseWallKicks) {

		this.rotations = rotations;
		this.enteringColumn = enteringColumn;
		this.clockwiseWallKicks = clockwiseWallKicks;
		this.counterClockwiseWallKicks = counterClockwiseWallKicks;

		rotation = 0;
	}

	@Override
	public List<Block> getBlocks() {
		return rotations.get(rotation);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	@Override
	public void move(Direction direction, int distance) {

		int dx = direction.dx();
		int dy = direction.dy();
		int dr = direction.dr();

		x += dx * distance;
		y += dy * distance;

		rotation = (rotation + dr * distance) % 4;

		if (rotation < 0) {
			rotation += 4;
		}
	}

	@Override
	public AbstractPiece copy() {

		var clone = new AbstractPiece(rotations, enteringColumn, clockwiseWallKicks, counterClockwiseWallKicks);

		clone.setX(x);
		clone.setY(y);
		clone.setRotation(rotation);

		return clone;
	}

	@Override
	public int getEnteringColumn() {
		return enteringColumn;
	}

	@Override
	public List<Direction> getClockwiseWallKicks() {
		return clockwiseWallKicks.get(rotation);
	}

	@Override
	public List<Direction> getCounterClockwiseWallKicks() {
		return counterClockwiseWallKicks.get(rotation);
	}
}
