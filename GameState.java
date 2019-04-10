import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.ArrayList;
import javax.swing.JApplet;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.awt.Dimension;
import java.lang.*;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.application.*;

//TODO shortlist 1: implement save game

//TODO reminders: make blinds?, ai, settings/music

public class GameState extends Application implements Serializable{
    private static final long serialVersionUID = 3L;
    //start of instance variables for the game

    private static ArrayList<Player> playerList = new ArrayList<>();

    private static ArrayList<Card> tableCards = new ArrayList<>();

    private static int pot = 0;

    private Deck deck = new Deck();

    private int round = 0;

    private int amountToCall = 0;//represents a per round amount

    private Player currentPlayer;

    private Player nextPlayer;

    private Player lastPlayerToRaise;

    private Player leftOfDealer;

    private Stage primary;//workaround to set scene within methods

    private ArrayList<Player> winnerList = new ArrayList<>();

    //Start of GUI rendering parts

    //Windows resolution
    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    double screenWidth = primaryScreenBounds.getWidth();
    double screenHeight = primaryScreenBounds.getHeight();

    //  Start of Main menu
    private StackPane mainMenuRoot = new StackPane();
    private Scene mainMenuScene = new Scene(mainMenuRoot, screenWidth, screenHeight);
    private Button newGameButton = new Button("Start New Game");
    private Button aiGameButton = new Button("Play vs. CPU");
    private Button loadGameButton = new Button("Load Game");//TODO implement this button
    private Button settingsButton = new Button("Settings");//TODO implement this button
    private Text mainMenuTitle = new Text("Welcome to \nTexas Hold 'em!");

    private VBox mainMenu = new VBox(30);

    //Start of new game menu
    private StackPane newGameMenuRoot = new StackPane();
    private Scene newGameMenuScene = new Scene(newGameMenuRoot, screenWidth, screenHeight);
    private Text chipInputLabel = new Text("Enter Starting Chip Amount:");
    private Text playerInputLabel = new Text("Enter Number of Players:");
    private TextField chipInput = new TextField("");
    private TextField playerInput = new TextField("");
    private Button startNewGameButton = new Button("Start");
    private Button backToMainButton = new Button("Back to Main Menu");
    private Text newGameMenuTitle = new Text("New Game");

    private VBox newGameMenu = new VBox(35);

    //start of heads up menu
    private StackPane aiMenuRoot = new StackPane();
    private Scene aiMenuScene = new Scene(aiMenuRoot, screenWidth, screenHeight);
    private Text aiMenuTitle = new Text("Heads Up \nvs.\nCPU ");
    private Button aiStartButton = new Button("Start");
    private Text aiChipInputLabel = new Text("Enter Starting Chip Amount:");
    private TextField aiChipInput = new TextField("");
    private Button aiToMainButton = new Button("Back to Main Menu");

    private VBox aiMenu = new VBox(35);

    //start of inter - player Screen
    private StackPane privacyScreenRoot = new StackPane();
    private Scene privacyScreenScene = new Scene(privacyScreenRoot, screenWidth, screenHeight);
    private Text privacyScreenTitle = new Text("It's Player 1's Turn");
    private Button nextTurnButton = new Button("Begin Turn");

    private VBox privacyScreen = new VBox(200);

    //start of in game interface
    private StackPane inGameRoot = new StackPane();

    private Scene inGameScene = new Scene(inGameRoot, screenWidth, screenHeight);

    private VBox inGameInterface = new VBox(30);

    private HBox tableCardsBox = new HBox();

    private HBox playerCardsBox = new HBox();

    private HBox buttonOptionsBox = new HBox(20.0);

    private Button raise = new Button("Raise");
    private Button call = new Button("Call");
    private Button check = new Button("Check");
    private Button fold = new Button("Fold");
    private Button viewStateOfGameButton = new Button("Look Around the Table");
    private Button endGameButton = new Button("End Game");

    private Text potLabel = new Text("Current Pot: " + pot);

    private VBox playerInfoBox = new VBox();

    private Text playerStackLabel = new Text();

    //start of state of game display menu

