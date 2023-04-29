package com.github.achaaab.tetroshow.audio;

import static com.github.achaaab.tetroshow.audio.AudioPlayer.getSoundEffect;

public class AudioBank {

	public static final SoundEffect MOVE = getSoundEffect("audio/effect/move.wav", 6);
	public static final SoundEffect LOCK = getSoundEffect("audio/effect/lock.wav", 2);
	public static final SoundEffect HARD_DROP = getSoundEffect("audio/effect/hard_drop.wav", 2);
	public static final SoundEffect CLEAR = getSoundEffect("audio/effect/clear.wav", 2);
	public static final SoundEffect LINE_DROP = getSoundEffect("audio/effect/line_drop.wav", 2);
}
