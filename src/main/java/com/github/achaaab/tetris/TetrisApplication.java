package com.github.achaaab.tetris;

import com.github.achaaab.tetris.audio.Playlist;
import com.github.achaaab.tetris.game.GameLoop;
import com.github.achaaab.tetris.model.classic.Tetris;
import com.github.achaaab.tetris.view.TetrisView;

import javax.swing.JFrame;

import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrisApplication {

	/**
	 * @param arguments none
	 * @since 0.0.0
	 */
	public static void main(String... arguments) {

		var tetris = new Tetris();
		var keyboard = tetris.getKeyboard();
		var loop = new GameLoop(tetris, 60);

		invokeLater(() -> {

			var view = new TetrisView(tetris);

			view.setFocusable(true);
			view.requestFocus();
			view.addKeyListener(keyboard);

			var window = new JFrame("Tetris");
			window.setContentPane(view);
			window.pack();
			window.setLocationRelativeTo(null);
			window.setVisible(true);
			window.setDefaultCloseOperation(EXIT_ON_CLOSE);

			loop.setView(view);
		});

		var playlist = new Playlist();
		new Thread(playlist, "playlist").start();

		tetris.newPiece();
		loop.run();
	}
}
