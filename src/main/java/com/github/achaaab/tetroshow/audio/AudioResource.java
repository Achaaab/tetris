package com.github.achaaab.tetroshow.audio;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.MissingResourceException;

/**
 * audio resource
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class AudioResource implements Audio {

	protected final String name;

	/**
	 * @param name
	 * @since 0.0.0
	 */
	public AudioResource(String name) {
		this.name = name;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public InputStream openInputStream() {

		var classLoader = getClass().getClassLoader();
		var inputStream = classLoader.getResourceAsStream(name);

		if (inputStream == null) {
			throw new MissingResourceException("missing audio resource", name, null);
		}

		return new BufferedInputStream(inputStream);
	}
}
