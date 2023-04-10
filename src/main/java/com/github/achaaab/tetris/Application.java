package com.github.achaaab.tetris;

import com.github.achaaab.tetris.audio.Playlist;
import com.github.achaaab.tetris.controller.MenuController;
import com.github.achaaab.tetris.game.GameLoop;
import com.github.achaaab.tetris.model.classic.Tetris;
import com.github.achaaab.tetris.scene.Menu;
import com.github.achaaab.tetris.view.Window;
import com.github.achaaab.tetris.view.menu.MenuView;
import com.github.achaaab.tetris.view.play.TetrisView;
import org.slf4j.Logger;

import static javax.swing.SwingUtilities.invokeLater;
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
		play();

		var model = new Menu();
		var controller = new MenuController(model);

		invokeLater(() -> {

			var view = new MenuView();
			var window = new Window();
			window.setContentPane(view);
			controller.setView(view);
		});
	}

	/**
	 * @since 0.0.0
	 */
	private static void play() {

		var tetris = new Tetris();
		var keyboard = tetris.getKeyboard();
		var loop = new GameLoop(tetris, 60);

		invokeLater(() -> {

			var view = new TetrisView(tetris);
			view.addKeyListener(keyboard);
			var window = new Window();
			window.setContentPane(view);
			loop.setView(view);
		});

		var playlist = new Playlist();
		new Thread(playlist, "playlist").start();

		tetris.newPiece();
		loop.run();
	}
}
