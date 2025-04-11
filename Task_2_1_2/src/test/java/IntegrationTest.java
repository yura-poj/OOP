import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

public class IntegrationTest {

    private static final Network NETWORK = Network.newNetwork();

    @Test
    public void testManagerFindsWorkers() throws Exception {
        try (
                GenericContainer<?> worker1 = new GenericContainer<>("task_2_1_2-worker1:latest")
                        .withNetwork(NETWORK)
                        .withNetworkAliases("worker1")
                        .withCommand("java", "-cp", "build/classes/java/main",
                                "ru.nsu.pozhidaev.worker.Main");
                GenericContainer<?> worker2 = new GenericContainer<>("task_2_1_2-worker2:latest")
                        .withNetwork(NETWORK)
                        .withNetworkAliases("worker2")
                        .withCommand("java", "-cp", "build/classes/java/main",
                                "ru.nsu.pozhidaev.worker.Main");
                GenericContainer<?> manager = new GenericContainer<>("task_2_1_2-manager:latest")
                        .withNetwork(NETWORK)
                        .withNetworkAliases("manager")
                        .withCommand("java", "-cp", "build/classes/java/main",
                                "ru.nsu.pozhidaev.manager.Main",
                                "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
        ) {
            worker1.start();
            worker2.start();
            manager.start();

            Thread.sleep(5000);

            String managerLogs = manager.getLogs();
            String worker1Logs = worker1.getLogs();
            String worker2Logs = worker2.getLogs();

            System.out.println("Manager logs:");
            System.out.println(managerLogs);
            System.out.println("\nWorker1 logs:");
            System.out.println(worker1Logs);
            System.out.println("\nWorker2 logs:");
            System.out.println(worker2Logs);

            Assertions.assertTrue(managerLogs.contains("Find worker"), "Manager didn't find any workers");
        }
    }
}