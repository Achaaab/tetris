package com.github.achaaab.tetroshow.settings;

import java.util.List;

/**
 * audio settings
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class AudioSettings {

	private int musicVolume;
	private int soundEffectVolume;
	private List<String> tracks;

	/**
	 * @return music volume in {@code [0, 10]}
	 * @since 0.0.0
	 */
	public int getMusicVolume() {
		return musicVolume;
	}

	/**
	 * @param musicVolume music volume in {@code [0, 10]}
	 * @since 0.0.0
	 */
	public void setMusicVolume(int musicVolume) {
		this.musicVolume = musicVolume;
	}

	/**
	 * @return sound effects volume in {@code [0, 10]}
	 * @since 0.0.0
	 */
	public int getSoundEffectVolume() {
		return soundEffectVolume;
	}

	/**
	 * @param soundEffectVolume sound effect volume in {@code [0, 10]}
	 * @since 0.0.0
	 */
	public void setSoundEffectVolume(int soundEffectVolume) {
		this.soundEffectVolume = soundEffectVolume;
	}

	/**
	 * @return tracks
	 * @since 0.0.0
	 */
	public List<String> getTracks() {
		return tracks;
	}

	/**
	 * @param tracks tracks
	 * @since 0.0.0
	 */
	public void setTracks(List<String> tracks) {
		this.tracks = tracks;
	}
}
