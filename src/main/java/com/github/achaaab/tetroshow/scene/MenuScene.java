package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.view.menu.MenuView;

import java.awt.Container;

import static javax.swing.SwingUtilities.invokeLater;

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
	public void initialize() {

		view.requestFocus();
		view.selectButton(0);
	}

	@Override
	public void update(double deltaTime) {

		view.update(deltaTime);
		invokeLater(view::repaint);
	}

	@Override
	public Container getView() {
		return view;
	}

	/**
	 * @since 0.0.0
	 */
	public void play() {
		manager.display(playScene);
	}

	/**
	 * @since 0.0.0
	 */
	public void options() {
		manager.display(optionScene);
	}

	/**
	 * @since 0.0.0
	 */
	public void credits() {
		manager.display(creditsScene);
	}

	/**
	 * @since 0.0.0
	 */
	private void back() {
		exit();
	}
}
