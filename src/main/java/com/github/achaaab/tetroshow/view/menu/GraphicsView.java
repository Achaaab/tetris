package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.settings.Level;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.component.Button;
import com.github.achaaab.tetroshow.view.component.Label;
import com.github.achaaab.tetroshow.view.component.Option;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;

import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.BACK;
import static com.github.achaaab.tetroshow.view.message.Messages.GRAPHICS;
import static com.github.achaaab.tetroshow.view.message.Messages.NO;
import static com.github.achaaab.tetroshow.view.message.Messages.PARTICLES;
import static com.github.achaaab.tetroshow.view.message.Messages.SKIN;
import static com.github.achaaab.tetroshow.view.message.Messages.SYNCHRONIZATION;
import static com.github.achaaab.tetroshow.view.message.Messages.YES;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static com.github.achaaab.tetroshow.view.skin.Skin.SKINS;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_UP;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;

/**
 * view to display and change graphics settings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GraphicsView extends MenuView {

	private static final int INPUT_HEIGHT = scale(40);
	private static final int VALUE_X = scale(180);
	private static final int INPUT_X = scale(55);
	private static final int FIRST_INPUT_Y = scale(120);

	private final Option<String> skin;
	private final Option<Boolean> synchronization;
	private final Option<Level> particleLevel;
	private final Button back;

	/**
	 * Creates a view to display and change graphics settings.
	 *
	 * @since 0.0.0
	 */
	public GraphicsView() {

		var title = new Label(GRAPHICS);
		title.setFont(DEFAULT_TITLE_FONT);

		skin = new Option<>(
				SKIN,
				SKINS,
				identity(),
				VALUE_X);

		synchronization = new Option<>(
				SYNCHRONIZATION,
				List.of(true, false),
				value -> getMessage(value ? YES : NO),
				VALUE_X);

		particleLevel = new Option<>(
				PARTICLES,
				stream(Level.values()).toList(),
				level -> getMessage(level.name().toLowerCase()),
				VALUE_X);

		back = new Button(BACK);

		add(title);
		add(skin);
		add(synchronization);
		add(particleLevel);
		add(back);

		selectFirstInput();

		var settings = Settings.getDefaultInstance();
		var graphicsSettings = settings.getGraphics();
		var currentSkin = graphicsSettings.getSkin();

		skin.select(currentSkin);
		synchronization.select(graphicsSettings.isSynchronization());
		particleLevel.select(graphicsSettings.getParticleLevel());

		title.setX(scale(130.0f));
		title.setY(scale(50.0f));

		var y = FIRST_INPUT_Y;

		skin.setX(INPUT_X);
		skin.setY(y);

		y += INPUT_HEIGHT;
		synchronization.setX(INPUT_X);
		synchronization.setY(y);

		y += INPUT_HEIGHT;
		particleLevel.setX(INPUT_X);
		particleLevel.setY(y);

		y += INPUT_HEIGHT + INPUT_HEIGHT / 2;
		back.setX(INPUT_X);
		back.setY(y);
	}

	/**
	 * @param consumer skin change consumer
	 * @since 0.0.0
	 */
	public void onSkinChanged(Consumer<String> consumer) {
		skin.setConsumer(consumer);
	}

	/**
	 * @param consumer synchronization change consumer
	 * @since 0.0.0
	 */
	public void onSynchronizationChanged(Consumer<Boolean> consumer) {
		synchronization.setConsumer(consumer);
	}

	/**
	 * @param consumer particle level change consumer
	 * @since 0.0.0
	 */
	public void onParticleLevelChanged(Consumer<Level> consumer) {
		particleLevel.setConsumer(consumer);
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
