package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.view.menu.MainMenuView;

import java.awt.Container;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * Tetroshow menu scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MainMenuScene extends AbstractScene {

	private final PlayScene playScene;
	private final SettingsScene settingsScene;
	private final CreditsScene creditsScene;

	private final MainMenuView view;

	/**
	 * Creates a menu scene.
	 *
	 * @param manager scene manager
	 * @since 0.0.0
	 */
	public MainMenuScene(SceneManager manager) {

		super(manager);

		playScene = new PlayScene(manager, this);
		settingsScene = new SettingsScene(manager, this);
		creditsScene = new CreditsScene(manager, this);

		view = new MainMenuView();
		view.onPlay(this::startsGame);
		view.onOptions(this::displayOptions);
		view.onCredits(this::displayCredits);
		view.onQuit(this::back);
	}

	@Override
	public void initialize() {

		view.requestFocus();
		view.selectFirstInput();
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
		manager.display(settingsScene);
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
