package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.audio.SoundEffect;
import com.github.achaaab.tetroshow.view.component.Button;
import com.github.achaaab.tetroshow.view.message.Messages;
import com.github.achaaab.tetroshow.view.play.TetroshowView;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.utility.SwingUtility.hideCursor;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.CREDITS;
import static com.github.achaaab.tetroshow.view.message.Messages.OPTIONS;
import static com.github.achaaab.tetroshow.view.message.Messages.PLAY;
import static com.github.achaaab.tetroshow.view.message.Messages.QUIT;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static java.awt.Font.DIALOG;
import static java.awt.Font.PLAIN;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_UP;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MenuView extends JComponent implements KeyListener {

	private static final Color BACKGROUND_COLOR = new Color(0, 0, 16);
	private static final int FONT_SIZE = scale(25);
	private static final Font FONT = new Font(DIALOG, PLAIN, FONT_SIZE);
	private static final int BUTTON_HEIGHT = scale(50);
	private static final SoundEffect SELECTION_SOUND_EFFECT = getSoundEffect("audio/effect/move.wav", 6);

	private final Button play;
	private final Button options;
	private final Button quit;
	private final Button credits;
	private final List<Button> buttons;
	private final int buttonCount;

	private int selectedIndex;

	/**
	 * @since 0.0.0
	 */
	public MenuView() {

		Messages.register(this::localeChanged);

		play = new Button(getMessage(PLAY));
		options = new Button(getMessage(OPTIONS));
		credits = new Button(getMessage(CREDITS));
		quit = new Button(getMessage(QUIT));

		buttons = List.of(play, options, credits, quit);
		buttonCount = buttons.size();
		play.setSelected(true);
		selectedIndex = 0;

		setPreferredSize(TetroshowView.DIMENSION);
		var height = getPreferredSize().height;
		var buttonsHeight = buttonCount * BUTTON_HEIGHT;

		var y = (height - buttonsHeight) / 2;
		var x = scale(50.0f);

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

		addKeyListener(this);
		hideCursor(this);
	}

	@Override
	public void paint(Graphics graphics) {

		var graphics2d = (Graphics2D) graphics;

		graphics2d.setColor(BACKGROUND_COLOR);
		graphics2d.fillRect(0, 0, getWidth(), getHeight());

		graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		graphics.setFont(FONT);
		buttons.forEach(button -> button.paint(graphics2d));
		graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
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
	 * Selects a button.
	 *
	 * @param index button index
	 * @since 0.0.0
	 */
	public void selectButton(int index) {

		buttons.get(selectedIndex).setSelected(false);
		selectedIndex = index;
		buttons.get(selectedIndex).setSelected(true);
		SELECTION_SOUND_EFFECT.play();
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

			case VK_UP -> selectButton(((selectedIndex - 1) + buttonCount) % buttons.size());
			case VK_DOWN -> selectButton((selectedIndex + 1) % buttons.size());
			case VK_ENTER, VK_SPACE -> buttons.get(selectedIndex).executeAction();
			case VK_ESCAPE -> quit.executeAction();
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {

	}
}
