package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.view.component.Component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.text.MessageFormat;

import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.LEVEL;
import static com.github.achaaab.tetroshow.view.message.Messages.SCORE;
import static com.github.achaaab.tetroshow.view.message.Messages.TIME;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static java.awt.Color.ORANGE;
import static java.awt.Color.WHITE;
import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import static java.lang.Math.round;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class ScoreView extends Component {

	private static final int MARGIN = scale(10.0f);
	public static final int WIDTH = 2 * MARGIN + scale(120.0f);
	private static final int LINE_SPACE = scale(5.0f);
	private static final int FONT_SIZE = scale(20.0f);

	private static final Font FONT = new Font(MONOSPACED, PLAIN, FONT_SIZE);
	private static final MessageFormat TIME_FORMAT = new MessageFormat("{0,number,00}:{1,number,00}:{2,number,00}");

	private static final Color[] KEY_COLORS = new Color[] { WHITE, WHITE, WHITE.darker(), WHITE, WHITE };
	private static final Color[] VALUE_COLORS = new Color[] { ORANGE, ORANGE, ORANGE.darker(), ORANGE, ORANGE };
	private static final float[] TEXT_GRADIENT_FRACTIONS = new float[] { 0.0f, 0.6f, 0.75f, 0.9f, 1.0f };

	private final Tetroshow tetroshow;

	/**
	 * Creates a new score view.
	 *
	 * @param tetroshow Tetroshow of which to display the score
	 * @since 0.0.0
	 */
	public ScoreView(Tetroshow tetroshow) {

		this.tetroshow = tetroshow;

		width = WIDTH;
		height = 0;
		margin = MARGIN;
	}

	@Override
	public void paint(Graphics2D graphics) {

		super.paint(graphics);

		var level = tetroshow.getLevel();
		var time = tetroshow.getTime();
		var score = tetroshow.getScore();

		graphics.setFont(FONT);

		var fontMetrics = graphics.getFontMetrics();
		var textHeight = fontMetrics.getHeight();
		var textAscent = fontMetrics.getAscent();

		var x = 0;
		var y = 0;

		y += textAscent;
		drawString(graphics, getMessage(LEVEL), x, y, textHeight, KEY_COLORS);
		y += textHeight + LINE_SPACE;
		drawString(graphics, Integer.toString(level), x, y, textHeight, VALUE_COLORS);

		y += textHeight + LINE_SPACE + LINE_SPACE;
		drawString(graphics, getMessage(SCORE), x, y, textHeight, KEY_COLORS);
		y += textHeight + LINE_SPACE;
		drawString(graphics, Integer.toString(score), x, y, textHeight, VALUE_COLORS);

		y += textHeight + LINE_SPACE + LINE_SPACE;
		drawString(graphics, getMessage(TIME), x, y, textHeight, KEY_COLORS);
		y += textHeight + LINE_SPACE;
		drawString(graphics, formatTime(time), x, y, textHeight, VALUE_COLORS);
	}

	/**
	 * Draws a text.
	 *
	 * @param graphics graphics with which to draw
	 * @param text text to draw
	 * @param x x position of the text
	 * @param y y position of the text
	 * @param height height of the text
	 * @param colors gradient colors
	 * @since 0.0.0
	 */
	private void drawString(Graphics2D graphics, String text,
			int x, int y, int height, Color[] colors) {

		var top = new Point2D.Float(0, y - height);
		var bottom = new Point2D.Float(0, y);
		var gradient = new LinearGradientPaint(top, bottom, TEXT_GRADIENT_FRACTIONS, colors);

		graphics.setPaint(gradient);
		graphics.drawString(text, x, y);
	}

	/**
	 * @param time time in seconds
	 * @return time formatted as "00:00:00"
	 * @since 0.0.0
	 */
	private String formatTime(double time) {

		var timeHundredths = round(time * 100);
		var hundredths = timeHundredths % 100;

		var timeSeconds = timeHundredths / 100;
		var seconds = timeSeconds % 60;

		var timeMinutes = timeSeconds / 60;
		var minutes = timeMinutes % 60;

		return TIME_FORMAT.format(new Object[] { minutes, seconds, hundredths });
	}
}
