package com.github.achaaab.tetroshow.view.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static com.github.achaaab.tetroshow.utility.ResourceUtility.loadFont;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scaleFloat;
import static java.lang.Math.PI;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import static java.lang.Math.toIntExact;

/**
 * label with letters y position following a sine law
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class SineLabel extends Component {

	private static final String FONT_RESOURCE_NAME = "font/monofonto_regular.otf";
	private static final Font FONT = loadFont(FONT_RESOURCE_NAME).deriveFont(scaleFloat(50.0f));

	private static final double DEFAULT_FREQUENCY = 0.8;
	private static final double DEFAULT_AMPLITUDE = scaleFloat(30.0f);
	private static final double DEFAULT_PERIOD_COUNT = 1.0;

	private static final Color[] DEFAULT_PALETTE = {
			new Color(0, 159, 218),
			new Color(0, 101, 189),
			new Color(255, 121, 0),
			new Color(254, 203, 0),
			new Color(105, 190, 40),
			new Color(149, 45, 152),
			new Color(237, 41, 57) };

	private final double frequency;
	private final double amplitude;
	private final double periodCount;
	private final Color[] palette;

	private String text;
	private int letterCount;
	private int[] letterY;
	private double time;

	/**
	 * Creates a new sinus label.
	 *
	 * @param text text to display and animate
	 * @since 0.0.0
	 */
	public SineLabel(String text) {
		this(text, DEFAULT_FREQUENCY, DEFAULT_AMPLITUDE, DEFAULT_PERIOD_COUNT, DEFAULT_PALETTE);
	}

	/**
	 * Creates a new sinus label.
	 *
	 * @param text text to display and animate
	 * @param frequency number of oscillations per second for each letter
	 * @param amplitude amplitude of oscillations (in pixels)
	 * @param periodCount number of displayed periods
	 * @param palette palette of colors
	 * @since 0.0.0
	 */
	public SineLabel(String text, double frequency, double amplitude, double periodCount, Color[] palette) {

		setText(text);

		this.frequency = frequency;
		this.amplitude = amplitude;
		this.periodCount = periodCount;
		this.palette = palette;
	}

	/**
	 * Changes the text.
	 *
	 * @param text text to display
	 * @since 0.0.0
	 */
	public void setText(String text) {

		this.text = text;

		letterCount = text.length();
		letterY = new int[letterCount];
		time = 0;
	}

	/**
	 * Updates the sinus animation. Changes the vertical positioning of each letter according to a sine law.
	 *
	 * @param deltaTime time elapsed since the last update (in seconds)
	 * @since 0.0.0
	 */
	public void update(double deltaTime) {

		time += deltaTime;

		for (var letterIndex = 0; letterIndex < letterCount; letterIndex++) {

			var phase = letterIndex * periodCount / (letterCount - 1);
			var angle = PI * 2 * (frequency * time + phase);

			letterY[letterIndex] = toIntExact(round(amplitude * (1 + sin(angle))));
		}
	}

	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		graphics.setFont(FONT);
		var fontMetrics = graphics.getFontMetrics();
		var letterHeight = fontMetrics.getHeight();
		var x = 0;

		for (var letterIndex = 0; letterIndex < letterCount; letterIndex++) {

			var letter = text.substring(letterIndex, letterIndex + 1);

			graphics.setColor(palette[letterIndex % palette.length]);
			graphics.drawString(letter, x, letterY[letterIndex] + letterHeight);
			x += round(1.2f * fontMetrics.stringWidth(letter));
		}
	}
}
