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

//import javax.swing.JApplet;

//TODO shortlist 1: fix rankhands bug 3: implement input getting in raise method 4: JOption Panes for error messages 5: Ask if they want to save when they click the X

//TODO reminders: make all buttons appear the same width?, make blinds, show a dealer

public class GameState extends Application {
    //start of instance variables for the game

    private static ArrayList<Player> playerList = new ArrayList<>();

    private static ArrayList<Card> tableCards = new ArrayList<>();

    private static int pot = 0;

    private Deck deck = new Deck();

    private int round = 0;

    private int amountToCall = 0;//represents a cumulative amount

    private Player currentPlayer;

    private Player nextPlayer;

    private Player lastPlayerToRaise;

    private Player leftOfDealer;

    private Stage primary;//workaround to set scene within methods

    private ArrayList<Player> winnerList = new ArrayList<>();

    //Start of GUI rendering parts

    //  Start of Main menu
    private StackPane mainMenuRoot = new StackPane();//equivalent to root
    private Scene mainMenuScene = new Scene(mainMenuRoot, 800, 600);
    private Button newGameButton = new Button("Start New Game");
    private Button aiGameButton = new Button("Play vs. CPU");
    private Button loadGameButton = new Button("Load Game");//TODO implement this button
    private Button settingsButton = new Button("Settings");//TODO implement this button
    private Text mainMenuTitle = new Text("Welcome to \nTexas Hold 'em!");

    private VBox mainMenu = new VBox(30);

    //Start of new game menu
    private StackPane newGameMenuRoot = new StackPane();
    private Scene newGameMenuScene = new Scene(newGameMenuRoot, 800, 600);
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
    private Scene aiMenuScene = new Scene(aiMenuRoot, 800, 600);
    private Text aiMenuTitle = new Text("Heads Up \nvs.\nCPU ");
    private Button aiEasyButton = new Button("Easy");//TODO implement button
    private Button aiMediumButton = new Button("Medium");//TODO implement button
    private Button aiHardButton = new Button("Hard");//tODO implement button
    private Button aiToMainButton = new Button("Back to Main Menu");

    private VBox aiMenu = new VBox(35);

    //start of inter - player Screen
    private StackPane privacyScreenRoot = new StackPane();
    private Scene privacyScreenScene = new Scene(privacyScreenRoot, 800, 600);
    private Text privacyScreenTitle = new Text("It's Player 1's Turn");
    private Button nextTurnButton = new Button("Begin Turn");

    private VBox privacyScreen = new VBox(200);

    //start of in game interface
    private StackPane inGameRoot = new StackPane();

    private Scene inGameScene = new Scene(inGameRoot, 800, 600);

    private VBox inGameInterface = new VBox(30);//equivalent to root 2

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
    private Scene stateOfGameDisplayScene = new Scene(stateOfGameDisplayRoot, 800, 600);
    private VBox stateOfGameDisplay = new VBox(30);
    private HBox stateOfGameDisplayTableCardsBox = new HBox();
    private Text stateOfGameDisplayPotLabel = new Text("Current Pot " + pot);
    private HBox multiplePlayerInfoBox = new HBox(25);
    private Button stateOfGameDisplayButton = new Button("OK");
    private Button newRoundButton = new Button("Next Round");

    //start of end game screen (displays winner)
    private StackPane winScreenRoot = new StackPane();
    private Scene winScreenScene = new Scene(winScreenRoot, 800, 600);
    private VBox winScreen = new VBox(200);
    private Text winScreenTitle = new Text();
    private Button winScreenBackToMainButton = new Button("Back to Main Menu");

    //end of instance variables for the game

    //Start of Event Handlers

