import java.util.ArrayList;

public class Player implements Comparable {

    private String name;

    private ArrayList<Card> hand;

    private int chipCount = 100;

    private int amountBetThisRound = 0;//TODO reset every round?

    public int potInvestment = 0;//for display

    private double scoreThisRound;

    public boolean isFolded = false;

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
