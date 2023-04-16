package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.settings.Settings;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import static java.awt.Toolkit.getDefaultToolkit;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;
import static java.lang.Math.toIntExact;
import static javax.swing.SwingUtilities.invokeLater;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class CreditsView extends JComponent {

	private static final int FONT_SIZE = scale(12.0f);
	private static final Font FONT = new Font(MONOSPACED, PLAIN, FONT_SIZE);
	private static final Color BACKGROUND = new Color(0, 0, 16);
	private static final Color FOREGROUND = new Color(192, 192, 192);

	/**
	 * credits scrolling speed in lines of text per second
	 */
	private static final double SCROLLING_SPEED = 1.5;

	private final List<String> lines;
	private double time;

	/**
	 * @param lines lines of credits
	 * @since 0.0.0
	 */
	public CreditsView(List<String> lines) {
		this.lines = lines;
	}

	/**
	 * Displays this credits view at given time. Lines should go up while time increases.
	 *
	 * @param time credits scene time in seconds
	 * @since 0.0.0
	 */
	public void display(double time) {

		this.time = time;

		invokeLater(this::repaint);
	}

	@Override
	protected void paintComponent(Graphics graphics) {

		var width = getWidth();
		var height = getHeight();
		graphics.setColor(BACKGROUND);
		graphics.fillRect(0, 0, width, height);

		graphics.setFont(FONT);
		graphics.setColor(FOREGROUND);
		var fontMetrics = graphics.getFontMetrics();
		double lineHeight = fontMetrics.getHeight();

		var lineCount = lines.size();

		var scrolledLineCount = time * SCROLLING_SPEED;
		var minLineIndex = toIntExact(round(scrolledLineCount - height / lineHeight - 2));
		var maxLineIndex = toIntExact(round(scrolledLineCount));

		minLineIndex = min(max(minLineIndex, 0), lineCount - 1);
		maxLineIndex = min(max(maxLineIndex, 0), lineCount - 1);

		for (var lineIndex = minLineIndex; lineIndex <= maxLineIndex; lineIndex++) {

			var line = lines.get(lineIndex);
			var lineWidth = fontMetrics.stringWidth(line);

			var x = (width - lineWidth) / 2;
			var y = height + lineHeight * (1 + lineIndex - scrolledLineCount);

			graphics.drawString(line, x, toIntExact(round(y)));
		}

		if (Settings.getDefaultInstance().getGraphics().isSynchronizeState()) {
			getDefaultToolkit().sync();
		}
	}
}
