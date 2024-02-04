import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> names;
    private ArrayList<Integer> attempts;
    private ArrayList<Boolean> wins;
    private String type;

    Packet(ArrayList<String> names, ArrayList<Integer> attempts, ArrayList<Boolean> wins) {
        this.names = names;
        this.attempts = attempts;
        this.wins = wins;
        this.type = "category";
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Integer> getAttempts() {
        return attempts;
    }

    public ArrayList<Boolean> getWins() {
        return wins;
    }


    //RoundData
    private Set<Character> prevGuesses;
    private String hiddenWord;
    private int remainingGuesses;
    
    public Packet(Set<Character> prevGuesses, String hiddenWord, int remainingGuesses) {
        this.prevGuesses = new HashSet<>(prevGuesses);
        this.hiddenWord = new String(hiddenWord);
        this.remainingGuesses = remainingGuesses;
        this.type = "round";
    }

    public Set<Character> getPrevGuesses() {
        return prevGuesses;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public int getRemainingGuesses() {
        return remainingGuesses;
    }

    public String getType() {
        return type;
    }

}
