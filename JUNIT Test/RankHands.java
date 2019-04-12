import java.util.ArrayList;
import java.util.Collections;

public class RankHands {


    public static ArrayList<Player> rankHands(ArrayList<Player> players, ArrayList<Card> tableCards) {
        ArrayList<Card> cards;
        for (Player player : players) {
            if (!player.isFolded) {
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


    private static double matchingValueCheck(ArrayList<Card> cards, ArrayList<Card> hand) {//TODO check the logic on high card comparisons
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
                return 2 + Math.max(value, secondValue) / 100.0 + highCard(cards, hand) / 100.0;//two pair TODO kicker
            }
            return 1 + value / 100.0 + highCard(cards, hand) / 100.0;//pair
        } else return highCard(cards, hand);//highCard
    }

    private static double highCard(ArrayList<Card> cards) {
        Collections.sort(cards);
        return cards.get(cards.size() - 1).getValue() / 100.0;
    }

    private static double highCard(ArrayList<Card> cards, ArrayList<Card> hand) {
        return highCard(cards) + highCard(hand) / 100.0;
    }

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

    private static double flush(ArrayList<Card> cards) {

        ArrayList<Card> hearts = new ArrayList<>();
        ArrayList<Card> spades = new ArrayList<>();
        ArrayList<Card> clubs = new ArrayList<>();
        ArrayList<Card> diamonds = new ArrayList<>();

        for (Card c : cards) {
            if (c.getSuit().equals("H")) {
                hearts.add(c);
            } else if (c.getSuit().equals("S")) {
                spades.add(c);
            } else if (c.getSuit().equals("C")) {
                clubs.add(c);
            } else if (c.getSuit().equals("D")) {
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

    private static double straightFlush(ArrayList<Card> cards) {
        if (straight(cards) > 4 && flush(cards) > 5) {
            return 8;
        }
        return 0;
    }
}
