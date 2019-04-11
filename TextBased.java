package game;

import java.util.ArrayList;
import java.util.Scanner;

public class TextBased {

    private static Deck deck = new Deck();

    private static int round = 0;

    private static int amountToCall = 0;

    private static int pot = 0;

    private static Player currentPlayer = new Player("1", 100);

    private static Player nextPlayer = new Player("2", 100);

    private static Player leftOfDealer = currentPlayer;

    private static Player lastPlayerToRaise = leftOfDealer;

    private static ArrayList<Player> playerList = new ArrayList<>();

    private static ArrayList<Card> tableCards = new ArrayList<>();

    private static ArrayList<Player> winnerList = new ArrayList<>();

    private static void displayTableCards() {
        System.out.println("Table Cards: ");
        for (int i = 0; i < round + 3; i++)
            System.out.print(tableCards.get(i).toDisplayString() + "\t");
        System.out.println();
    }

    private static void displayPlayerCards(Player player) {
        System.out.println("\nPlayer " + player.getName() + " Info:");
        System.out.println("Stack: " + player.getChipCount());
        System.out.println("Pot Investment: " + player.getPotInvestment());
        System.out.println((amountToCall - currentPlayer.getAmountBetThisRound()) + " to call");
        System.out.println("\nCards:\n" + player.getHand().get(0).toDisplayString() + " " + player.getHand().get(1).toDisplayString());
    }

    public static void check() {
        if (amountToCall == currentPlayer.getAmountBetThisRound()) {
            if (nextPlayer == lastPlayerToRaise)
                nextRound();
            else
                nextTurn();
        } else {
            System.out.println("Can't Check");
            getMove();
        }
    }

    public static void call() {
        int call = amountToCall - currentPlayer.getAmountBetThisRound();
        if (call <= currentPlayer.getChipCount()) {
            currentPlayer.setAmountBetThisRound(currentPlayer.getAmountBetThisRound() + call);
            currentPlayer.setPotInvestment(currentPlayer.getPotInvestment() + call);//for display
            pot += call;
            currentPlayer.removeChips(call);
            if (lastPlayerToRaise == currentPlayer)
                nextTurn();
            else
                nextRound();
        } else {
            //Special case of going all in
            currentPlayer.setAmountBetThisRound(currentPlayer.getAmountBetThisRound() + currentPlayer.getChipCount());
            currentPlayer.setPotInvestment(currentPlayer.getPotInvestment() + currentPlayer.getAmountBetThisRound());
            pot += currentPlayer.getChipCount();
            currentPlayer.removeChips(currentPlayer.getChipCount());
            round = 2;
            nextRound();
        }
    }

    public static void raise() {
        System.out.println("Enter an amount to raise");
        Scanner keyInput = new Scanner(System.in);
        String raiseInput = keyInput.nextLine();
        try {
            int raiseAmount = Integer.parseInt(raiseInput);
            if (raiseAmount < 0) throw new Exception();
            int sizeOfBet = raiseAmount + amountToCall - currentPlayer.getAmountBetThisRound();
            if ((currentPlayer.getChipCount() - sizeOfBet) >= 0) {
                currentPlayer.setAmountBetThisRound(currentPlayer.getAmountBetThisRound() + sizeOfBet);
                currentPlayer.setPotInvestment(currentPlayer.getPotInvestment() + sizeOfBet);//for display
                currentPlayer.removeChips(sizeOfBet);
                pot += sizeOfBet;
                amountToCall += raiseAmount;
                lastPlayerToRaise = currentPlayer;
                nextTurn();
            } else {
                System.out.println("Not enough chips");
                getMove();
            }
        } catch (Exception e) {
            System.out.println("Invalid Entry");
            getMove();
        }
    }

    public static void fold() {
        if (currentPlayer.getChipCount() == 0) {
            System.out.println("Can't fold, all in");
            getMove();
        } else {
            currentPlayer.isFolded = true;
            round = 2;
            nextRound();
        }
    }

