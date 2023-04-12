package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.audio.Playlist;
import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.view.play.TetroshowView;

import java.awt.Container;

/**
 * play scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class PlayScene extends AbstractScene {

	private final Tetroshow tetroshow;
	private final TetroshowView view;

	/**
	 * @param manager
	 * @param parent
	 * @since 0.0.0
	 */
	public PlayScene(SceneManager manager, Scene parent) {

		super(manager, parent);

		tetroshow = new Tetroshow();
		view = new TetroshowView(tetroshow);
		tetroshow.setExitListener(this::exit);

		var keyboard = tetroshow.getKeyboard();
		view.addKeyListener(keyboard);

		var playlist = new Playlist();
		new Thread(playlist, "playlist").start();
	}

	@Override
	public void initialize() {

		tetroshow.reset();
		view.requestFocus();
		tetroshow.newPiece();
	}

	@Override
	public void update(double deltaTime) {

		tetroshow.update(deltaTime);
		view.render();
	}

	@Override
	public Container getView() {
		return view;
	}
}
