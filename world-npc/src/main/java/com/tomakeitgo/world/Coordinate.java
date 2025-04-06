package com.tomakeitgo.world;

public record Coordinate(int x, int y, int z) {
    public Coordinate add(Coordinate other) {
        return new Coordinate(
                x + other.x,
                y + other.y,
                z + other.z
        );
    }
}
