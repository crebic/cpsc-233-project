package game;

import java.util.ArrayList;
import java.util.Random;

public class AI {

    //TODO 2:AI checking loop 3:Integrate with GameState


    public static int bet(int amountToCall, Player self, ArrayList<Player> playerList, ArrayList<Card> tableCards) {//TODOtwo clicks to update?
        Random rnd = new Random();
        double thoughtOutcome = rnd.nextDouble();
        int chips = self.getChipCount();
        int betSize = 0;
        ArrayList<Player> winnerList = RankHands.rankHands(playerList, tableCards);
        if (!self.isFolded) {
            if (winnerList.contains(self)) {
                //Strategies for winning
                if (thoughtOutcome > 0.96 && self.getPotInvestment() < (0.3 * chips)) {
                    betSize = 0;
                } else if (thoughtOutcome > 0.35) {
                    if (amountToCall < (chips / 5))
                        betSize = (int) (amountToCall * (1 + rnd.nextDouble()));
                    else
                        betSize = amountToCall;

                } else {
                    if (amountToCall > (chips / 4)) {
                        betSize = amountToCall;
                    } else if (amountToCall > 0) {
                        betSize = (int) (amountToCall * (1 + rnd.nextDouble()));
                    } else {
                        betSize = (int) (chips * rnd.nextDouble() / 8);
                    }
                }
            } else {
                //strategy for losing
                if (thoughtOutcome > 0.33 && amountToCall < (chips / 6)) {
                    betSize = amountToCall;
                }
            }

            if (betSize < amountToCall) {
                betSize = 0;
                self.isFolded = true;
            }
            self.setAmountBetThisRound(self.getAmountBetThisRound() + betSize);
            self.removeChips(betSize);
            self.getPotInvestment() += betSize;
        }
        return betSize;

    }

}
