package com.github.achaaab.tetroshow.scene;

import org.slf4j.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import static java.lang.Math.round;
import static java.lang.String.format;
import static java.lang.System.nanoTime;
import static java.lang.Thread.sleep;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class SceneManager implements Runnable {

	private static final Logger LOGGER = getLogger(SceneManager.class);

	/**
	 * Creates a container and adds the given component in it, taking all the space.
	 *
	 * @param component component to containerize
	 * @return created container
	 * @since 0.0.0
	 */
	private static Container containerize(Component component) {

		if (component instanceof Container container) {

			return container;

		} else {

			var container = new JPanel();
			container.setLayout(new BorderLayout());
			container.add(component);
			return container;
		}
	}

	private final long targetFrameDuration;

	private JFrame window;
	private boolean running;
	private Scene scene;

	/**
	 * @param title window title
	 * @param width window width in physical pixels
	 * @param height window height in physical pixels
	 * @param fps target frame rate in frames per second
	 * @since 0.0.0
	 */
	public SceneManager(String title, int width, int height, double fps) {

		targetFrameDuration = round(1_000_000_000 / fps);

		invokeLater(() -> {

			window = new JFrame(title);
			window.setSize(width, height);
			window.setDefaultCloseOperation(EXIT_ON_CLOSE);
			window.setResizable(false);
			window.setLocationRelativeTo(null);
			window.setVisible(true);
		});
	}

	@Override
	public void run() {

		running = true;

		var frameStartTime = nanoTime();
		var frameEndTime = frameStartTime;

		while (running) {

			var deltaTime = frameEndTime - frameStartTime;
			frameStartTime = frameEndTime;

			if (scene != null) {
				scene.update(deltaTime / 1_000_000_000.0);
			}

			frameEndTime = ensureFrameDuration(frameStartTime, nanoTime());
		}

		System.exit(0);
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

	/**
	 * @param scene
	 * @since 0.0.0
	 */
	public void display(Scene scene) {

		if (isEventDispatchThread()) {

			LOGGER.info("displaying scene {}", scene);

			this.scene = scene;

			var view = scene.getView();
			window.setContentPane(containerize(view));
			window.validate();

			scene.initialize();

		} else {

			invokeLater(() -> display(scene));
		}
	}

	/**
	 * @param scene
	 * @since 0.0.0
	 */
	public void exit(Scene scene) {

		scene.getParent().ifPresentOrElse(
				this::display,
				this::exit);
	}

	/**
	 * @since 0.0.0
	 */
	public void exit() {
		running = false;
	}
}
