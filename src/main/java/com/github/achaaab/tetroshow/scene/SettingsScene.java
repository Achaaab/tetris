package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.menu.SettingsView;
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
public class SettingsScene extends AbstractScene {

	private final ControlsScene controlsScene;
	private final GraphicsScene graphicsScene;
	private final GameplayScene gameplayScene;
	private final SettingsView view;
	private final Settings settings;

	/**
	 * Creates a new option scene.
	 *
	 * @param manager scene manager
	 * @param parent parent scene
	 * @since 0.0.0
	 */
	public SettingsScene(SceneManager manager, Scene parent) {

		super(manager, parent);

		controlsScene = new ControlsScene(manager, this);
		graphicsScene = new GraphicsScene(manager, this);
		gameplayScene = new GameplayScene(manager, this);
		settings = Settings.getDefaultInstance();

		view = new SettingsView();
		view.onLanguageChanged(this::languageChanged);
		view.onMusicVolumeChanged(this::musicVolumeChanged);
		view.onSoundEffectVolumeChanged(this::soundEffectVolumeChanged);
		view.onControls(this::displayControls);
		view.onGraphics(this::displayGraphics);
		view.onGameplay(this::displayGameplay);
		view.onBack(this::back);
	}

	@Override
	public void initialize() {

		view.requestFocus();
		view.selectFirstInput();
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
	 * @param musicVolume new selected music volume (in {@code [0, 10]})
	 * @since 0.0.0
	 */
	private void musicVolumeChanged(int musicVolume) {

		settings.getAudio().setMusicVolume(musicVolume);
		setTrackVolume(musicVolume);
	}

	/**
	 * @param soundEffectVolume new selected sound effect volume (in {@code [0, 10]})
	 * @since 0.0.0
	 */
	private void soundEffectVolumeChanged(int soundEffectVolume) {

		settings.getAudio().setSoundEffectVolume(soundEffectVolume);
		setEffectVolume(soundEffectVolume);
	}

	/**
	 * @since 0.0.0
	 */
	private void back() {

		settings.save();
		exit();
	}

	/**
	 * Displays controls settings scene.
	 *
	 * @since 0.0.0
	 */
	private void displayControls() {
		manager.display(controlsScene);
	}

	/**
	 * Displays graphics settings scene.
	 *
	 * @since 0.0.0
	 */
	private void displayGraphics() {
		manager.display(graphicsScene);
	}

	/**
	 * Displays gameplay settings scene.
	 *
	 * @since 0.0.0
	 */
	private void displayGameplay() {
		manager.display(gameplayScene);
	}

	@Override
	public Container getView() {
		return view;
	}
}
