package com.tomakeitgo.world;

import com.tomakeitgo.world.data.Coordinate;
import com.tomakeitgo.world.data.Player;
import com.tomakeitgo.world.data.Ship;
import com.tomakeitgo.world.data.ShipLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoveShip {

    public ErrorOrValue<Result> doIt(MoveRequest request) {
        if (!Objects.equals(
                request.player().id(),
                request.ship().playerId()
        )) {
            return new ErrorOrValue<>(
                    new Errors().add("playerId", "Player doesn't own ship")
            );
        }

        return new ErrorOrValue<>(new Result(
                request.currentLocation(),
                request.currentLocation().adjust(request.toMove())
        ));
    }

    public record MoveRequest(
            Player player,
            Ship ship,
            ShipLocation currentLocation,
            Coordinate toMove
    ) {
    }

    public record Result(
            ShipLocation currentLocation,
            ShipLocation newLocation
    ) {
    }

    public static class ErrorOrValue<T> {
        private final Errors error;
        private final T result;

        public ErrorOrValue(Errors error) {
            this.error = error;
            this.result = null;
        }

        public ErrorOrValue(T result) {
            this.error = null;
            this.result = result;
        }

        public boolean hasErrors() {
            return error != null;
        }

        public Errors getError() {
            return error;
        }

        public T getResult() {
            return result;
        }
    }

    public static class Errors {
        private final ArrayList<Error> errors = new ArrayList<>();

        public record Error(
                String field,
                String message
        ) {
        }

        public Errors add(String field, String message) {
            errors.add(new Error(field, message));
            return this;
        }

        public List<Error> getErrors() {
            return errors;
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }
    }
}