    public void setEventHandlers() {//TODO replace syntax with lambda expressions
        //could replace passed primaryStage with a reference to primary

        primary.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");//TODO joption pane for would you like to save
            }
        });

        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(newGameMenuScene);
                primary.show();

            }
        });

        backToMainButton.setOnAction(new EventHandler<ActionEvent>() {//TODO this button needs to reset everything
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(mainMenuScene);
                primary.show();
            }
        });

        winScreenBackToMainButton.setOnAction(new EventHandler<ActionEvent>() {//TODO this button needs to reset everything
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(mainMenuScene);
                primary.show();
            }
        });

        aiToMainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(mainMenuScene);
                primary.show();
            }
        });

        aiGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(aiMenuScene);
                primary.show();
            }
        });

        startNewGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int numberOfPlayers = Integer.parseInt(playerInput.getText());
                    int startingChips = Integer.parseInt(chipInput.getText());
                    for (int i = 1; i <= numberOfPlayers; i++) {
                        playerList.add(new Player("" + i, startingChips));
                    }
                    deck.deal(playerList, tableCards);
                    showTableCards(tableCardsBox);
                    showTableCards(stateOfGameDisplayTableCardsBox);//TODO 1
                    showMultiplePlayerCards();//new
                    leftOfDealer = playerList.get(0);
                    currentPlayer = leftOfDealer;
                    lastPlayerToRaise = currentPlayer;
                    nextPlayer = playerList.get(1);
                    playerStackLabel.setText("Player: " + currentPlayer.getName() + "\nYour Stack: " + currentPlayer.getChipCount() + "\nPot Investment: " + currentPlayer.potInvestment);
                    showPlayerCards(currentPlayer);
                    primary.setScene(inGameScene);
                    primary.show();
                } catch (Exception E) {
                    //TODO multiple catches depending on the exception thrown, and corresponding feedback??
                    //use joptionpane
                    //possible exception is a one player game
                    //wierd behaviour results from awkward numplayers inputs or going to the main menu and back
                }
            }
        });

        nextTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(inGameScene);
                primary.show();
            }
        });

        viewStateOfGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setScene(stateOfGameDisplayScene);
                primary.show();
            }
        });

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
                    drawNextTurn();
                    showTableCards(tableCardsBox);
                    showTableCards(stateOfGameDisplayTableCardsBox);//TODO 1
                    stateOfGameDisplay.getChildren().remove(newRoundButton);
                    stateOfGameDisplay.getChildren().add(stateOfGameDisplayButton);
                    primary.setScene(privacyScreenScene);
                    primary.show();
                }
            }
        });

        endGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO joption pane asking if they'd like to save first
            }
        });

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
                    //TODO let them know they can't check?
                }
            }
        });

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
                } else {
                    //TODO don't let them fold because they're all in (Joption pane)
                }
            }
        });

        raise.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int raiseAmount = 3;//TODO 2 replace this with a JOptionPane to get input amount to raise
                int sizeOfBet = raiseAmount + amountToCall - currentPlayer.getAmountBetThisRound();
                if ((currentPlayer.getChipCount() - sizeOfBet) >= 0) {
                    currentPlayer.setAmountBetThisRound(currentPlayer.getAmountBetThisRound() + sizeOfBet);
                    currentPlayer.potInvestment += sizeOfBet;//for display
                    currentPlayer.removeChips(sizeOfBet);
                    pot += sizeOfBet;
                    amountToCall += raiseAmount;
                    lastPlayerToRaise = currentPlayer;
                    nextTurn();
                } else {
                    //TODO let them know they don't have enough chips
                }

            }
        });

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
        /*newGameButton.setLayoutX(380);TODO are these useful on other machines?
        newGameButton.setLayoutY(5000);
        aiGameButton.setLayoutX(380);
        aiGameButton.setLayoutY(500);
        loadGameButton.setLayoutX(380);
        loadGameButton.setLayoutY(500);
        settingsButton.setLayoutX(380);
        settingsButton.setLayoutY(500);
        endGameButton.setLayoutX(380);
        endGameButton.setLayoutY(500);*/
        mainMenuTitle.setFill(Color.WHITE);
        mainMenuTitle.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        mainMenuTitle.setTextAlignment(TextAlignment.CENTER);
        mainMenu.getChildren().addAll(mainMenuTitle, newGameButton, aiGameButton, loadGameButton, settingsButton, endGameButton);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenuRoot.getChildren().add(mainMenu);
        //###############end of main menu###############
        newGameMenuRoot.setStyle("-fx-background-color: #228B22;");
        //buttons
        /*startNewGameButton.setLayoutX(380); TODO are these useful on other machines?
        startNewGameButton.setLayoutY(500);
        backToMainButton.setLayoutX(380);
        backToMainButton.setLayoutY(500);*/
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
        //TODO set up button sizes if necessary
        aiMenu.setAlignment(Pos.CENTER);
        aiMenu.getChildren().addAll(aiMenuTitle, aiEasyButton, aiMediumButton, aiHardButton, aiToMainButton);
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
                ImageView imageOne = new ImageView(new Image("blue_back.png"));
                ImageView imageTwo = new ImageView(new Image("blue_back.png"));
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

    /*private void showStateOfGameDisplayTableCards() {//TODO low priority, condense this and showTableCards into a single method which takes an hbox
        stateOfGameDisplayTableCardsBox.getChildren().clear();
        Image cardFront;
        Image cardBack = new Image("blue_back.png");
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
                stateOfGameDisplayTableCardsBox.getChildren().add(imageView);

            } else {
                imageView.setImage(cardBack);
                stateOfGameDisplayTableCardsBox.getChildren().add(imageView);
            }
        }
    }*/

    /*private void showTableCards() {
        tableCardsBox.getChildren().clear();
        Image cardFront;
        Image cardBack = new Image("blue_back.png");
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
                tableCardsBox.getChildren().add(imageView);

            } else {
                imageView.setImage(cardBack);
                tableCardsBox.getChildren().add(imageView);
            }
        }
    }*/

    private void showTableCards(HBox cardBox) {
        cardBox.getChildren().clear();
        Image cardFront;
        Image cardBack = new Image("blue_back.png");
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

        //inter turn screen
        privacyScreenTitle.setText("It's Player " + currentPlayer.getName() + "'s Turn");
        primary.setScene(privacyScreenScene);
        primary.show();
        //
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
                try {//TODO replace these try-catch statements with a seperate method, the method returning the object first
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
            drawNextTurn();
            round++;
            showTableCards(tableCardsBox);
            showTableCards(stateOfGameDisplayTableCardsBox);//TODO 1
        } else {
            //reset round, determine winners, and split up the pot
            round = 0;
            winnerList = RankHands.rankHands(playerList, tableCards);
            showHandResult();
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

//Bug when one player has less chips then they need to call, wierd patterns develop (fixed?)

//Bug when a player is eliminated strange patterns develop, privacy screen label is off (fixed?)

//Bug one time in a two player game managed to get it to display "6 to call" shouldn't be possible

//Bug starting with one player the privacy screen says it's player 2's turn (need to make a win condition to eliminate the scenario)
