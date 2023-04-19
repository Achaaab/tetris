package com.github.achaaab.tetroshow.view.segment;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;

/**
 * segment display
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public abstract class SegmentDisplay {

	protected List<Segment> segments;

	protected Map<Character, int[]> onTable;

	/**
	 * @param onTable
	 */
	public SegmentDisplay(Map<Character, int[]> onTable) {
		this.onTable = onTable;
	}

	/**
	 * @param character, can be null
	 * @since 0.0.0
	 */
	public void display(Character character) {

		segments.forEach(Segment::turnOff);

		if (onTable.containsKey(character)) {

			stream(onTable.get(character)).
					mapToObj(segments::get).
					forEach(Segment::turnOn);
		}
	}

	/**
	 * @return la liste des segments de l'afficheur unitaire
	 */
	public List<Segment> getSegments() {
		return segments;
	}
}
