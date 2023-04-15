package com.github.achaaab.tetroshow.audio;

/**
 * dummy audio that does not play anything
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Silence implements Audio {

	public static final Silence INSTANCE = new Silence();

	/**
	 * private constructor to ensure {@link #INSTANCE} usage
	 *
	 * @since 0.0.0
	 */
	private Silence() {

	}

	@Override
	public String getName() {
		return "silence";
	}

	@Override
	public void play() {

	}

	@Override
	public void playAndWait() {

	}
}
