package com.github.achaaab.tetroshow.view.play;

import com.github.achaaab.tetroshow.view.component.Component;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

import static com.github.achaaab.tetroshow.utility.SwingUtility.scaleFloat;
import static com.github.achaaab.tetroshow.view.skin.Skin.getCurrentSkin;
import static java.awt.geom.AffineTransform.getTranslateInstance;
import static java.lang.Math.round;

/**
 * UI component as simple as possible, we don't need much
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class PlayComponent extends Component {

	private static final float BORDER = scaleFloat(4.0f);
	protected static final int HALF_BORDER = round(BORDER / 2);
	private static final Stroke DEFAULT_STROKE = new BasicStroke(1.0f);
	private static final Stroke BORDER_STROKE = new BasicStroke(BORDER);

	/**
	 * Creates a new component.
	 *
	 * @since 0.0.0
	 */
	public PlayComponent() {

		x = 0;
		y = 0;
		width = 0;
		height = 0;
		margin = HALF_BORDER;
	}

	@Override
	public void paint(Graphics2D graphics) {

		var translation = getTranslateInstance(x, y);
		graphics.setTransform(translation);

		var skin = getCurrentSkin();

		graphics.setStroke(BORDER_STROKE);
		graphics.setColor(skin.getBorderColor());
		graphics.drawRect(0, 0, width, height);
		graphics.setStroke(DEFAULT_STROKE);

		translation = getTranslateInstance(x + margin, y + margin);
		graphics.setTransform(translation);
	}
}
