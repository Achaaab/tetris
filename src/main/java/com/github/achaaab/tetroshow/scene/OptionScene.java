package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.menu.OptionView;
import com.github.achaaab.tetroshow.view.message.Language;

import java.awt.Container;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.setEffectVolume;
import static com.github.achaaab.tetroshow.audio.AudioPlayer.setTrackVolume;
import static com.github.achaaab.tetroshow.view.message.Messages.setLocale;
import static javax.swing.SwingUtilities.invokeLater;

/**
 * option scene
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class OptionScene extends AbstractScene {

	private final ControlsScene controlsScene;

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

		controlsScene = new ControlsScene(manager, this);

		view = new OptionView();
		view.onLanguageChanged(this::languageChanged);
		view.onSkinChanged(this::skinChanged);
		view.onMusicVolumeChanged(this::musicVolumeChanged);
		view.onSoundEffectVolumeChanged(this::soundEffectVolumeChanged);
		view.onControls(this::displayControls);
		view.onBack(this::back);
	}

	@Override
	public void initialize() {

		view.requestFocus();
		view.selectInput(0);
	}

	@Override
	public void update(double deltaTime) {
		invokeLater(view::repaint);
	}

	/**
	 * @param language new selected language
	 * @since 0.0.0
	 */
	private void languageChanged(Language language) {
		setLocale(language.getLocale());
	}

	/**
	 * @param skin name of the new selected skin
	 * @since 0.0.0
	 */
	private void skinChanged(String skin) {
		Settings.getDefaultInstance().getGraphics().setSkin(skin);
	}

	/**
	 * @param musicVolume new selected music volume
	 * @since 0.0.0
	 */
	private void musicVolumeChanged(double musicVolume) {
		setTrackVolume(musicVolume);
	}

	/**
	 * @param soundEffectVolume new selected sound effect volume
	 * @since 0.0.0
	 */
	private void soundEffectVolumeChanged(double soundEffectVolume) {
		setEffectVolume(soundEffectVolume);
	}

	/**
	 * @since 0.0.0
	 */
	private void back() {
		exit();
	}

	/**
	 * Displays control scene.
	 *
	 * @since 0.0.0
	 */
	private void displayControls() {
		manager.display(controlsScene);
	}

	@Override
	public Container getView() {
		return view;
	}
}
