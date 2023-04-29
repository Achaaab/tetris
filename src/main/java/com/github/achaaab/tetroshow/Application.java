package com.github.achaaab.tetroshow;

import com.github.achaaab.tetroshow.audio.Playlist;
import com.github.achaaab.tetroshow.scene.MainMenuScene;
import com.github.achaaab.tetroshow.scene.SceneManager;
import org.slf4j.Logger;

import static java.lang.System.exit;
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
	 * application main method
	 *
	 * @param arguments none
	 * @since 0.0.0
	 */
	public static void main(String... arguments) {

		LOGGER.info("start");

		var playlist = new Playlist();
		new Thread(playlist, "playlist").start();

		var sceneManager = new SceneManager("Tetroshow", 60);
		var menuScene = new MainMenuScene(sceneManager);
		sceneManager.display(menuScene);

		// the scene manager runs in the main thread
		sceneManager.run();

		LOGGER.info("exit");
		exit(0);
	}
}
