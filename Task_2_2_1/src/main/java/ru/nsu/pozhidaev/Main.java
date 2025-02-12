package ru.nsu.pozhidaev;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Bakery bakery = new Bakery(new int[] {2,2}, new int[] {2,2}, 3);
        bakery.start();

        int numberOfOrders = 5;
        for (int i = 0; i < numberOfOrders; i++) {
            bakery.order();
        }
        sleep(2500);
        bakery.close();
    }
}
