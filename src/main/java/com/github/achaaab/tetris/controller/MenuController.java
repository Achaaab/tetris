package com.github.achaaab.tetris.controller;

import com.github.achaaab.tetris.scene.Menu;
import com.github.achaaab.tetris.view.menu.MenuView;

import java.awt.event.ActionEvent;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MenuController {

	private final Menu model;
	private MenuView view;

	/**
	 * @param model
	 * @since 0.0.0
	 */
	public MenuController(Menu model) {
		this.model = model;
	}

	/**
	 * @param view
	 * @since 0.0.0
	 */
	public void setView(MenuView view) {

		this.view = view;

		view.onPlay(this::play);
		view.onOptions(this::options);
		view.onQuit(this::quit);
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	private void play(ActionEvent event) {
		model.play();
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	private void options(ActionEvent event) {
		model.options();
	}

	/**
	 * @param event
	 * @since 0.0.0
	 */
	private void quit(ActionEvent event) {

		if (view.confirmQuit()) {
			model.quit();
		}
	}
}
