package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.audio.SoundEffect;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.component.Button;
import com.github.achaaab.tetroshow.view.component.Input;
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
import static com.github.achaaab.tetroshow.view.message.Messages.BACK;
import static com.github.achaaab.tetroshow.view.message.Messages.CONTROLS;
import static com.github.achaaab.tetroshow.view.message.Messages.LANGUAGE;
import static com.github.achaaab.tetroshow.view.message.Messages.MUSIC;
import static com.github.achaaab.tetroshow.view.message.Messages.SKIN;
import static com.github.achaaab.tetroshow.view.message.Messages.SOUND_EFFECT;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static com.github.achaaab.tetroshow.view.skin.Skin.SKINS;
import static java.awt.Font.DIALOG;
import static java.awt.Font.PLAIN;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
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
public class OptionView extends JComponent implements KeyListener {

	private static final double VOLUME_SCALE = 10.0;
	private static final Color BACKGROUND_COLOR = new Color(0, 0, 16);
	private static final int FONT_SIZE = 18;
	private static final Font FONT = new Font(DIALOG, PLAIN, scale(FONT_SIZE));
	private static final int INPUT_HEIGHT = scale(50);
	private static final int VALUE_X = scale(150);
	private static final SoundEffect SELECTION_SOUND_EFFECT = getSoundEffect("audio/effect/move.wav", 6);

	private final Option<Language> language;
	private final Option<String> skin;
	private final Option<Integer> musicVolume;
	private final Option<Integer> soundEffectVolume;
	private final Button controls;
	private final Button back;

	private final List<Input> inputs;
	private final int inputCount;
	private int selectedInputIndex;

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
				getMessage(MUSIC),
				rangeClosed(0, 10).boxed().toList(),
				Object::toString,
				VALUE_X);

		soundEffectVolume = new Option<>(
				getMessage(SOUND_EFFECT),
				rangeClosed(0, 10).boxed().toList(),
				Object::toString,
				VALUE_X);

		controls = new Button(getMessage(CONTROLS));
		back = new Button(getMessage(BACK));

		inputs = List.of(
				language,
				skin,
				musicVolume,
				soundEffectVolume,
				controls,
				back);

		inputCount = inputs.size();
		language.setSelected(true);
		selectedInputIndex = 0;

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
		inputs.forEach(input -> input.paint(graphics2d));
		graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
	}

	/**
	 * @since 0.0.0
	 */
	private void localeChanged() {

		language.setName(getMessage(LANGUAGE));
		skin.setName(getMessage(SKIN));
		musicVolume.setName(getMessage(MUSIC));
		soundEffectVolume.setName(getMessage(SOUND_EFFECT));
		controls.setText(getMessage(CONTROLS));
		back.setText(getMessage(BACK));
	}

	/**
	 * Selects an input.
	 *
	 * @param index input index
	 * @since 0.0.0
	 */
	public void selectInput(int index) {

		inputs.get(selectedInputIndex).setSelected(false);
		selectedInputIndex = index;
		inputs.get(selectedInputIndex).setSelected(true);
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

			case VK_UP -> selectInput(((selectedInputIndex - 1) + inputCount) % inputCount);
			case VK_DOWN -> selectInput((selectedInputIndex + 1) % inputCount);
			case VK_ESCAPE -> back.executeAction();
			default -> inputs.get(selectedInputIndex).keyTyped(keyCode);
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {

	}
}
