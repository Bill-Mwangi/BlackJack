package com.bill;
import java.util.*;

public class Deck {
    List<Card> cardList;

    public Deck() {
        Card king = new Card("King", 10);
        Card queen = new Card("Queen", 10);
        Card jack = new Card("Jack", 10);
        Card ace = new Card("Ace", 11);
        Card two = new Card("2", 2);
        Card three = new Card("3", 3);
        Card four = new Card("4", 4);
        Card five = new Card("5", 5);
        Card six = new Card("6", 6);
        Card seven = new Card("7", 7);
        Card eight = new Card("8", 8);
        Card nine = new Card("9", 9);
        Card ten = new Card("10", 10);
        cardList = new ArrayList<>(Arrays.asList(king, queen, jack, ace, two, three, four, five, six, seven, eight, nine, ten));
        Collections.shuffle(cardList);
    }

    public Card getCard() {
        return cardList.remove(cardList.size() - 1);
    }
}
