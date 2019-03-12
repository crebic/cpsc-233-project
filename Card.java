public class Card implements comparable{


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
		if (o instanceOf Card){
			Card c = (Card) o;
		} else return 0;//If given an improper input
		
		return value - c.getValue();
	}

}
