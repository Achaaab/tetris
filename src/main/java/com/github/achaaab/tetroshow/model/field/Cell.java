package com.github.achaaab.tetroshow.model.field;

import com.github.achaaab.tetroshow.model.piece.Block;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * A cell is a unit of storage that can be empty or contain a block.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Cell {

	private Block block;

	/**
	 * Creates an empty cell.
	 *
	 * @since 0.0.0
	 */
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
	 * @return block contained in this cell
	 * @since 0.0.0
	 */
	public Optional<Block> getBlock() {
		return ofNullable(block);
	}

	/**
	 * Puts a block in this cell.
	 *
	 * @param block block to put
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
