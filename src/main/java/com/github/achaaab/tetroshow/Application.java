package com.github.achaaab.tetroshow;

import com.github.achaaab.tetroshow.audio.Playlist;
import com.github.achaaab.tetroshow.scene.MenuScene;
import com.github.achaaab.tetroshow.scene.SceneManager;
import org.slf4j.Logger;

import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Tetroshow desktop application
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Application {

	private static final Logger LOGGER = getLogger(Application.class);

	/**
	 * @param arguments none
	 * @since 0.0.0
	 */
	public static void main(String... arguments) {

		LOGGER.info("start");

		var playlist = new Playlist();
		new Thread(playlist, "playlist").start();

		var sceneManager = new SceneManager("Tetroshow", scale(420), scale(500), 60);
		var menuScene = new MenuScene(sceneManager);
		sceneManager.display(menuScene);
		sceneManager.run();

		LOGGER.info("exit");
		System.exit(0);
	}
}
