package com.github.achaaab.tetroshow.scene;

import java.awt.Component;
import java.util.Optional;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface Scene {

	/**
	 * Initializes (if necessary) this scene.
	 *
	 * @since 0.0.0
	 */
	void initialize();

	/**
	 * Updates this scene (state and view).
	 *
	 * @param deltaTime elapsed time since previous update in seconds
	 * @since 0.0.0
	 */
	void update(double deltaTime);

	/**
	 * @return
	 * @since 0.0.0
	 */
	Component getView();

	/**
	 * @return
	 * @since 0.0.0
	 */
	Optional<Scene> getParent();

	/**
	 * @since 0.0.0
	 */
	void exit();
}