    private StackPane stateOfGameDisplayRoot = new StackPane();
    private Scene stateOfGameDisplayScene = new Scene(stateOfGameDisplayRoot, screenWidth, screenHeight);
    private VBox stateOfGameDisplay = new VBox(30);
    private HBox stateOfGameDisplayTableCardsBox = new HBox();
    private Text stateOfGameDisplayPotLabel = new Text("Current Pot " + pot);
    private HBox multiplePlayerInfoBox = new HBox(25);
    private Button stateOfGameDisplayButton = new Button("OK");
    private Button newRoundButton = new Button("Next Round");

    //start of end game screen (displays winner)
    private StackPane winScreenRoot = new StackPane();
    private Scene winScreenScene = new Scene(winScreenRoot, screenWidth, screenHeight);
    private VBox winScreen = new VBox(200);
    private Text winScreenTitle = new Text();
    private Button winScreenBackToMainButton = new Button("Back to Main Menu");

    //end of instance variables for the game
    //////
    private SaveSlot savedData;

    public void createSaveSlot()
    {
        SaveSlot saveData = new SaveSlot(playerList, tableCards, pot, deck, round, amountToCall, currentPlayer, nextPlayer, lastPlayerToRaise, leftOfDealer);
        saveGame(saveData);
    }
    
