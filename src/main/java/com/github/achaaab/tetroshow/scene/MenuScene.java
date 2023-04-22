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
	private final PlayScene playScene;
	private final OptionScene optionScene;
	private final CreditsScene creditsScene;

	/**
	 * Creates a menu scene.
	 *
	 * @param manager scene manager
	 * @since 0.0.0
	 */
	public MenuScene(SceneManager manager) {

		super(manager);

		playScene = new PlayScene(manager, this);
		optionScene = new OptionScene(manager, this);
		creditsScene = new CreditsScene(manager, this);

		view = new MenuView();
		view.onPlay(this::play);
		view.onOptions(this::options);
		view.onCredits(this::credits);
		view.onQuit(this::back);
	}

	@Override
	public Container getView() {
		return view;
	}

	/**
	 * @param event play action event
	 * @since 0.0.0
	 */
	public void play(ActionEvent event) {
		manager.display(playScene);
	}

	/**
	 * @param event options action event
	 * @since 0.0.0
	 */
	public void options(ActionEvent event) {
		manager.display(optionScene);
	}

	/**
	 * @param event credits action event
	 * @since 0.0.0
	 */
	public void credits(ActionEvent event) {
		manager.display(creditsScene);
	}

	/**
	 * @param event back action event
	 * @since 0.0.0
	 */
	private void back(ActionEvent event) {
		exit();
	}
}
