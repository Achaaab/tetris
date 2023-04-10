package com.github.achaaab.tetris.view.play;

import com.github.achaaab.tetris.model.classic.Tetris;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.text.MessageFormat;

import static com.github.achaaab.tetris.view.Scaler.scale;
import static java.awt.Color.BLACK;
import static java.awt.Color.GRAY;
import static java.awt.Color.ORANGE;
import static java.awt.Color.WHITE;
import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import static java.lang.Math.round;
import static javax.swing.BorderFactory.createLineBorder;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class ScoreView extends JComponent {

	private static final int MARGIN = scale(5.0f);
	private static final int LINE_SPACE = scale(5.0f);
	private static final int PREFERRED_WIDTH = scale(120.0f);
	private static final int FONT_SIZE = scale(20.0f);

	private static final Font FONT = new Font(MONOSPACED, PLAIN, FONT_SIZE);
	private static final MessageFormat TIME_FORMAT = new MessageFormat("{0,number,00}:{1,number,00}:{2,number,00}");

	private static final Color[] KEY_COLORS = new Color[] { WHITE, WHITE, WHITE.darker(), WHITE, WHITE };
	private static final Color[] VALUE_COLORS = new Color[] { ORANGE, ORANGE, ORANGE.darker(), ORANGE, ORANGE };
	private static final float[] TEXT_GRADIENT_FRACTIONS = new float[] { 0.0f, 0.6f, 0.75f, 0.9f, 1.0f };

	private final Tetris tetris;

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public ScoreView(Tetris tetris) {

		this.tetris = tetris;

		setPreferredSize(new Dimension(PREFERRED_WIDTH, 0));
		setBorder(createLineBorder(GRAY, MARGIN / 2));
		setBackground(BLACK);
	}

	@Override
	public void paintComponent(Graphics graphics) {

		var level = tetris.getLevel();
		var time = tetris.getTime();
		var score = tetris.getScore();

		graphics.setColor(getBackground());
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setFont(FONT);

		var graphics2d = (Graphics2D) graphics;
		var fontMetrics = graphics.getFontMetrics();
		var textHeight = fontMetrics.getHeight();
		var textAscent = fontMetrics.getAscent();

		var x = MARGIN;
		var y = MARGIN;

		y += textAscent;
		drawString(graphics2d, "Level", x, y, textHeight, KEY_COLORS);
		y += textHeight + LINE_SPACE;
		drawString(graphics2d, Integer.toString(level), x, y, textHeight, VALUE_COLORS);

		y += textHeight + LINE_SPACE + LINE_SPACE;
		drawString(graphics2d, "Score", x, y, textHeight, KEY_COLORS);
		y += textHeight + LINE_SPACE;
		drawString(graphics2d, Integer.toString(score), x, y, textHeight, VALUE_COLORS);

		y += textHeight + LINE_SPACE + LINE_SPACE;
		drawString(graphics2d, "Time", x, y, textHeight, KEY_COLORS);
		y += textHeight + LINE_SPACE;
		drawString(graphics2d, formatTime(time), x, y, textHeight, VALUE_COLORS);
	}

	/**
	 * @param graphics
	 * @param text
	 * @param x
	 * @param y
	 * @param height
	 * @param colors
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
	 * @param time time in nanoseconds
	 * @return time formatted as "00:00:00"
	 * @since 0.0.0
	 */
	private String formatTime(long time) {

		var timeHundredths = round(time / 10_000_000.0);
		var hundredths = timeHundredths % 100;

		var timeSeconds = timeHundredths / 100;
		var seconds = timeSeconds % 60;

		var timeMinutes = timeSeconds / 60;
		var minutes = timeMinutes % 60;

		return TIME_FORMAT.format(new Object[] { minutes, seconds, hundredths });
	}
}
