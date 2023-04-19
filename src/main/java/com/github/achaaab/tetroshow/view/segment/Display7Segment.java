package com.github.achaaab.tetroshow.view.segment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.github.achaaab.tetroshow.view.Scaler.scale;

/**
 * 7 LCD-segment display
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Display7Segment extends SegmentDisplay {

	private static final int SEGMENT_COUNT = 7;

	private static final int SEGMENT_GAP = scale(0.5f);
	private static final int SEGMENT_WIDTH = scale(3.0f);
	private static final int SEGMENT_HEIGHT = scale(7.5f);
	private static final int SEGMENT_LENGTH = scale(9.5f);

	private static final int G = SEGMENT_GAP;
	private static final int W = SEGMENT_WIDTH;
	private static final int H = SEGMENT_HEIGHT;
	private static final int L = SEGMENT_LENGTH;

	private static final int WG = W + G;

	private static final int[] SEGMENT_0_X = { WG, WG + L, WG + L + W / 2, WG + L, WG, W / 2 + G };
	private static final int[] SEGMENT_0_Y = { 0, 0, W / 2, W, W, W / 2 };
	private static final int[] SEGMENT_1_X = { W / 2, W, W, W / 2, 0, 0 };
	private static final int[] SEGMENT_1_Y = { W / 2 + G, WG, WG + H, WG + H + W / 2, WG + H, WG };
	private static final int[] SEGMENT_2_X = { W + G * 2 + L + W / 2, W + G * 2 + L + W, W + G * 2 + L + W, W + G * 2 + L + W / 2, W + G * 2 + L, W + G * 2 + L };
	private static final int[] SEGMENT_2_Y = { W / 2 + G, W + G, W + G + H, W + G + H + W / 2, W + G + H, W + G };
	private static final int[] SEGMENT_3_X = { W + G, W + G + L, W + G + L + W / 2, W + G + L, W + G, W / 2 + G };
	private static final int[] SEGMENT_3_Y = { W + G * 2 + H, W + G * 2 + H, W + G * 2 + H + W / 2, W + G * 2 + H + W, W + G * 2 + H + W, W + G * 2 + H + W / 2 };
	private static final int[] SEGMENT_4_X = { W / 2, W, W, W / 2, 0, 0 };
	private static final int[] SEGMENT_4_Y = { W + G * 2 + H + W / 2 + G, W + G * 2 + H + W + G, W + G * 2 + H + W + G + H, W + G * 2 + H + W + G + H + W / 2, W + G * 2 + H + W + G + H, W + G * 2 + H + W + G };
	private static final int[] SEGMENT_5_X = { W + G * 2 + L + W / 2, W + G * 2 + L + W, W + G * 2 + L + W, W + G * 2 + L + W / 2, W + G * 2 + L, W + G * 2 + L };
	private static final int[] SEGMENT_5_Y = { W + G * 2 + H + W / 2 + G, W + G * 2 + H + W + G, W + G * 2 + H + W + G + H, W + G * 2 + H + W + G + H + W / 2, W + G * 2 + H + W + G + H, W + G * 2 + H + W + G };
	private static final int[] SEGMENT_6_X = { W + G, W + G + L, W + G + L + W / 2, W + G + L, W + G, W / 2 + G };
	private static final int[] SEGMENT_6_Y = { W * 2 + H * 2 + G * 4, W * 2 + H * 2 + G * 4, W * 2 + H * 2 + G * 4 + W / 2, W * 2 + H * 2 + G * 4 + W, W * 2 + H * 2 + G * 4 + W, W * 2 + H * 2 + G * 4 + W / 2 };

	private static final int[] ON_SEGMENTS_0 = { 0, 1, 2, 4, 5, 6 };
	private static final int[] ON_SEGMENTS_1 = { 2, 5 };
	private static final int[] ON_SEGMENTS_2 = { 0, 2, 3, 4, 6 };
	private static final int[] ON_SEGMENTS_3 = { 0, 2, 3, 5, 6 };
	private static final int[] ON_SEGMENTS_4 = { 1, 2, 3, 5 };
	private static final int[] ON_SEGMENTS_5 = { 0, 1, 3, 5, 6 };
	private static final int[] ON_SEGMENTS_6 = { 0, 1, 3, 4, 5, 6 };
	private static final int[] ON_SEGMENTS_7 = { 0, 2, 5 };
	private static final int[] ON_SEGMENTS_8 = { 0, 1, 2, 3, 4, 5, 6 };
	private static final int[] ON_SEGMENTS_9 = { 0, 1, 2, 3, 5, 6 };

	private static final Map<Character, int[]> ON_SEGMENTS_TABLE;

	static {

		ON_SEGMENTS_TABLE = new HashMap<>();

		ON_SEGMENTS_TABLE.put('0', ON_SEGMENTS_0);
		ON_SEGMENTS_TABLE.put('1', ON_SEGMENTS_1);
		ON_SEGMENTS_TABLE.put('2', ON_SEGMENTS_2);
		ON_SEGMENTS_TABLE.put('3', ON_SEGMENTS_3);
		ON_SEGMENTS_TABLE.put('4', ON_SEGMENTS_4);
		ON_SEGMENTS_TABLE.put('5', ON_SEGMENTS_5);
		ON_SEGMENTS_TABLE.put('6', ON_SEGMENTS_6);
		ON_SEGMENTS_TABLE.put('7', ON_SEGMENTS_7);
		ON_SEGMENTS_TABLE.put('8', ON_SEGMENTS_8);
		ON_SEGMENTS_TABLE.put('9', ON_SEGMENTS_9);
	}

	/**
	 * @since 0.0.0
	 */
	public Display7Segment() {

		super(ON_SEGMENTS_TABLE);

		segments = new ArrayList<>(SEGMENT_COUNT);

		var segment0 = new Segment(SEGMENT_0_X, SEGMENT_0_Y);
		var segment1 = new Segment(SEGMENT_1_X, SEGMENT_1_Y);
		var segment2 = new Segment(SEGMENT_2_X, SEGMENT_2_Y);
		var segment3 = new Segment(SEGMENT_3_X, SEGMENT_3_Y);
		var segment4 = new Segment(SEGMENT_4_X, SEGMENT_4_Y);
		var segment5 = new Segment(SEGMENT_5_X, SEGMENT_5_Y);
		var segment6 = new Segment(SEGMENT_6_X, SEGMENT_6_Y);

		segments.add(segment0);
		segments.add(segment1);
		segments.add(segment2);
		segments.add(segment3);
		segments.add(segment4);
		segments.add(segment5);
		segments.add(segment6);
	}

	public void translate() {
		translate(L + 3 * W + 2 * G, 0);
	}

	/**
	 * @param deltaX
	 * @param deltaY
	 * @since 0.0.0
	 */
	public void translate(int deltaX, int deltaY) {
		segments.forEach(segment -> segment.translate(deltaX, deltaY));
	}
}
