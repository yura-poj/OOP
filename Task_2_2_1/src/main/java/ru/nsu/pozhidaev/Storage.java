package ru.nsu.pozhidaev;

public class Storage {
    private final int volume;
    private int[] pizzas;
    private int size;
    private int removeQueue;
    private int currentPosition;

    public Storage(int volume) {
        this.volume = volume;
        pizzas = new int[volume];
        size = 0;
        removeQueue = 0;
        currentPosition = 0;
    }

    public synchronized void bringPizza(int index) {
        if(volume > size) {
            pizzas[size] = index;
            size++;
            currentPosition++;
            currentPosition %= volume;
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                close();
            }
        }
    }

    public int[] getPizzas(int amount) {
        int nums = Math.min(size, amount);
        int[] baggage = new int[nums];
        for(int i = 0; i < nums; i++) {
            baggage[i] = pizzas[removeQueue];
            removeQueue++;
            removeQueue %= volume;
            size--;
            notify();
        }
        return baggage;
    }

    private void close() {
        
    }

}
