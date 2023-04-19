package com.github.achaaab.tetroshow.view.segment;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MultiplexedDisplay {

	private final int size;
	private final Color onColor;
	private final Color offColor;


	public MultiplexedDisplay(int size, Color onColor, Color offColor) {

		this.size = size;
		this.onColor = onColor;
		this.offColor = offColor;
	}

	/**
	 * @param string
	 * @param graphics
	 * @since 0.0.0
	 */
	public void display(String string, Graphics2D graphics, int x, int y) {

		var display = new Display7Segment();
		display.translate(x, y);

		var stringLength = string.length();

		var characterIndex = stringLength - size;

		for (var index = 0; index < size; index++) {

			var character = characterIndex >= 0 ?
					string.charAt(characterIndex) :
					null;

			display.display(character);
			var segments = display.getSegments();

			for (var segment : segments) {

				graphics.setColor(segment.isOn() ? onColor : offColor);
				graphics.fillPolygon(segment);
			}

			characterIndex++;
			display.translate();
		}
	}
}
