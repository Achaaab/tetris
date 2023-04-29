package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.menu.GraphicsView;

import java.awt.Container;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * graphics settings scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GraphicsScene extends AbstractScene {

	private final GraphicsView view;
	private final Settings settings;

	/**
	 * Creates a new graphics settings scene.
	 *
	 * @param manager scene manager
	 * @param parent parent scene
	 * @since 0.0.0
	 */
	public GraphicsScene(SceneManager manager, Scene parent) {

		super(manager, parent);

		settings = Settings.getDefaultInstance();

		view = new GraphicsView();
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
