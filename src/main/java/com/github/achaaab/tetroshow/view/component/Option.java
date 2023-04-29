package com.github.achaaab.tetroshow.view.component;

import com.github.achaaab.tetroshow.audio.SoundEffect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.achaaab.tetroshow.audio.AudioBank.MOVE;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

/**
 * simple select
 *
 * @param <T> type of selectable values
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Option<T> extends Input {

	private static final SoundEffect OPTION_CHANGED_SOUND_EFFECT = MOVE;
	private static final int SELECTED_SHIFT = scale(10.0f);

	private static final Color SELECTED_NAME_COLOR = WHITE;
	private static final Color UNSELECTED_NAME_COLOR = GRAY;
	private static final Color SELECTED_VALUE_COLOR = new Color(160, 208, 255);
	private static final Color UNSELECTED_VALUE_COLOR = new Color(0, 101, 189);

	private final Function<T, String> valueToStringFunction;
	private final List<T> values;
	private final int valueX;
	private int index;
	private Consumer<T> consumer;

	/**
	 * Creates a new select.
	 *
	 * @param textKey key of the displayed option name
	 * @param values selectable values
	 * @param valueToStringFunction function converting a value to its string representation
	 * @param valueX distance between name and value (in pixels)
	 * @since 0.0.0
	 */
	public Option(String textKey, List<T> values, Function<T, String> valueToStringFunction, int valueX) {

		super(textKey);

		this.values = values;
		this.valueToStringFunction = valueToStringFunction;
		this.valueX = valueX;

		select(0);
	}

	/**
	 * Selects value at specified index.
	 *
	 * @param index index of the value to select
	 * @since 0.0.0
	 */
	private void select(int index) {
		this.index = index;
	}

	/**
	 * Selects specified value.
	 *
	 * @param value value to select
	 * @since 0.0.0
	 */
	public void select(T value) {
		select(values.indexOf(value));
	}

	/**
	 * @return selected value
	 * @since 0.0.0
	 */
	public T selectedValue() {
		return values.get(index);
	}

	/**
	 * @param consumer selected value consumer
	 * @since 0.0.0
	 */
	public void setConsumer(Consumer<T> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		var text = getMessage(textKey);
		var selectedValue = selectedValue();
		var selectedString = valueToStringFunction.apply(selectedValue);

		graphics.setColor(selected ? SELECTED_NAME_COLOR : UNSELECTED_NAME_COLOR);
		graphics.drawString(text, selected ? SELECTED_SHIFT : 0, 0);
		graphics.setColor(selected ? SELECTED_VALUE_COLOR : UNSELECTED_VALUE_COLOR);
		graphics.drawString("⏴ " + selectedString + " ⏵", valueX, 0);
	}

	/**
	 * Selects the previous value.
	 *
	 * @since 0.0.0
	 */
	public void previous() {

		OPTION_CHANGED_SOUND_EFFECT.play();
		select((index + values.size() - 1) % values.size());
		consumer.accept(selectedValue());
	}

	/**
	 * Selects the next value.
	 *
	 * @since 0.0.0
	 */
	public void next() {

		OPTION_CHANGED_SOUND_EFFECT.play();
		select((index + 1) % values.size());
		consumer.accept(selectedValue());
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

		switch (keyEvent.getKeyCode()) {

			case VK_LEFT -> previous();
			case VK_RIGHT -> next();
		}
	}
}
