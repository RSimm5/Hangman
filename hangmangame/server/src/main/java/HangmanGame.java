import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HangmanGame {
    private Map<String, Category> categories; 
    private Category currCategory;
    private String currWord;
    private String hiddenWord;
    private Set<Character> prevGuesses;
    private int remainingGuesses;

    public HangmanGame() {
        this.categories = new HashMap<>();
        prevGuesses = new HashSet<>();
        initialize();
    }

    public void initialize() {
        currWord = "";
        hiddenWord = "";
        prevGuesses.clear();
        initializeMap();
    }

    public void initializeMap() {
        categories.clear();
        
        ArrayList<String> fruits = new ArrayList<>(Arrays.asList(
            "apple", "banana", "orange", "grape", "strawberry","watermelon", "kiwi", "pineapple", "peach", "plum","pear", "cherry", "mango", "blueberry", "raspberry","blackberry", "avocado", "lemon", "lime", "coconut",
            "pomegranate", "fig", "grapefruit", "apricot", "nectarine","melon", "cantaloupe", "passionfruit", "papaya", "guava","date", "kiwifruit", "dragonfruit", "persimmon", "cranberry","gooseberry", "elderberry", "boysenberry", "kiwano", "tangerine",
            "apricot", "honeydew", "mandarin", "mulberry", "rhubarb","starfruit", "plantain", "quince", "tamarillo"
        ));
        ArrayList<String> usPresidents = new ArrayList<>(Arrays.asList(
            "washington", "adams", "jefferson", "madison", "monroe", "jackson", "van buren", "tyler", "polk", "taylor", "fillmore", "pierce", "buchanan", "lincoln", "grant", "hayes",
            "garfield", "arthur", "cleveland", "harrison", "mckinley", "taft", "wilson", "harding", "coolidge", "hoover", "roosevelt", "truman", "eisenhower", "kennedy", "johnson", "nixon", "ford",
            "carter", "reagan", "clinton", "bush", "obama", "trump", "biden"
        ));
        ArrayList<String> countries = new ArrayList<>(Arrays.asList(
            "china", "india", "unitedstates", "indonesia", "pakistan", "brazil", "nigeria", "bangladesh", "russia", "mexico",
             "japan", "ethiopia", "philippines", "egypt", "vietnam", "drcongo", "turkey", "iran", "germany", "thailand","unitedkingdom", "france", "italy", "southafrica", "tanzania", "myanmar", "kenya", "southkorea", "colombia", 
             "spain", "argentina", "uganda", "algeria", "sudan", "ukraine", "iraq", "afghanistan", "poland", "canada","morocco", "saudiarabia", "uzbekistan", "malaysia", "venezuela", "peru", "angola", "ghana", "mozambique", "yemen"
        ));
        
        categories.put("fruits", new Category(fruits));
        categories.put("USA presidents", new Category(usPresidents));
        categories.put("countries", new Category(countries));
    }

    public void pickCategory(String category) {
        if (categories.containsKey(category)) {
            currCategory = categories.get(category);
        } else {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
        startRound();
    }

    public void startRound() {
        currWord = currCategory.getRandomWord();
        makeHiddenWord();
        prevGuesses.clear();
        remainingGuesses = 6;
    }

    public void makeHiddenWord() {
        hiddenWord = "";
        for (int i = 0; i < currWord.length(); i++) {
            hiddenWord += "-";
        }
    }

    public void makeGuess(char guess) {
        if (prevGuesses.contains(guess)) return;
        prevGuesses.add(guess);

        int index = currWord.indexOf(guess);
        if (currWord.indexOf(guess) != -1) {
            while (index != -1) {
                hiddenWord = hiddenWord.substring(0, index) + guess + hiddenWord.substring(index + 1); 
                index = currWord.indexOf(guess, index + 1);
            }

            // win
            if (hiddenWord.equals(currWord)) {
                currCategory.setHasWon(true);
            }
        } else {
            remainingGuesses--;

            //lose
            if (remainingGuesses == 0) {
                currCategory.setRemainingAttempts(currCategory.getRemainingAttempts()-1);
            }
        }
    }

    public boolean gameWon() {
        for (Map.Entry<String, Category> entry : categories.entrySet()) {
            if (entry.getValue().getHasWon() == false) {
                return false;
            }
        }
        return true;
    }

    public boolean gameLost() {
        if (currCategory.getRemainingAttempts() == 0) { 
            return true; 
        }
        return false;
    }

    // for categories page
    public Packet getCategoryData() {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> attempts = new ArrayList<>();
        ArrayList<Boolean> wins = new ArrayList<>();

        for (Map.Entry<String, Category> entry : categories.entrySet()) {
            names.add(entry.getKey());
            attempts.add(entry.getValue().getRemainingAttempts());
            wins.add(entry.getValue().getHasWon());
        }

        Packet packet = new Packet(names, attempts, wins);
        return packet;
    }

    public Packet getRoundData() {
        Packet packet = new Packet(prevGuesses, hiddenWord, remainingGuesses);
        return packet;
    }

    //getters
    public String getHiddenWord() {
        return hiddenWord;
    }

    public Set<Character> getPrevGuesses() {
        return prevGuesses;
    }

    public int getRemainingGuesses() {
        return remainingGuesses;
    }

    public Category getCurrCategory() {
        return currCategory;
    }

}
