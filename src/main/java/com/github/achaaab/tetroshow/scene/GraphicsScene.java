package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.settings.Level;
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
		view.onSkinChanged(this::skinChanged);
		view.onSynchronizationChanged(this::synchronizationChanged);
		view.onParticleLevelChanged(this::particleLevelChanged);
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
	 * @param skinName name of the new skin to apply
	 * @since 0.0.0
	 */
	private void skinChanged(String skinName) {
		settings.getGraphics().setSkin(skinName);
	}

	/**
	 * @param synchronization whether to synchronize graphics state
	 * @since 0.0.0
	 */
	private void synchronizationChanged(boolean synchronization) {
		settings.getGraphics().setSynchronization(synchronization);
	}

	/**
	 * @param particleLevel level of details for particle effects
	 * @since 0.0.0
	 */
	private void particleLevelChanged(Level particleLevel) {
		settings.getGraphics().setParticleLevel(particleLevel);
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
