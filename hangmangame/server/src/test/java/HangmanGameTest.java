import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HangmanGameTest {
	HangmanGame test = new HangmanGame();

	@Test
	void constructorTest() {
		HangmanGame t2 = new HangmanGame();
		assertEquals("", t2.getHiddenWord(), "Expected equal strings");
		Set<Character> t3 = new HashSet<>();
		assertEquals(t3, t2.getPrevGuesses(), "Expected equal sets");
		assertEquals(null, t2.getCurrCategory(), "Expected equal categories");
	}

	@Test
	void pickCategoryTest() {
		String s1 = "fruits";
		assertDoesNotThrow(() -> test.pickCategory(s1));
		assertNotEquals(null, test.getCurrCategory(), "Expected category change");

		String s2 = "USA presidents";
		assertDoesNotThrow(() -> test.pickCategory(s2));
		assertNotEquals(null, test.getCurrCategory(), "Expected category change");

		String s3 = "countries";
		assertDoesNotThrow(() -> test.pickCategory(s3));
		assertNotEquals(null, test.getCurrCategory(), "Expected category change");
	}

	@Test
    void pickInvalidCategory() {

        String invalidCategory = "InvalidCategory";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> test.pickCategory(invalidCategory));

        assertEquals("Invalid category: " + invalidCategory, exception.getMessage(), "expected equal strings");
    }

	@Test
	void startRoundTest() {
		test.pickCategory("fruits");
		test.startRound();
		assertNotEquals("", test.getHiddenWord(), "Expected unequal strings");
	}

	@Test
	void gameWonTest() {
		assertEquals(false, test.gameWon());
	}

}
