package game;

import java.util.ArrayList;
import java.io.Serializable;

public class Player implements Comparable, Serializable {

    private static final long serialVersionUID = 2L;
    
    private String name;

    private ArrayList<Card> hand;

    private int chipCount = 100;

    private int amountBetThisRound = 0;//TODO reset every round?

    private int potInvestment = 0;//for display

    private double scoreThisRound;

    protected boolean isFolded = false;

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

    public void newHand() {
        //when there is a new hand, reset the hand and all player values for the round
        potInvestment = 0;
        hand.clear();
        scoreThisRound = 0;
        setAmountBetThisRound(0);
        isFolded = false;
    }

    public int getAmountBetThisRound() {
        return amountBetThisRound;
    }

    public void setAmountBetThisRound(int amount) {
        if (amount >= 0)
            amountBetThisRound = amount;
    }

    public int getChipCount() {
        return chipCount;
    }

    public String getName() {
        return name;
    }
    
    public int getPotInvestment(){
        return potInvestment;
    }
    
    public void setPotInvestment(int newPotInvestment){
        potInvestment = newPotInvestment;
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

    public ArrayList<Card> getHand() {//returns the players hand
        ArrayList<Card> handCopy = new ArrayList<>();
        for (Card c : hand) {
            handCopy.add(new Card(c));
        }
        return handCopy;
    }

    public int compareTo(Object o) {
        if (o instanceof Player) {
            Player p = (Player) o;
            return (int) ((scoreThisRound - p.getScoreThisRound()) * 1000000);
        } else return 0;
    }
}
