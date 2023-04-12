package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.piece.Piece;
import com.github.achaaab.tetroshow.model.Tetroshow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * unit tests of {@link Hold}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
@ExtendWith(MockitoExtension.class)
class TestHold {

	@Mock
	private Tetroshow tetroshow;

	@Mock
	private Piece piece;

	@Test
	void testExecuteWithoutFallingPiece() {

		var hold = new Hold(tetroshow);
		when(tetroshow.getFallingPiece()).thenReturn(empty());
		hold.execute();
		verify(tetroshow).setInitialHold(hold);
	}

	@Test
	void testExecuteWithFallingPiece() {

		var hold = new Hold(tetroshow);
		when(tetroshow.getFallingPiece()).thenReturn(Optional.of(piece));
		hold.execute();
		verify(tetroshow).hold();
	}
}
