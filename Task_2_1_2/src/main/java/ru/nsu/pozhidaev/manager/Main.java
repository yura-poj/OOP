package ru.nsu.pozhidaev.manager;

import java.util.ArrayList;

/**
 * The Main class for the Manager component of the distributed system.
 * 
 * <p>This class serves as the entry point for the Manager application. It:
 * <ul>
 *   <li>Parses command-line arguments as numbers to be processed</li>
 *   <li>Creates a Manager instance</li>
 *   <li>Passes the numbers to the Manager for processing</li>
 *   <li>Outputs the result of the processing</li>
 * </ul>
 * </p>
 * 
 * <p>Usage:
 * <pre>
 * java ru.nsu.pozhidaev.manager.Main [number1] [number2] ... [numberN]
 * </pre>
 * </p>
 * 
 * <p>Example:
 * <pre>
 * java ru.nsu.pozhidaev.manager.Main 2 3 4 5 6 7 8 9 10 11
 * </pre>
 * </p>
 */
public class Main {
    /**
     * The main method that serves as the entry point for the Manager application.
     * 
     * @param args Command-line arguments representing numbers to be processed
     */
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            for (int i = 0; i < args.length; i++) {
                numbers.add(Integer.parseInt(args[i]));
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: one of the arguments is not a valid number.");
            return;
        }
        Manager manager = new Manager();
        boolean result =  manager.work(numbers.stream().mapToInt(Integer::intValue).toArray());
        System.out.println(result);
    }
}
