package com.github.achaaab.tetris.scene;

import com.github.achaaab.tetris.view.menu.OptionView;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Locale;

import static com.github.achaaab.tetris.view.message.Messages.setLocale;

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
		view.onBack(this::back);
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	public void languageChange(ItemEvent event) {

		var locale = (Locale) event.getItem();
		setLocale(locale);
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
