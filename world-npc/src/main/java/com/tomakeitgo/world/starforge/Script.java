package com.tomakeitgo.world.starforge;

import com.tomakeitgo.world.Coordinate;
import com.tomakeitgo.world.Picker;
import com.tomakeitgo.world.Spiral;

import java.util.List;
import java.util.Set;

public class Script {
    private final Picker picker;

    public Script(Picker picker) {
        this.picker = picker;
    }

    public Result next(
            Location location,
            Action lastAction
    ) {
        Action action = pickAction(lastAction);

        if (action == Action.MOVE) {
            var result = Spiral.next(location.ring(), location.offset());
            return new Result(
                    new Location(result.ring(), result.offset()),
                    result.moveToMake(),
                    action
            );
        } else {
            return new Result(location, new Coordinate(0, 0, 0), action);
        }
    }

    private Action pickAction(Action lastAction) {
        if (Set.of(Action.CREATE_DWARF, Action.CREATE_PLANET, Action.CREATE_STAR).contains(lastAction)) {
            return picker.pick(
                    List.of(
                            Action.CREATE_PLANET,
                            Action.CREATE_DWARF,
                            Action.MOVE
                    ),
                    List.of(20, 60, 20)
            );
        } else {
            return picker.pick(
                    List.of(
                            Action.CREATE_STAR,
                            Action.MOVE
                    ),
                    List.of(1, 14)
            );
        }
    }

    public record Location(int ring, int offset) {
    }

    public record Result(
            Location location,
            Coordinate direction,
            Action newAction
    ) {
    }

    public enum Action {
        CREATE_STAR(1),
        CREATE_PLANET(2),
        CREATE_DWARF(3),
        MOVE(4),
        ;

        private final int id;

        Action(int id) {
            this.id = id;
        }

        public int id(){
            return id;
        }

        public static Action fromId(int id) {
            for (Action action : values()) {
                if (action.id == id) return action;
            }
            throw new IllegalArgumentException("Invalid action id: " + id);
        }
    }

    public static void main(String[] args) {

        var script = new Script(new Picker());

        var lastMove = script.next(new Location(0, 0), Action.MOVE);

        for (int i = 0; i < 100; i++) {

            lastMove = script.next(lastMove.location(), lastMove.newAction());
        }
    }
}
