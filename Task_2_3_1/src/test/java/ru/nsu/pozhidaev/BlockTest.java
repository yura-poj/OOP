package ru.nsu.pozhidaev;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {

    @ParameterizedTest
    @MethodSource("provideBlocks")
    void setAndGet(Block block) {
        block.setX(3);
        block.setY(4);
        assertEquals(3, block.getX());
        assertEquals(4, block.getY());
    }

    static Stream<Block> provideBlocks() {
        return Stream.of(
                new SnakePart(2,2),
                new Treat(),
                new Wall(2,2)
        );
    }
}
