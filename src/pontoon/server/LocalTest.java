
package pontoon.server;

import pontoon.Hand;
import pontoon.Deck;
import java.util.Scanner;

/**
 *
 * @author Eric
 */
public class LocalTest {
    //this test runs the code without socket.

    public static void localTest() {
        // TODO code application logic here
        Deck theDeck = new Deck(1);
        Hand dealer = new Hand();
        Hand playerHand = new Hand();
        dealer.start(theDeck.deal(), theDeck.deal());
        playerHand.start(theDeck.deal(), theDeck.deal());

        Scanner scanner = new Scanner(System.in);
        boolean continueGame = true;
        while (continueGame) {
            System.out.println("Your hand is :\r\n" + playerHand.toString());
            System.out.println("Twist or Stick?T|S");
            String twist = scanner.nextLine();
            if (twist.equals("T") || twist.equals("t")) {
                playerHand.twist(theDeck.deal());
                int max = playerHand.getMaxMark();
                int min = playerHand.getMinMark();
                if (min > 21) {
                    System.out.print("you bust!");
                    System.out.print(playerHand.toString());
                    return;
                }
            } else if (twist.equals("S") || twist.equals("s")) {
                continueGame = false;
            }
        }

        //the dealer's turn
        //boolean dealerBust = false;
        while (dealer.getMaxMark() < playerHand.getMaxMark()) {
            dealer.twist(theDeck.deal());
            if (dealer.getMaxMark() > 21) {
                System.out.print("dealer bust!");
                return;
            }
        }

        if (dealer.getMaxMark() > playerHand.getMaxMark()) {
            System.out.print("dealer win");
        } else {
            System.out.print("player win");
        }

        System.out.print("\r\n" + dealer.toString());
    }
}