    //Start of Event Handlers
    public void saveGame(SaveSlot saveData)
    {
        //saves only when end game button pressed as of now TODORICK
        //String saveName = JOptionPane.showInputDialog(null, "Please choose a file name to save to:");
        try
        {
            String saveName = "SavedGame";
            File file = new File(saveName);
            file.delete();
            FileOutputStream fileStream = new FileOutputStream(saveName);
            BufferedOutputStream bufferedStream = new BufferedOutputStream(fileStream);
            ObjectOutputStream objectStream = new ObjectOutputStream(bufferedStream);
            objectStream.writeObject(saveData);
            objectStream.close();
            fileStream.close();
        }
        catch(Exception e)
        {
            System.out.println("ERROR OCCURRED WHILE SAVING DATA");
        }
    }
    public void loadGame()
    {
        try{
            String saveName = "SavedGame";
            File file = new File(saveName);
            FileInputStream fileStream = new FileInputStream(saveName);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);

            savedData = (SaveSlot) objectStream.readObject();
            loadAndProcessSavedData();

            objectStream.close();
            fileStream.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("FILE NOT FOUND");
        }
        catch(Exception e)
        {
            System.out.println("ERROR OCCURRED WHILE LOADING DATA");
        }
    }

    public void loadAndProcessSavedData()
    {
        this.playerList = savedData.getPlayerList();
        this.tableCards = savedData.getTableCards();
        this.pot = savedData.getPot();
        this.deck = savedData.getDeck();
        this.round = savedData.getRound();
        this.amountToCall = savedData.getAmountToCall();
        this.currentPlayer = savedData.getCurrentPlayer();
        this.nextPlayer = savedData.getNextPlayer();
        this.lastPlayerToRaise = savedData.getLastPlayerToRaise();
        this.leftOfDealer = savedData.getLeftOfDealer();

        potLabel.setText("Current pot: " + this.pot);
        showTableCards(tableCardsBox);
        showTableCards(stateOfGameDisplayTableCardsBox);
        showMultiplePlayerCards();
        playerStackLabel.setText("Player: " + currentPlayer.getName() + "\nYour Stack: " + currentPlayer.getChipCount() + "\nPot Investment: " + currentPlayer.potInvestment);
        showPlayerCards(currentPlayer);
        primary.setScene(inGameScene);
        primary.show();
    }//////

    //TODORICK testing purposes only
    public int getPot()
    {
        return pot;
    }
    //Start of Event Handlers

    public void setEventHandlers() {

        //@param we of type window event
        // upon action of hitting the exit in the window it will prompt the user to save.

        primary.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                int option = JOptionPane.showConfirmDialog(null, "Would you Like to Save Before Exiting?");
                if (option == 0) {
                    createSaveSlot();
                } else if (option == 1) {
                    primary.close();
                } else {
                    we.consume();
                }
            }
        });
        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadGame();
            }
        });
        // if the user hits the new game button it will bring them to a new menu screen.
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(newGameMenuScene);
                primary.show();

            }
        });

        // brings user back to the main menu after hitting the main menu button
        backToMainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reset();
                primary.setScene(mainMenuScene);
                primary.show();
            }
        });

        // has a button that appears after a winner is found, button resets the gamestate and brings the user back to the main menu
        winScreenBackToMainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reset();
                primary.setScene(mainMenuScene);
                primary.show();
            }
        });


        // button to navigate from ai screen to the main menu
        aiToMainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(mainMenuScene);
                primary.show();
            }
        });

        // button that navigates from main manue to ai menu screen (bot difficulty etc)
        aiGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(aiMenuScene);
                primary.show();
            }
        });

        // try catch to start a new game, try: start new game catch:invalid paramater passed
        // catches invalid inputs for number of players and starting chips
        startNewGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int numberOfPlayers = Integer.parseInt(playerInput.getText());
                    if (numberOfPlayers <= 1) throw new Exception();
                    int startingChips = Integer.parseInt(chipInput.getText());
                    for (int i = 1; i <= numberOfPlayers; i++) {
                        playerList.add(new Player("" + i, startingChips));
                    }
                    deck.deal(playerList, tableCards);
                    showTableCards(tableCardsBox);
                    showTableCards(stateOfGameDisplayTableCardsBox);
                    showMultiplePlayerCards();
                    leftOfDealer = playerList.get(0);
                    currentPlayer = leftOfDealer;
                    lastPlayerToRaise = currentPlayer;
                    nextPlayer = playerList.get(1);
                    playerStackLabel.setText("Player: " + currentPlayer.getName() + "\nYour Stack: " + currentPlayer.getChipCount() + "\nPot Investment: " + currentPlayer.potInvestment);
                    showPlayerCards(currentPlayer);
                    primary.setScene(inGameScene);
                    primary.show();
                } catch (Exception E) {
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                }
            }
        });


        // button for passing player control and ending your turn
        nextTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(inGameScene);
                primary.show();
            }
        });

        //button for seeing other players chip count and folded states.
        viewStateOfGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(stateOfGameDisplayScene);
                primary.show();
            }
        });

        // exits the viewStateOfGame display screen
        stateOfGameDisplayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(inGameScene);
                primary.show();
            }
        });


        newRoundButton.setOnAction(new EventHandler<ActionEvent>() {
            //This essentially sets up the new round
            @Override
            public void handle(ActionEvent event) {
                pot = 0;
                //deal out a new round and reset the leftOfDealer position. Also removes broke players and resets attributes

                ArrayList<Player> loserList = new ArrayList<>();
                for (int i = 0; i < playerList.size(); i++) {
                    if (playerList.get(i).getChipCount() == 0) {
                        loserList.add(playerList.get(i));
                    }
                }
                playerList.removeAll(loserList);
                if (playerList.size() == 1) {
                    //show the end game screen
                    winScreenTitle.setText("Congratulations\nPlayer " + playerList.get(0).getName() + "\nYou Win!");
                    primary.setScene(winScreenScene);
                    primary.show();
                } else {

                    deck.deal(playerList, tableCards);
                    try {
                        leftOfDealer = playerList.get(playerList.indexOf(leftOfDealer) + 1);
                    } catch (Exception E) {
                        leftOfDealer = playerList.get(0);//In the case that the dealer was at the end position
                    }
                    currentPlayer = leftOfDealer;
                    lastPlayerToRaise = currentPlayer;
                    try {
                        nextPlayer = playerList.get(playerList.indexOf(currentPlayer) + 1);
                    } catch (Exception E) {
                        nextPlayer = playerList.get(0);//In the case that the current player is at the end position
                    }
                    if (currentPlayer.getName().equals("AI")) {
                        int aiBet = AI.bet(amountToCall, currentPlayer, playerList, tableCards);
                        pot += aiBet;
                        if (aiBet > 0) {
                            lastPlayerToRaise = currentPlayer;
                            amountToCall = currentPlayer.getAmountBetThisRound();
                        }
                        currentPlayer = playerList.get(0);
                        nextPlayer = playerList.get(1);
                    }
                    drawNextTurn();
                    showTableCards(tableCardsBox);
                    showTableCards(stateOfGameDisplayTableCardsBox);
                    stateOfGameDisplay.getChildren().remove(newRoundButton);
                    stateOfGameDisplay.getChildren().add(stateOfGameDisplayButton);
                    primary.setScene(privacyScreenScene);
                    primary.show();
                }
            }
        });


        //Ends the game full stop, but prompts the user to save before exiting.
        endGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int option = JOptionPane.showConfirmDialog(null, "Would you Like to Save Before Exiting?");
                if (option == 0) {
                    createSaveSlot();
                    primary.close();
                } else if (option == 1) {
                    primary.close();
                }

            }
        });

        aiStartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int startingChips = Integer.parseInt(aiChipInput.getText());

                    playerList.add(new Player("1", startingChips));
                    playerList.add(new Player("AI", startingChips));
                    deck.deal(playerList, tableCards);
                    showTableCards(tableCardsBox);
                    showTableCards(stateOfGameDisplayTableCardsBox);
                    showMultiplePlayerCards();
                    leftOfDealer = playerList.get(0);
                    currentPlayer = leftOfDealer;
                    lastPlayerToRaise = currentPlayer;
                    nextPlayer = playerList.get(1);
                    playerStackLabel.setText("Player: " + currentPlayer.getName() + "\nYour Stack: " + currentPlayer.getChipCount() + "\nPot Investment: " + currentPlayer.potInvestment);
                    showPlayerCards(currentPlayer);
                    primary.setScene(inGameScene);
                    primary.show();
                } catch (Exception E) {
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                }
            }
        });


        // button for a "check" dont raise additional chips and end your turn
        check.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (lastPlayerToRaise.isFolded) {//this is for the left of dealer folding, and everyone else checking
                    lastPlayerToRaise = currentPlayer;
                }
                if (amountToCall == currentPlayer.getAmountBetThisRound()) {
                    if (lastPlayerToRaise == nextPlayer) {
                        nextRound();
                    } else {
                        nextTurn();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Can't Check Here, Pick Another Option");
                }
            }
        });

        // button that changes your folded state to true, removing you from the hand till the next round
        fold.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentPlayer.getChipCount() > 0) {
                    currentPlayer.isFolded = true;
                    boolean everyoneElseCalled = true;
                    int countOfFolded = 0;
                    for (Player player : playerList) {
                        if (player.isFolded) {
                            countOfFolded++;
                        } else if (player.getAmountBetThisRound() < amountToCall) {
                            everyoneElseCalled = false;
                        }
                    }
                    //in case someone has just won the hand
                    if (playerList.size() - countOfFolded == 1) {
                        round = 4;
                        nextRound();
                    } else if (nextPlayer == lastPlayerToRaise && everyoneElseCalled) {
                        //in case everyone has called
                        nextRound();
                    } else {
                        nextTurn();
                    }
                } else {//check if the current player is all in
                    JOptionPane.showMessageDialog(null, "Can't Fold, You're All In");
                }
            }
        });


        // button that has a pop up window for the user to input desired raise amount, wrapped in a try catch to ensure amount entered is valid
        raise.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int raiseAmount = 0;
                boolean validInputs = false;
                try {
                    String input = JOptionPane.showInputDialog("How Much Would You Like to Raise?\n Amount to Call: " + (amountToCall - currentPlayer.getAmountBetThisRound()));
                    if (input == null) {
                        return;
                    }
                    raiseAmount = Integer.parseInt(input);
                    if (raiseAmount < 0) throw new Exception();
                    else validInputs = true;
                } catch (Exception E) {
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                }
                int sizeOfBet = raiseAmount + amountToCall - currentPlayer.getAmountBetThisRound();
                if (validInputs) {
                    if ((currentPlayer.getChipCount() - sizeOfBet) >= 0) {
                        currentPlayer.setAmountBetThisRound(currentPlayer.getAmountBetThisRound() + sizeOfBet);
                        currentPlayer.potInvestment += sizeOfBet;//for display
                        currentPlayer.removeChips(sizeOfBet);
                        pot += sizeOfBet;
                        amountToCall += raiseAmount;
                        lastPlayerToRaise = currentPlayer;
                        nextTurn();
                    } else {
                        JOptionPane.showMessageDialog(null, "You Don't Have Enough Chips");
                    }
                }

            }
        });


        // button that matches the highest raise currently on the table
        call.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int call = amountToCall - currentPlayer.getAmountBetThisRound();
                if (call <= currentPlayer.getChipCount()) {
                    currentPlayer.setAmountBetThisRound(currentPlayer.getAmountBetThisRound() + call);
                    currentPlayer.potInvestment += call;//for display
                    pot += call;
                    currentPlayer.removeChips(call);
                } else {
                    //Special case of going all in
                    currentPlayer.setAmountBetThisRound(currentPlayer.getAmountBetThisRound() + currentPlayer.getChipCount());
                    currentPlayer.potInvestment += currentPlayer.getAmountBetThisRound();
                    pot += currentPlayer.getChipCount();
                    currentPlayer.removeChips(currentPlayer.getChipCount());
                }
                if (lastPlayerToRaise == nextPlayer) {
                    nextRound();
                } else {
                    nextTurn();
                }
            }
        });

    }

    //End of Event Handlers

    //Start of GameState methods

    private void initialize() {
        //Configures GUI
        mainMenuRoot.setStyle("-fx-background-color: #228B22;");
        mainMenuTitle.setFill(Color.WHITE);
        mainMenuTitle.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        mainMenuTitle.setTextAlignment(TextAlignment.CENTER);
        mainMenu.getChildren().addAll(mainMenuTitle, newGameButton, aiGameButton, loadGameButton, settingsButton, endGameButton);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenuRoot.getChildren().add(mainMenu);
        //###############end of main menu###############
        newGameMenuRoot.setStyle("-fx-background-color: #228B22;");
        //title
        newGameMenuTitle.setFill(Color.WHITE);
        newGameMenuTitle.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        newGameMenuTitle.setTextAlignment(TextAlignment.CENTER);
        //labels and text fields
        chipInputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        chipInputLabel.setFill(Color.WHITE);
        playerInputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        playerInputLabel.setFill(Color.WHITE);
        playerInput.setPrefWidth(200);
        playerInput.setMaxWidth(200);
        playerInput.setMinWidth(200);
        chipInput.setPrefWidth(200);
        chipInput.setMaxWidth(200);
        chipInput.setMinWidth(200);
        //finalize
        newGameMenu.setAlignment(Pos.CENTER);
        newGameMenu.getChildren().addAll(newGameMenuTitle, playerInputLabel, playerInput, chipInputLabel, chipInput, startNewGameButton, backToMainButton);
        newGameMenuRoot.getChildren().add(newGameMenu);
        //###############end of new game menu################
        aiMenuRoot.setStyle("-fx-background-color: #228B22;");
        aiMenuTitle.setFill(Color.WHITE);
        aiMenuTitle.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        aiMenuTitle.setTextAlignment(TextAlignment.CENTER);
        aiChipInputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        aiChipInputLabel.setFill(Color.WHITE);
        aiChipInputLabel.setTextAlignment(TextAlignment.CENTER);
        aiChipInput.setPrefWidth(200);
        aiChipInput.setMaxWidth(200);
        aiChipInput.setMinWidth(200);
        aiMenu.setAlignment(Pos.CENTER);
        aiMenu.getChildren().addAll(aiMenuTitle, aiChipInputLabel, aiChipInput, aiStartButton, aiToMainButton);
        aiMenuRoot.getChildren().add(aiMenu);
        //###############end of heads up menu#################
        privacyScreenRoot.setStyle("-fx-background-color: #228B22;");
        privacyScreenTitle.setFill(Color.WHITE);
        privacyScreenTitle.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        privacyScreenTitle.setTextAlignment(TextAlignment.CENTER);
        privacyScreen.setAlignment(Pos.CENTER);
        privacyScreen.getChildren().addAll(privacyScreenTitle, nextTurnButton);
        privacyScreenRoot.getChildren().add(privacyScreen);
        //###############end of inter-turn screen#################
        inGameRoot.setStyle("-fx-background-color: #228B22;");
        inGameInterface.setAlignment(Pos.CENTER);
        buttonOptionsBox.getChildren().addAll(raise, call, check, fold, viewStateOfGameButton, endGameButton);
        potLabel.setFill(Color.WHITE);
        potLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        playerStackLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        playerStackLabel.setFill(Color.WHITE);
        playerInfoBox.getChildren().addAll(playerStackLabel, playerCardsBox);

        tableCardsBox.setAlignment(Pos.CENTER);
        buttonOptionsBox.setAlignment(Pos.CENTER);
        inGameInterface.getChildren().addAll(potLabel, tableCardsBox, buttonOptionsBox, playerInfoBox);

        inGameRoot.getChildren().add(inGameInterface);
        //###############end of in game interface###############
        stateOfGameDisplayRoot.setStyle("-fx-background-color: #228B22;");
        stateOfGameDisplayPotLabel.setFill(Color.WHITE);
        stateOfGameDisplayPotLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        stateOfGameDisplayTableCardsBox.setAlignment(Pos.CENTER);
        stateOfGameDisplay.setAlignment(Pos.CENTER);
        stateOfGameDisplay.getChildren().addAll(stateOfGameDisplayPotLabel, stateOfGameDisplayTableCardsBox, multiplePlayerInfoBox, stateOfGameDisplayButton);
        stateOfGameDisplayRoot.getChildren().add(stateOfGameDisplay);
        //###############end of state of game display################
        winScreenRoot.setStyle("-fx-background-color: #228B22;");
        winScreenTitle.setFill(Color.WHITE);
        winScreenTitle.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        winScreenTitle.setTextAlignment(TextAlignment.CENTER);
        winScreen.setAlignment(Pos.CENTER);
        winScreen.getChildren().addAll(winScreenTitle, winScreenBackToMainButton);
        winScreenRoot.getChildren().add(winScreen);
        //###############end of win screen################
    }

    private void showMultiplePlayerCards() {
        multiplePlayerInfoBox.getChildren().clear();
        for (Player player : playerList) {
            if (player != currentPlayer && !player.isFolded) {
                VBox thisPlayerInfo = new VBox();
                Text thisPlayerStackLabel = new Text("Player: " + player.getName() + "\nStack: " + player.getChipCount() + "\nPot Investment: " + player.potInvestment);
                thisPlayerStackLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                thisPlayerStackLabel.setFill(Color.WHITE);
                HBox imageBox = new HBox();
                ImageView imageOne = new ImageView(new Image("./CardFolder/blue_back.png"));
                ImageView imageTwo = new ImageView(new Image("./CardFolder/blue_back.png"));
                imageOne.setPreserveRatio(true);
                imageOne.setFitHeight(100);
                imageOne.setFitWidth(100);
                imageTwo.setPreserveRatio(true);
                imageTwo.setFitHeight(100);
                imageTwo.setFitWidth(100);
                imageBox.getChildren().addAll(imageOne, imageTwo);
                thisPlayerInfo.getChildren().addAll(thisPlayerStackLabel, imageBox);
                multiplePlayerInfoBox.getChildren().add(thisPlayerInfo);
            }
        }
    }

    private void showTableCards(HBox cardBox) {
        cardBox.getChildren().clear();
        Image cardFront;
        Image cardBack = new Image("./CardFolder/blue_back.png");
        int numberToShow = 0;
        if (round > 0) numberToShow = round + 2;
        for (Card c : tableCards) {
            ImageView imageView = new ImageView();
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            if (tableCards.indexOf(c) + 1 <= numberToShow) {
                cardFront = new Image(c.toString());
                imageView.setImage(cardFront);
                cardBox.getChildren().add(imageView);

            } else {
                imageView.setImage(cardBack);
                cardBox.getChildren().add(imageView);
            }
        }
    }

    private void showPlayerCards(Player player) {
        playerCardsBox.getChildren().clear();
        ImageView firstCard = new ImageView();
        firstCard.setPreserveRatio(true);
        firstCard.setFitHeight(150);
        firstCard.setFitWidth(150);
        ImageView secondCard = new ImageView();
        secondCard.setPreserveRatio(true);
        secondCard.setFitHeight(150);
        secondCard.setFitWidth(150);
        Image imageOne = new Image(player.getHand().get(0).toString());
        firstCard.setImage(imageOne);
        playerCardsBox.getChildren().add(firstCard);
        Image imageTwo = new Image(player.getHand().get(1).toString());
        secondCard.setImage(imageTwo);
        playerCardsBox.getChildren().add(secondCard);
    }
    //start of helper methods for next turn, next round

    private void showHandResult() {
        //shows the state of game screen after configuring it to display the result of the hand
        stateOfGameDisplay.getChildren().remove(stateOfGameDisplayButton);
        stateOfGameDisplay.getChildren().add(newRoundButton);
        multiplePlayerInfoBox.getChildren().clear();
        for (Player player : playerList) {
            if (!player.isFolded) {
                VBox thisPlayerInfo = new VBox();
                Text thisPlayerStackLabel = new Text("Player: " + player.getName() + "\nStack: " + player.getChipCount() + "\nPot Investment: " + player.potInvestment);
                thisPlayerStackLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                thisPlayerStackLabel.setFill(Color.WHITE);

                if (winnerList.contains(player)) {
                    thisPlayerStackLabel.setText(thisPlayerStackLabel.getText() + "\nWinner!");
                    player.addChips(pot / winnerList.size());//add chips to the winners' stacks, without displaying it yet
                }
                HBox imageBox = new HBox();
                ImageView imageOne = new ImageView(new Image(player.getHand().get(0).toString()));
                ImageView imageTwo = new ImageView(new Image(player.getHand().get(1).toString()));
                imageOne.setPreserveRatio(true);
                imageOne.setFitHeight(100);
                imageOne.setFitWidth(100);
                imageTwo.setPreserveRatio(true);
                imageTwo.setFitHeight(100);
                imageTwo.setFitWidth(100);
                imageBox.getChildren().addAll(imageOne, imageTwo);
                thisPlayerInfo.getChildren().addAll(thisPlayerStackLabel, imageBox);
                multiplePlayerInfoBox.getChildren().add(thisPlayerInfo);
            }
        }
        //reset winnerList
        winnerList.clear();
        //Display
        primary.setScene(stateOfGameDisplayScene);
        primary.show();

    }

    private void drawNextTurn() {
        //sets up visuals for the next turn, called in nextTurn, nextRound
        potLabel.setText("Current Pot: " + pot);
        stateOfGameDisplayPotLabel.setText("Current Pot: " + pot);

        //inter turn screen (blind screen for mutliplayer)
        privacyScreenTitle.setText("It's Player " + currentPlayer.getName() + "'s Turn");
        primary.setScene(privacyScreenScene);
        primary.show();

        playerStackLabel.setText("Player: " + currentPlayer.getName() + "\nYour Stack: " + currentPlayer.getChipCount() + "\nPot Investment: " + currentPlayer.potInvestment + "\n" + (amountToCall - currentPlayer.getAmountBetThisRound()) + " to Call");
        showPlayerCards(currentPlayer);
        showMultiplePlayerCards();
    }

    //end of helper methods for next turn, next round


    private void nextTurn() {
        //these loops move through the player list until the next player for this hand that isn't folded is current, and the next player after that is determined
        do {
            currentPlayer = nextPlayer;
            try {
                nextPlayer = playerList.get(playerList.indexOf(currentPlayer) + 1);
            } catch (Exception E) {
                nextPlayer = playerList.get(0);//In the case that the current player is at the end position
            }
        } while (currentPlayer.isFolded);
        while (nextPlayer.isFolded) {
            try {
                nextPlayer = playerList.get(playerList.indexOf(nextPlayer) + 1);
            } catch (Exception E) {
                nextPlayer = playerList.get(0);//In the case that the current player is at the end position
            }
        }
        //If the player is an AI automate a turn, else draw the next player's turn
        if (currentPlayer.getName().equals("AI")) {
            int aiBet = AI.bet(amountToCall, currentPlayer, playerList, tableCards);
            pot += aiBet;
            if (aiBet > 0) {//ai bets
                if (amountToCall < currentPlayer.getAmountBetThisRound()) {//ai raises
                    lastPlayerToRaise = currentPlayer;
                    amountToCall = currentPlayer.getAmountBetThisRound();
                    currentPlayer = playerList.get(0);
                    nextPlayer = playerList.get(1);
                    drawNextTurn();
                } else {//ai calls
                    nextRound();
                    return;
                }
            } else if (!currentPlayer.isFolded) {//ai checks
                nextRound();
            } else {//ai folds
                round = 4;
                nextRound();
            }
        } else
            drawNextTurn();
    }

    public void nextRound() {
        amountToCall = 0;
        for (Player player : playerList) {
            player.setAmountBetThisRound(0);
        }
        if (round < 3) {
            //start of convoluted logic to handle folded players
            if (!leftOfDealer.isFolded) {
                currentPlayer = leftOfDealer;
            } else {
                try {
                    currentPlayer = playerList.get(playerList.indexOf(leftOfDealer) + 1);
                } catch (Exception E) {
                    currentPlayer = playerList.get(0);//In the case that the dealer was at the end position
                }
                while (currentPlayer.isFolded) {
                    try {
                        currentPlayer = playerList.get(playerList.indexOf(currentPlayer) + 1);
                    } catch (Exception E) {
                        currentPlayer = playerList.get(0);//In the case that the dealer was at the end position
                    }
                }
            }
            do {
                try {
                    nextPlayer = playerList.get(playerList.indexOf(nextPlayer) + 1);
                } catch (Exception E) {
                    nextPlayer = playerList.get(0);//In the case that the current player is at the end position
                }
            } while (nextPlayer.isFolded);
            //end of it
            lastPlayerToRaise = currentPlayer;
            if (currentPlayer.getName().equals("AI")) {
                int aiBet = AI.bet(amountToCall, currentPlayer, playerList, tableCards);
                pot += aiBet;
                if (amountToCall < currentPlayer.getAmountBetThisRound()) {//ai raises
                    lastPlayerToRaise = currentPlayer;
                    amountToCall = currentPlayer.getAmountBetThisRound();
                }
                currentPlayer = playerList.get(0);
                nextPlayer = playerList.get(1);

            }
            drawNextTurn();
            round++;
            showTableCards(tableCardsBox);
            showTableCards(stateOfGameDisplayTableCardsBox);
        } else {
            //reset round, determine winners, and split up the pot
            round = 0;
            winnerList = RankHands.rankHands(playerList, tableCards);
            showHandResult();
        }
    }

    //method to reset all instance variables once a game is quit
    public void reset() {
        playerList = new ArrayList<>();

        tableCards = new ArrayList<>();

        pot = 0;

        deck = new Deck();

        round = 0;

        amountToCall = 0;

        currentPlayer = null;

        nextPlayer = null;

        lastPlayerToRaise = null;

        leftOfDealer = null;

        winnerList = new ArrayList<>();

        if (stateOfGameDisplay.getChildren().contains(newRoundButton)) {
            stateOfGameDisplay.getChildren().remove(newRoundButton);
            stateOfGameDisplay.getChildren().add(stateOfGameDisplayButton);//new
        }
    }

//End of GameState methods

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primary = primaryStage;
        initialize();
        setEventHandlers();
        primaryStage.setTitle("Texas Hold'em");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }
}


//Bug after reseting a game with an AI, started a new game and clicked look around the table, and there is no back to main button (fixed?)

//Bug raising the ai and it updates the raise, redraws the turn, no new round and no call by the ai
