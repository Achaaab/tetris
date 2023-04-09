package com.github.achaaab.tetris.game;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface Game {

	/**
	 * @since 0.0.0
	 */
	void update();

	/**
	 * @return
	 * @since 0.0.0
	 */
	boolean isPaused();

	/**
	 * Stops this game.
	 *
	 * @since 0.0.0
	 */
	void stop();

	/**
	 * @return
	 * @since 0.0.0
	 */
	boolean isRunning();

	/**
	 * @return game time in nanoseconds
	 * @since 0.0.0
	 */
	long getTime();

	/**
	 * @param deltaTime time to add in nanoseconds
	 * @since 0.0.0
	 */
	void addTime(long deltaTime);
}
