import java.util.ArrayList;

public class Player implements Comparable {

    private String name;

    private ArrayList<Card> hand;

    private int chipCount = 100;

    private int amountBetThisRound = 0;//TODO reset every round?

    public int potInvestment = 0;//for display

    private double scoreThisRound;

    public boolean isFolded = false;

    /**
     * @param ns name of player
     * @param c amount of chips
     */
    public Player(String ns, int c) {
        hand = new ArrayList<>();
        name = ns;
        if (c >= 0) chipCount = c;
    }

    /**
     * @param d 
     */
    public void setScoreThisRound(double d) {
        if (d > scoreThisRound) {
            scoreThisRound = d;
        }
    }

    /**
     * @return player's score
     */
    public double getScoreThisRound() {
        return scoreThisRound;
    }

    public void newHand() {
        potInvestment = 0;
        hand.clear();
        scoreThisRound = 0;
        setAmountBetThisRound(0);
        isFolded = false;
    }

    /**
     * @return amount the player has bet
     */
    public int getAmountBetThisRound() {
        return amountBetThisRound;
    }

    /**
     * @param amount amount player wants to bet
     */
    public void setAmountBetThisRound(int amount) {
        if (amount >= 0)
            amountBetThisRound = amount;
    }

    /**
     * @return amount of chips player has
     */
    public int getChipCount() {
        return chipCount;
    }

    /**
     * @return player's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param amount amount of chips to add to player's chips
     */
    public void addChips(int amount) {
        if (amount > 0) chipCount += amount;
    }

    /**
     * @param amount amount of chips to take from player's chips
     */
    public void removeChips(int amount) {
        if (amount > 0) chipCount -= amount;
    }

    /**
     * @param c1 player's first card
     * @param c2 player's second card
     */
    public void setHand(Card c1, Card c2) {
        hand.add(c1);
        hand.add(c2);
    }

    /**
     * @return a copy of the player's hand
     */
    public ArrayList<Card> getHand() {
        ArrayList<Card> handCopy = new ArrayList<>();
        for (Card c : hand) {
            handCopy.add(new Card(c));
        }
        return handCopy;
    }

    /**
     * @param o object being coompared to
     * @return if the object is a player, compares the player's score and returns difference; otherwise returns 0
     */
    public int compareTo(Object o) {
        if (o instanceof Player) {
            Player p = (Player) o;
            return (int) ((scoreThisRound - p.getScoreThisRound()) * 1000000);
        } else return 0;
    }
}
