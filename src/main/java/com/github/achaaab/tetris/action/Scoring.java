package com.github.achaaab.tetris.action;

import com.github.achaaab.tetris.configuration.Configuration;
import com.github.achaaab.tetris.model.Section;
import com.github.achaaab.tetris.model.classic.Tetris;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
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
public class Scoring extends Action {

	private static final Logger LOGGER = getLogger(Scoring.class);

	private static final String NOM_FICHIER_CONFIGURATION = "tgm3";

	private static final String TYPE_COOL = "cool";
	private static final String TYPE_REGRET = "regret";

	private static final Pattern PATTERN_SECTION = compile(
			"(" + TYPE_COOL + "|" + TYPE_REGRET + ")_(\\d*)-(\\d*)");

	private static final int INDEX_GROUPE_TYPE = 1;
	private static final int INDEX_GROUPE_DEBUT = 2;
	private static final int INDEX_GROUPE_FIN = 3;

	private Iterator<Section> iterateurSectionsCool;
	private Iterator<Section> iterateurSectionsRegret;
	private Section sectionCool;
	private Section sectionRegret;
	private int combo;

	/**
	 * @param tetris
	 * @since 0.0.0
	 */
	public Scoring(Tetris tetris) {

		super(tetris);

		combo = 1;

		lireSections();
	}

	/**
	 * @since 0.0.0
	 */
	private void lireSections() {

		var sectionsCool = new ArrayList<Section>();
		var sectionsRegret = new ArrayList<Section>();

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
					case TYPE_COOL -> sectionsCool.add(section);
					case TYPE_REGRET -> sectionsRegret.add(section);
				}
			}
		}

		sort(sectionsCool);
		sort(sectionsRegret);

		iterateurSectionsCool = sectionsCool.iterator();
		iterateurSectionsRegret = sectionsRegret.iterator();

		sectionCool = iterateurSectionsCool.next();
		sectionRegret = iterateurSectionsRegret.next();
	}

	@Override
	public void execute() {

		var lineCount = tetris.getLineCount();
		var levelBefore = tetris.getLevel();
		var levelSpeed = tetris.getLevelSpeed();
		var score = tetris.getScore();
		var dropBonus = tetris.dropBonus();
		var dureePiece = tetris.getFallingPieceAge();
		var bonusNiveau = tetris.getBonusNiveau();
		var time = tetris.getTime();

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

		if (sectionCool != null) {

			sectionCool.setTime(niveauApres, time);

			if (sectionCool.isCool()) {

				tetris.setBonusNiveau(bonusNiveau + 100);
				LOGGER.info("Cool !");
			}

			if (sectionCool.isEnded()) {

				if (iterateurSectionsCool.hasNext()) {
					sectionCool = iterateurSectionsCool.next();
				} else {
					sectionCool = null;
				}
			}
		}

		if (sectionRegret != null) {

			sectionRegret.setTime(niveauApres, time);

			if (sectionRegret.isRegret()) {
				LOGGER.info("Regret...");
			}

			if (sectionRegret.isEnded()) {

				if (iterateurSectionsRegret.hasNext()) {
					sectionRegret = iterateurSectionsRegret.next();
				} else {
					sectionRegret = null;
				}
			}
		}

		tetris.setLevel(niveauApres);
		tetris.setScore(score);
	}
}
