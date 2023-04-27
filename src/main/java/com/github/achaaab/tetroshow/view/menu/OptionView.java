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
import static com.github.achaaab.tetroshow.view.message.Language.getLanguage;
import static com.github.achaaab.tetroshow.view.message.Messages.BACK;
import static com.github.achaaab.tetroshow.view.message.Messages.CONTROLS;
import static com.github.achaaab.tetroshow.view.message.Messages.LANGUAGE;
import static com.github.achaaab.tetroshow.view.message.Messages.MUSIC;
import static com.github.achaaab.tetroshow.view.message.Messages.SKIN;
import static com.github.achaaab.tetroshow.view.message.Messages.SOUND_EFFECT;
import static com.github.achaaab.tetroshow.view.skin.Skin.SKINS;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_UP;
import static java.util.function.Function.identity;
import static java.util.stream.IntStream.rangeClosed;

/**
 * view to display and change options
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class OptionView extends MenuView {

	private static final double VOLUME_SCALE = 10.0;
	private static final int INPUT_HEIGHT = scale(50);
	private static final int VALUE_X = scale(150);

	private final Option<Language> language;
	private final Option<String> skin;
	private final Option<Integer> musicVolume;
	private final Option<Integer> soundEffectVolume;
	private final Button controls;
	private final Button back;

	/**
	 * Creates a view to display and change options.
	 *
	 * @since 0.0.0
	 */
	public OptionView() {

		language = new Option<>(
				LANGUAGE,
				List.of(Language.values()),
				Language::toString,
				VALUE_X);

		skin = new Option<>(
				SKIN,
				SKINS,
				identity(),
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
		back = new Button(BACK);

		add(language);
		add(skin);
		add(musicVolume);
		add(soundEffectVolume);
		add(controls);
		add(back);

		selectFirstInput();

		var currentLocale = Messages.getLocale();
		var code = currentLocale.getLanguage();
		language.select(getLanguage(code));

		var currentSkin = Settings.getDefaultInstance().getGraphics().getSkin();
		skin.select(currentSkin);

		musicVolume.select(10);
		soundEffectVolume.select(10);

		var x = scale(75.0f);
		var y = scale(100.0f);

		language.setX(x);
		language.setY(y);

		y += INPUT_HEIGHT;
		skin.setX(x);
		skin.setY(y);

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
	 * @param consumer skin name consumer
	 * @since 0.0.0
	 */
	public void onSkinChanged(Consumer<String> consumer) {
		skin.setConsumer(consumer);
	}

	/**
	 * @param consumer music volume consumer
	 * @since 0.0.0
	 */
	public void onMusicVolumeChanged(Consumer<Double> consumer) {
		musicVolume.setConsumer(level -> consumer.accept(level / VOLUME_SCALE));
	}

	/**
	 * @param consumer sound effect volume consumer
	 * @since 0.0.0
	 */
	public void onSoundEffectVolumeChanged(Consumer<Double> consumer) {
		soundEffectVolume.setConsumer(level -> consumer.accept(level / VOLUME_SCALE));
	}

	/**
	 * @param action controls action
	 * @since 0.0.0
	 */
	public void onControls(Runnable action) {
		controls.setAction(action);
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
