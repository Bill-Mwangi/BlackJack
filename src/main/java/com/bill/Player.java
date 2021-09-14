package com.bill;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private int cardCount;
    private final List<Card> cardsList;
    private int stake;
    private boolean ace;

    public Player() {
        cardsList = new ArrayList<>();
    }

    public Player(Card card) {
        cardsList = new ArrayList<>(4);
        cardsList.add(card);
        cardCount += card.getValue();
    }

    public void addCard(Card card) {
        cardsList.add(card);
        cardCount += card.getValue();
        if (card.getName().equals("Ace")) ace = true;
    }

    public int getCardCount() { return cardCount; }

    public void adjustCardCount() { this.cardCount -= 10; }

    public List<Card> getCardsList() { return cardsList; }

    public void enterStake() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the amount to wager:");
        this.stake = in.nextInt();
    }

    public int getStake() { return stake; }

    public void setStake(int stake) { this.stake = stake; }

    public boolean hasAce() { return ace; }

//    public int getHandValue() {
//        return cardsList.stream().mapToInt(Card::getValue).sum();
//    }

}
