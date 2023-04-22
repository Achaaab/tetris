package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.audio.SoundEffect;
import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.model.field.Playfield;
import com.github.achaaab.tetroshow.model.piece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.achaaab.tetroshow.model.piece.Direction.DOWN;
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
	private Tetroshow tetroshow;

	@Mock
	private Playfield playfield;

	@Mock
	private SoundEffect soundEffect;

	@Mock
	private Piece piece;

	@BeforeEach
	void setUp() {
		when(tetroshow.getPlayfield()).thenReturn(playfield);
	}

	@Test
	void testExecuteWithoutFallingPiece() {

		var hardDrop = new HardDrop(tetroshow, soundEffect);

		when(tetroshow.getFallingPiece()).thenReturn(empty());

		hardDrop.execute();

		verify(tetroshow, never()).increaseDropBonus();
		verify(tetroshow, never()).lockFallingPiece();
	}

	@Test
	void testExecuteWithFallingPiece() {

		var hardDrop = new HardDrop(tetroshow, soundEffect);

		when(tetroshow.getFallingPiece()).thenReturn(Optional.of(piece));
		when(playfield.isMovePossible(piece, DOWN)).thenReturn(true, true, false);
		when(tetroshow.lockFallingPiece()).thenReturn(true);

		hardDrop.execute();

		verify(piece, times(2)).move(DOWN);
		verify(tetroshow, times(2)).increaseDropBonus();
		verify(tetroshow).lockFallingPiece();
	}
}
