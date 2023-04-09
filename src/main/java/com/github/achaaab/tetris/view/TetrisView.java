package com.github.achaaab.tetris.view;

import com.github.achaaab.tetris.game.GameView;
import com.github.achaaab.tetris.model.classic.Tetris;
import org.slf4j.Logger;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.InvocationTargetException;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static javax.swing.SwingUtilities.invokeAndWait;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetrisView extends JPanel implements GameView {

	private static final Logger LOGGER = getLogger(TetrisView.class);

	private final GridView storageView;
	private final PreviewView previewView;
	private final GridView playfieldView;
	private final ScoreView scoreView;

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public TetrisView(Tetris tetris) {

		var storage = tetris.getStorage();
		var preview = tetris.getPreview();
		var playfield = tetris.getPlayfield();

		storageView = new StorageView(storage);
		previewView = new PreviewView(preview);
		playfieldView = new PlayfieldView(playfield);
		scoreView = new ScoreView(tetris);

		setLayout(new GridBagLayout());

		addComponents();
		setIgnoreRepaint(true);
	}

	/**
	 * Ajoute les différents composants de l'interface.
	 *
	 * @since 0.0.0
	 */
	private void addComponents() {

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

	@Override
	public void render() {

		try {
			invokeAndWait(() -> paintImmediately(getBounds()));
		} catch (InterruptedException | InvocationTargetException exception) {
			LOGGER.error("render error", exception);
		}
	}
}
