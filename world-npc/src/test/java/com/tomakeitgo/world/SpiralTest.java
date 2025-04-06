package com.tomakeitgo.world;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpiralTest {

    @Test
    void test() {
        Spiral.Result item = Spiral.next(0, 0);

        assertEquals(new Coordinate(-1, 1, 0), item.moveToMake());

        assertEquals(new Coordinate(1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());

        assertEquals(new Coordinate(0, -1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(0, -1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());

        assertEquals(new Coordinate(-1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(-1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());

        assertEquals(new Coordinate(0, 1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(0, 1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());

        assertEquals(new Coordinate(-1, 1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());

        assertEquals(new Coordinate(1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());

        assertEquals(new Coordinate(0, -1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(0, -1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(0, -1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(0, -1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());

        assertEquals(new Coordinate(-1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(-1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(-1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(-1, 0, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());

        assertEquals(new Coordinate(0, 1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(0, 1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(0, 1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());
        assertEquals(new Coordinate(0, 1, 0), (item = Spiral.next(item.ring(), item.offset())).moveToMake());

    }

}