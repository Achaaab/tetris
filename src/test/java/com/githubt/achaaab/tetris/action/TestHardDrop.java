package com.githubt.achaaab.tetris.action;

import com.github.achaaab.tetris.action.HardDrop;
import com.github.achaaab.tetris.audio.Audio;
import com.github.achaaab.tetris.model.Piece;
import com.github.achaaab.tetris.model.classic.Playfield;
import com.github.achaaab.tetris.model.classic.Tetris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.achaaab.tetris.model.Direction.DOWN;
import static java.util.Optional.empty;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * unit tests of {@link HardDrop}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
@ExtendWith(MockitoExtension.class)
public class TestHardDrop {

	@Mock
	private Tetris tetris;

	@Mock
	private Playfield playfield;

	@Mock
	private Audio soundEffect;

	@Mock
	private Piece piece;

	@BeforeEach
	void setUp() {
		when(tetris.getPlayfield()).thenReturn(playfield);
	}

	@Test
	void testExecuteWithoutFallingPiece() {

		var hardDrop = new HardDrop(tetris, soundEffect);

		when(tetris.getFallingPiece()).thenReturn(empty());

		hardDrop.execute();

		verify(tetris, never()).increaseDropBonus();
		verify(tetris, never()).lockFallingPiece();
		verify(soundEffect, never()).play();
	}

	@Test
	void testExecuteWithFallingPiece() {

		var hardDrop = new HardDrop(tetris, soundEffect);

		when(tetris.getFallingPiece()).thenReturn(Optional.of(piece));
		when(playfield.isMovePossible(piece, DOWN)).thenReturn(true, true, false);

		hardDrop.execute();

		verify(piece, times(2)).move(DOWN);
		verify(tetris, times(2)).increaseDropBonus();
		verify(tetris).lockFallingPiece();
	}
}
