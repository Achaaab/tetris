package com.github.achaaab.tetris.game;

import org.slf4j.Logger;

import static java.lang.Math.round;
import static java.lang.String.format;
import static java.lang.System.nanoTime;
import static java.lang.Thread.sleep;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GameLoop implements Runnable {

	private static final Logger LOGGER = getLogger(GameLoop.class);

	private final Game game;
	private final long targetFrameDuration;

	private GameView view;

	/**
	 * @param game game to update
	 * @param fps target update rate in frames per second
	 * @since 0.0.0
	 */
	public GameLoop(Game game, double fps) {

		this.game = game;

		view = null;
		targetFrameDuration = round(1_000_000_000 / fps);
	}

	/**
	 * @param view
	 * @since 0.0.0
	 */
	public void setView(GameView view) {
		this.view = view;
	}

	@Override
	public void run() {

		var frameStartTime = nanoTime();
		var frameEndTime = frameStartTime;

		while (game.isRunning()) {

			if (!game.isPaused()) {
				game.addTime(frameEndTime - frameStartTime);
			}

			frameStartTime = frameEndTime;
			update();
			frameEndTime = ensureFrameDuration(frameStartTime, nanoTime());
		}
	}

	/**
	 * Updates the game state and renders the view.
	 *
	 * @since 0.0.1
	 */
	private void update() {

		game.update();

		if (view != null) {
			view.render();
		}
	}

	/**
	 * Ensures frame duration.
	 *
	 * @param frameStartTime frame start time
	 * @param frameEndTime actual frame end time
	 * @return frame end time after temporisation
	 * @since 0.0.0
	 */
	private long ensureFrameDuration(long frameStartTime, long frameEndTime) {

		var frameDuration = frameEndTime - frameStartTime;

		if (frameDuration < targetFrameDuration) {

			var freeTime = (targetFrameDuration - frameDuration) / 1_000_000;

			if (freeTime > 0) {

				try {
					sleep(freeTime);
				} catch (InterruptedException interruption) {
					LOGGER.error("interruption during game loop temporisation", interruption);
				}

				frameEndTime = nanoTime();
			}

		} else {

			LOGGER.warn(format("FPS drop: %.1f", 1_000_000_000.0 / frameDuration));
		}

		return frameEndTime;
	}
}
