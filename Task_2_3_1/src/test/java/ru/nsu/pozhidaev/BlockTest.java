package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class BlockTest {

    @ParameterizedTest
    @MethodSource("provideBlocks")
    void setAndGet(Block block) {
        block.setCoordinateX(3);
        block.setCoordinateY(4);
        assertEquals(3, block.getCoordinateX());
        assertEquals(4, block.getCoordinateY());
    }

    static Stream<Block> provideBlocks() {
        return Stream.of(
                new SnakePart(2, 2),
                new Treat(),
                new Wall(2, 2)
        );
    }
}
