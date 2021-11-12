package com.furkanaxx34.gmail.conshashing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class Node {

    public static final List<Node> nodes = List.of(
            new Node("node-a"),
            new Node("node-b"),
            new Node("node-c"),
            new Node("node-d"),
            new Node("node-e"),
            new Node("node-f"),
            new Node("node-g"),
            new Node("node-h"),
            new Node("node-i"),
            new Node("node-j")
    );

    @Getter
    private final String name;

    private final HashMap<String, Object> dataStore = new HashMap<>();

    public void addData(HashMap.Entry<String, Object> data) {
        dataStore.put(data.getKey(), data.getValue());
    }

    public Object getData(String key) {
        return this.dataStore.get(key);
    }

    public int size() {
        return dataStore.size();
    }
}
