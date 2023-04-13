package com.github.achaaab.tetroshow.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.Math.min;
import static java.util.Collections.sort;
import static java.util.ResourceBundle.getBundle;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Configuration {

	public static final Configuration INSTANCE = new Configuration();

	private static final String BUNDLE_NAME = "tetroshow";

	private static final String TOOLKIT_SYNCHRONIZED = "toolkit.synchronized";
	private static final String LEFT_KEY = "left_key";
	private static final String RIGHT_KEY = "right_key";
	private static final String CLOCKWISE_KEY = "clockwise_key";
	private static final String COUNTERCLOCKWISE_KEY = "counterclockwise_key";
	private static final String SOFT_DROP_KEY = "soft_drop_key";
	private static final String HARD_DROP_KEY = "hard_drop_key";
	private static final String HOLD_KEY = "hold_key";
	private static final String PAUSE_KEY = "pause_key";
	private static final String EXIT_KEY = "exit_key";

	private static final String MUSIC = "music";
	private static final String HOLD_LIMIT = "hold_limit";
	private static final String REPEAT_DELAY = "repeat_delay";
	private static final String LOCK_DELAY = "lock_delay";
	private static final String ENTRY_DELAY = "entry_delay";
	private static final String CLEAR_DELAY = "clear_delay";
	private static final String GRAVITY = "gravity";
	private static final String LINE_BONUS = "line_bonus";

	private static final String LEVEL_SEPARATOR = "_";

	private final ResourceBundle bundle;

	private final boolean toolkitSynchronized;
	private final int leftKey;
	private final int rightKey;
	private final int clockwiseKey;
	private final int counterclockwiseKey;
	private final int softDropKey;
	private final int hardDropKey;
	private final int holdKey;
	private final int pauseKey;
	private final int exitKey;

	private List<String> musicResourceNames;

	private final int holdLimit;
	private final int[] lineBonuses;
	private final int[] gravities;
	private final int[] repeatDelays;
	private final int[] lockDelays;
	private final int[] clearDelays;
	private final int[] entryDelays;

	/**
	 * @since 0.0.0
	 */
	private Configuration() {

		bundle = getBundle(BUNDLE_NAME);

		toolkitSynchronized = getBoolean(TOOLKIT_SYNCHRONIZED);
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

		lineBonuses = getValues(LINE_BONUS);
		gravities = getValues(GRAVITY);
		repeatDelays = getValues(REPEAT_DELAY);
		lockDelays = getValues(LOCK_DELAY);
		clearDelays = getValues(CLEAR_DELAY);
		entryDelays = getValues(ENTRY_DELAY);

		readMusicResourceNames();
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
	 * @param key
	 * @return
	 * @since 0.0.0
	 */
	private boolean getBoolean(String key) {
		return parseBoolean(bundle.getString(key));
	}

	/**
	 * Lit le chemin des musiques dans le bundle.
	 *
	 * @since 0.0.0
	 */
	private void readMusicResourceNames() {

		var prefixeCle = MUSIC + LEVEL_SEPARATOR;
		var indexIndex = prefixeCle.length();

		musicResourceNames = new ArrayList<>();
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
		musicResourceNames = new ArrayList<>(nombreMusiques);

		for (var parametre : parametresMusiques) {

			cheminMusique = parametre.value();
			musicResourceNames.add(cheminMusique);
		}
	}

	/**
	 * @param parameter
	 * @return
	 * @since 0.0.0
	 */
	private int[] getValues(String parameter) {

		var keyPrefix = parameter + LEVEL_SEPARATOR;
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
		return lineBonuses[nombreLignesCompletees];
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

		niveau = min(niveau, entryDelays.length - 1);
		return entryDelays[niveau];
	}

	/**
	 * @param niveau
	 * @return
	 * @since 0.0.0
	 */
	public int getDelaiNettoyage(int niveau) {

		niveau = min(niveau, clearDelays.length - 1);
		return clearDelays[niveau];
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
	public List<String> getMusicResourceNames() {
		return musicResourceNames;
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

	/**
	 * @return whether the toolkit should be synchronized
	 * @since 0.0.0
	 */
	public boolean isToolkitSynchronized() {
		return toolkitSynchronized;
	}
}
