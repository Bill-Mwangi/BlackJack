package com.bill;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public static void createPlayers(Deck deck, int number) {
        List<Player> playerList = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            Player p = new Player();
            playerList.add(p);
        }
        deal(deck, playerList);
    }

    /***
     * Start the game by dealing the players two cards face up
     * and the dealer 2 cards with only one face up.
     */
    public static void deal(Deck deck, List<Player> playerList) {
        Card card1;
        Dealer dealer = new Dealer();
        playerList.forEach(Player::enterStake);
        Scanner in = new Scanner(System.in);

        for (int i = 1; i < playerList.size()+1; i++) {
            Card card = deck.getCard();
            playerList.get(i-1).addCard(card);
            System.out.println("Player " +i +": " +card.getName());
        }

        card1 = deck.getCard();
        dealer.addCard(card1);
        System.out.println("Dealer: " +card1.getName());
        System.out.println();

        for (int i = 1; i < playerList.size()+1; i++) {
            Card card = deck.getCard();
            playerList.get(i-1).addCard(card);
            System.out.println("Player " +i +": " +card.getName());
        }
        dealer.addCard(deck.getCard());
        System.out.println();

        playerList.forEach(player -> {
            List<Card> cardList = player.getCardsList();
            if (cardList.get(0).getValue() > 10 && cardList.get(1).getValue() > 10) {
                System.out.println("BlackJack!!! You won 1.5 times your stake.");
                System.exit(0);
            }
        });

        System.out.println("Starting with the left most player, you can choose to either\n" +
                " hit (H), double-down (DD), split (SP), stand (ST) or surrender (SU).\n" +
                "Enter the code enclosed in the brackets to perform the respective action.");

        playerList.forEach(e -> {
            String choice = in.next();
            switch (choice.toUpperCase()) {
                case "H" : hit(e, deck);
                break;
                case "DD": doubleDown(e, deck);
                break;
                case "SP": split(e, deck);
                break;
                case "ST": stand();
                break;
                case "SU": surrender(e);
                break;
            }
        });

        System.out.println("Dealer's cards:");
        List<Card> dealersCards = dealer.getCardsList();
        dealersCards.forEach(System.out::println);
        System.out.println();

        while(dealer.getCardCount() < 17) {
            hit(dealer, deck);
        }

        System.out.println("Final dealer's cards:");
        dealer.getCardsList().forEach(System.out::println);
        int dealersTotal = dealersCards.stream().mapToInt(Card::getValue).sum();
        System.out.println("Dealer's hand total: " +dealersTotal +"\n");

        System.out.println("Settling Scores");
        playerList.forEach(player -> {
            if (dealersTotal < 21) {
                int playerHandValue = player.getCardCount();
                if (playerHandValue < dealersTotal || playerHandValue > 21) {
                    player.setStake(0);
                    System.out.println("Score: " +playerHandValue +" You bust, Try your luck later.");
                } else if (playerHandValue == dealersTotal) System.out.println("A tie, you get to keep your money.");
                else{
                    player.setStake(player.getStake()*2);
                    System.out.println("Score: " +playerHandValue +" Nice game, you won. Payout: " +player.getStake());
                }} else {
                player.setStake(player.getStake()*2);
                    if (player.getStake() > 0) System.out.println("Dealer busts, you win!! Payout: " +player.getStake());
                }
        });
    }

    /**
     * Take another card
     */
    public static void hit(Player player, Deck deck) {
        Card card = deck.getCard();
        player.addCard(card);
        System.out.println("New hand:");
        player.getCardsList().forEach(System.out::println);
        if (player.getCardCount() > 21 && !player.hasAce()) {
            System.out.println("You bust");
            player.setStake(0);
        } else if (player.getCardCount() > 21 && player.hasAce()) player.adjustCardCount();
        System.out.println();
    }

    /**
     * Increase original bet by 100% and take one more card
     */
    public static void doubleDown(Player player, Deck deck) {
        player.setStake(player.getStake()*2);
        System.out.println("Wager doubled");
        hit(player, deck);
    }

    /**
     * Take no more cards
     */
    public static void stand(){
        System.out.println("Next player to play.\n");
    }

    /**
     * Create two hands from a starting hand where both cards are the same value.
     * Each new hand gets another card so that a player has two starting hands.
     * Requires a second similar bet on the other hand.
     */
    public static void split(Player player, Deck deck) {
        List<Card> cardsList = player.getCardsList();
        if (cardsList.get(0).getName().equals(cardsList.get(1).getName())) {
            Player pn = new Player(cardsList.remove(1));
            pn.setStake(player.getStake());
            hit(pn, deck);
            hit(player, deck);
        } else System.out.println("Split not possible");
    }

    /**
     * Forfeit half of the bet and end the hand immediately.
     * The option is only available as the first decision
     */
    public static void surrender(Player player) {
        System.out.println("You forfeit half of your stake");
        player.setStake(player.getStake()/2);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Deck deck = new Deck();
        System.out.println("Welcome to a game of BlackJack\nHow many players are in the house?");
        createPlayers(deck, in.nextInt());
    }
}
