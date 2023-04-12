package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.view.message.Messages;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionListener;

import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.OPTIONS;
import static com.github.achaaab.tetroshow.view.message.Messages.PLAY;
import static com.github.achaaab.tetroshow.view.message.Messages.QUIT;
import static com.github.achaaab.tetroshow.view.message.Messages.QUIT_CONFIRM;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static java.awt.Font.DIALOG;
import static java.awt.Font.PLAIN;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;

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

	/**
	 * @since 0.0.0
	 */
	public MenuView() {

		Messages.register(this::localeChanged);

		play = new JButton(getMessage(PLAY));
		options = new JButton(getMessage(OPTIONS));
		quit = new JButton(getMessage(QUIT));

		play.setFont(FONT);
		options.setFont(FONT);
		quit.setFont(FONT);

		add(play);
		add(options);
		add(quit);
	}

	/**
	 * @since 0.0.0
	 */
	private void localeChanged() {

		play.setText(getMessage(PLAY));
		options.setText(getMessage(OPTIONS));
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
	 * @param quitListener
	 * @since 0.0.0
	 */
	public void onQuit(ActionListener quitListener) {
		quit.addActionListener(quitListener);
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public boolean confirmQuit() {
		return showConfirmDialog(this, getMessage(QUIT_CONFIRM)) == YES_OPTION;
	}
}
