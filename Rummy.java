package MiniProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rummy {

    List<Card> hand;
    List<List<Card>> pureSeqOfThree;
    List<List<Card>> possibleSeqOfThree;
    List<List<Card>> pureSeqOfFour;
    List<List<Card>> possibleSeqOfFourCountThree;
    List<List<Card>> possibleSeqOfFourCountTwo;

    public Rummy() {
        this.hand = new ArrayList<>();
        this.pureSeqOfThree = new ArrayList<>();
        this.possibleSeqOfThree = new ArrayList<>();
        this.pureSeqOfFour = new ArrayList<>();
        this.possibleSeqOfFourCountThree = new ArrayList<>();
        this.possibleSeqOfFourCountTwo = new ArrayList<>();
    }

    public boolean isSequenceOfTwo(Card c1, Card c2, int diff){
        return Card.isDiffEqual(c2, c1, diff);
    }

    public boolean isSequenceOfFour(Card c1, Card c2, Card c3, Card c4){
        return Card.isDiffEqual(c2, c1, 1) && Card.isDiffEqual(c3, c2, 1) && Card.isDiffEqual(c4, c3, 1);
    }

    public boolean isSequenceOfThree(Card c1, Card c2, Card c3){
        return Card.isDiffEqual(c2, c1, 1) && Card.isDiffEqual(c3, c2, 1);
    }

    public boolean isSetOfThree (Card c1, Card c2, Card c3){
        return Card.isSameRankDiffSuite(c1, c2) && Card.isSameRankDiffSuite(c2, c3) && Card.isSameRankDiffSuite(c1, c3);
    }

    public int isPureSequence(){
        return isSequence(true);
    }

    public int isPotentialSequence(){
        return isSequence(false);
    }

    public int isSequence (boolean isPure){
        int count = 0;

        List<Card> tempCards = new ArrayList<>(hand);

        Collections.sort(tempCards, (a, b)->Card.compareSuits(a, b));

        if (isPure){
            isSequenceLoop(tempCards, true);
            isSequenceLoop(tempCards, false);
        } else {
            isPotenSeqLoop(tempCards, 3, true);
            isPotenSeqLoop(tempCards, 3, false);
            isPotenSeqLoop(tempCards, 2, false);
            isPotenSeqLoop(tempCards, 1, false);
        }
        return count;
    }

    public int isPotenSeqLoop (List<Card> tempCards, int diff, boolean isSeqOfFour){
        int count = 0;
        int startIter = 1;
        List<List<Card>> tempSeq = possibleSeqOfThree;
        if (isSeqOfFour){
            startIter = 2;
            tempSeq = possibleSeqOfFourCountThree;
        }
        if (!isSeqOfFour && diff == 3){
            tempSeq = possibleSeqOfFourCountTwo;
        }
        for (int i = startIter; i < tempCards.size(); i++) {
            if (isSeqOfFour && isSequenceOfTwo(tempCards.get(i - 2), tempCards.get(i), diff)) {
                //System.out.println("Potential 4 - " + tempCards.get(i-2) + " - " + tempCards.get(i-1) + " - "+tempCards.get(i));
                if (isSequenceOfTwo(tempCards.get(i-2), tempCards.get(i-1), 0) || isSequenceOfTwo(tempCards.get(i), tempCards.get(i-1), 0)){
                    continue;
                }

                List<Card> tempPotenSeqlist = new ArrayList<>();
                tempPotenSeqlist.add(tempCards.get(i-2));
                tempPotenSeqlist.add(tempCards.get(i-1));
                tempPotenSeqlist.add(tempCards.get(i));

                tempSeq.add(tempPotenSeqlist);

                tempCards.remove(i);
                tempCards.remove(i-1);
                tempCards.remove(i-2);

                return ++count;
            } else if (!isSeqOfFour && isSequenceOfTwo(tempCards.get(i - 1), tempCards.get(i), diff)) {
                //System.out.println("Potential 3 - " + tempCards.get(i-1) + " - "+tempCards.get(i));

                List<Card> tempPotenSeqlist = new ArrayList<>();
                tempPotenSeqlist.add(tempCards.get(i-1));
                tempPotenSeqlist.add(tempCards.get(i));

                possibleSeqOfThree.add(tempPotenSeqlist);

                tempCards.remove(i);
                tempCards.remove(i-1);

                count++;
            }
        }
        return count;
    }

    public int isSequenceLoop (List<Card> tempCards, boolean isSeqOfFour) {
        int count = 0;
        int startIter = 2;
        List<List<Card>> tempSeq = pureSeqOfThree;
        if (isSeqOfFour){
            startIter = 3;
            tempSeq = pureSeqOfFour;
        }
        for (int i = startIter; i < tempCards.size(); i++) {
            if (isSeqOfFour && isSequenceOfFour(tempCards.get(i - 3), tempCards.get(i - 2), tempCards.get(i - 1), tempCards.get(i))) {
                //System.out.println("Pure 4 - " + tempCards.get(i - 3) + " - " + tempCards.get(i - 2) + " - " + tempCards.get(i - 1) + " - " + tempCards.get(i));

                List<Card> tempPureSeqlist = new ArrayList<>();
                tempPureSeqlist.add(tempCards.get(i - 3));
                tempPureSeqlist.add(tempCards.get(i - 2));
                tempPureSeqlist.add(tempCards.get(i - 1));
                tempPureSeqlist.add(tempCards.get(i));

                tempSeq.add(tempPureSeqlist);

                tempCards.remove(i);
                tempCards.remove(i - 1);
                tempCards.remove(i - 2);
                tempCards.remove(i - 3);

                return ++count;
            } else if (!isSeqOfFour && isSequenceOfThree(tempCards.get(i - 2), tempCards.get(i - 1), tempCards.get(i))) {
                //System.out.println("Pure 3 - " + tempCards.get(i - 2) + " - " + tempCards.get(i - 1) + " - " + tempCards.get(i));

                List<Card> tempPureSeqlist = new ArrayList<>();
                tempPureSeqlist.add(tempCards.get(i - 2));
                tempPureSeqlist.add(tempCards.get(i - 1));
                tempPureSeqlist.add(tempCards.get(i));

                tempSeq.add(tempPureSeqlist);

                tempCards.remove(i);
                tempCards.remove(i - 1);
                tempCards.remove(i - 2);
                count++;
                i = 2;
                continue;
            }
        }
        hand = tempCards;
        return count;
    }

    public int isSet (){
        int count = 0;
        List<Card> tempCards = new ArrayList<>(hand);

        Collections.sort(tempCards, (a, b)->Card.compareValue(a, b));

        for (int i=2; i<tempCards.size(); i++){
            if ( isSetOfThree(tempCards.get(i-2), tempCards.get(i-1), tempCards.get(i))){
                count++;
            }
        }
        return count;
    }

    public static void main (String args[]){
        Rummy r = new Rummy();
        CardsDeck c = new CardsDeck();
        c.shuffle(2, 13);
        r.hand = c.cards;
        Collections.sort(r.hand, (a, b)->Card.compareSuits(a, b));
        System.out.println(r.hand);
        r.isPureSequence();
        r.isSet();
        r.isPotentialSequence();
        System.out.println("All Pure 4 - " + r.pureSeqOfFour);
        System.out.println("All Pure 3 - " + r.pureSeqOfThree);
        System.out.println("All potential 4 (3)- " + r.possibleSeqOfFourCountThree);
        System.out.println("All potential 4 (2)- " + r.possibleSeqOfFourCountTwo);
        System.out.println("All potential 3 - " + r.possibleSeqOfThree);
    }
}
