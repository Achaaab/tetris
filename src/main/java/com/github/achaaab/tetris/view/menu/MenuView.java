package com.github.achaaab.tetris.view.menu;

import com.github.achaaab.tetris.view.message.Messages;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import static com.github.achaaab.tetris.view.Scaler.scale;
import static com.github.achaaab.tetris.view.message.Messages.OPTIONS;
import static com.github.achaaab.tetris.view.message.Messages.PLAY;
import static com.github.achaaab.tetris.view.message.Messages.QUIT;
import static com.github.achaaab.tetris.view.message.Messages.QUIT_CONFIRM;
import static com.github.achaaab.tetris.view.message.Messages.getMessage;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MenuView extends JPanel {

	private static final int BUTTON_WIDTH = scale(100);
	private static final int BUTTON_HEIGHT = scale(30);

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

		play.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		options.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		quit.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

		add(play);
		add(options);
		add(quit);
	}

	/**
	 * @since 0.0.0
	 */
	private void localeChanged() {

		play.setText(getMessage(PLAY));
		options.setText(getMessage(PLAY));
		quit.setText(getMessage(PLAY));
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
