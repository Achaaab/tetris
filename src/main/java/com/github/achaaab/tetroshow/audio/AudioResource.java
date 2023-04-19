package com.github.achaaab.tetroshow.audio;

import com.github.achaaab.tetroshow.utility.ResourceUtility;

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
	 * @param name resource name
	 * @since 0.0.0
	 */
	public AudioResource(String name) {
		this.name = name;
	}

	/**
	 * @return input stream
	 * @since 0.0.0
	 */
	public InputStream openInputStream() {

		var inputStream = ResourceUtility.openInputStream(name);

		if (inputStream == null) {
			throw new MissingResourceException("missing audio resource", name, null);
		}

		return new BufferedInputStream(inputStream);
	}

	@Override
	public String name() {
		return name;
	}
}
