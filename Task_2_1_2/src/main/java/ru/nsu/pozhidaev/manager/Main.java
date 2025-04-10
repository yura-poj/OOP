package ru.nsu.pozhidaev.manager;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        boolean result =  manager.work(new int[] {6,4,8});
        System.out.println(result);
    }
}
