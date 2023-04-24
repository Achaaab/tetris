package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.audio.SoundEffect;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.component.Option;
import com.github.achaaab.tetroshow.view.message.Language;
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
import java.util.function.Consumer;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;
import static com.github.achaaab.tetroshow.utility.SwingUtility.hideCursor;
import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Language.getLanguage;
import static com.github.achaaab.tetroshow.view.message.Messages.LANGUAGE;
import static com.github.achaaab.tetroshow.view.message.Messages.MUSIC_GAIN;
import static com.github.achaaab.tetroshow.view.message.Messages.SKIN;
import static com.github.achaaab.tetroshow.view.message.Messages.SOUND_FX_GAIN;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static com.github.achaaab.tetroshow.view.skin.Skin.SKINS;
import static java.awt.Font.DIALOG;
import static java.awt.Font.PLAIN;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import static java.util.function.Function.identity;
import static java.util.stream.IntStream.rangeClosed;

/**
 * options view
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class OptionView extends JComponent implements KeyListener {

	private static final double VOLUME_SCALE = 10.0;
	private static final Color BACKGROUND_COLOR = new Color(0, 0, 16);
	private static final int FONT_SIZE = 18;
	private static final Font FONT = new Font(DIALOG, PLAIN, scale(FONT_SIZE));
	private static final int OPTION_HEIGHT = scale(50);
	private static final int VALUE_X = scale(150);
	private static final SoundEffect SELECTION_SOUND_EFFECT = getSoundEffect("audio/effect/move.wav", 6);

	private final Option<Language> language;
	private final Option<String> skin;
	private final Option<Integer> musicVolume;
	private final Option<Integer> soundEffectVolume;

	private final List<Option<?>> options;
	private final int optionCount;
	private int selectedOptionIndex;

	private Runnable backAction;

	/**
	 * Creates a view to display and change options.
	 *
	 * @since 0.0.0
	 */
	public OptionView() {

		Messages.register(this::localeChanged);

		language = new Option<>(
				getMessage(LANGUAGE),
				List.of(Language.values()),
				Language::toString,
				VALUE_X);

		skin = new Option<>(
				getMessage(SKIN),
				SKINS,
				identity(),
				VALUE_X);

		musicVolume = new Option<>(
				getMessage(MUSIC_GAIN),
				rangeClosed(0, 10).boxed().toList(),
				Object::toString,
				VALUE_X);

		soundEffectVolume = new Option<>(
				getMessage(SOUND_FX_GAIN),
				rangeClosed(0, 10).boxed().toList(),
				Object::toString,
				VALUE_X);

		options = List.of(language, skin, musicVolume, soundEffectVolume);
		optionCount = options.size();
		language.setSelected(true);
		selectedOptionIndex = 0;

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

		y += OPTION_HEIGHT;
		skin.setX(x);
		skin.setY(y);

		y += OPTION_HEIGHT;
		musicVolume.setX(x);
		musicVolume.setY(y);

		y += OPTION_HEIGHT;
		soundEffectVolume.setX(x);
		soundEffectVolume.setY(y);

		addKeyListener(this);
		hideCursor(this);
		setPreferredSize(TetroshowView.DIMENSION);
	}

	@Override
	public void paint(Graphics graphics) {

		var graphics2d = (Graphics2D) graphics;

		graphics2d.setColor(BACKGROUND_COLOR);
		graphics2d.fillRect(0, 0, getWidth(), getHeight());

		graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		graphics.setFont(FONT);
		options.forEach(option -> option.paint(graphics2d));
		graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
	}

	/**
	 * @since 0.0.0
	 */
	private void localeChanged() {

		language.setName(getMessage(LANGUAGE));
		skin.setName(getMessage(SKIN));
		musicVolume.setName(getMessage(MUSIC_GAIN));
		soundEffectVolume.setName(getMessage(SOUND_FX_GAIN));
	}

	/**
	 * Selects an option.
	 *
	 * @param index option index
	 * @since 0.0.0
	 */
	public void selectOption(int index) {

		options.get(selectedOptionIndex).setSelected(false);
		selectedOptionIndex = index;
		options.get(selectedOptionIndex).setSelected(true);
		SELECTION_SOUND_EFFECT.play();
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
	 * @param action back action
	 * @since 0.0.0
	 */
	public void onBack(Runnable action) {
		backAction = action;
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

		var keyCode = keyEvent.getKeyCode();

		switch (keyCode) {

			case VK_UP -> selectOption(((selectedOptionIndex - 1) + optionCount) % optionCount);
			case VK_DOWN -> selectOption((selectedOptionIndex + 1) % optionCount);
			case VK_LEFT -> options.get(selectedOptionIndex).previous();
			case VK_RIGHT -> options.get(selectedOptionIndex).next();
			case VK_ESCAPE -> backAction.run();
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {

	}
}
