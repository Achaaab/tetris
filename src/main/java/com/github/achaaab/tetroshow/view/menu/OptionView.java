package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.message.Language;
import com.github.achaaab.tetroshow.view.message.Messages;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static com.github.achaaab.tetroshow.view.message.Language.getLanguage;
import static com.github.achaaab.tetroshow.view.message.Messages.BACK;
import static com.github.achaaab.tetroshow.view.message.Messages.LANGUAGE;
import static com.github.achaaab.tetroshow.view.message.Messages.MUSIC_GAIN;
import static com.github.achaaab.tetroshow.view.message.Messages.SKIN;
import static com.github.achaaab.tetroshow.view.message.Messages.SOUND_FX_GAIN;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static com.github.achaaab.tetroshow.view.skin.Skin.SKINS;
import static java.awt.Font.DIALOG;
import static java.awt.Font.PLAIN;
import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static javax.swing.SwingConstants.HORIZONTAL;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class OptionView extends JPanel {

	private static final int FONT_SIZE = 18;
	private static final Font FONT = new Font(DIALOG, PLAIN, scale(FONT_SIZE));

	private final JLabel languageLabel;
	private final JComboBox<Language> languages;

	private final JLabel skinLabel;
	private final JComboBox<String> skins;

	private final JLabel musicGainLabel;
	private final JSlider musicGain;

	private final JLabel soundFxGainLabel;
	private final JSlider soundFxGain;

	private final JButton back;

	/**
	 * @since 0.0.0
	 */
	public OptionView() {

		Messages.register(this::localeChanged);

		languageLabel = new JLabel(getMessage(LANGUAGE));
		languages = new JComboBox<>(Language.values());

		skinLabel = new JLabel(getMessage(SKIN));
		skins = new JComboBox<>(SKINS);

		musicGainLabel = new JLabel(getMessage(MUSIC_GAIN));
		musicGain = new JSlider(HORIZONTAL, -80, 0, 0);

		soundFxGainLabel = new JLabel(getMessage(SOUND_FX_GAIN));
		soundFxGain = new JSlider(HORIZONTAL, -80, 0, 0);

		back = new JButton(getMessage(BACK));

		languageLabel.setFont(FONT);
		languages.setFont(FONT);
		skinLabel.setFont(FONT);
		skins.setFont(FONT);
		musicGainLabel.setFont(FONT);
		soundFxGainLabel.setFont(FONT);
		back.setFont(FONT);

		var currentLocale = Messages.getLocale();
		var code = currentLocale.getLanguage();
		languages.setSelectedItem(getLanguage(code));

		var currentSkin = Settings.getDefaultInstance().getGraphics().getSkin();
		skins.setSelectedItem(currentSkin);

		addComponents();
	}

	/**
	 * Adds and lay out components.
	 *
	 * @since 0.0.0
	 */
	private void addComponents() {

		setLayout(new GridBagLayout());
		var constraints = new GridBagConstraints();
		constraints.anchor = FIRST_LINE_START;

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(scale(5.0f), scale(5.0f), scale(5.0f), scale(5.0f));
		add(languageLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(languages, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(skinLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(skins, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(musicGainLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(musicGain, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(soundFxGainLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(soundFxGain, constraints);

		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(back, constraints);
	}

	/**
	 * @since 0.0.0
	 */
	private void localeChanged() {

		languageLabel.setText(getMessage(LANGUAGE));
		skinLabel.setText(getMessage(SKIN));
		musicGainLabel.setText(getMessage(MUSIC_GAIN));
		soundFxGainLabel.setText(getMessage(SOUND_FX_GAIN));
		back.setText(getMessage(BACK));
	}

	/**
	 * @param listener
	 * @since 0.0.0
	 */
	public void onLanguageChanged(ItemListener listener) {
		languages.addItemListener(listener);
	}

	/**
	 * @param listener
	 * @since 0.0.0
	 */
	public void onSkinChanged(ItemListener listener) {
		skins.addItemListener(listener);
	}

	/**
	 * @param listener
	 * @since 0.0.0
	 */
	public void onMusicGainChanged(ChangeListener listener) {
		musicGain.addChangeListener(listener);
	}

	/**
	 * @param listener
	 * @since 0.0.0
	 */
	public void onSoundFxChanged(ChangeListener listener) {
		soundFxGain.addChangeListener(listener);
	}

	/**
	 * @param listener
	 * @since 0.0.0
	 */
	public void onBack(ActionListener listener) {
		back.addActionListener(listener);
	}
}
