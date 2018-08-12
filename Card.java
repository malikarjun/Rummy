package MiniProject;

import java.util.Random;

public class Card {

    private int value;
    private int suits;

    public Card(int value, int suits) {
        this.value = value;
        this.suits = suits;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSuits() {
        return suits;
    }

    public void setSuits(int suits) {
        this.suits = suits;
    }

    public static int compareSuits(Card c1, Card c2){
        if (c1.suits-c2.suits==0){
            return c1.value - c2.value;
        }
        return  c1.suits - c2.suits;
    }

    public static int compareValue(Card c1, Card c2){
        if (c1.value == c2.value){
            return c1.suits - c2.suits;
        }
        return  c1.value - c2.value;
    }

    public static boolean isSameRankDiffSuite (Card c1, Card c2){
        return c1.suits == c2.suits && c1.value == c2.value;
    }

    public static boolean isDiffEqual (Card greaterCard, Card smallerCard, int diff){
        return greaterCard.suits == smallerCard.suits && greaterCard.value ==smallerCard.value + diff;
    }

    public Card getRandom(){
        Random random = new Random();
        int suits = random.nextInt(4);
        int value = random.nextInt(14);

        return new Card(suits, value);
    }

    @Override
    public String toString() {
        char suitsChar = '?';
        switch (suits) {
            case 0:
                suitsChar = ((char)'\u2660');
                break;
            case 1:
                suitsChar = ((char)'\u2666');
                break;
            case 2:
                suitsChar = ((char)'\u2663');
                break;
            case 3:
                suitsChar = ((char)'\u2764');
                break;
        }
        return value + " " + String.valueOf(suitsChar);
    }

    public static void main(String args[]){

    }

}
