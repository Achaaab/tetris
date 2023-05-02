package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.model.Tetroshow;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static com.github.achaaab.tetroshow.utility.SwingUtility.hideCursor;
import static com.github.achaaab.tetroshow.view.skin.Skin.getCurrentSkin;

/**
 * Tetroshow view
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TetroshowView extends JComponent implements FocusListener {

	private static final int WIDTH = StorageView.WIDTH + PlayfieldView.WIDTH + ScoreView.WIDTH;
	private static final int HEIGHT = PreviewView.HEIGHT + PlayfieldView.HEIGHT;
	public static final Dimension DIMENSION = new Dimension(WIDTH, HEIGHT);

	private final Tetroshow tetroshow;
	private final GridView storageView;
	private final PreviewView previewView;
	private final GridView playfieldView;
	private final ScoreView scoreView;

	/**
	 * Creates a new Tetroshow view.
	 *
	 * @param tetroshow Tetroshow to display
	 * @since 0.0.0
	 */
	public TetroshowView(Tetroshow tetroshow) {

		this.tetroshow = tetroshow;

		var storage = tetroshow.getStorage();
		var preview = tetroshow.getPreview();
		var playfield = tetroshow.getPlayfield();

		storageView = new StorageView(storage);
		previewView = new PreviewView(preview);
		playfieldView = new PlayfieldView(playfield);
		scoreView = new ScoreView(tetroshow);

		// set constraints
		storageView.setX(0);
		storageView.setY(0);
		previewView.setX(storageView.getWidth());
		playfieldView.setX(previewView.getX());
		playfieldView.setY(previewView.getHeight());
		scoreView.setX(playfieldView.getX() + playfieldView.getWidth());
		scoreView.setY(playfieldView.getY());
		previewView.setWidth(playfieldView.getWidth() + scoreView.getWidth());
		scoreView.setHeight(playfieldView.getHeight());

		setPreferredSize(DIMENSION);
		hideCursor(this);
		addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent event) {

	}

	@Override
	public void focusLost(FocusEvent event) {
		tetroshow.setPaused(true);
	}

	@Override
	public void paint(Graphics graphics) {

		var graphics2d = (Graphics2D) graphics;
		var skin = getCurrentSkin();

		graphics2d.setColor(skin.getBackgroundColor());
		graphics2d.fillRect(0, 0, getWidth(), getHeight());

		storageView.paint(graphics2d);
		previewView.paint(graphics2d);
		playfieldView.paint(graphics2d);
		scoreView.paint(graphics2d);
	}
}
