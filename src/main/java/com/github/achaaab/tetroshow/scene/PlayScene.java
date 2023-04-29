package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.view.play.TetroshowView;
import org.slf4j.Logger;

import java.awt.Container;
import java.lang.reflect.InvocationTargetException;

import static javax.swing.SwingUtilities.invokeAndWait;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * play scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class PlayScene extends AbstractScene {

	private static final Logger LOGGER = getLogger(PlayScene.class);

	private final Tetroshow tetroshow;
	private final TetroshowView view;

	/**
	 * Creates a play scene.
	 *
	 * @param manager scene manager
	 * @param parent parent scene
	 * @since 0.0.0
	 */
	public PlayScene(SceneManager manager, Scene parent) {

		super(manager, parent);

		tetroshow = new Tetroshow();
		view = new TetroshowView(tetroshow);
		tetroshow.setExitListener(this::exit);

		var keyboard = tetroshow.getKeyboard();
		view.addKeyListener(keyboard);
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

		try {
			invokeAndWait(() -> view.paintImmediately(view.getBounds()));
		} catch (InterruptedException | InvocationTargetException exception) {
			LOGGER.error(exception.getMessage(), exception);
		}
	}

	@Override
	public Container getView() {
		return view;
	}
}
