public class Card implements Comparable {
    private int value;
    private String suit;

    public Card(int v, String f) {
        value = v;
        suit = f;
    }

    public Card(Card c) {
        value = c.getValue();
        suit = c.getSuit();
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public int compareTo(Object o) {
        if (o instanceof Card) {
            Card c = (Card) o;
            return value - c.getValue();
        }
        //In the case of incorrect inputs
        else return 0;
    }

    public String toString() {
        //For showing card images
        String relativePath = "./CardFolder/";
        if (value < 11) {
            return relativePath + value + suit.charAt(0) + ".png";
        } else if (value == 11) {
            return relativePath + "J" + suit.charAt(0) + ".png";
        } else if (value == 12) {
            return relativePath + "Q" + suit.charAt(0) + ".png";
        } else {
            return relativePath + "K" + suit.charAt(0) + ".png";
        }
    }
}
