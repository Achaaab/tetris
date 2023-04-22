package com.github.achaaab.tetroshow.audio;

/**
 * an audio with a name
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class NamedAudio implements Audio {

	protected final String name;

	/**
	 * Creates a new named audio.
	 *
	 * @param name audio name
	 * @since 0.0.0
	 */
	public NamedAudio(String name) {
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}
}
