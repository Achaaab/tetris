package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.model.Credits;
import com.github.achaaab.tetroshow.view.menu.CreditsView;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * credits scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class CreditsScene extends AbstractScene implements KeyListener {

	private final Credits model;
	private final CreditsView view;

	/**
	 * @param manager scene manager
	 * @param parent parent scene
	 * @since 0.0.0
	 */
	public CreditsScene(SceneManager manager, Scene parent) {

		super(manager, parent);

		model = new Credits();
		view = new CreditsView(model.getLines());
		view.addKeyListener(this);

		initialize();
	}

	@Override
	public void initialize() {

		model.reset();
		view.requestFocus();
	}

	@Override
	public void update(double deltaTime) {

		model.update(deltaTime);
		view.display(model.getTime());
	}

	@Override
	public Component getView() {
		return view;
	}

	@Override
	public void keyTyped(KeyEvent event) {

	}

	@Override
	public void keyPressed(KeyEvent event) {
		exit();
	}

	@Override
	public void keyReleased(KeyEvent event) {

	}
}
