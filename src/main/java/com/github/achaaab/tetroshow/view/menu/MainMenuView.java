package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.view.component.Button;
import com.github.achaaab.tetroshow.view.component.SineLabel;

import java.awt.Font;
import java.awt.event.KeyEvent;

import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.CREDITS;
import static com.github.achaaab.tetroshow.view.message.Messages.OPTIONS;
import static com.github.achaaab.tetroshow.view.message.Messages.PLAY;
import static com.github.achaaab.tetroshow.view.message.Messages.QUIT;
import static java.awt.Font.DIALOG;
import static java.awt.Font.PLAIN;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_UP;

/**
 * main menu view
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MainMenuView extends MenuView {

	private static final String TITLE = "TETROSHOW";
	private static final Font FONT = new Font(DIALOG, PLAIN, scale(25));
	private static final int BUTTON_HEIGHT = scale(50);

	private final SineLabel title;
	private final Button play;
	private final Button options;
	private final Button quit;
	private final Button credits;

	/**
	 * Creates a new main menu view.
	 *
	 * @since 0.0.0
	 */
	public MainMenuView() {

		title = new SineLabel(TITLE);
		play = new Button(PLAY);
		options = new Button(OPTIONS);
		credits = new Button(CREDITS);
		quit = new Button(QUIT);

		add(title);
		add(play);
		add(options);
		add(credits);
		add(quit);

		play.setFont(FONT);
		options.setFont(FONT);
		credits.setFont(FONT);
		quit.setFont(FONT);

		selectFirstInput();

		title.setX(scale(90.0f));
		title.setY(scale(20.0f));

		var x = scale(100.0f);
		var y = scale(250.0f);

		play.setX(x);
		play.setY(y);

		y += BUTTON_HEIGHT;
		options.setX(x);
		options.setY(y);

		y += BUTTON_HEIGHT;
		credits.setX(x);
		credits.setY(y);

		y += BUTTON_HEIGHT;
		quit.setX(x);
		quit.setY(y);
	}

	/**
	 * Updates this menu view.
	 * Used to animate the title.
	 *
	 * @param deltaTime time elapsed since the last update (in seconds)
	 * @since 0.0.0
	 */
	public void update(double deltaTime) {
		title.update(deltaTime);
	}

	/**
	 * @param playAction play action
	 * @since 0.0.0
	 */
	public void onPlay(Runnable playAction) {
		play.setAction(playAction);
	}

	/**
	 * @param optionsAction options action
	 * @since 0.0.0
	 */
	public void onOptions(Runnable optionsAction) {
		options.setAction(optionsAction);
	}

	/**
	 * @param creditsAction credits action
	 * @since 0.0.0
	 */
	public void onCredits(Runnable creditsAction) {
		credits.setAction(creditsAction);
	}

	/**
	 * @param quitAction quit action
	 * @since 0.0.0
	 */
	public void onQuit(Runnable quitAction) {
		quit.setAction(quitAction);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

		var keyCode = keyEvent.getKeyCode();

		switch (keyCode) {

			case VK_UP -> selectPreviousInput();
			case VK_DOWN -> selectNextInput();
			case VK_ESCAPE -> quit.executeAction();
			default -> getSelectedInput().keyTyped(keyEvent);
		}
	}
}
