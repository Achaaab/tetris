package com.github.achaaab.tetroshow.view.component;

/**
 * input component (button, select, text field...)
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class Input extends Component {

	protected boolean selected;

	/**
	 * Creates a new input component, initially not selected.
	 *
	 * @since 0.0.0
	 */
	public Input() {
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
	 * @param keyCode code of the typed key
	 * @since 0.0.0
	 */
	public abstract void keyTyped(int keyCode);
}
