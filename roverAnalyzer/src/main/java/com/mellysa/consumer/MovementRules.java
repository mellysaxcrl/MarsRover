package com.mellysa.consumer;

import com.mellysa.consumer.Position;

import java.util.HashMap;
import java.util.Map;

public class MovementRules {
    @FunctionalInterface
    interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    private static final Map<Character, TriFunction<Integer, Integer, Character, Position>> movementRules = new HashMap<>();

    static {
        movementRules.put('f', (x, y, dir) -> {
            int newX = x;
            int newY = y;
            if (dir == 'N') newY += 1;
            else if (dir == 'S') newY -= 1;
            else if (dir == 'E') newX += 1;
            else if (dir == 'W') newX -= 1;
            return new Position(newX, newY, dir);
        });

        movementRules.put('b', (x, y, dir) -> {
            int newX = x;
            int newY = y;
            if (dir == 'N') newY -= 1;
            else if (dir == 'S') newY += 1;
            else if (dir == 'E') newX -= 1;
            else if (dir == 'W') newX += 1;
            return new Position(newX, newY, dir);
        });

        movementRules.put('r', (x, y, dir) -> {
            if (dir == 'N') dir = 'E';
            else if (dir == 'S') dir = 'W';
            else if (dir == 'E') dir = 'S';
            else if (dir == 'W') dir = 'N';
            return new Position(x, y, dir);
        });

        movementRules.put('l', (x, y, dir) -> {
            if (dir == 'N') dir = 'W';
            else if (dir == 'S') dir = 'E';
            else if (dir == 'E') dir = 'N';
            else if (dir == 'W') dir = 'S';
            return new Position(x, y, dir);
        });
    }

    public static Position applyMovement(Position position, char command) {
        TriFunction<Integer, Integer, Character, Position> movementRule = movementRules.get(command);

        if (movementRule != null) {
            return movementRule.apply(position.getX(), position.getY(), position.getDirection());
        }

        return position;
    }
}
