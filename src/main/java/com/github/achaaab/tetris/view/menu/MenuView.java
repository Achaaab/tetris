package com.github.achaaab.tetris.view.menu;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import static com.github.achaaab.tetris.view.Scaler.scale;
import static java.lang.System.exit;
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

		play = new JButton("Play");
		options = new JButton("Options");
		quit = new JButton("Quit");

		play.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		options.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		quit.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

		add(play);
		add(options);
		add(quit);
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
		return showConfirmDialog(this, "Are you sure?") == YES_OPTION;
	}
}
