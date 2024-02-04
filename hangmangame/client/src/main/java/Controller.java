import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Controller {
    private Client clientConnection;

    @FXML
    private VBox startPage, selectCategoryPage, roundPage, rulesPage, portPage, winPage, losePage;
    @FXML
    private TextField guessInput, portField, prevGuesses;
    @FXML
    private Button cat1, cat2, cat3;
    @FXML 
    private Label att1, att2, att3, attRound;
    @FXML
    private Text hiddenWord;

    @FXML
    public void initialize() {
        // Set the maximum length for the TextField
        guessInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                guessInput.setText(oldValue);
            }
        });
    }

    // sets vboxes invisible
    private void setInvisible() {
        startPage.setVisible(false);
        selectCategoryPage.setVisible(false);
        roundPage.setVisible(false);
        rulesPage.setVisible(false);
        losePage.setVisible(false);
        winPage.setVisible(false);
    }

    // Methods for handling button actions
    @FXML
    private void handlePort(ActionEvent event) {
        portPage.setVisible(false);
        startPage.setVisible(true);
        clientConnection = new Client(Integer.parseInt(portField.getText()), data->{
				Platform.runLater(()->{
                    if (data instanceof Packet) {
                        Packet pdata = (Packet) data;
                        if (pdata.getType().equals("category")) {
                            processCategories(pdata);
                        }
                        else if (pdata.getType().equals("round")) {
                            processRound(pdata);
                        }
                        
                    }
				});
			});
		clientConnection.start();
    }
    
    @FXML
    private void handleRules(ActionEvent event) {
        setInvisible();
        rulesPage.setVisible(true);
    }

    private void processCategories(Packet data) {
        ArrayList<Boolean> wins = data.getWins();
        ArrayList<Integer> attempts = data.getAttempts();
        if (wins.get(0) && wins.get(1) && wins.get(2)) {
            playerWon();
            return;
        } 
        else if (attempts.get(0) == 0 || attempts.get(1) == 0 || attempts.get(2) == 0 ) {
            playerLost();
            return;
        }
        cat1.setText(data.getNames().get(0));
        cat2.setText(data.getNames().get(1));
        cat3.setText(data.getNames().get(2));

        cat1.setDisable(wins.get(0));
        cat2.setDisable(wins.get(1));
        cat3.setDisable(wins.get(2));

        att1.setText("Attempts Left: " + attempts.get(0));
        att2.setText("Attempts Left: " + attempts.get(1));
        att3.setText("Attempts Left: " + attempts.get(2));
    }

    private void processRound (Packet data) {
        if (data.getRemainingGuesses() <= 0 || data.getHiddenWord().indexOf('-') == -1) {
            backToCategories();
            return;
        }
        hiddenWord.setText(data.getHiddenWord());
        attRound.setText("Attempts Left: " +data.getRemainingGuesses());
        StringBuilder sb = new StringBuilder();
        for (Character c : data.getPrevGuesses()) {
            sb.append(c);
            sb.append(' ');
        }
        prevGuesses.setText(sb.toString());
    }

    private void backToCategories() {
        clientConnection.getCatData();
        setInvisible();
        selectCategoryPage.setVisible(true);
    }


    @FXML
    private void handlePlay(ActionEvent event) {
        clientConnection.startGame();
        setInvisible();
        selectCategoryPage.setVisible(true);
    }

    @FXML
    private void handleCat1(ActionEvent event) {
        clientConnection.pickCategory(cat1.getText());
        startRound();
    }

    @FXML
    private void handleCat2(ActionEvent event) {
        clientConnection.pickCategory(cat2.getText());
        startRound();
    }

    @FXML
    private void handleCat3(ActionEvent event) {
        clientConnection.pickCategory(cat3.getText());
        startRound();
    }

    private void startRound() {
        setInvisible();
        roundPage.setVisible(true);
    }

    @FXML
    private void handleGuess(ActionEvent event) {
        String input = guessInput.getText();
        guessInput.clear();
        if (input.equals("")) return;
        clientConnection.makeGuess(input.charAt(0));
    }

    @FXML
    private void handleBack(ActionEvent event) {
        setInvisible();
        startPage.setVisible(true);
    }

    @FXML
    private void handlePlayAgain(ActionEvent event) {
        setInvisible();
        startPage.setVisible(true);
    }

    private void playerWon() {
        setInvisible();
        winPage.setVisible(true);
    }

    private void playerLost() {
        setInvisible();
        losePage.setVisible(true);
    }
}
