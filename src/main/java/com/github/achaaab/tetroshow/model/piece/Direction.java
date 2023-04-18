package com.github.achaaab.tetroshow.model.piece;

/**
 * @param dx
 * @param dy
 * @param dr
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public record Direction(int dx, int dy, int dr) {

	public static final Direction DOWN = new Direction(0, 1, 0);
	public static final Direction LEFT = new Direction(-1, 0, 0);
	public static final Direction RIGHT = new Direction(1, 0, 0);
	public static final Direction CLOCKWISE = new Direction(0, 0, 1);
	public static final Direction COUNTERCLOCKWISE = new Direction(0, 0, -1);

	/**
	 * Adds {@code this} and a given direction.
	 *
	 * @param direction direction to add
	 * @return {@code this + direction}
	 * @since 0.0.0
	 */
	public Direction add(Direction direction) {

		return new Direction(
				dx + direction.dx,
				dy + direction.dy,
				dr + direction.dr);
	}
}
