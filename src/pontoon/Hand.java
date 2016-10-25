//100136054
package pontoon;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Hand implements Comparable<Hand> {

    List<Card> cards = new ArrayList<>();
    List<Integer> handMarkList = new ArrayList<>();
    
    public Hand(){
        handMarkList.add(0);
    }

    //Start means twist twice
    public void start(Card card1, Card card2) {
        twist(card1);
        twist(card2);
    }

    public void twist(Card newCard) {
        cards.add(newCard);
        addMarks(newCard);
    }

    private void addMarks(Card newCard) {
        int[] cardMarks = newCard.getMarks();
        List<Integer> tempList = new ArrayList<>();
        for (int j = 0; j < handMarkList.size(); j++) {
            for (int k = 0; k < cardMarks.length; k++) {
                tempList.add(handMarkList.get(j) + cardMarks[k]);
            }
        }
        handMarkList = tempList;
    }

    public List<Card> getCards(){
        return cards;
    }
    
    public int[] getMarks() {
        int[] arr = new int[handMarkList.size()];
        for (int i = 0; i < handMarkList.size(); i++) {
            arr[i] = handMarkList.get(i);
        }
        return arr;
    }

    public int getMaxMark() {
        return Collections.max(handMarkList);
    }

    public int getMinMark() {
        return Collections.min(handMarkList);
    }

    public int getValidMax() {
        int vMax = 0;
        for (int i : handMarkList) {
            if (i > vMax) {
                vMax = i;
            }
        }
        return vMax;
    }

    public boolean getIsBust() {
        return false;
    }

    private boolean getIsPontoon() {
        if (cards.size() != 2) {
            return false;
        }
        if (cards.get(0).getIsFaceCard()
                && cards.get(1).getLetter().equalsIgnoreCase("Ace")) {
            return true;
        }

        if (cards.get(1).getIsFaceCard()
                && cards.get(0).getLetter().equalsIgnoreCase("Ace")) {
            return true;
        }

        return false;
    }

    public boolean getIsFiveCardTrick() {
        if (this.getIsBust()) {
            return false;
        }
        return this.cards.size() >= 5;
    }

    @Override
    public String toString() {
        String returnString = "";
        for (Card c : cards) {
            returnString += c.toString() + "||";
        }
        //Concatenate marks
        int[] marks = getMarks();
        returnString += "(" + marks[0];
        if (marks.length > 1) {
            for (int i = 1; i < marks.length - 1; i++) {
                returnString += ", " + marks[0];
            }
            returnString += " or " + marks[marks.length - 1];
        }
        returnString += " points)";
        return returnString;
    }

    @Override
    public int compareTo(Hand o) {
        //check bust
        if (this.getMinMark() > 21 && o.getMinMark() > 21) {
            return 0;
        } else if (this.getMinMark() > 21) {
            return -1;
        } else if (o.getMinMark() > 21) {
            return 1;
        }
        //pontoon
        if (this.getIsPontoon() && o.getIsPontoon()) {
            return 0;
        } else if (this.getIsPontoon()) {
            return 1;
        } else if (o.getIsPontoon()) {
            return -1;
        }
        //five cards trick
        if (this.getIsFiveCardTrick() && o.getIsFiveCardTrick()) {
            //both of them are five cards trick
            //proceed to compare other conditions.
        } else if (this.getIsFiveCardTrick()) {
            return 1;
        } else if (o.getIsFiveCardTrick()) {
            return -1;
        }

        //compare the marks
        if (this.getValidMax() > o.getValidMax()) {
            return 1;
        }

        if (this.getValidMax() < o.getValidMax()) {
            return -1;
        }

        return 0;
    }
}
