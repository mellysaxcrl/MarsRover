package com.mellysa.consumer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Position {
    private int x;
    private int y;
    private char direction;
    private Pair<Integer, Integer> coordinates;

    public Position(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.coordinates = new Pair<>(x, y);
    }
}

@Data
@NoArgsConstructor
class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}