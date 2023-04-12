package com.github.achaaab.tetroshow.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;
import static java.lang.Math.min;
import static java.util.Collections.sort;
import static java.util.ResourceBundle.getBundle;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Configuration {

	private static final String BUNDLE_NAME = "tetroshow";

	private static final String LEFT_KEY = "touche_decalage_gauche";
	private static final String RIGHT_KEY = "touche_decalage_droite";
	private static final String CLOCKWISE_KEY = "clockwise_key";
	private static final String COUNTERCLOCKWISE_KEY = "counterclockwise_key";
	private static final String SOFT_DROP_KEY = "touche_descente_douce";
	private static final String HARD_DROP_KEY = "touche_descente_brutale";
	private static final String HOLD_KEY = "touche_reservation";
	private static final String PAUSE_KEY = "touche_pause";
	private static final String EXIT_KEY = "exit_key";

	private static final String CLE_MUSIQUE = "musique";
	private static final String HOLD_LIMIT = "limite_reservation";
	private static final String CLE_DELAI_REPETITION = "delai_repetition";
	private static final String CLE_DELAI_VERROUILLAGE = "delai_verrouillage";
	private static final String CLE_DELAI_TETROMINO = "delai_tetromino";
	private static final String CLE_DELAI_NETTOYAGE = "delai_nettoyage";
	private static final String GRAVITY = "gravity";
	private static final String CLE_INCREMENT = "increment";
	private static final String SEPARATEUR_NIVEAU = "_";

	public static final Configuration INSTANCE = new Configuration();

	private final ResourceBundle bundle;

	private final int leftKey;
	private final int rightKey;
	private final int clockwiseKey;
	private final int counterclockwiseKey;
	private final int softDropKey;
	private final int hardDropKey;
	private final int holdKey;
	private final int pauseKey;
	private final int exitKey;

	private List<String> cheminMusiques;

	private final int holdLimit;
	private final int[] incrementsNiveau;
	private final int[] gravities;
	private final int[] repeatDelays;
	private final int[] lockDelays;
	private final int[] delaisNettoyage;
	private final int[] delaisTetromino;

	/**
	 * @since 0.0.0
	 */
	private Configuration() {

		bundle = getBundle(BUNDLE_NAME);

		leftKey = getInteger(LEFT_KEY);
		rightKey = getInteger(RIGHT_KEY);
		clockwiseKey = getInteger(CLOCKWISE_KEY);
		counterclockwiseKey = getInteger(COUNTERCLOCKWISE_KEY);
		softDropKey = getInteger(SOFT_DROP_KEY);
		hardDropKey = getInteger(HARD_DROP_KEY);
		holdKey = getInteger(HOLD_KEY);
		pauseKey = getInteger(PAUSE_KEY);
		exitKey = getInteger(EXIT_KEY);

		holdLimit = getInteger(HOLD_LIMIT);

		incrementsNiveau = getValues(CLE_INCREMENT);
		gravities = getValues(GRAVITY);
		repeatDelays = getValues(CLE_DELAI_REPETITION);
		lockDelays = getValues(CLE_DELAI_VERROUILLAGE);
		delaisNettoyage = getValues(CLE_DELAI_NETTOYAGE);
		delaisTetromino = getValues(CLE_DELAI_TETROMINO);

		lireCheminMusiques();
	}

	/**
	 * @param key
	 * @return
	 * @since 0.0.0
	 */
	private int getInteger(String key) {
		return parseInt(bundle.getString(key));
	}

	/**
	 * Lit le chemin des musiques dans le bundle.
	 *
	 * @since 0.0.0
	 */
	private void lireCheminMusiques() {

		var prefixeCle = CLE_MUSIQUE + SEPARATEUR_NIVEAU;
		var indexIndex = prefixeCle.length();

		cheminMusiques = new ArrayList<>();
		var parametresMusiques = new ArrayList<Parameter<String>>();
		var cles = bundle.keySet();

		String cheminMusique;
		String indexMusiqueString;

		for (var cle : cles) {

			if (cle.startsWith(prefixeCle)) {

				indexMusiqueString = cle.substring(indexIndex);
				cheminMusique = bundle.getString(cle);

				var indexMusique = parseInt(indexMusiqueString);

				var parametreMusique = new Parameter<>(indexMusique, cheminMusique);
				parametresMusiques.add(parametreMusique);
			}
		}

		sort(parametresMusiques);
		var nombreMusiques = parametresMusiques.size();
		cheminMusiques = new ArrayList<>(nombreMusiques);

		for (var parametre : parametresMusiques) {

			cheminMusique = parametre.value();
			cheminMusiques.add(cheminMusique);
		}
	}

	/**
	 * @param parameter
	 * @return
	 * @since 0.0.0
	 */
	private int[] getValues(String parameter) {

		var keyPrefix = parameter + SEPARATEUR_NIVEAU;
		var levelIndex = keyPrefix.length();

		var parametres = new ArrayList<Parameter<Integer>>();
		var cles = bundle.keySet();

		for (String cle : cles) {

			if (cle.startsWith(keyPrefix)) {

				var niveauString = cle.substring(levelIndex);
				var valeurString = bundle.getString(cle);

				var niveau = parseInt(niveauString);
				var valeur = parseInt(valeurString);

				var parametre = new Parameter<>(niveau, valeur);
				parametres.add(parametre);
			}
		}

		sort(parametres);

		var nombreParametres = parametres.size();
		var indexDernierParametre = nombreParametres - 1;
		var dernierParametre = parametres.get(indexDernierParametre);
		var dernierNiveau = dernierParametre.level();
		var nombreNiveaux = dernierNiveau + 1;
		var valeurs = new int[nombreNiveaux];
		var valeur = 0;
		var iterateurParametres = parametres.iterator();
		var parametre = iterateurParametres.next();
		var niveauParametre = parametre.level();
		var valeurParametre = parametre.value();

		for (var niveau = 0; niveau < nombreNiveaux; niveau++) {

			if (niveau == niveauParametre) {

				valeur = valeurParametre;

				if (iterateurParametres.hasNext()) {

					parametre = iterateurParametres.next();
					niveauParametre = parametre.level();
					valeurParametre = parametre.value();
				}
			}

			valeurs[niveau] = valeur;
		}

		return valeurs;
	}

	/**
	 * @param level
	 * @return gravity expressed in 1/256 row per frame
	 * @since 0.0.0
	 */
	public int getGravity(int level) {

		level = min(level, gravities.length - 1);
		return gravities[level];
	}

	/**
	 * @param niveau
	 * @return
	 * @since 0.0.0
	 */
	public int getAutoRepeatDelay(int niveau) {

		niveau = min(niveau, repeatDelays.length - 1);
		return repeatDelays[niveau];
	}

	/**
	 * @param nombreLignesCompletees nombre de lignes complétées (entre 0 et 4 inclus)
	 * @return incrément du level en fonction du nombre de lignes complétées
	 * @since 0.0.0
	 */
	public int getIncrementNiveau(int nombreLignesCompletees) {
		return incrementsNiveau[nombreLignesCompletees];
	}

	/**
	 * @param niveau
	 * @return
	 * @since 0.0.0
	 */
	public int getLockDelay(int niveau) {

		niveau = min(niveau, lockDelays.length - 1);
		return lockDelays[niveau];
	}

	/**
	 * @param niveau
	 * @return
	 * @since 0.0.0
	 */
	public int getDelaiTetromino(int niveau) {

		niveau = min(niveau, delaisTetromino.length - 1);
		return delaisTetromino[niveau];
	}

	/**
	 * @param niveau
	 * @return
	 * @since 0.0.0
	 */
	public int getDelaiNettoyage(int niveau) {

		niveau = min(niveau, delaisNettoyage.length - 1);
		return delaisNettoyage[niveau];
	}

	/**
	 * @return nombre maximum de réservations pouvant être exécutées pour une pièce tombée
	 * @since 0.0.0
	 */
	public int getHoldLimit() {
		return holdLimit;
	}

	/**
	 * @return chemin des musiques dans l'ordre de lecture
	 * @since 0.0.0
	 */
	public List<String> getCheminMusiques() {
		return cheminMusiques;
	}

	/**
	 * @return touche de déplacement vers la gauche
	 * @since 0.0.0
	 */
	public int getLeftKey() {
		return leftKey;
	}

	/**
	 * @return touche de déplacement vers la droite
	 * @since 0.0.0
	 */
	public int getRightKey() {
		return rightKey;
	}

	/**
	 * @return touche de rotation dans le sens des aiguilles d'une montre
	 * @since 0.0.0
	 */
	public int getClockwiseKey() {
		return clockwiseKey;
	}

	/**
	 * @return counterclockwise rotation key
	 * @since 0.0.0
	 */
	public int getCounterclockwiseKey() {
		return counterclockwiseKey;
	}

	/**
	 * @return touche de descente forcée mais lente
	 * @since 0.0.0
	 */
	public int getSoftDropKey() {
		return softDropKey;
	}

	/**
	 * @return touche de descente instantanée
	 * @since 0.0.0
	 */
	public int getHardDropKey() {
		return hardDropKey;
	}

	/**
	 * @return touche de réservation
	 * @since 0.0.0
	 */
	public int getHoldKey() {
		return holdKey;
	}

	/**
	 * @return touche de mise en pause
	 * @since 0.0.0
	 */
	public int getPauseKey() {
		return pauseKey;
	}

	/**
	 * @return exit key (back to menu)
	 * @since 0.0.0
	 */
	public int getExitKey() {
		return exitKey;
	}
}
