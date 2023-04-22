package com.github.achaaab.tetroshow.scene;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * abstract scene with a scene manager and optionally a parent scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class AbstractScene implements Scene {

	protected final SceneManager manager;
	private final Scene parent;

	/**
	 * Creates an abstract scene.
	 *
	 * @param manager scene manager
	 * @since 0.0.0
	 */
	public AbstractScene(SceneManager manager) {
		this(manager, null);
	}

	/**
	 * Creates an abstract scene.
	 *
	 * @param manager scene manager
	 * @param parent parent scene
	 * @since 0.0.0
	 */
	public AbstractScene(SceneManager manager, Scene parent) {

		this.manager = manager;
		this.parent = parent;
	}

	@Override
	public void initialize() {

	}

	@Override
	public void update(double deltaTime) {

	}

	@Override
	public Optional<Scene> getParent() {
		return ofNullable(parent);
	}

	@Override
	public void exit() {
		manager.exit(this);
	}
}
