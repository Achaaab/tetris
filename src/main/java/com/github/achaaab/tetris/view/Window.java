package com.github.achaaab.tetris.view;

import javax.swing.JFrame;

import static com.github.achaaab.tetris.view.Scaler.scale;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Window extends JFrame {

	private static final int DEFAULT_WIDTH = scale(419);
	private static final int DEFAULT_HEIGHT = scale(500);

	/**
	 * @since 0.0.0
	 */
	public Window() {

		super("Tetris");

		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}
}
