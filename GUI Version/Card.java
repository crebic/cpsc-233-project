public class Card implements Comparable {
    private int value;
    private String suit;

    /**
     * @param v number value of card
     * @param f suit calue of card
     */
    public Card(int v, String f) {
        value = v;
        suit = f;
    }

    /**
     * @param c card
     */
    public Card(Card c) {
        value = c.getValue();
        suit = c.getSuit();
    }

    /**
     * @return number value of card
     */
    public int getValue() {
        return value;
    }

    /**
     * @return suit value of card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * @param 0 card to compare to
     * @return difference between values of cards, or 0 if o is not a card
     */
    public int compareTo(Object o) {
        if (o instanceof Card) {
            Card c = (Card) o;
            return value - c.getValue();
        }
        //In the case of incorrect inputs
        else return 0;
    }
    
    /**
     * @return string of card (to get the png file)
     */
    public String toString() {
        //For showing card images
        if (value < 11) {
            return "" + value + suit.charAt(0) + ".png";
        } else if (value == 11) {
            return "J" + suit.charAt(0) + ".png";
        } else if (value == 12) {
            return "Q" + suit.charAt(0) + ".png";
        } else {
            return "K" + suit.charAt(0) + ".png";
        }
    }
}
