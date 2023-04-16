package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.Tetroshow;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.FIRST_LINE_START;

/**
 * Tetroshow view
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetroshowView extends JPanel {

	private final GridView storageView;
	private final PreviewView previewView;
	private final GridView playfieldView;
	private final ScoreView scoreView;

	/**
	 * @param tetroshow Tetroshow to display
	 * @since 0.0.0
	 */
	public TetroshowView(Tetroshow tetroshow) {

		var storage = tetroshow.getStorage();
		var preview = tetroshow.getPreview();
		var playfield = tetroshow.getPlayfield();

		storageView = new StorageView(storage);
		previewView = new PreviewView(preview);
		playfieldView = new PlayfieldView(playfield);
		scoreView = new ScoreView(tetroshow);

		addComponents();

		setFocusable(true);
	}

	/**
	 * Adds components.
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
		add(storageView, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(playfieldView, constraints);

		constraints.fill = BOTH;

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		add(previewView, constraints);

		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(scoreView, constraints);
	}
}
