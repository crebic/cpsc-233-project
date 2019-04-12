package game;

import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;

public class Deck implements Serializable {
    
    
//*instance variables
    private static final long serialVersionUID = 5L;
    private ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        resetDeck();
    }
/**
* method for resetting the deck (reshuffle)
*/
    public void resetDeck() {//TODO make private, cut out of gamestate
        cards.clear();
        String suit = "";
        for (int i = 1; i <= 4; i++) { //cycle through each suit and populate the deck using the nested for loop
            switch (i) {
                case 1:
                    suit = "Hearts";
                    break;
                case 2:
                    suit = "Diamonds";
                    break;
                case 3:
                    suit = "Spades";
                    break;
                case 4:
                    suit = "Clubs";
            }
            for (int j = 1; j <= 13; j++) {
                cards.add(new Card(j, suit));
            }
        }
    }

/**
*deals cards to the players and to the table
*@param players an ArrayList of all the players
*@param table an ArrayList of the table cards
*/
    public void deal(ArrayList<Player> players, ArrayList<Card> table) {
        Random r = new Random();
        table.clear(); //clears the table cards
        resetDeck(); 
        Card c1, c2; // every player needs 2 cards
        int sizeOfDeck = 51;//starts at zero for Random.nextInt()
        //deals 5 random cards to the table
        for (int i = 0; i < 5; i++) {
            c1 = cards.get(r.nextInt(sizeOfDeck) + 1);//size of deck is 51, add 1 to randomly select 1 of the 52 cards
            table.add(c1); 
            cards.remove(c1);
            sizeOfDeck--;
        }
        //deals 2 random cards for every player in the game
        for (Player p : players) {
            //for each player, give them a new hand by taking cards from the deck and placing them as part of the players hand
            //update the decks size to -2 cards for each player
            p.newHand();
            c1 = cards.get(r.nextInt(sizeOfDeck) + 1);//first card in the selected players hand
            cards.remove(c1);
            sizeOfDeck--;
            c2 = cards.get(r.nextInt(sizeOfDeck) + 1);//second card in the selected players hand
            cards.remove(c2);
            sizeOfDeck--;
            p.setHand(c1, c2);
        }
    }
}
