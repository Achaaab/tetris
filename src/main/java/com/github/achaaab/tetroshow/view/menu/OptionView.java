package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.message.Language;
import com.github.achaaab.tetroshow.view.message.Messages;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import static com.github.achaaab.tetroshow.view.Scaler.scale;
import static com.github.achaaab.tetroshow.view.message.Language.getLanguage;
import static com.github.achaaab.tetroshow.view.message.Messages.BACK;
import static com.github.achaaab.tetroshow.view.message.Messages.LANGUAGE;
import static com.github.achaaab.tetroshow.view.message.Messages.SKIN;
import static com.github.achaaab.tetroshow.view.message.Messages.getMessage;
import static com.github.achaaab.tetroshow.view.skin.Skin.SKINS;
import static java.awt.Font.DIALOG;
import static java.awt.Font.PLAIN;

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

		back = new JButton(getMessage(BACK));

		languageLabel.setFont(FONT);
		languages.setFont(FONT);
		skinLabel.setFont(FONT);
		skins.setFont(FONT);
		back.setFont(FONT);

		var currentLocale = Messages.getLocale();
		var code = currentLocale.getLanguage();
		languages.setSelectedItem(getLanguage(code));

		var currentSkin = Settings.getDefaultInstance().getGraphics().getSkin();
		skins.setSelectedItem(currentSkin);

		add(languageLabel);
		add(languages);
		add(skinLabel);
		add(skins);
		add(back);
	}

	/**
	 * @since 0.0.0
	 */
	private void localeChanged() {

		languageLabel.setText(getMessage(LANGUAGE));
		skinLabel.setText(getMessage(SKIN));
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
	public void onBack(ActionListener listener) {
		back.addActionListener(listener);
	}
}
