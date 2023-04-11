package com.github.achaaab.tetris.scene;

import com.github.achaaab.tetris.audio.Playlist;
import com.github.achaaab.tetris.model.classic.Tetris;
import com.github.achaaab.tetris.view.play.TetrisView;

import java.awt.Container;

/**
 * play scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class PlayScene extends AbstractScene {

	private final Tetris tetris;
	private final TetrisView view;

	/**
	 * @param manager
	 * @param parent
	 * @since 0.0.0
	 */
	public PlayScene(SceneManager manager, Scene parent) {

		super(manager, parent);

		tetris = new Tetris();
		view = new TetrisView(tetris);
		tetris.setExitListener(this::exit);

		var keyboard = tetris.getKeyboard();
		view.addKeyListener(keyboard);

		var playlist = new Playlist();
		new Thread(playlist, "playlist").start();
	}

	@Override
	public void initialize() {

		tetris.reset();
		view.requestFocus();
		tetris.newPiece();
	}

	@Override
	public void update(double deltaTime) {

		tetris.update(deltaTime);
		view.render();
	}

	@Override
	public Container getView() {
		return view;
	}
}
