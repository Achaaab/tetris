package com.github.achaaab.tetroshow.view.component;

import com.github.achaaab.tetroshow.audio.SoundEffect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;

/**
 * simple select
 *
 * @param <T> type of selectable values
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Option<T> extends Component {

	private static final SoundEffect OPTION_CHANGED_SOUND_EFFECT = getSoundEffect("audio/effect/move.wav", 6);
	private static final int SELECTED_SHIFT = scale(10.0f);

	private static final Color SELECTED_NAME_COLOR = WHITE;
	private static final Color UNSELECTED_NAME_COLOR = GRAY;
	private static final Color SELECTED_VALUE_COLOR = new Color(96, 224, 255);
	private static final Color UNSELECTED_VALUE_COLOR = new Color(0, 101, 189);

	private final Function<T, String> valueToStringFunction;
	private final List<T> values;
	private final int valueX;
	private String name;
	private int index;
	private boolean selected;
	private Consumer<T> consumer;

	/**
	 * Creates a new select.
	 *
	 * @param name displayed option name
	 * @param values selectable values
	 * @param valueToStringFunction function converting a value to its string representation
	 * @param valueX distance between title start and value start
	 * @since 0.0.0
	 */
	public Option(String name, List<T> values, Function<T, String> valueToStringFunction, int valueX) {

		this.name = name;
		this.values = values;
		this.valueToStringFunction = valueToStringFunction;
		this.valueX = valueX;

		selected = false;
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
	 * @param name displayed option name
	 * @since 0.0.0
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param selected whether this button is selected
	 * @since 0.0.0
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
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

		var selectedValue = selectedValue();
		var selectedString = valueToStringFunction.apply(selectedValue);
		graphics.setColor(selected ? SELECTED_NAME_COLOR : UNSELECTED_NAME_COLOR);
		graphics.drawString(name, selected ? SELECTED_SHIFT : 0, 0);
		graphics.setColor(selected ? SELECTED_VALUE_COLOR : UNSELECTED_VALUE_COLOR);
		graphics.drawString("< " + selectedString + " >", valueX, 0);
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
}
