package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.component.Button;
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
import static com.github.achaaab.tetroshow.view.message.Messages.GRAPHICS;
import static com.github.achaaab.tetroshow.view.message.Messages.LANGUAGE;
import static com.github.achaaab.tetroshow.view.message.Messages.MUSIC;
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

	private static final int INPUT_HEIGHT = scale(50);
	private static final int VALUE_X = scale(150);

	private final Option<Language> language;
	private final Option<Integer> musicVolume;
	private final Option<Integer> soundEffectVolume;
	private final Button controls;
	private final Button graphics;
	private final Button back;

	/**
	 * Creates a view to display and change options.
	 *
	 * @since 0.0.0
	 */
	public SettingsView() {

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
		back = new Button(BACK);

		add(language);
		add(musicVolume);
		add(soundEffectVolume);
		add(controls);
		add(graphics);
		add(back);

		selectFirstInput();

		var currentLocale = Messages.getLocale();
		var code = currentLocale.getLanguage();
		language.select(getLanguageByCode(code));

		var settings = Settings.getDefaultInstance();

		musicVolume.select(settings.getAudio().getMusicVolume());
		soundEffectVolume.select(settings.getAudio().getSoundEffectVolume());

		var x = scale(75.0f);
		var y = scale(100.0f);

		language.setX(x);
		language.setY(y);

		y += INPUT_HEIGHT;
		musicVolume.setX(x);
		musicVolume.setY(y);

		y += INPUT_HEIGHT;
		soundEffectVolume.setX(x);
		soundEffectVolume.setY(y);

		y += INPUT_HEIGHT;
		controls.setX(x);
		controls.setY(y);

		y += INPUT_HEIGHT;
		graphics.setX(x);
		graphics.setY(y);

		y += INPUT_HEIGHT;
		back.setX(x);
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
