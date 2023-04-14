package com.github.achaaab.tetroshow.scene;

import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.menu.OptionView;
import com.github.achaaab.tetroshow.view.message.Language;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import static com.github.achaaab.tetroshow.view.message.Messages.setLocale;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class OptionScene extends AbstractScene {

	private final OptionView view;

	/**
	 * @param manager
	 * @param parent
	 * @since 0.0.0
	 */
	public OptionScene(SceneManager manager, Scene parent) {

		super(manager, parent);

		view = new OptionView();
		view.onLanguageChanged(this::languageChange);
		view.onSkinChanged(this::skinChange);
		view.onBack(this::back);
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	public void languageChange(ItemEvent event) {

		var language = (Language) event.getItem();
		setLocale(language.getLocale());
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	public void skinChange(ItemEvent event) {

		var skin = (String) event.getItem();
		Settings.getDefaultInstance().getGraphics().setSkin(skin);
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	public void back(ActionEvent event) {
		exit();
	}

	@Override
	public Container getView() {
		return view;
	}
}
