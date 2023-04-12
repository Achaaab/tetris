package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.view.message.Messages;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionListener;

import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.CREDITS;
import static com.github.achaaab.tetroshow.view.message.Messages.OPTIONS;
import static com.github.achaaab.tetroshow.view.message.Messages.PLAY;
import static com.github.achaaab.tetroshow.view.message.Messages.QUIT;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static java.awt.Font.DIALOG;
import static java.awt.Font.PLAIN;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MenuView extends JPanel {

	private static final int FONT_SIZE = 20;
	private static final Font FONT = new Font(DIALOG, PLAIN, scale(FONT_SIZE));

	private final JButton play;
	private final JButton options;
	private final JButton quit;
	private final JButton credits;

	/**
	 * @since 0.0.0
	 */
	public MenuView() {

		Messages.register(this::localeChanged);

		play = new JButton(getMessage(PLAY));
		options = new JButton(getMessage(OPTIONS));
		credits = new JButton(getMessage(CREDITS));
		quit = new JButton(getMessage(QUIT));

		play.setFont(FONT);
		options.setFont(FONT);
		credits.setFont(FONT);
		quit.setFont(FONT);

		add(play);
		add(options);
		add(credits);
		add(quit);
	}

	/**
	 * @since 0.0.0
	 */
	private void localeChanged() {

		play.setText(getMessage(PLAY));
		options.setText(getMessage(OPTIONS));
		credits.setText(getMessage(CREDITS));
		quit.setText(getMessage(QUIT));
	}

	/**
	 * @param playListener
	 * @since 0.0.0
	 */
	public void onPlay(ActionListener playListener) {
		play.addActionListener(playListener);
	}

	/**
	 * @param optionsListener
	 * @since 0.0.0
	 */
	public void onOptions(ActionListener optionsListener) {
		options.addActionListener(optionsListener);
	}

	/**
	 * @param creditsListener
	 * @since 0.0.0
	 */
	public void onCredits(ActionListener creditsListener) {
		credits.addActionListener(creditsListener);
	}

	/**
	 * @param quitListener
	 * @since 0.0.0
	 */
	public void onQuit(ActionListener quitListener) {
		quit.addActionListener(quitListener);
	}
}
