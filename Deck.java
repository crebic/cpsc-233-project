import java.util.ArrayList;
import java.util.Random;

public class Deck {

    private ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        resetDeck();
    }

    public void resetDeck() {//TODO make private, cut out of gamestate
        cards.clear();
        String suit = "";
        for (int i = 1; i <= 4; i++) {
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

    public void deal(ArrayList<Player> players, ArrayList<Card> table) {
        Random r = new Random();
        table.clear();
        resetDeck();
        Card c1, c2;
        int sizeOfDeck = 51;//starts at zero for Random.nextInt()
        for (int i = 0; i < 5; i++) {
            c1 = cards.get(r.nextInt(sizeOfDeck) + 1);//TODO why plus 1 here?
            table.add(c1);
            cards.remove(c1);
            sizeOfDeck--;
        }
        for (Player p : players) {
            p.newHand();
            c1 = cards.get(r.nextInt(sizeOfDeck) + 1);
            cards.remove(c1);
            sizeOfDeck--;
            c2 = cards.get(r.nextInt(sizeOfDeck) + 1);
            cards.remove(c2);
            sizeOfDeck--;
            p.setHand(c1, c2);
        }
    }
}
