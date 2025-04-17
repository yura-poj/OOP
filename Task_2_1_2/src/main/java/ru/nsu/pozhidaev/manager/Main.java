package ru.nsu.pozhidaev.manager;

import java.util.ArrayList;

/**
 * Main class for the Manager component of the distributed prime number detection system.
 * 
 * <p>This class serves as the entry point for the Manager application.
 * It processes command line arguments as numbers to check for primality,
 * creates a Manager instance, and coordinates the distributed processing.
 * 
 * <p>The Manager will:
 * - Parse numbers from command line arguments
 * - Create a Manager instance to coordinate workers
 * - Distribute the numbers among available workers
 * - Collect and process results from workers
 * - Output the final result
 * 
 * <p>Usage:
 * java ru.nsu.pozhidaev.manager.Main [number1] [number2] ... [numberN]
 * Example:
 * java ru.nsu.pozhidaev.manager.Main 2 3 4 5 6 7 8 9 10 11
 */
public class Main {
    /**
     * The main entry point for the Manager application.
     * Processes command line arguments as numbers and coordinates their processing.
     * 
     * <p>The method:
     * 1. Parses numbers from command line arguments
     * 2. Creates a Manager instance
     * 3. Passes the numbers for processing
     * 4. Outputs the result
     * 
     * <p>@param args Command line arguments representing numbers to be processed
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
        boolean result = manager.work(numbers.stream().mapToInt(Integer::intValue).toArray());
        System.out.println(result);
    }
}
