package ru.nsu.pozhidaev.worker;

/**
 * Main class for the Worker component of the distributed prime number detection system.
 * This class serves as the entry point for the Worker application.
 * It creates a Worker instance and starts its operation in a continuous loop.
 * The Worker will:
 * - Listen for Manager discovery messages
 * - Connect to the Manager when found
 * - Process tasks until receiving a termination signal
 * Usage:
 * java ru.nsu.pozhidaev.worker.Main
 */
public class Main {
    /**
     * The main entry point for the Worker application.
     * Creates a new Worker instance and starts its operation.
     * The Worker will run indefinitely until the application is terminated.
     * 
     * <p>@param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.work();
    }
}
