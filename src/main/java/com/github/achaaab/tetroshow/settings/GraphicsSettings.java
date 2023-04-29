package com.github.achaaab.tetroshow.settings;

/**
 * graphics settings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GraphicsSettings {

	private String skin;
	private boolean synchronizeState;
	private Level particleLevel;

	/**
	 * @return skin name
	 * @since 0.0.0
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * @param skin skin name
	 * @since 0.0.0
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

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

	/**
	 * @return particle level
	 * @since 0.0.0
	 */
	public Level getParticleLevel() {
		return particleLevel;
	}

	/**
	 * @param particleLevel particle level
	 * @since 0.0.0
	 */
	public void setParticleLevel(Level particleLevel) {
		this.particleLevel = particleLevel;
	}
}
