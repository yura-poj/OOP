package ru.nsu.pozhidaev.manager;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            for (int i = 0; i < args.length; i++) {
                System.out.println(args[i]);
                numbers.add(Integer.parseInt(args[i]));
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: один из аргументов не является корректным числом.");
            return;
        }
        Manager manager = new Manager();
        boolean result =  manager.work(numbers.stream().mapToInt(Integer::intValue).toArray());
        System.out.println(result);
    }
}
