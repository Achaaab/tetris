package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.settings.Rules;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.menu.GameplayView;

import java.awt.Container;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * gameplay settings scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GameplayScene extends AbstractScene {

	private final GameplayView view;
	private final Settings settings;

	/**
	 * Creates a new gameplay settings scene.
	 *
	 * @param manager scene manager
	 * @param parent parent scene
	 * @since 0.0.0
	 */
	public GameplayScene(SceneManager manager, Scene parent) {

		super(manager, parent);

		settings = Settings.getDefaultInstance();

		view = new GameplayView();
		view.onHoldLimitChanged(this::holdLimitChanged);
		view.onRulesChanged(this::rulesChanged);
		view.onBack(this::back);
	}

	@Override
	public void initialize() {

		view.requestFocus();
		view.selectFirstInput();
	}

	@Override
	public void update(double deltaTime) {
		invokeLater(view::repaint);
	}

	/**
	 * @param holdLimit hold capacity
	 * @since 0.0.0
	 */
	private void holdLimitChanged(int holdLimit) {
		settings.getGameplay().setHoldLimit(holdLimit);
	}

	/**
	 * @param rules set of rules
	 * @since 0.0.0
	 */
	private void rulesChanged(Rules rules) {
		settings.getGameplay().setRules(rules.lowerCaseName());
	}

	/**
	 * @since 0.0.0
	 */
	private void back() {

		settings.save();
		exit();
	}

	@Override
	public Container getView() {
		return view;
	}
}
