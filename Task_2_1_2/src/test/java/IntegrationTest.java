import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import java.time.Duration;

public class IntegrationTest {

    private static final Network NETWORK = Network.newNetwork();

    GenericContainer<?> worker1 = new GenericContainer<>("task_2_1_2-worker1:latest")
            .withNetwork(NETWORK)
            .withNetworkAliases("worker1")
            .withExposedPorts(5005)
            .withCommand("java", "-cp", "build/classes/java/main", "ru.nsu.pozhidaev.worker.Main");
    GenericContainer<?> worker2 = new GenericContainer<>("task_2_1_2-worker2:latest")
            .withNetwork(NETWORK)
            .withNetworkAliases("worker2")
            .withExposedPorts(5005)
            .withCommand("java", "-cp", "build/classes/java/main", "ru.nsu.pozhidaev.worker.Main");

    public void testManagerFindsWorkers() throws Exception {
        try (
                GenericContainer<?> manager = new GenericContainer<>("task_2_1_2-manager:latest")
                        .withNetwork(NETWORK)
                        .withNetworkAliases("manager")
                        .withExposedPorts(5005)
                        .withStartupTimeout(Duration.ofSeconds(20))
                        .withCommand("java", "-cp", "build/classes/java/main",
                                "ru.nsu.pozhidaev.manager.Main",
                                "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
        ) {
            worker1.start();
            worker2.start();
            while (!worker1.isRunning()) {
                Thread.sleep(100);
            }
            manager.start();

            Thread.sleep(5000);

            String managerLogs = manager.getLogs();
            System.out.println("Manager container logs:");
            System.out.println(managerLogs);

            Assertions.assertTrue(managerLogs.contains("Find worker"), "Manager didn't find any workers");
        }
    }

    @Test
    public void workerLog() throws InterruptedException {
        worker1.start();
        String workerLogs = worker1.getLogs();
        System.out.println(workerLogs);
        Thread.sleep(2000);
        assert workerLogs.contains("Listening");
    }
}