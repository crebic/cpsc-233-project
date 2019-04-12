package game;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * A object Player that contains all the relavent information about a player in the game
 */
public class Player implements Comparable, Serializable {

    /**
     * A unique ID that is used for serializing the class.
     */
    private static final long serialVersionUID = 2L;
    
    /**
     * The name of the player in play.
     * Currently set to default ("Player" followed by a number).
     */
    private String name;

    /**
     * An arraylist of the 2 cards the player has.
     */
    private ArrayList<Card> hand;

    /**
     * The amount of chips the player has.
     */
    private int chipCount = 100;

    /**
     * The amount the player has bet during the current round.
     * This is reset every betting round.
     */
    private int amountBetThisRound = 0;

    /**
     * The amount the player has contributed to the pot.
     * This is reset at the start of a hand.
     */
    private int potInvestment = 0;

    /**
     * The value of the player's hand.
     */
    private double scoreThisRound;

    /**
     * Whether the player has folded.
     * If true, they are removed from the current betting round and all bets made are lost.
     */
    protected boolean isFolded = false;

    /**
     * @param ns the player's name.
     * @param c the amount of chips the player has.
     */
    public Player(String ns, int c) {
        hand = new ArrayList<>();
        name = ns;
        if (c >= 0) chipCount = c;
    }
    
    /**
     * @param d the value of player's hand to set.
     */
    public void setScoreThisRound(double d) {
        if (d > scoreThisRound) {
            scoreThisRound = d;
        }
    }

    /**
     * @return the value of the player's hand.
     */
    public double getScoreThisRound() {
        return scoreThisRound;
    }

    /**
     * Resets the hand and all the player's hand values for the round.
     */
    public void newHand() {
        potInvestment = 0;
        hand.clear();
        scoreThisRound = 0;
        setAmountBetThisRound(0);
        isFolded = false;
    }

    /**
     * @return the amount the player has bet during the current round.
     */
    public int getAmountBetThisRound() {
        return amountBetThisRound;
    }

    /**
     * @param amount the amount the player has bet this round to set.
     */
    public void setAmountBetThisRound(int amount) {
        if (amount >= 0)
            amountBetThisRound = amount;
    }

    /**
     * @return the amount of chips the player has.
     */
    public int getChipCount() {
        return chipCount;
    }

    /**
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the amound the player has invested to the overall pot.
     */
    public int getPotInvestment(){
        return potInvestment;
    }
    
    /**
     * @param newPotInvestment the amount the player has invested to the pot to set.
     */
    public void setPotInvestment(int newPotInvestment){
        potInvestment = newPotInvestment;
    }

    /**
     * @param amount the amount of chips to add to the player's chips.
     */
    public void addChips(int amount) {
        if (amount > 0) chipCount += amount;
    }

    /**
     * @param amount the amount of chips to remove from the player's chips.
     */
    public void removeChips(int amount) {
        if (amount > 0) chipCount -= amount;
    }

    /**
     * @param c1 the player's first card to set.
     * @param c2 the player's second card to set.
     */
    public void setHand(Card c1, Card c2) {
        hand.add(c1);
        hand.add(c2);
    }
    
    /**
     * @return an arraylist of the cards in the player's hand.
     */
    public ArrayList<Card> getHand() {
        ArrayList<Card> handCopy = new ArrayList<>();
        for (Card c : hand) {
            handCopy.add(new Card(c));
        }
        return handCopy;
    }
    
    /**
     * @param o the object being compared to.
     * If the object is not of type Player, @return 0.
     * Otherwise, @return the difference between two players' scores.
     */
    public int compareTo(Object o) {
        if (o instanceof Player) {
            Player p = (Player) o;
            return (int) ((scoreThisRound - p.getScoreThisRound()) * 1000000);
        } else return 0;
    }
}
