import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

@Testcontainers
public class IntegrationTest {

    @Container
    public GenericContainer<?> worker1 = new GenericContainer<>("task_2_1_2-worker1:latest")
            .withNetworkMode("bridge")
            .withExposedPorts(5005)
            .withStartupTimeout(Duration.ofSeconds(20));

    @Container
    public GenericContainer<?> worker2 = new GenericContainer<>("task_2_1_2-worker2:latest")
            .withNetworkMode("bridge")
            .withExposedPorts(5005)
            .withStartupTimeout(Duration.ofSeconds(20));

    @Container
    public GenericContainer<?> manager = new GenericContainer<>("task_2_1_2-manager:latest")
            .withNetworkMode("bridge")
            .dependsOn(worker1, worker2)
            .withStartupTimeout(Duration.ofSeconds(20));

    @Test
    public void testManagerFindsWorkers() throws Exception {
        Thread.sleep(5000);
        String logs = manager.getLogs();
        System.out.println("Manager logs:");
        System.out.println(logs);

        assert logs.contains("Listening") : "Manager didn't listen";
    }
}