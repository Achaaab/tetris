package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.view.menu.MenuView;

import java.awt.Container;
import java.awt.event.ActionEvent;

/**
 * Tetroshow menu scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MenuScene extends AbstractScene {

	private final MenuView view;
	private final OptionScene optionScene;
	private final PlayScene playScene;

	/**
	 * @param manager
	 * @since 0.0.0
	 */
	public MenuScene(SceneManager manager) {

		super(manager);

		optionScene = new OptionScene(manager, this);
		playScene = new PlayScene(manager, this);

		view = new MenuView();
		view.onQuit(this::back);
		view.onPlay(this::play);
		view.onOptions(this::options);
	}

	@Override
	public Container getView() {
		return view;
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	public void options(ActionEvent event) {
		manager.display(optionScene);
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	public void play(ActionEvent event) {
		manager.display(playScene);
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	private void back(ActionEvent event) {
		exit();
	}
}
