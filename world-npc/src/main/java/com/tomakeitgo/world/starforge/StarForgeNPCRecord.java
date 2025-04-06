package com.tomakeitgo.world.starforge;

public record StarForgeNPCRecord(
        String id,
        String playerId,
        String shipId,
        int ring,
        int offset,
        int actionId
) {
}
