package com.github.achaaab.tetroshow.view.component;

import java.awt.Color;
import java.awt.Graphics2D;

import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;

/**
 * simple label
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Label extends Component {

	private static final Color DEFAULT_COLOR = new Color(255, 121, 0);

	private final String textKey;

	/**
	 * Creates a new simple label.
	 *
	 * @param textKey key of the text to display and animate
	 * @since 0.0.0
	 */
	public Label(String textKey) {
		this.textKey = textKey;
	}

	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		var text = getMessage(textKey);

		graphics.setColor(DEFAULT_COLOR);
		graphics.drawString(text, 0, 0);
	}
}
