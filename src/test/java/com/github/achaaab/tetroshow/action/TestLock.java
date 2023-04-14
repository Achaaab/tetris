package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.audio.Audio;
import com.github.achaaab.tetroshow.settings.Settings;
import com.github.achaaab.tetroshow.model.Tetroshow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * unit tests of {@link Lock}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
@ExtendWith(MockitoExtension.class)
class TestLock {

	@Mock
	private Tetroshow tetroshow;

	@Mock
	private Settings settings;

	@Mock
	private Audio soundEffect;

	@Test
	void testExecuteInactive() {

		var lock = new Lock(tetroshow, soundEffect);
		lock.execute();

		verify(tetroshow, never()).lockFallingPiece();
		verify(soundEffect, never()).play();
	}

	@Test
	void testExecuteActive() {

		var lock = new Lock(tetroshow, soundEffect);

		when(tetroshow.getLevel()).thenReturn(42);
		when(settings.getLock(42)).thenReturn(42);

		try (var staticSettings = mockStatic(Settings.class)) {

			staticSettings.when(Settings::getDefaultInstance).thenReturn(settings);

			lock.start();
			lock.execute();
		}

		verify(tetroshow, never()).lockFallingPiece();
		verify(soundEffect, never()).play();
	}

	@Test
	void testExecuteLocking() {

		var lock = new Lock(tetroshow, soundEffect);
		lock.start();

		when(tetroshow.getLevel()).thenReturn(42);
		when(settings.getLock(42)).thenReturn(1);
		when(tetroshow.lockFallingPiece()).thenReturn(true);

		try (var staticSettings = mockStatic(Settings.class)) {

			staticSettings.when(Settings::getDefaultInstance).thenReturn(settings);
			lock.execute();
		}

		verify(tetroshow).lockFallingPiece();
		verify(soundEffect).play();
	}

	@Test
	void testCancel() {

		var lock = new Lock(tetroshow, soundEffect);

		when(tetroshow.getLevel()).thenReturn(42);
		when(settings.getLock(42)).thenReturn(2);

		try (var staticSettings = mockStatic(Settings.class)) {

			staticSettings.when(Settings::getDefaultInstance).thenReturn(settings);
			lock.start();
			lock.execute();
			lock.cancel();
			lock.execute();
		}

		verify(tetroshow, never()).lockFallingPiece();
		verify(soundEffect, never()).play();
	}
}
