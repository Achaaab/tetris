package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.audio.AudioPlayer;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.menu.OptionView;
import com.github.achaaab.tetroshow.view.message.Language;

import javax.swing.event.ChangeEvent;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import static com.github.achaaab.tetroshow.view.message.Messages.setLocale;

/**
 * option scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class OptionScene extends AbstractScene {

	private final OptionView view;

	/**
	 * Creates a new option scene.
	 *
	 * @param manager scene manager
	 * @param parent parent scene
	 * @since 0.0.0
	 */
	public OptionScene(SceneManager manager, Scene parent) {

		super(manager, parent);

		view = new OptionView();
		view.onLanguageChanged(this::languageChanged);
		view.onSkinChanged(this::skinChanged);
		view.onMusicVolumeChanged(this::musicVolumeChanged);
		view.onSoundEffectVolumeChanged(this::soundEffectVolumeChanged);
		view.onBack(this::back);
	}

	/**
	 * @param event language change event
	 * @since 0.0.0
	 */
	private void languageChanged(ItemEvent event) {

		var language = (Language) event.getItem();
		setLocale(language.getLocale());
	}

	/**
	 * @param event skin change event
	 * @since 0.0.0
	 */
	private void skinChanged(ItemEvent event) {

		var skin = (String) event.getItem();
		Settings.getDefaultInstance().getGraphics().setSkin(skin);
	}

	/**
	 * @param event music volume change event
	 * @since 0.0.0
	 */
	private void musicVolumeChanged(ChangeEvent event) {

		var volume = view.getMusicVolume();
		AudioPlayer.setTrackVolume(volume);
	}

	/**
	 * @param event sound effect volume change event
	 * @since 0.0.0
	 */
	private void soundEffectVolumeChanged(ChangeEvent event) {

		var volume = view.getSoundEffectVolume();
		AudioPlayer.setEffectVolume(volume);
	}

	/**
	 * @param event back action event
	 * @since 0.0.0
	 */
	private void back(ActionEvent event) {
		exit();
	}

	@Override
	public Container getView() {
		return view;
	}
}
