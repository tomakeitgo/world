package com.tomakeitgo.world.data;

public record Coordinate(int x, int y, int z) {
    String toStorageFormat() {
        return String.format("%s:%s:%s", x, y, z);
    }

    public Coordinate add(Coordinate other) {
        return new Coordinate(x + other.x, y + other.y, z + other.z);
    }

    static Coordinate fromStorageFormat(String storageFormat) {
        String[] parts = storageFormat.split(":");
        return new Coordinate(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2])
        );
    }
}
