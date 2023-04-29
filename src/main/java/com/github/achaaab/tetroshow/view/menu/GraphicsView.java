package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.settings.Level;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.component.Button;
import com.github.achaaab.tetroshow.view.component.Option;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.BACK;
import static com.github.achaaab.tetroshow.view.message.Messages.PARTICLES;
import static com.github.achaaab.tetroshow.view.message.Messages.SKIN;
import static com.github.achaaab.tetroshow.view.message.Messages.SYNCHRONIZATION;
import static com.github.achaaab.tetroshow.view.skin.Skin.SKINS;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_UP;
import static java.util.function.Function.identity;

/**
 * view to display and change graphics settings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GraphicsView extends MenuView {

	private static final int INPUT_HEIGHT = scale(50);
	private static final int VALUE_X = scale(175);
	private static final int INPUT_X = scale(60);
	private static final int FIRST_INPUT_Y = scale(100);

	private final Button back;

	/**
	 * Creates a view to display and change graphics settings.
	 *
	 * @since 0.0.0
	 */
	public GraphicsView() {

		Option<String> skin = new Option<>(
				SKIN,
				SKINS,
				identity(),
				VALUE_X);

		Option<Boolean> synchronization = new Option<>(
				SYNCHRONIZATION,
				List.of(true, false),
				Object::toString,
				VALUE_X);

		Option<Level> particleLevel = new Option<>(
				PARTICLES,
				Arrays.stream(Level.values()).toList(),
				Level::name,
				VALUE_X);

		back = new Button(BACK);

		add(skin);
		add(synchronization);
		add(particleLevel);
		add(back);

		selectFirstInput();

		var settings = Settings.getDefaultInstance();
		var graphicsSettings = settings.getGraphics();
		var currentSkin = graphicsSettings.getSkin();

		skin.select(currentSkin);
		synchronization.select(graphicsSettings.isSynchronizeState());
		particleLevel.select(graphicsSettings.getParticleLevel());

		skin.setConsumer(graphicsSettings::setSkin);
		synchronization.setConsumer(graphicsSettings::setSynchronizeState);
		particleLevel.setConsumer(graphicsSettings::setParticleLevel);

		var y = FIRST_INPUT_Y;

		skin.setX(INPUT_X);
		skin.setY(y);

		y += INPUT_HEIGHT;
		synchronization.setX(INPUT_X);
		synchronization.setY(y);

		y += INPUT_HEIGHT;
		particleLevel.setX(INPUT_X);
		particleLevel.setY(y);

		y += INPUT_HEIGHT;
		back.setX(INPUT_X);
		back.setY(y);
	}

	/**
	 * @param action back action
	 * @since 0.0.0
	 */
	public void onBack(Runnable action) {
		back.setAction(action);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

		var keyCode = keyEvent.getKeyCode();

		switch (keyCode) {

			case VK_UP -> selectPreviousInput();
			case VK_DOWN -> selectNextInput();
			case VK_ESCAPE -> back.executeAction();
			default -> getSelectedInput().keyTyped(keyEvent);
		}
	}
}
