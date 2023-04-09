package com.github.achaaab.tetris.game;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class AbstractGame implements Game {

	protected boolean running;
	protected boolean paused;
	protected long time;

	/**
	 * @since 0.0.0
	 */
	public AbstractGame() {

		running = true;
		paused = false;
		time = 0;
	}

	/**
	 * Inverts the pause status:
	 * <ul>
	 *     <li>If this Tetris is paused: unpauses it.</li>
	 *     <li>If this Tetris is not paused: pauses it.</li>
	 * </ul>
	 *
	 * @since 0.0.0
	 */
	public void pause() {
		paused = !paused;
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public long getTime() {
		return time;
	}

	@Override
	public void addTime(long deltaTime) {
		time += deltaTime;
	}
}
