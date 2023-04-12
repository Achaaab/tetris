package com.github.achaaab.tetroshow.configuration;

/**
 * @param level
 * @param value
 * @param <T>
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public record Parameter<T>(int level, T value) implements Comparable<Parameter<T>> {

	/**
	 * @param level
	 * @param value
	 * @since 0.0.0
	 */
	public Parameter {

	}

	@Override
	public int compareTo(Parameter<T> parameter) {
		return level - parameter.level;
	}
}
