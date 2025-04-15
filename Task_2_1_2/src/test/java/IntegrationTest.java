import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

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

            Thread.sleep(20000);

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
            Assertions.assertTrue(managerLogs.contains("true"), "Manager didn't find the answer");
        }
    }

    @Test
    public void oneWorkerWillDrop() throws Exception {
        ArrayList<String> command = new ArrayList<>();
        command.addAll(Arrays.asList("java", "-cp", "build/classes/java/main", "ru.nsu.pozhidaev.manager.Main"));
        for(int i = 2; i < 100; i++){
            for (int j = 2; j < 100; j++){
                command.add(Integer.toString(j*i));
            }
        }

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
                        .withCommand(command.toArray(new String[0]))
        ) {
            worker2.start();
            worker1.start();
            manager.start();

            String worker1Logs;
            while(true) {
                String manLog = manager.getLogs();
                if(manLog.contains("send task")) {
                    worker1Logs = worker1.getLogs();
                    worker1.stop();
                    break;
                }
            }

            Thread.sleep(20000);

            String managerLogs = manager.getLogs();
            String worker2Logs = worker2.getLogs();


            System.out.println("Manager logs:");
            System.out.println(managerLogs);
            System.out.println("\nWorker2 logs:");
            System.out.println(worker2Logs);
            System.out.println("\nWorker1 logs:");
            System.out.println(worker1Logs);

            Assertions.assertTrue(worker2Logs.contains("get task: [4, 6"), "Worker didn't receive part");
            Assertions.assertTrue(worker2Logs.contains("get task: [102, 153"), "Worker didn't receive part");
        }
    }

    @Test
    public void testManagerAfterManager() throws Exception {
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
                                "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
                GenericContainer<?> manager2 = new GenericContainer<>("task_2_1_2-manager:latest")
                        .withNetwork(NETWORK)
                .withNetworkAliases("manager")
                .withCommand("java", "-cp", "build/classes/java/main",
                        "ru.nsu.pozhidaev.manager.Main",
                        "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
        ) {
            worker1.start();
            worker2.start();
            manager.start();

            Thread.sleep(20000);

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
            Assertions.assertTrue(managerLogs.contains("true"), "Manager didn't find the answer");

            manager2.start();

            Thread.sleep(20000);
            String manager2Logs = manager2.getLogs();

            Assertions.assertTrue(manager2Logs.contains("Find worker"), "Manager didn't find any workers");
            Assertions.assertTrue(manager2Logs.contains("true"), "Manager didn't find the answer");
        }
    }
}
