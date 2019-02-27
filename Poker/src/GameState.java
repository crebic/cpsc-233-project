import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameState {
    //start of instance variables for the game

    private ArrayList<Player> playerList = new ArrayList<>();

    private ArrayList<Card> tableCards = new ArrayList<>();

    private int pot;


    //end of instance variables for the game

    //Start of GameState methods

    public void getInputs() {//TODO add any other inputs, change to getInputs
        int numberOfPlayers = 0;
        int input;
        int startingChips = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Poker");
        while (numberOfPlayers == 0) {
            while (true) {
                System.out.println("\nPlease enter an number of players from 2 - 10");
                if (sc.hasNextInt()) {//
                    break;//
                }//
                sc.nextLine();
            }
            input = sc.nextInt();
            if (input < 11 && input > 1) {
                numberOfPlayers = input;
            } else {
                sc.nextLine();
            }
        }
        while (startingChips <= 0) {
            System.out.println("\nEnter the amount of starting chips");
            if (sc.hasNextInt()) {
                startingChips = sc.nextInt();
            } else {
                sc.next();
            }
        }
        sc.nextLine();
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("\nPlease enter the name for player " + (i + 1));
            if (sc.hasNext()) {
                playerList.add(new Player(sc.nextLine(), startingChips));
            }
        }
    }

    //helper methods for winning hands

    private double highCard(Player p) {
        double highCard = 0;
        for (Card c : p.getHand()) {
            if (c.getValue() > highCard) {
                highCard = c.getValue();
            }
        }
        for (Card c : tableCards) {
            if (c.getValue() > highCard) {
                highCard = c.getValue();
            }
        }
        return highCard / 100.0;
    }

    private double pair(Player p) {
        double pairScore = 0, pairValue = 0, kicker = 0;

        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(p.getHand());
        cards.addAll(tableCards);

        for (Card c : cards) {
            for (Card a : cards) {
                if (a.getValue() == c.getValue() && !a.getFace().equals(c.getFace()) && (1 + a.getValue() / 100.0 > pairScore)) {
                    pairValue = a.getValue();
                    pairScore = 1 + pairValue / 100.0;
                }
            }
        }
        //kicker
        for (Card c : cards) {
            if (c.getValue() > kicker && c.getValue() != pairValue) {
                kicker = c.getValue();
            }
        }
        pairScore += kicker / 1000.0;
        return pairScore;
    }

    private double twoPair(Player p) {
        //TODO should compare top ranked pa
        if (pair(p) > 0) {
            double pairOneValue = (int) ((pair(p) - 1) * 100);
            double score, kicker = 0;
            ArrayList<Card> cards = new ArrayList<>(), cardsWithoutPair = new ArrayList<>();
            cards.addAll(p.getHand());
            cards.addAll(tableCards);
            for (Card c : cards) {
                if (c.getValue() != pairOneValue) {
                    cardsWithoutPair.add(c);
                }
            }
            double pairTwoValue = 0;
            for (Card c : cardsWithoutPair) {
                for (Card a : cardsWithoutPair) {
                    if (a.getValue() == c.getValue() && !a.getFace().equals(c.getFace()) && a.getValue() > pairTwoValue) {
                        pairTwoValue = a.getValue();
                    }
                }
            }
            score = 2 + pairOneValue / 100.0 + pairTwoValue / 1000.0;
            //kicker
            for (Card c : cards) {
                if (c.getValue() > kicker && c.getValue() != pairOneValue && c.getValue() != pairTwoValue) {
                    kicker = c.getValue();
                }
            }
            score += kicker / 10000.0;
            return score;
        }
        return 0;
    }

    private double triple(Player p) {
        double tripScore = 0, tripValue = 0, kicker = 0;
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(p.getHand());
        cards.addAll(tableCards);
        for (Card a : cards) {
            for (Card b : cards) {
                for (Card c : cards) {
                    if (a.getValue() == b.getValue() && a.getValue() == c.getValue() && !a.getFace().equals(b.getFace()) && !c.getFace().equals(b.getFace())) {
                        if (a.getValue() > tripValue) {
                            tripValue = a.getValue();//TODO make sure it's the highest rated triple
                            tripScore = 3 + tripValue / 100.0;
                        }
                    }
                }
            }
        }
        //kicker
        for (Card c : cards) {
            if (c.getValue() > kicker && c.getValue() != tripValue) {
                kicker = c.getValue();
            }
        }
        tripScore += kicker / 1000.0;
        return tripScore;
    }

    private double straight(Player p) {
        return 0;
    }

    private double flush(Player p) {
        return 0;
    }

    private double fullHouse(Player p) {
        return 0;
    }

    private double quad(Player p) {
        return 0;
    }

    private double straightFlush(Player p) {
        return 0;
    }
    //method for winning hand logic

    public void rankHands() {
        //TODO iterate through all players and run all helper methods, setting there scores appropriately
        for (Player p : playerList) {
            p.setScoreThisRound(highCard(p));
            p.setScoreThisRound(pair(p));
            p.setScoreThisRound(twoPair(p));
            p.setScoreThisRound(triple(p));
            //straight
            //flush
            //full house
            //quads
            //straight flush

        }
    }


