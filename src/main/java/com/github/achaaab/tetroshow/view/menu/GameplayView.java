package com.github.achaaab.tetroshow.view.menu;

import com.github.achaaab.tetroshow.settings.Rules;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.view.component.Button;
import com.github.achaaab.tetroshow.view.component.Label;
import com.github.achaaab.tetroshow.view.component.Option;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;

import static com.github.achaaab.tetroshow.utility.SwingUtility.scale;
import static com.github.achaaab.tetroshow.view.message.Messages.BACK;
import static com.github.achaaab.tetroshow.view.message.Messages.GAMEPLAY;
import static com.github.achaaab.tetroshow.view.message.Messages.HOLD;
import static com.github.achaaab.tetroshow.view.message.Messages.RULES;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_UP;
import static java.util.Arrays.stream;

/**
 * view to display and change gameplay settings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class GameplayView extends MenuView {

	private static final int INPUT_HEIGHT = scale(40);
	private static final int VALUE_X = scale(180);
	private static final int INPUT_X = scale(55);
	private static final int FIRST_INPUT_Y = scale(120);

	private final Option<Integer> holdLimit;
	private final Option<Rules> rules;
	private final Button back;

	/**
	 * Creates a view to display and change graphics settings.
	 *
	 * @since 0.0.0
	 */
	public GameplayView() {

		var title = new Label(GAMEPLAY);
		title.setFont(DEFAULT_TITLE_FONT);

		holdLimit = new Option<>(
				HOLD,
				List.of(0, 1),
				Object::toString,
				VALUE_X);

		rules = new Option<>(
				RULES,
				stream(Rules.values()).toList(),
				Rules::getDisplayName,
				VALUE_X);

		back = new Button(BACK);

		add(title);
		add(holdLimit);
		add(rules);
		add(back);

		selectFirstInput();

		var settings = Settings.getDefaultInstance();
		var gameplaySettings = settings.getGameplay();

		holdLimit.select(gameplaySettings.getHoldLimit());
		rules.select(Rules.fromLowerCaseName(gameplaySettings.getRules()));

		title.setX(scale(130.0f));
		title.setY(scale(50.0f));

		var y = FIRST_INPUT_Y;

		holdLimit.setX(INPUT_X);
		holdLimit.setY(y);

		y += INPUT_HEIGHT;
		rules.setX(INPUT_X);
		rules.setY(y);

		y += INPUT_HEIGHT + INPUT_HEIGHT / 2;
		back.setX(INPUT_X);
		back.setY(y);
	}

	/**
	 * @param consumer hold limit change consumer
	 * @since 0.0.0
	 */
	public void onHoldLimitChanged(Consumer<Integer> consumer) {
		holdLimit.setConsumer(consumer);
	}

	/**
	 * @param consumer rules change consumer
	 * @since 0.0.0
	 */
	public void onRulesChanged(Consumer<Rules> consumer) {
		rules.setConsumer(consumer);
	}

	/**
	 * @param action back action
	 * @since 0.0.0
	 */
	public void onBack(Runnable action) {
		back.setAction(action);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

		var keyCode = keyEvent.getKeyCode();

		switch (keyCode) {

			case VK_UP -> selectPreviousInput();
			case VK_DOWN -> selectNextInput();
			case VK_ESCAPE -> back.executeAction();
			default -> getSelectedInput().keyTyped(keyEvent);
		}
	}
}
