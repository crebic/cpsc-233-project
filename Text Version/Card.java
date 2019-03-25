public class Card implements Comparable{


//* Authors Sam Robertson 
//* 2018 February 02


//* instance variables for the int value and string suit for each card in a deck of cards


	private int value;
	private String suit;
	
	
	//* constructor 
	//* @param v of type int which is the numeric value of the card (jack is 11)
	//* @ param s of type String which holds the suite value of a card (Club is c)
	public Card(int v, String s){
		value = v;
		suit = s;
	}
	
	
	//* getters
	
	//* getter for the String of suit
	//* @return returns the String Suit
	public String getSuit(){
		return suit;
	}
	
	
	//* Getter for Int value of a card
	//* @return returns the int value of a card 
	public int getValue(){
		return value;
	}
	
	//* method for checking if the object is of type card if it is, it returns the difference of values between two cards to find their priority 
	//* @param o of type object
	//* @return returns difference between Object and value of the instance of Card
	public int compareTo(Object o){
		if (o instanceof Card){
			Card c = (Card) o;
			return value - c.getValue();
		} 
		return 0;//If given an improper input
	}

}
