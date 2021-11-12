package com.furkanaxx34.gmail.conshashing;

import com.google.common.hash.Hashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ConsistentHashing {

    private final List<Node> nodes;
    private final List<BigInteger> keys;
    private final int totalSlots;

    public ConsistentHashing(int totalSlots) {
        this.nodes = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.totalSlots = totalSlots;
    }

    public BigInteger addNode(Node node) {
        if (this.keys.size() == totalSlots)
            throw new ArrayStoreException("There is not an empty slot.");

        BigInteger key = hashFunc(node.getName(), totalSlots);
        int index = Collections.binarySearch(keys, key);

        if (index < 0)
            index = Math.abs(index) - 1;

        if (index > 0 && keys.get(index).equals(key)) {
            throw new ArrayStoreException("Collision Detected");
        }

        this.nodes.add(index, node);
        this.keys.add(index, key);
        return key;
    }

    public void addData(HashMap.Entry<String, Object> data) {
        BigInteger key = hashFunc(data.getKey(), totalSlots);
        // +1 -> Go to right of index
        int index = Collections.binarySearch(keys, key) + 1;

        if (index < 0)
            index = Math.abs(index);

        if (index == this.keys.size())
            index = this.keys.size() - 1;

        Node node = this.nodes.get(index);
        node.addData(data);
        Main.getLogger().info(String.format("Data inserted into the '%s' node successfully.", node.getName()));
    }

    public Object getData(String dataKey) {
        BigInteger key = hashFunc(dataKey, totalSlots);
        int index = Collections.binarySearch(keys, key);

        if (index < 0)
            index = Math.abs(index) - 1;

        if (index == this.keys.size())
            index = this.keys.size() - 1;

        Node node = this.nodes.get(index);
        return node.getData(dataKey);
    }

    private BigInteger hashFunc(String key, int totalSlots) {
        String sha = Hashing.sha256()
                .hashString(key, StandardCharsets.UTF_8).toString();

        return new BigInteger(sha, 16).remainder(BigInteger.valueOf(totalSlots));
    }
}
