package com.githubt.achaaab.tetris.action;

import com.github.achaaab.tetris.action.Gravity;
import com.github.achaaab.tetris.action.Lock;
import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.configuration.Configuration;
import com.github.achaaab.tetris.model.classic.Tetris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * unit tests of {@link Gravity}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
@ExtendWith(MockitoExtension.class)
class TestLock {

	@Mock
	private Tetris tetris;

	@Mock
	private Configuration configuration;

	@Mock
	private Audio soundEffect;

	@BeforeEach
	void setUp() {
		when(tetris.getConfiguration()).thenReturn(configuration);
	}

	@Test
	void testExecuteInactive() {

		var lock = new Lock(tetris, soundEffect);
		lock.execute();

		verify(tetris, never()).lockFallingPiece();
		verify(soundEffect, never()).play();
	}

	@Test
	void testExecuteActive() {

		var lock = new Lock(tetris, soundEffect);

		when(tetris.getLevelSpeed()).thenReturn(42);
		when(configuration.getLockDelay(42)).thenReturn(42);

		lock.start();
		lock.execute();

		verify(tetris, never()).lockFallingPiece();
		verify(soundEffect, never()).play();
	}

	@Test
	void testExecuteLocking() {

		var lock = new Lock(tetris, soundEffect);
		lock.start();

		when(tetris.getLevelSpeed()).thenReturn(42);
		when(configuration.getLockDelay(42)).thenReturn(1);
		when(tetris.lockFallingPiece()).thenReturn(true);

		lock.execute();

		verify(tetris).lockFallingPiece();
		verify(soundEffect).play();
	}

	@Test
	void testCancel() {

		var lock = new Lock(tetris, soundEffect);

		when(tetris.getLevelSpeed()).thenReturn(42);
		when(configuration.getLockDelay(42)).thenReturn(2);

		lock.start();
		lock.execute();
		lock.cancel();
		lock.execute();

		verify(tetris, never()).lockFallingPiece();
		verify(soundEffect, never()).play();
	}
}
