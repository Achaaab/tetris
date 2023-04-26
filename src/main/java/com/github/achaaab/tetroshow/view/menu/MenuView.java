package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.audio.SoundEffect;
import com.github.achaaab.tetroshow.view.component.Component;
import com.github.achaaab.tetroshow.view.component.Input;
import com.github.achaaab.tetroshow.view.play.TetroshowView;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.utility.SwingUtility.hideCursor;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

/**
 * abstract menu view
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class MenuView extends JComponent implements KeyListener {

	private static final Color BACKGROUND_COLOR = new Color(0, 0, 16);
	private static final SoundEffect SELECTION_SOUND_EFFECT = getSoundEffect("audio/effect/move.wav", 6);

	private final List<Component> components;
	private final List<Input> inputs;
	private int inputCount;
	private int selectedInputIndex;

	/**
	 * Creates a menu view with initially empty input list.
	 *
	 * @since 0.0.0
	 */
	public MenuView() {

		components = new ArrayList<>();
		inputs = new ArrayList<>();
		inputCount = 0;
		selectedInputIndex = 0;

		addKeyListener(this);
		hideCursor(this);
		setPreferredSize(TetroshowView.DIMENSION);
	}

	/**
	 * Adds a component to this menu view.
	 *
	 * @param component component to add
	 * @since 0.0.0
	 */
	protected void add(Component component) {

		components.add(component);

		if (component instanceof Input input) {

			inputs.add(input);
			inputCount++;
		}
	}


	@Override
	public void paint(Graphics graphics) {

		var graphics2d = (Graphics2D) graphics;

		graphics2d.setColor(BACKGROUND_COLOR);
		graphics2d.fillRect(0, 0, getWidth(), getHeight());

		graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		components.forEach(component -> component.paint(graphics2d));
		graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
	}

	/**
	 * Selects the first input.
	 *
	 * @since 0.0.0
	 */
	public void selectFirstInput() {
		selectInput(0, false);
	}

	/**
	 * Selects the next input. Selects the first input if the selected input was the last one.
	 *
	 * @since 0.0.0
	 */
	protected void selectNextInput() {
		selectInput((selectedInputIndex + 1) % inputCount, true);
	}

	/**
	 * Selects the previous input. Selects the last input if the selected input was the first one.
	 *
	 * @since 0.0.0
	 */
	protected void selectPreviousInput() {
		selectInput((selectedInputIndex - 1 + inputCount) % inputCount, true);
	}

	/**
	 * Selects an input.
	 *
	 * @param index input index
	 * @param playSound whether to play a selection sound
	 * @since 0.0.0
	 */
	private void selectInput(int index, boolean playSound) {

		inputs.get(selectedInputIndex).setSelected(false);
		selectedInputIndex = index;
		inputs.get(selectedInputIndex).setSelected(true);

		if (playSound) {
			SELECTION_SOUND_EFFECT.play();
		}
	}

	/**
	 * @return currently selected input
	 * @since 0.0.0
	 */
	protected Input getSelectedInput() {
		return inputs.get(selectedInputIndex);
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {

	}
}
