///*
//authoer Weiguang Zhou 100136054.
//This is a dealer,
//which speaks
//it response to every string message
//and than reply.
//This decouples socket messages and the business.
// */
//package pontoon.speakers;
//
//import pontoon.Hand;
//import pontoon.Deck;
//
///**
// *
// * @author Eric
// */
//public class DealerSpeaker implements Speaker {
//
//    Deck theDeck = new Deck(1);
//    Hand dealer = new Hand();
//    Hand playerHand = new Hand();
//    boolean started = false;
//    //double vertical bars is used to represent \r\n.
//    String endingMessage = "||Type start to start/restart the game.||"
//            + "Type quit to quit the game.";
//
//    @Override
//    public String reply(String message) throws Exception {
//        String capitalMessage = message.toUpperCase();
//
//        switch (capitalMessage) {
//            //start, restart game
//            case "START":
//            case "RESTART":
//                theDeck = new Deck(1);
//                dealer = new Hand();
//                playerHand = new Hand();
//                dealer.start(theDeck.deal(), theDeck.deal());
//                playerHand.start(theDeck.deal(), theDeck.deal());
//                started = true;
//                return "Your hand is :||" + playerHand.toString()
//                        + "||Twist or Stick?T|S";
//            case "T":
//            case "TWIST":
//                playerHand.twist(theDeck.deal());
//                if (playerHand.getMinMark() > 21) {
//                    return "you bust!||"
//                            + playerHand.toString()
//                            + endingMessage;
//                }
//                return "Your hand is :||" + playerHand.toString()
//                        + "||Twist or Stick?T|S";
//            case "S":
//            case "Stick":
//                //the dealer's turn
//                //boolean dealerBust = false;
//                while (dealer.getValidMax() < playerHand.getValidMax()) {
//                    dealer.twist(theDeck.deal());
//                    if (dealer.getMinMark() > 21) {
//                        return "dealer bust! || you win! ||"
//                                + dealer.toString()
//                                + endingMessage;
//                    }
//                }
//
//                if (dealer.compareTo(playerHand) > 0) {
//                    return "dealer  win! || dealer hand:||"
//                            + dealer.toString()
//                            + endingMessage;
//                } else {
//                    return "player win! || dealer hand:||"
//                            + dealer.toString()
//                            + endingMessage;
//                }
//        }
//        return message;
//    }
//}
