package com.github.achaaab.tetris.model;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Cell {

	private Block block;

	public Cell() {
		block = null;
	}

	/**
	 * @return {@code true} if this cell does not contain a block, {@code false} if this cell contains a block
	 * @since 0.0.0
	 */
	public boolean isEmpty() {
		return block == null;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public Optional<Block> getBlock() {
		return ofNullable(block);
	}

	/**
	 * @param block
	 * @since 0.0.0
	 */
	public void setBlock(Block block) {

		block.setX(0);
		block.setY(0);

		this.block = block;
	}

	/**
	 * Moves the block contained in this cell into another cell.
	 * Does nothing if this cell is empty.
	 *
	 * @param cell cell in which to move the block
	 * @see #isEmpty()
	 * @since 0.0.0
	 */
	public void moveBlock(Cell cell) {

		if (!isEmpty()) {

			cell.setBlock(block);
			clear();
		}
	}

	/**
	 * Clears this cell, removing its block.
	 *
	 * @since 0.0.0
	 */
	public void clear() {
		block = null;
	}
}
