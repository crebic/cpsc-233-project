import Card;
import Math;

public class AI {

    private double handValue;

    public Player ai;//private?

    private ArrayList<Card> tableCards;

    private String strat;//TODO represents betting strategy this turn

    //TODO how does the AI get the tableCards???

    public AI(Player p) {//initializer to be called only once
        ai = p;
        handValue = 0;
        tableCards = new ArrayList<>();
        strat = "";
    }

    public double preFlop() {//TODO might be unnecesary if AI cheats (currently out of use)
        double score = 0;
        if (hand.get(0).getValue() == hand.get(1).getValue()) {
            score++;
        } else {
            handValue -= Math.abs(hand.get(0).getValue() - hand.get(1).getValue()) / 100.0;
        }
        if (hand.get(0).getSuit() == hand.get(1).getSuit()) {
            score += .5;
        }
        return score;
    }

    //next method is a selection sort support method

    private void selectionSort(ArrayList<Card> cards) {
        Card min;
        int minIndex;
        for (int i = 0; i < cards.length(); i++) {
            min = cards.get(i);
            minIndex = i;
            for (int j = i + 1; j < cards.length(); j++) {
                if (cards.get(j).getValue() < min.getValue()) {
                    min = cards.get(j);
                    minIndex = j;
                }
            }
            cards.remove(minIndex);
            cards.add(minIndex, cards.get(i));
            cards.remove(i);
            cards.add(i, min);
        }
    }

    //TODO I'm not sure if this selection sort works

    public void setHandValue(double d) {
        if (d > handValue)
            handValue = d;
    }

    public void setTableCards(ArrayList<Card> arr) {
        tableCards = arr;
    }

    private double highCard(Player p) {
        double highCard = 0;
        for (Card c : p.getHand()) {
            if (c.getValue() > highCard) {
                highCard = c.getValue();
            }
        }
        for (Card c : tableCards) {
            if (c.getValue() > highCard) {
                highCard = c.getValue();
            }
        }
        return highCard / 100.0;
    }

    private double straight(Player p) {
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(p.getHand());
        cards.addAll(tableCards);
        selectionSort(cards);
        int cardsInARow = 0, highestValue = 0;
        for (int i = 0; i < cards.length() - 1; i++) {
            if (cards.get(i + 1).getValue() - 1 == cards.get(i).getValue()) {
                cardsInARow++;
                highestValue = cards.get(i + 1).getValue();
            } else if (cardsInARow < 5) {//to prevent counting seperated streak? TODO test
                cardsInARow = 0;
            }
        }
        if (cardsInARow >= 5) {
            return 4 + highestValue / 100.0;
        } else if (cards.get(cards.length() - 1).getValue() == 13) {//hardcode for Ace - 5 Straight
            cardsInARow = 0;
            for (int i = 0; i < 4; i++) {
                if (cards.get(i + 1).getValue() - 1 == cards.get(i).getValue()) {
                    cardsInARow++;
                }
            }
            if (cardsInARow >= 5) {
                return 4.05;
            }
            return 0;
        }
    }

    private double flush(Player p) {
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(p.getHand());
        cards.addAll(tableCards);

        ArrayList<Card> hearts = new ArrayList<>();
        ArrayList<Card> spades = new ArrayList<>();
        ArrayList<Card> clubs = new ArrayList<>();
        ArrayList<Card> diamonds = new ArrayList<>();

        //TODO this code needs to match up with the gamestate strings, assumes "heart" format

        for (Card c : cards) {
            if (c.getFace().equals("heart")) {
                hearts.add(c);
            } else if (c.getFace().equals("spade")) {
                spades.add(c);
            } else if (c.getFace().equals("club")) {
                clubs.add(c);
            } else if (c.getFace().equals("diamond")) {
                diamonds.add(c);
            }
        }
        if (hearts.length() > 4) {
            selectionSort(hearts);
            return 5 + hearts.get(hearts.length() - 1).getValue() / 100.0;
        } else if (clubs.length() > 4) {
            selectionSort(clubs);
            return 5 + clubs.get(clubs.length() - 1).getValue() / 100.0;
        } else if (spades.length() > 4) {
            selectionSort(spades);
            return 5 + spades.get(spades.length() - 1).getValue() / 100.0;
        } else if (diamonds.length() > 4) {
            selectionSort(diamonds);
            return 5 + diamonds.get(diamonds.length() - 1).getValue() / 100.0;
        }
        return 0;
    }

    private double matchingValueCheck(Player p) {
        int[] valueCounts = new int[13];//this is the possible call of the error
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(p.getHand());
        cards.addAll(tableCards);
        for (Card c : cards) {
            valueCounts[c.getValue() - 1]++;//TODO might be a not yet initialized error
        }
        int max = 0, secondToMax = 0;
        int value = 0, secondValue = 0;
        for (int i = 0; i < valueCounts.length; i++) {
            if (valueCounts[i] > max) {
                max = valueCounts[i];
                value = i + 1;
            } else if (valueCounts[i] > secondToMax) {
                secondToMax = valueCounts[i];
                secondValue = i + 1;
            }
        }
        if (max == 4) {
            return 7 + value / 100.0 + highCard(p) / 100.0;//quads
        } else if (max == 3) {
            if (secondToMax > 1) {
                return 6 + value / 100.0 + secondValue / 10000.0;//full house
            }
            return 3 + value / 100.0 + highCard(p) / 100.0;//triple
        } else if (max == 2) {
            if (secondToMax == 2) {
                return 2 + Math.max(value, secondValue) / 100.0 + highCard(p) / 100.0;//two pair TODO kicker
            }
            return 1 + value / 100.0 + highCard(p) / 100.0;//pair
        } else return highCard(p);//highCard
    }

    private double straightFlush(Player p) {
        //TODO straightFlush
        return 0;
    }

    private double royalFlush(Player p) {
        //TODO royalFlush
        return 0;
    }

    public void estimateScore() {
        ai.setHandValue(matchingValueCheck(ai));
        ai.setHandValue(straight(ai));
        ai.setHandValue(flush(ai));
        ai.setHandValue(straightFlush(ai));
        ai.setHandValue(royalFlush(ai));
    }

    public double setStrat() {
        //TODO this should be changed to reflect a more advanced strategy
        estimateScore();
        double rnd = Math.random();
        if (handValue > 1 && handValue < 3) {
            strat = "Value Bet";
        } else if (handValue >= 3) {
            if (rnd > .5) {
                strat = "Reel Them";
            } else {
                strat = "Value Bet";
            }
        } else {
            if (rnd > .5) {
                strat = "Check/Fold";
            } else {
                strat = "Bluff";
            }
        }
    }

    public int bet(double amountToCall) {
        double chips = ai.getChips();//TODO not sure if this is a method of player
        int maxBet = 0;
        //Bet depending on strategy, using chips and hand scor
        if (strat.equals("Value Bet")) {

            maxBet = (int) ((chips / 12) * handValue);

        } else if (strat.equals("Reel Them")) {

            maxBet = (int) Math.max(amountToCall, ((int) ((chips / 12) * handValue) / 2))

        } else if (strat.equals("Check/Fold")) {

            maxBet = 0;

        } else if (strat.equals("Bluff")) {
            maxBet = Math.random() * .7 * chips;
        }
        if (maxBet < amountToCall) {
            ai.changeFoldedState(true);
        } else if (maxBet <= chips) {
            ai.setChips(chips - maxBet);
            return maxBet;
        }
        return 0;

    }

}
