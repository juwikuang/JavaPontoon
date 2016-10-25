/*
This dealer deals out cards to a batck of players.
These players are plays independently.
 */
package pontoon.speakers;

import pontoon.Deck;
import pontoon.GameResultType;
import pontoon.Hand;

public class MeleeDealerSpeaker implements Speaker {

    Deck theDeck = new Deck(1);
    Hand dealer = new Hand();
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
                dealer = new Hand();
                playerHand = new Hand();
                dealer.start(theDeck.deal(), theDeck.deal());
                playerHand.start(theDeck.deal(), theDeck.deal());
                started = true;
                return speakingWay.
                        toMessage(GameResultType.Continue, playerHand);
            case "T":
            case "TWIST":
                //it is possible that the client keeps asking for cards.
                //especially machine players.
                if (playerHand.getMinMark() > 21) {
                    return speakingWay.toMessage(GameResultType.PlayerBust,
                            playerHand);
                }
                playerHand.twist(theDeck.deal());
                if (playerHand.getMinMark() > 21) {
                    return speakingWay.toMessage(GameResultType.PlayerBust,
                            playerHand);
                }
                return speakingWay.
                        toMessage(GameResultType.Continue, playerHand);
            case "S":
            case "Stick":
                //the dealer's turn
                //boolean dealerBust = false;
                while (dealer.getValidMax() < playerHand.getValidMax()) {
                    dealer.twist(theDeck.deal());
                    if (dealer.getMinMark() > 21) {
                        return speakingWay.toMessage(GameResultType.DealerBust,
                                playerHand);
                    }
                }

                if (dealer.compareTo(playerHand) > 0) {
                    return speakingWay.toMessage(GameResultType.Loose,
                            playerHand);
                } else {
                    return speakingWay.toMessage(GameResultType.Win, playerHand);
                }
        }
        //throw new Exception("Wrong");
        return message;
    }
}
