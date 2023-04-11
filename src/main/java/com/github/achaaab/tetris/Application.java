package com.github.achaaab.tetris;

import com.github.achaaab.tetris.scene.MenuScene;
import com.github.achaaab.tetris.scene.SceneManager;
import org.slf4j.Logger;

import static com.github.achaaab.tetris.view.Scaler.scale;
import static org.slf4j.LoggerFactory.getLogger;

/**
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

		LOGGER.info("starting Tetris");

		var sceneManager = new SceneManager("Tetris", scale(420), scale(500), 60);
		var menuScene = new MenuScene(sceneManager);
		sceneManager.display(menuScene);
		sceneManager.run();
	}
}
