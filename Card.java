public class Card implements Comparable{


	private int value;
	private String suit;
	
	public Card(int v, String s){
		value = v;
		suit = s;
	}

	public String getSuit(){
		return suit;
	}

	public int getValue(){
		return value;
	}
	
	public int compareTo(Object o){
		if (o instanceof Card){
			Card c = (Card) o;
			return value - c.getValue();
		} 
		return 0;//If given an improper input
	}

}
