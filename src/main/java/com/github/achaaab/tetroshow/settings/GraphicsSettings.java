package com.github.achaaab.tetroshow.settings;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GraphicsSettings {

	private boolean synchronizeState;

	/**
	 * @return whether to synchronize graphics state (useful for some animations that could stutter otherwise)
	 * @since 0.0.0
	 */
	public boolean isSynchronizeState() {
		return synchronizeState;
	}

	/**
	 * @param synchronizeState whether to synchronize graphics state
	 * (useful for some animations that could stutter otherwise)
	 * @since 0.0.0
	 */
	public void setSynchronizeState(boolean synchronizeState) {
		this.synchronizeState = synchronizeState;
	}
}
