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

	private final PlayScene playScene;
	private final OptionScene optionScene;
	private final CreditsScene creditsScene;

	private final MenuView view;

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
		view.onPlay(this::startsGame);
		view.onOptions(this::displayOptions);
		view.onCredits(this::displayCredits);
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
	 * Starts game.
	 *
	 * @since 0.0.0
	 */
	public void startsGame() {
		manager.display(playScene);
	}

	/**
	 * Displays options.
	 *
	 * @since 0.0.0
	 */
	public void displayOptions() {
		manager.display(optionScene);
	}

	/**
	 * Displays credits.
	 *
	 * @since 0.0.0
	 */
	public void displayCredits() {
		manager.display(creditsScene);
	}

	/**
	 * @since 0.0.0
	 */
	private void back() {
		exit();
	}
}
