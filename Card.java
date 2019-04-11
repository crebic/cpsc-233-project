package game;

import java.io.Serializable;
//* Authors Group 9
//* 2018 February 02


public class Card implements Comparable, Serializable {
    private static final long serialVersionUID = 1L;
    
    private int value;
    private String suit;

    
    /**
	*@param v of type int which is the numeric value of the card (jack is 11)
	*@param s of type String which holds the suite value of a card (Club is c)
	*/
    
    public Card(int v, String f) {
        value = v;
        suit = f;
    }

    /**
	*@param c of type card, copy constructor 
	*/
    public Card(Card c) {
        value = c.getValue();
        suit = c.getSuit();
    }
    
    /**
	*@return value, returns numerical value of a card
	*/
    public int getValue() {
        return value;
    }
    /**
	*@return returns the String Suit
	*/
    public String getSuit() {
        return suit;
    }
    
     /**
	*@param o of type Object, this method compares two instances of a card
    *@return value, returns the difference in the numeric value between two instances of a card 
    
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
	*@return returns a string used as a relative path from the current folder to retrieve the png image of a card. 
	*/
    public String toString() {
        //For showing card images
        String relativePath = "./AssetFolder/";
        if (value < 10) {
            return relativePath + (value+1) + suit.charAt(0) + ".png";
        } else if (value == 10) {
            return relativePath + "J" + suit.charAt(0) + ".png";
        } else if (value == 11) {
            return relativePath + "Q" + suit.charAt(0) + ".png";
        } else if (value == 12) {
            return relativePath + "K" + suit.charAt(0) + ".png";
        } else {
            return relativePath + "1" + suit.charAt(0) + ".png";
        }
    }
    
      /**
	*@return returns a string of the card, ie 10 of clubs is 10c.
	*/
    public String toDisplayString() {
        if (value < 11) {
            return "" + value + suit.charAt(0);
        } else if (value == 11) {
            return "J" + suit.charAt(0);
        } else if (value == 12) {
            return "Q" + suit.charAt(0);
        } else {
            return "K" + suit.charAt(0);
        }
    }
    
}
