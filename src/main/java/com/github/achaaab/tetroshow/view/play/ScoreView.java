package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.Tetroshow;

import java.awt.Graphics2D;
import java.text.MessageFormat;

import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.LEVEL;
import static com.github.achaaab.tetroshow.view.message.Messages.SCORE;
import static com.github.achaaab.tetroshow.view.message.Messages.TIME;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static com.github.achaaab.tetroshow.view.skin.Skin.getCurrentSkin;
import static java.lang.Math.round;

/**
 * score view
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class ScoreView extends PlayComponent {

	private static final int MARGIN = scale(10.0f);
	public static final int WIDTH = 2 * MARGIN + scale(120.0f);
	private static final int LINE_HEIGHT = scale(30.0f);

	private static final MessageFormat TIME_FORMAT = new MessageFormat("{0,number,00}:{1,number,00}:{2,number,00}");

	/**
	 * @param time time in seconds
	 * @return time formatted as "00:00:00"
	 * @since 0.0.0
	 */
	private static String formatTime(double time) {

		var timeHundredths = round(time * 100);
		var hundredths = timeHundredths % 100;

		var timeSeconds = timeHundredths / 100;
		var seconds = timeSeconds % 60;

		var timeMinutes = timeSeconds / 60;
		var minutes = timeMinutes % 60;

		return TIME_FORMAT.format(new Object[] { minutes, seconds, hundredths });
	}

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

		var skin = getCurrentSkin();

		skin.drawTitle(graphics, getMessage(LEVEL), 0, 0);
		skin.drawValue(graphics, Integer.toString(level), 0, LINE_HEIGHT);

		skin.drawTitle(graphics, getMessage(SCORE), 0, 2 * LINE_HEIGHT);
		skin.drawValue(graphics, Integer.toString(score), 0, 3 * LINE_HEIGHT);

		skin.drawTitle(graphics, getMessage(TIME), 0, 4 * LINE_HEIGHT);
		skin.drawValue(graphics, formatTime(time), 0, 5 * LINE_HEIGHT);
	}
}
