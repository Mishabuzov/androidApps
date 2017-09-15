package com.fsep.sova.local_events;

public class Client {
    public static void main(String[] args) {
        InchAdapter inchAdapter = new InchAdapter(100.0);
        inchAdapter.move(55.4);
        inchAdapter.getPosition();
        inchAdapter.move(-232.1);
        inchAdapter.getPosition();
    }
}
