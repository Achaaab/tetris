package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.component.Button;
import com.github.achaaab.tetroshow.view.component.Label;
import com.github.achaaab.tetroshow.view.component.Option;
import com.github.achaaab.tetroshow.view.message.Language;
import com.github.achaaab.tetroshow.view.message.Messages;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;

import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Language.getLanguageByCode;
import static com.github.achaaab.tetroshow.view.message.Messages.BACK;
import static com.github.achaaab.tetroshow.view.message.Messages.CONTROLS;
import static com.github.achaaab.tetroshow.view.message.Messages.GAMEPLAY;
import static com.github.achaaab.tetroshow.view.message.Messages.GRAPHICS;
import static com.github.achaaab.tetroshow.view.message.Messages.LANGUAGE;
import static com.github.achaaab.tetroshow.view.message.Messages.MUSIC;
import static com.github.achaaab.tetroshow.view.message.Messages.OPTIONS;
import static com.github.achaaab.tetroshow.view.message.Messages.SOUND_EFFECT;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_UP;
import static java.util.stream.IntStream.rangeClosed;

/**
 * view to display and change options
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class SettingsView extends MenuView {

	private static final int INPUT_HEIGHT = scale(40);
	private static final int INPUT_X = scale(75);
	private static final int FIRST_INPUT_Y = scale(150);
	private static final int VALUE_X = scale(150);

	private final Option<Language> language;
	private final Option<Integer> musicVolume;
	private final Option<Integer> soundEffectVolume;
	private final Button controls;
	private final Button graphics;
	private final Button gameplay;
	private final Button back;

	/**
	 * Creates a view to display and change options.
	 *
	 * @since 0.0.0
	 */
	public SettingsView() {

		var title = new Label(OPTIONS);
		title.setFont(DEFAULT_TITLE_FONT);

		language = new Option<>(
				LANGUAGE,
				List.of(Language.values()),
				Language::toString,
				VALUE_X);

		musicVolume = new Option<>(
				MUSIC,
				rangeClosed(0, 10).boxed().toList(),
				Object::toString,
				VALUE_X);

		soundEffectVolume = new Option<>(
				SOUND_EFFECT,
				rangeClosed(0, 10).boxed().toList(),
				Object::toString,
				VALUE_X);

		controls = new Button(CONTROLS);
		graphics = new Button(GRAPHICS);
		gameplay = new Button(GAMEPLAY);
		back = new Button(BACK);

		add(title);
		add(language);
		add(musicVolume);
		add(soundEffectVolume);
		add(controls);
		add(graphics);
		add(gameplay);
		add(back);

		selectFirstInput();

		var currentLocale = Messages.getLocale();
		var code = currentLocale.getLanguage();
		language.select(getLanguageByCode(code));

		var settings = Settings.getDefaultInstance();

		musicVolume.select(settings.getAudio().getMusicVolume());
		soundEffectVolume.select(settings.getAudio().getSoundEffectVolume());

		title.setX(scale(130.0f));
		title.setY(scale(50.0f));

		var y = FIRST_INPUT_Y;

		language.setX(INPUT_X);
		language.setY(y);

		y += INPUT_HEIGHT;
		musicVolume.setX(INPUT_X);
		musicVolume.setY(y);

		y += INPUT_HEIGHT;
		soundEffectVolume.setX(INPUT_X);
		soundEffectVolume.setY(y);

		y += INPUT_HEIGHT;
		controls.setX(INPUT_X);
		controls.setY(y);

		y += INPUT_HEIGHT;
		graphics.setX(INPUT_X);
		graphics.setY(y);

		y += INPUT_HEIGHT;
		gameplay.setX(INPUT_X);
		gameplay.setY(y);

		y += INPUT_HEIGHT + INPUT_HEIGHT / 2;
		back.setX(INPUT_X);
		back.setY(y);
	}

	/**
	 * @param consumer language consumer
	 * @since 0.0.0
	 */
	public void onLanguageChanged(Consumer<Language> consumer) {
		language.setConsumer(consumer);
	}

	/**
	 * @param consumer music volume consumer
	 * @since 0.0.0
	 */
	public void onMusicVolumeChanged(Consumer<Integer> consumer) {
		musicVolume.setConsumer(consumer);
	}

	/**
	 * @param consumer sound effect volume consumer
	 * @since 0.0.0
	 */
	public void onSoundEffectVolumeChanged(Consumer<Integer> consumer) {
		soundEffectVolume.setConsumer(consumer);
	}

	/**
	 * @param action controls action
	 * @since 0.0.0
	 */
	public void onControls(Runnable action) {
		controls.setAction(action);
	}

	/**
	 * @param action graphics action
	 * @since 0.0.0
	 */
	public void onGraphics(Runnable action) {
		graphics.setAction(action);
	}

	/**
	 * @param action gameplay action
	 * @since 0.0.0
	 */
	public void onGameplay(Runnable action) {
		gameplay.setAction(action);
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
