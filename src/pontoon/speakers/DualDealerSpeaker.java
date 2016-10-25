/*
authoer Weiguang Zhou 100136054.
This is a dealer,
which speaks
it response to every string message
and than reply.
This decouples socket messages and the business.
 */
package pontoon.speakers;

import pontoon.Deck;
import pontoon.GameResultType;
import pontoon.Hand;
import pontoon.PontoonLogger;

/**
 * 1 vs 1
 *
 * @author Eric
 */
public class DualDealerSpeaker implements Speaker {

    Deck theDeck = new Deck(1);
    Hand dealerHand = new Hand();
    Hand playerHand = new Hand();
    boolean started = false;
    SpeakingStrategy speakingWay = null;

    @Override
    public String reply(String message) throws Exception {
        String capitalMessage = message.toUpperCase();

        switch (capitalMessage) {
            //start, restart game
            case "PLAIN":
                speakingWay = new PlainSpeakingStrategy();
                return message;
            case "JSON":
                speakingWay = new JsonSpeakingStrategy();
                return message;
            case "START":
            case "RESTART":
                theDeck = new Deck(1);
                dealerHand = new Hand();
                playerHand = new Hand();
                dealerHand.start(theDeck.deal(), theDeck.deal());
                playerHand.start(theDeck.deal(), theDeck.deal());
                started = true;
                return speakingWay.
                        toMessage(GameResultType.Continue, playerHand);
            case "T":
            case "TWIST":
                //it is possible that the client keeps asking for cards.
                //especially machine players.
                if (playerHand.getMinMark() > 21) {
                    PontoonLogger.logGameResult(GameResultType.PlayerBust,
                            playerHand, dealerHand);
                    return speakingWay.toMessage(GameResultType.PlayerBust,
                            playerHand);
                }
                playerHand.twist(theDeck.deal());
                if (playerHand.getMinMark() > 21) {
                    PontoonLogger.logGameResult(GameResultType.PlayerBust,
                            playerHand, dealerHand);
                    return speakingWay.toMessage(GameResultType.PlayerBust,
                            playerHand);
                }
                return speakingWay.
                        toMessage(GameResultType.Continue, playerHand);
            case "S":
            case "STICK":
                //the dealer's turn
                //boolean dealerBust = false;
                while (dealerHand.getValidMax() < playerHand.getValidMax()) {
                    dealerHand.twist(theDeck.deal());
                    if (dealerHand.getMinMark() > 21) {
                        PontoonLogger.logGameResult(GameResultType.DealerBust,
                            playerHand, dealerHand);
                        return speakingWay.toMessage(GameResultType.DealerBust,
                                playerHand);
                    }
                }

                if (dealerHand.compareTo(playerHand) > 0) {
                    PontoonLogger.logGameResult(GameResultType.Loose,
                            playerHand, dealerHand);
                    return speakingWay.toMessage(GameResultType.Loose,
                            playerHand);
                } else {
                    PontoonLogger.logGameResult(GameResultType.Win,
                            playerHand, dealerHand);
                    return speakingWay.toMessage(GameResultType.Win, playerHand);
                }
        }
        //throw new Exception("Wrong");
        return message;
    }
}
