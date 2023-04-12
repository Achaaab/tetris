package com.github.achaaab.tetroshow.action;

import com.github.achaaab.tetroshow.model.Tetroshow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

/**
 * unit tests of {@link Pause}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
@ExtendWith(MockitoExtension.class)
class TestPause {

	@Mock
	private Tetroshow tetroshow;

	@Test
	void testExecute() {

		var pause = new Pause(tetroshow);
		pause.execute();

		verify(tetroshow).pause();
	}
}
