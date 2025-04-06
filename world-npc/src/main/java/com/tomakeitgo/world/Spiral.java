package com.tomakeitgo.world;

public class Spiral {
    public static Coordinate[] OPTIONS = {
            new Coordinate(1, 0, 0),
            new Coordinate(0, -1, 0),
            new Coordinate(-1, 0, 0),
            new Coordinate(0, 1, 0),
    };

    public static Result next(int ring, int offset) {
        if (ringSize(ring) == offset) {
            return new Result(ring + 1, 0, new Coordinate(-1, 1, 0));
        }

        return new Result(ring, offset + 1, OPTIONS[offset / (ring * 2)]);
    }

    private static int ringSize(int ring) {
        return 2 * 4 * ring;
    }

    public record Result(
            int ring,
            int offset,
            Coordinate moveToMake
    ) {
    }
}
