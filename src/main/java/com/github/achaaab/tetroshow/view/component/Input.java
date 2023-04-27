package com.github.achaaab.tetroshow.view.component;

import java.awt.event.KeyEvent;

/**
 * input component (button, select, text field...)
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class Input extends Component {

	protected final String textKey;
	protected boolean selected;

	/**
	 * Creates a new input component, initially not selected.
	 *
	 * @param textKey key of the text describing the input to create
	 * @since 0.0.0
	 */
	public Input(String textKey) {

		this.textKey = textKey;

		selected = false;
	}

	/**
	 * @param selected whether this input is selected
	 * @since 0.0.0
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @param keyEvent key event
	 * @since 0.0.0
	 */
	public abstract void keyTyped(KeyEvent keyEvent);
}
