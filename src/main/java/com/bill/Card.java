package com.bill;

public class Card implements Comparable<Card> {
    private final String name;
    private final int value;
    int multiple;

    public Card(String name, int value) {
        this.name = name;
        this.value = value;
        multiple = 4;
    }

    public int compareTo(Card a) {
       return this.value - a.value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return this.name;
    }
}
