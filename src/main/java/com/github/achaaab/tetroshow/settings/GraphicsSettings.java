package com.github.achaaab.tetroshow.settings;

/**
 * graphics settings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GraphicsSettings {

	private String skin;
	private boolean synchronization;
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
	public boolean isSynchronization() {
		return synchronization;
	}

	/**
	 * @param synchronization whether to synchronize graphics state
	 * (useful for some animations that could stutter otherwise)
	 * @since 0.0.0
	 */
	public void setSynchronization(boolean synchronization) {
		this.synchronization = synchronization;
	}

	/**
	 * @return level of details for particle effects
	 * @since 0.0.0
	 */
	public Level getParticleLevel() {
		return particleLevel;
	}

	/**
	 * @param particleLevel level of details for particle effects
	 * @since 0.0.0
	 */
	public void setParticleLevel(Level particleLevel) {
		this.particleLevel = particleLevel;
	}
}
