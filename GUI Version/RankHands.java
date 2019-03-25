import java.util.ArrayList;
import java.util.Collections;

public class RankHands {

    /**
     * @param players arraylist of players
     * @param tableCards arraylist of cards on table
     * @return arraylist of player(s) who won
     */
    public static ArrayList<Player> rankHands(ArrayList<Player> players, ArrayList<Card> tableCards) {
        ArrayList<Card> cards;
        for (Player player : players) {
            if (!player.isFolded) {
                cards = player.getHand();
                cards.addAll(tableCards);
                player.setScoreThisRound(matchingValueCheck(cards));
                player.setScoreThisRound(straight(cards));
                player.setScoreThisRound(flush(cards));
                player.setScoreThisRound(straightFlush(cards));
                player.setScoreThisRound(royalFlush(cards));
            }
        }
        double maxScore = 0;
        ArrayList<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (player.getScoreThisRound() > maxScore) {
                maxScore = player.getScoreThisRound();
            }
        }
        for (Player player : players) {
            if (player.getScoreThisRound() == maxScore) {
                winners.add(player);
            }
        }
        return winners;
    }

    /**
     * @param cards arraylist of cards
     * @return value of the player's hand
     */
    private static double matchingValueCheck(ArrayList<Card> cards) {
        Collections.sort(cards);
        int[] valueCounts = new int[13];//this is the possible cause of the error
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
            return 7 + value / 100.0 + highCard(cards) / 100.0;//quads
        } else if (max == 3) {
            if (secondToMax > 1) {
                return 6 + value / 100.0 + secondValue / 10000.0;//full house
            }
            return 3 + value / 100.0 + highCard(cards) / 100.0;//triple
        } else if (max == 2) {
            if (secondToMax == 2) {
                return 2 + Math.max(value, secondValue) / 100.0 + highCard(cards) / 100.0;//two pair TODO kicker
            }
            return 1 + value / 100.0 + highCard(cards) / 100.0;//pair
        } else return highCard(cards);//highCard
    }

    /**
     * @param cards arraylist of cards
     * @return value of the highest card in hand or on table
     */
    private static double highCard(ArrayList<Card> cards) {
        Collections.sort(cards);
        return cards.get(cards.size() - 1).getValue() / 100.0;
    }

    /**
     * @param cards arraylist of player's cards
     * @return value of hand if there is a straight, otherwise returns 0
     */
    private static double straight(ArrayList<Card> cards) {//TODO this doesn't seem to work
        Collections.sort(cards);
        int cardsInARow = 0, highestValue = 0;
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i + 1).getValue() - 1 == cards.get(i).getValue()) {
                cardsInARow++;
                highestValue = cards.get(i + 1).getValue();
            } else if (cardsInARow < 5) {//to prevent counting seperated streak? TODO test
                cardsInARow = 0;
            }
        }
        if (cardsInARow >= 5) {
            return 4 + highestValue / 100.0;
        } else if (cards.get(cards.size() - 1).getValue() == 13) {//hardcode for Ace - 5 Straight
            cardsInARow = 1;
            for (int i = 0; i < 4; i++) {
                if (cards.get(i + 1).getValue() - 1 == cards.get(i).getValue()) {
                    cardsInARow++;
                }
            }
            if (cardsInARow >= 5) {
                return 4.05;
            }
        }
        return 0;
    }

    /**
     * @param cards arraylist of player's cards
     * @return value of had if there is a flush, otherwise returns 0
     */
    private static double flush(ArrayList<Card> cards) {

        ArrayList<Card> hearts = new ArrayList<>();
        ArrayList<Card> spades = new ArrayList<>();
        ArrayList<Card> clubs = new ArrayList<>();
        ArrayList<Card> diamonds = new ArrayList<>();

        for (Card c : cards) {
            if (c.getSuit().equals("hearts")) {
                hearts.add(c);
            } else if (c.getSuit().equals("spades")) {
                spades.add(c);
            } else if (c.getSuit().equals("clubs")) {
                clubs.add(c);
            } else if (c.getSuit().equals("diamonds")) {
                diamonds.add(c);
            }
        }
        if (hearts.size() > 4) {
            Collections.sort(hearts);
            return 5 + hearts.get(hearts.size() - 1).getValue() / 100.0;
        } else if (clubs.size() > 4) {
            Collections.sort(clubs);
            return 5 + clubs.get(clubs.size() - 1).getValue() / 100.0;
        } else if (spades.size() > 4) {
            Collections.sort(spades);
            return 5 + spades.get(spades.size() - 1).getValue() / 100.0;
        } else if (diamonds.size() > 4) {
            Collections.sort(diamonds);
            return 5 + diamonds.get(diamonds.size() - 1).getValue() / 100.0;
        }
        return 0;
    }

    /**
     * @param cards arraylist of cards
     * @return value of hand if there is a straight flush, otherwise 0
     */
    private static double straightFlush(ArrayList<Card> cards) {
        //TODO straightFlush
        return 0;
    }

    /**
     * @param cards arraylist of cards
     * @return value of hand if there is a royal flush, otherwise 0
     */
    private static double royalFlush(ArrayList<Card> cards) {
        //TODO royalFlush
        return 0;
    }
}
