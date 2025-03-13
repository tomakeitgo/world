package com.tomakeitgo.world.data;

public record ShipLocation(Coordinate coordinate, String shipId) {
    public ShipLocation adjust(Coordinate offset){
        return new ShipLocation(coordinate.add(offset), shipId);
    }
}
