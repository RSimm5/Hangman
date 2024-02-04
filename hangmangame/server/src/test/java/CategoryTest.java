import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    Category test = new Category(null);
    Category test2;
    
    @BeforeEach
    void setup() {
        ArrayList<String> fruits = new ArrayList<>(Arrays.asList(
            "apple", "banana", "orange", "grape", "strawberry","watermelon", "kiwi", "pineapple", "peach", "plum","pear", "cherry", "mango", "blueberry", "raspberry","blackberry", "avocado", "lemon", "lime", "coconut",
            "pomegranate", "fig", "grapefruit", "apricot", "nectarine","melon", "cantaloupe", "passionfruit", "papaya", "guava","date", "kiwifruit", "dragonfruit", "persimmon", "cranberry","gooseberry", "elderberry", "boysenberry", "kiwano", "tangerine",
            "apricot", "honeydew", "mandarin", "mulberry", "rhubarb","starfruit", "plantain", "quince", "tamarillo"
        ));
        test2 = new Category(fruits);
    }

    @Test
	void constructorTest() {
        assertEquals(false, test.getHasWon(), "Expected boolean equal");
        assertEquals(false, test2.getHasWon(), "Expected boolean equal");
        assertEquals(3, test.getRemainingAttempts(), "Expected ints equal");
        assertEquals(3, test2.getRemainingAttempts(), "Expected ints equal");
        assertEquals(null, test.getWords(), "Expected ArrayLists equal");

        ArrayList<String> fruits = new ArrayList<>(Arrays.asList(
            "apple", "banana", "orange", "grape", "strawberry","watermelon", "kiwi", "pineapple", "peach", "plum","pear", "cherry", "mango", "blueberry", "raspberry","blackberry", "avocado", "lemon", "lime", "coconut",
            "pomegranate", "fig", "grapefruit", "apricot", "nectarine","melon", "cantaloupe", "passionfruit", "papaya", "guava","date", "kiwifruit", "dragonfruit", "persimmon", "cranberry","gooseberry", "elderberry", "boysenberry", "kiwano", "tangerine",
            "apricot", "honeydew", "mandarin", "mulberry", "rhubarb","starfruit", "plantain", "quince", "tamarillo"
        ));

        assertEquals(fruits, test2.getWords(), "Expected ArrayLists equal");
	}

    @Test
    void randomWordTest() {
        String prevRand = "", currRand = "";
        ArrayList<String> words = test2.getWords();
        int size = words.size();
        for (int i = 0; i < words.size(); i++) {
            prevRand = currRand;
            currRand = test2.getRandomWord();
            size--;
            assertEquals(size, words.size(), "Expected ints equal");
            assertNotEquals(prevRand, currRand, "Expected Strings equal");
        }
    }
}
