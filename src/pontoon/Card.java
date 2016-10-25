//100136054
package pontoon;

public class Card {

    //region variables
    private static String CARD_LETTERS[] = {"Ace", "Two", "Three", "Four",
        "Five",
        "Six", "Seven", "Eight", "Nigh", "Ten", "Jack", "Queen", "King"};
    //Ace can be 1 or 11.
    private final static int ALL_MARKS[][] = {{1, 11}, {2}, {3}, {4}, {5},
    {6}, {7}, {8}, {9}, {10}, {10}, {10}, {10}};

    //instant Variables
    private CardType cardType;

    public CardType getCardType() {
        return cardType;
    }

    private int ordinal;
        public int getOrdinal() {
        return ordinal;
    }

    
    private String letter;

    public String getLetter() {
        return letter;
    }

    private int[] marks;

    public int[] getMarks() {
        return marks;
    }

    //constructor
    public Card(int ordinal, CardType cardType) {
        this.ordinal=ordinal;
        this.marks = ALL_MARKS[ordinal];
        this.letter = CARD_LETTERS[ordinal];
        this.cardType = cardType;
    }

    public Card(String letter, String color) {
        for (int i = 0; i < 13; i++) {
            if (CARD_LETTERS[i].equalsIgnoreCase(letter)) {
                this.ordinal = i;
            }
        }
        CardType cardType = CardType.valueOf(color);

        this.marks = ALL_MARKS[ordinal];
        this.letter = CARD_LETTERS[ordinal];
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return getLetter() + " of " + getCardType().toString();
    }

    public boolean getIsFaceCard() {
        return letter.equalsIgnoreCase("Jack")
                || letter.equalsIgnoreCase("Queen")
                || letter.equalsIgnoreCase("King");
    }


}
