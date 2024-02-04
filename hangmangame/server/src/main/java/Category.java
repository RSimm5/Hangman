import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Category {
    private ArrayList<String> words;
    private boolean hasWon;
    private int remainingAttempts;

    public Category(ArrayList<String> words) {
        this.words = words;
        reset();
    }

    public void reset() {
        remainingAttempts = 3;
        hasWon = false;
    }

    public String getRandomWord() {
        if (words == null || words.isEmpty()) {
            throw new IllegalStateException("ArrayList is either null or empty");
        }
        Random rand = new Random();
        return words.remove(rand.nextInt(words.size()));
    }
    
    // getter
    public ArrayList<String> getWords() {
        return words;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    // getter
    public boolean getHasWon() {
        return this.hasWon;
    }
    
    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    // setter
    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    
}