    public static void nextTurn() {
        Player temporaryPlayer = nextPlayer;
        nextPlayer = currentPlayer;
        currentPlayer = temporaryPlayer;

        privacyScreen();
        displayTableCards();
        displayPlayerCards(currentPlayer);
        getMove();
    }

    public static void privacyScreen() {
        for (int i = 0; i < 25; i++)
            System.out.println("###############################################");

    }

    public static void nextRound() {
        amountToCall = 0;
        for (Player player : playerList) {
            player.setAmountBetThisRound(0);
        }
        if (round < 2) {
            currentPlayer = leftOfDealer;
            if (nextPlayer == currentPlayer) {
                try {
                    nextPlayer = playerList.get(playerList.indexOf(currentPlayer) + 1);
                } catch (Exception e) {
                    nextPlayer = playerList.get(0);
                }
            }
            lastPlayerToRaise = currentPlayer;
            round++;
            privacyScreen();
            displayTableCards();
            displayPlayerCards(currentPlayer);
            getMove();

        } else {
            //determine the winners, show the outcome, reset the round, and split up the pot
            winnerList = RankHands.rankHands(playerList, tableCards);
            for (Player p : winnerList) {
                p.addChips(pot / winnerList.size());
            }
            showResult();
            //set up the next round
            round = 0;
            pot = 0;
            winnerList.clear();
            deck.deal(playerList, tableCards);
            if (leftOfDealer == currentPlayer)
                leftOfDealer = nextPlayer;
            else
                leftOfDealer = currentPlayer;
            currentPlayer = leftOfDealer;
            lastPlayerToRaise = leftOfDealer;
            try {
                nextPlayer = playerList.get(playerList.indexOf(leftOfDealer) + 1);
            } catch (Exception e) {
                nextPlayer = playerList.get(0);
            }
            privacyScreen();
            displayTableCards();
            displayPlayerCards(currentPlayer);
            getMove();
        }
    }

    public static void showResult() {
        privacyScreen();
        System.out.println("Result of this Hand:");
        displayTableCards();
        for (Player player : playerList) {
            if (!player.isFolded)
                System.out.println("\nPlayer " + player.getName() + " Cards:\n" + player.getHand().get(0).toDisplayString() + " " + player.getHand().get(1).toDisplayString());
        }
        if (winnerList.size() == 1) {
            System.out.println("Player " + winnerList.get(0).getName() + " wins " + pot / winnerList.size() + "!");
        } else
            System.out.println("It's a draw!");
        System.out.println("--enter anything to proceed--");
        Scanner proceed = new Scanner(System.in);
        proceed.next();
        //check if someone won
        if (playerList.get(0).getChipCount() == 0) {
            System.out.println("Player 2 Wins!");
            System.exit(0);
        } else if (playerList.get(1).getChipCount() == 0) {
            System.out.println("Player 1 Wins!");
            System.exit(0);
        }
    }

    private static void getMove() {
        System.out.println("It's your turn, choose an option");
        System.out.println("1: fold 2: call 3: raise 4: check");
        Scanner keyInput = new Scanner(System.in);
        String choice = keyInput.nextLine();
        switch (choice) {
            case "1":
                fold();
                break;
            case "2":
                call();
                break;
            case "3":
                raise();
                break;
            case "4":
                check();
                break;
            default:
                System.out.println("Invalid Input");
                getMove();
        }
    }

    private static void initialize() {
        playerList.add(currentPlayer);
        playerList.add(nextPlayer);
        deck.deal(playerList, tableCards);
        System.out.println("     Welcome to Poker\n --enter anything to begin--");
        Scanner startGame = new Scanner(System.in);
        startGame.next();
    }

    public static void main(String[] args) {
        initialize();
        displayTableCards();
        displayPlayerCards(currentPlayer);
        //getMove starts off a sequence that will continue until the game is won
        getMove();
    }

}
