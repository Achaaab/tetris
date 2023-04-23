package com.github.achaaab.tetroshow.model;

/**
 * A game component is a component of the game having a state.
 * The game components are persistent and therefore, should be reset in case of a new game.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface GamePart {

	/**
	 * Resets this game object.
	 *
	 * @since 0.0.0
	 */
	void reset();
}
