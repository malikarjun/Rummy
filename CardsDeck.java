package MiniProject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CardsDeck
{

    List<Card> cards;

    public CardsDeck() {
        cards = new ArrayList<>();
    }

    public List<Card> getDeck (int numberOfDecks){
        List<Card> newCards = new ArrayList<>();
        for (int k=0; k<numberOfDecks; k++) {
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 4; j++) {
                    newCards.add(new Card(i, j));
                }
            }
        }
        return newCards;
    }

    public void shuffle (int numberOfDecks, int numberOfCards){
        cards = getDeck(numberOfDecks);
        Collections.shuffle(cards);
        List<Card> tempCards = new ArrayList<>();
        for (int i=0;i<numberOfCards;i++){
            tempCards.add(cards.get(i));
        }
        cards = tempCards;
        System.out.println(cards);
    }

    public static void main(String args[]){
        CardsDeck c = new CardsDeck();
        c.shuffle(2, 13);
        System.out.println(c.cards);
    }

}
