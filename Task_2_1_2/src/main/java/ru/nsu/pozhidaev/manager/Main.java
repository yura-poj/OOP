package ru.nsu.pozhidaev.manager;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        boolean result =  manager.work(new int[] {1,4,3});
        System.out.println(result);
    }
}
