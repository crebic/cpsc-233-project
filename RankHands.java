package game;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class used to determine who's hand has the greatest value (who has the best hand) based on their cards.
 * 
 * @author T01 G09
 * Date: April 11, 2019
 */
public class RankHands {

    public static ArrayList<Player> rankHands(ArrayList<Player> players, ArrayList<Card> tableCards) {
        ArrayList<Card> cards;
        for (Player player : players) {
            if (!player.isFolded) { //gets the hands and scores them accordingly if the player is not folded
                cards = player.getHand();
                cards.addAll(tableCards);
                player.setScoreThisRound(matchingValueCheck(cards, player.getHand()));
                player.setScoreThisRound(straight(cards));
                player.setScoreThisRound(flush(cards));
                player.setScoreThisRound(straightFlush(cards));//also does royal flush
            }
        }
        double maxScore = 0;
        ArrayList<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (player.getScoreThisRound() > maxScore) {
                maxScore = player.getScoreThisRound(); //the max score was set by the current player, update max score
            }
        }
        for (Player player : players) {
            if (player.getScoreThisRound() == maxScore) {
                winners.add(player); //the current player has the highest score, therefore is the winner
            }
        }
        return winners;
    }

    /**
     * Matches cards of the same number from the player's hand and the cards on the table.
     * @param cards an arraylist of the cards on the table.
     * @param hand an arraylist of cards in a player's hand.
     * @return value of the hand.
     * If no matches exist, @return highCard method to run.
     */
    private static double matchingValueCheck(ArrayList<Card> cards, ArrayList<Card> hand) {
        Collections.sort(cards);
        int[] valueCounts = new int[13];
        for (Card c : cards) {
            valueCounts[c.getValue() - 1]++;
        }
        int max = 0, secondToMax = 0;
        int value = 0, secondValue = 0;
        for (int i = 0; i < valueCounts.length; i++) {
            if (valueCounts[i] > max) {
                max = valueCounts[i]; //set the new max
                value = i + 1;
            }
        }
        //
       for (int j = 0; j < valueCounts.length; j++){
           if (valueCounts[j] > secondToMax && j != value - 1) {
                secondToMax = valueCounts[j];
                secondValue = j + 1;
            }
       }
        //
        if (max == 4) {
            return 7 + value / 100.0 + highCard(cards, hand) / 100.0;//quads
        } else if (max == 3) {
            if (secondToMax > 1) {
                return 6 + value / 100.0 + secondValue / 10000.0;//full house
            }
            return 3 + value / 100.0 + highCard(cards, hand) / 100.0;//triple
        } else if (max == 2) {
            if (secondToMax == 2) {
                return 2 + Math.max(value, secondValue) / 100.0 + highCard(cards, hand) / 100.0;//two pair
            }
            return 1 + value / 100.0 + highCard(cards, hand) / 100.0;//pair
        } else return highCard(cards, hand);//highCard
    }

    /**
     * Looks for the highest value of an arraylist of cards.
     * @param an arraylist of cards
     * @return the value of the cards
     */
    private static double highCard(ArrayList<Card> cards) {
        Collections.sort(cards);
        return cards.get(cards.size() - 1).getValue() / 100.0;//score for a high card
    }

    /**
     * Looks for the highest in player's hand and on the table.
     * @param cards an arraylist of the cards on the table.
     * @param hand an arraylist of the cards in the player's hand.
     * @return the highest value of the player's cards and the cards on the table.
     */
    private static double highCard(ArrayList<Card> cards, ArrayList<Card> hand) {
        return highCard(cards) + highCard(hand) / 100.0; //score for a high card
    }

    /**
     * Looks for straights on the table and in the player's hand.
     * @param cards an arraylist of cards.
     * @return the value of the hand if there is a straight.
     * If no straight, @return 0.
     */
    private static double straight(ArrayList<Card> cards) {
        Collections.sort(cards);
        int cardsInARow = 0, highestValue = 0;
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i + 1).getValue() - 1 == cards.get(i).getValue()) {
                cardsInARow++;
                highestValue = cards.get(i + 1).getValue();
            } else if (cardsInARow < 5) {//to prevent counting seperated streak
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
     * Looks for a flush on the table and in the player's hand.
     * @param cards an arraylist of cards.
     * @return the value of the hand if there is a flush.
     * If there is no flush, @return 0.
     */
    private static double flush(ArrayList<Card> cards) {

        ArrayList<Card> hearts = new ArrayList<>();
        ArrayList<Card> spades = new ArrayList<>();
        ArrayList<Card> clubs = new ArrayList<>();
        ArrayList<Card> diamonds = new ArrayList<>();

        for (Card c : cards) {//check the suit of each card and correspondingly add them to the proper suit list
            if (c.getSuit().equals("Hearts")) {
                hearts.add(c);
            } else if (c.getSuit().equals("Spades")) {
                spades.add(c);
            } else if (c.getSuit().equals("Clubs")) {
                clubs.add(c);
            } else if (c.getSuit().equals("Diamonds")) {
                diamonds.add(c);
            }
        }
        //check for each suit if there occurs five of the same suit with the table cards and player hands
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
     * Looks for a straight flush on the table and in the player's hand.
     * @param cards an arraylist of cards.
     * @return the value of the hand if there is a straight flush.
     * If there s no straight flush, @return 0.
     */
    private static double straightFlush(ArrayList<Card> cards) {
        if (straight(cards) > 4 && flush(cards) > 5) {
            ArrayList<Card> hearts = new ArrayList<>();
            ArrayList<Card> spades = new ArrayList<>();
            ArrayList<Card> clubs = new ArrayList<>();
            ArrayList<Card> diamonds = new ArrayList<>();

            for (Card c : cards) { // check the suit of each card and correspondingly add them to the proper suit list
                if (c.getSuit().equals("Hearts")) {
                    hearts.add(c);
                } else if (c.getSuit().equals("Spades")) {
                    spades.add(c);
                } else if (c.getSuit().equals("Clubs")) {
                    clubs.add(c);
                } else if (c.getSuit().equals("Diamonds")) {
                    diamonds.add(c);
                }
            }
            //return a total value of 8+the highcard used in the straight
            if (hearts.size() > 4) {
                return 4 + straight(hearts);
            } else if (clubs.size() > 4) {
                return 4 + straight(clubs);
            } else if (spades.size() > 4) {
                return 4 + straight(spades);
            } else if (diamonds.size() > 4) {
                return 4 + straight(diamonds);
            }
        }
        return 0;
    }
}
