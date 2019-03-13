public class Card implements Comparable{

	private int value;
	private String suit;
    
    /**
     * @param v number on card
     * @param s suit on card
     */
	public Card(int v, String s){
		value = v;
		suit = s;
	}

    /**
     * @return the card's suit
     */
	public String getSuit(){
		return suit;
	}

    /**
     * @return the card's number
     */
	public int getValue(){
		return value;
	}
    
    /**
     * @param o an object in java
     * @return difference in number between 2 cards
     */
	public int compareTo(Object o){
		if (o instanceof Card){
			Card c = (Card) o;
			return value - c.getValue();
		} 
		return 0;//If given an improper input
	}
}
