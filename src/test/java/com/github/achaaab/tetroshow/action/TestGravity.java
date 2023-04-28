package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Tetroshow;
import com.github.achaaab.tetroshow.model.field.Playfield;
import com.github.achaaab.tetroshow.model.piece.Direction;
import com.github.achaaab.tetroshow.model.piece.Piece;
import com.github.achaaab.tetroshow.settings.GameplaySettings;
import com.github.achaaab.tetroshow.settings.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.achaaab.tetroshow.action.Gravity.ROW;
import static com.github.achaaab.tetroshow.model.piece.Direction.DOWN;
import static java.util.Optional.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * unit tests of {@link Gravity}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
@ExtendWith(MockitoExtension.class)
class TestGravity {

	@Mock
	private Tetroshow tetroshow;

	@Mock
	private Playfield playfield;

	@Mock
	private Settings settings;

	@Mock
	private GameplaySettings gameplaySettings;

	@Mock
	private Piece piece;

	@BeforeEach
	void setUp() {

		when(tetroshow.getPlayfield()).thenReturn(playfield);
		lenient().when(settings.getGameplay()).thenReturn(gameplaySettings);
	}

	@Test
	void testExecuteWithoutFallingPiece() {

		var gravity = new Gravity(tetroshow);
		when(tetroshow.getFallingPiece()).thenReturn(empty());
		gravity.execute();
		verify(tetroshow, never()).startLocking();
	}

	@Test
	void testExecuteWithoutMove() {

		var gravity = new Gravity(tetroshow);

		when(tetroshow.getFallingPiece()).thenReturn(Optional.of(piece));
		when(tetroshow.getLevel()).thenReturn(42);
		when(gameplaySettings.getGravity(42)).thenReturn(ROW - 1);
		gravity.settings = settings;

		gravity.execute();

		verifyNoInteractions(piece, playfield);
		verify(tetroshow, never()).startLocking();
	}

	@Test
	void testExecuteOneRow() {

		var gravity = new Gravity(tetroshow);

		when(tetroshow.getFallingPiece()).thenReturn(Optional.of(piece));
		when(tetroshow.getLevel()).thenReturn(42);
		when(gameplaySettings.getGravity(42)).thenReturn(ROW);
		when(playfield.isMovePossible(piece, DOWN)).thenReturn(true);
		gravity.settings = settings;

		gravity.execute();

		verify(piece).move(DOWN);
		verify(tetroshow, never()).startLocking();
	}

	@Test
	void testExecuteTwoRows() {

		var gravity = new Gravity(tetroshow);

		when(tetroshow.getFallingPiece()).thenReturn(Optional.of(piece));
		when(tetroshow.getLevel()).thenReturn(42);
		when(gameplaySettings.getGravity(42)).thenReturn(2 * ROW);
		when(playfield.isMovePossible(piece, DOWN)).thenReturn(true);
		gravity.settings = settings;

		gravity.execute();

		verify(piece, times(2)).move(DOWN);
		verify(tetroshow, never()).startLocking();
	}

	@Test
	void testExecuteLock() {

		var gravity = new Gravity(tetroshow);

		when(tetroshow.getFallingPiece()).thenReturn(Optional.of(piece));
		when(tetroshow.getLevel()).thenReturn(42);
		when(gameplaySettings.getGravity(42)).thenReturn(ROW);
		when(playfield.isMovePossible(piece, DOWN)).thenReturn(false);
		gravity.settings = settings;

		gravity.execute();

		verify(piece, never()).move(any(Direction.class));
		verify(tetroshow).startLocking();
	}
}
