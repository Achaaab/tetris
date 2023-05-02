package com.github.achaaab.tetroshow.settings;

import com.github.achaaab.tetroshow.action.scoring.GbScoring;
import com.github.achaaab.tetroshow.action.scoring.Scoring;
import com.github.achaaab.tetroshow.action.scoring.TgmScoring;
import com.github.achaaab.tetroshow.model.Tetroshow;

import java.util.function.Function;

/**
 * set of rules and timings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public enum Rules {

	GAME_BOY("Game Boy", GbScoring::new),
	TGM2_MASTER("TGM2 Master", TgmScoring::new),
	TGM2_NORMAL("TGM2 Normal", TgmScoring::new),
	TGM3_EASY("TGM3 Easy", TgmScoring::new),
	TGM3_MASTER("TGM3 Master", TgmScoring::new);

	/**
	 * @param lowerCaseName lower case name
	 * @return rules with given name
	 * @since 0.0.0
	 */
	public static Rules fromLowerCaseName(String lowerCaseName) {
		return valueOf(lowerCaseName.toUpperCase());
	}

	private final String displayName;
	private final Function<Tetroshow, Scoring> scoringFactory;

	/**
	 * Creates a new set of rules.
	 *
	 * @param displayName name to display
	 * @param scoringFactory function that creates a new Scoring instance from a Tetroshow instance
	 * @since 0.0.0
	 */
	Rules(String displayName, Function<Tetroshow, Scoring> scoringFactory) {

		this.displayName = displayName;
		this.scoringFactory = scoringFactory;
	}

	/**
	 * @return name to display
	 * @since 0.0.0
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return lower case name
	 * @since 0.0.0
	 */
	public String lowerCaseName() {
		return name().toLowerCase();
	}

	/**
	 * Creates a scoring for a Tetroshow.
	 *
	 * @param tetroshow tetroshow for which to create a scoring
	 * @return created scoring
	 * @since 0.0.0
	 */
	public Scoring createScoring(Tetroshow tetroshow) {
		return scoringFactory.apply(tetroshow);
	}
}