//End of GameState methods


    public class Card {
        private int value;
        private String face;

        public Card(int v, String f) {
            value = v;
            face = f;
        }

        public Card(Card c) {
            value = c.getValue();
            face = c.getFace();
        }

        public int getValue() {
            return value;
        }

        public String getFace() {
            return face;
        }
    }


    public class Deck {

        private ArrayList<Card> cards = new ArrayList<>();

        public void resetDeck() {
            String suit = "";
            for (int i = 0; i <= 4; i++) {
                switch (i) {
                    case 1:
                        suit = "hearts";
                        break;
                    case 2:
                        suit = "diamonds";
                        break;
                    case 3:
                        suit = "spades";
                        break;
                    case 4:
                        suit = "clubs";
                }
                for (int j = 1; j <= 13; j++) {
                    cards.add(new Card(j, suit));
                }
            }
        }

        public void deal(ArrayList<Player> players, ArrayList<Card> table) {
            Random r = new Random();
            table.clear();
            resetDeck();
            Card c1, c2;
            for (int i = 0; i < 5; i++) {
                c1 = cards.get(r.nextInt(51) + 1);
                table.add(c1);
                cards.remove(c1);
            }
            for (Player p : players) {
                p.newRound();
                for (int j = 0; j < 2; j++) {
                    c1 = cards.get(r.nextInt(51) + 1);
                    cards.remove(c1);
                    c2 = cards.get(r.nextInt(51) + 1);
                    cards.remove(c2);
                    p.setHand(c1, c2);
                }
            }
        }

    }


    public class Player {

        private String name;

        private ArrayList<Card> hand;

        private int chipCount;

        private double scoreThisRound;

        public Player(String ns, int c) {
            hand = new ArrayList<>();
            name = ns;
            if (c >= 0) chipCount = c;
        }

        public void setScoreThisRound(double d) {
            if (d > scoreThisRound) {
                scoreThisRound = d;
            }
        }

        public double getScoreThisRound() {
            return scoreThisRound;
        }

        public void newRound() {
            hand.clear();
            scoreThisRound = 0;
        }

        public int getChipCount() {
            return chipCount;
        }

        public String getName() {
            return name;
        }

        public void addChips(int amount) {
            if (amount > 0) chipCount += amount;
        }

        public void removeChips(int amount) {
            if (amount > 0) chipCount -= amount;
        }

        public void setHand(Card c1, Card c2) {
            hand.add(c1);
            hand.add(c2);
        }

        public ArrayList<Card> getHand() {
            return hand;
        }
    }

    public static void main(String[] args) {
        GameState table = new GameState();
        table.getInputs();
    }
}
