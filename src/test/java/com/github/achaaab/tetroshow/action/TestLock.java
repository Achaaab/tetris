package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.audio.Audio;
import com.github.achaaab.tetroshow.configuration.Configuration;
import com.github.achaaab.tetroshow.model.Tetroshow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
	private Configuration configuration;

	@Mock
	private Audio soundEffect;

	@BeforeEach
	void setUp() {
		when(tetroshow.getConfiguration()).thenReturn(configuration);
	}

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

		when(tetroshow.getLevelSpeed()).thenReturn(42);
		when(configuration.getLockDelay(42)).thenReturn(42);

		lock.start();
		lock.execute();

		verify(tetroshow, never()).lockFallingPiece();
		verify(soundEffect, never()).play();
	}

	@Test
	void testExecuteLocking() {

		var lock = new Lock(tetroshow, soundEffect);
		lock.start();

		when(tetroshow.getLevelSpeed()).thenReturn(42);
		when(configuration.getLockDelay(42)).thenReturn(1);
		when(tetroshow.lockFallingPiece()).thenReturn(true);

		lock.execute();

		verify(tetroshow).lockFallingPiece();
		verify(soundEffect).play();
	}

	@Test
	void testCancel() {

		var lock = new Lock(tetroshow, soundEffect);

		when(tetroshow.getLevelSpeed()).thenReturn(42);
		when(configuration.getLockDelay(42)).thenReturn(2);

		lock.start();
		lock.execute();
		lock.cancel();
		lock.execute();

		verify(tetroshow, never()).lockFallingPiece();
		verify(soundEffect, never()).play();
	}
}
