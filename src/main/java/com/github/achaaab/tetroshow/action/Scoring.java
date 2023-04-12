package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Section;
import com.github.achaaab.tetroshow.model.Tetroshow;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Collections.sort;
import static java.util.ResourceBundle.getBundle;
import static java.util.regex.Pattern.compile;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Scoring extends AbstractAction {

	private static final Logger LOGGER = getLogger(Scoring.class);

	private static final String NOM_FICHIER_CONFIGURATION = "tgm3";

	private static final String TYPE_COOL = "cool";
	private static final String TYPE_REGRET = "regret";

	private static final Pattern PATTERN_SECTION = compile(
			"(" + TYPE_COOL + "|" + TYPE_REGRET + ")_(\\d*)-(\\d*)");

	private static final int INDEX_GROUPE_TYPE = 1;
	private static final int INDEX_GROUPE_DEBUT = 2;
	private static final int INDEX_GROUPE_FIN = 3;

	private List<Section> coolSections;
	private List<Section> regretSections;
	private Iterator<Section> coolSectionIterator;
	private Iterator<Section> regretSectionIterator;
	private Section coolSection;
	private Section regretSection;
	private int combo;

	/**
	 * @param tetroshow
	 * @since 0.0.0
	 */
	public Scoring(Tetroshow tetroshow) {

		super(tetroshow);

		lireSections();
		reset();
	}

	/**
	 * @since 0.0.0
	 */
	private void lireSections() {

		coolSections = new ArrayList<Section>();
		regretSections = new ArrayList<Section>();

		var bundle = getBundle(NOM_FICHIER_CONFIGURATION);
		var cles = bundle.keySet();

		for (var cle : cles) {

			var matcher = PATTERN_SECTION.matcher(cle);

			if (matcher.matches()) {

				var typeSection = matcher.group(INDEX_GROUPE_TYPE);
				var niveauDebutString = matcher.group(INDEX_GROUPE_DEBUT);
				var niveauFinString = matcher.group(INDEX_GROUPE_FIN);

				var dureeConditionnelleString = bundle.getString(cle);

				var niveauDebut = parseInt(niveauDebutString);
				var niveauFin = parseInt(niveauFinString);
				var dureeCondition = parseInt(dureeConditionnelleString);

				var section = new Section(niveauDebut, niveauFin, dureeCondition);

				switch (typeSection) {
					case TYPE_COOL -> coolSections.add(section);
					case TYPE_REGRET -> regretSections.add(section);
				}
			}
		}

		sort(coolSections);
		sort(regretSections);
	}

	@Override
	public void execute() {

		var lineCount = tetroshow.getLineCount();
		var levelBefore = tetroshow.getLevel();
		var levelSpeed = tetroshow.getLevelSpeed();
		var score = tetroshow.getScore();
		var dropBonus = tetroshow.dropBonus();
		var dureePiece = tetroshow.getFallingPieceAge();
		var bonusNiveau = tetroshow.getBonusLevel();
		var time = tetroshow.getTime();

		if (lineCount == 0) {
			combo = 1;
		} else {
			combo += 2 * (lineCount - 1);
		}

		var incrementNiveau = configuration.getIncrementNiveau(lineCount);
		var niveauApres = levelBefore + incrementNiveau;

		score += ((levelBefore + lineCount + 3) / 4 + dropBonus) * lineCount * combo
				+ (niveauApres + 1) / 2;

		var delaiVerrouillage = configuration.getLockDelay(levelSpeed);
		var bonusVitesse = delaiVerrouillage - dureePiece;

		if (bonusVitesse > 0) {
			score += bonusVitesse;
		}

		if (coolSection != null) {

			coolSection.setTime(niveauApres, time);

			if (coolSection.isCool()) {

				tetroshow.setBonusLevel(bonusNiveau + 100);
				LOGGER.info("Cool !");
			}

			if (coolSection.isEnded()) {

				if (coolSectionIterator.hasNext()) {
					coolSection = coolSectionIterator.next();
				} else {
					coolSection = null;
				}
			}
		}

		if (regretSection != null) {

			regretSection.setTime(niveauApres, time);

			if (regretSection.isRegret()) {
				LOGGER.info("Regret...");
			}

			if (regretSection.isEnded()) {

				if (regretSectionIterator.hasNext()) {
					regretSection = regretSectionIterator.next();
				} else {
					regretSection = null;
				}
			}
		}

		tetroshow.setLevel(niveauApres);
		tetroshow.setScore(score);
	}

	@Override
	public void reset() {

		coolSectionIterator = coolSections.iterator();
		regretSectionIterator = regretSections.iterator();

		coolSection = coolSectionIterator.next();
		regretSection = regretSectionIterator.next();

		combo = 1;
	}
}
