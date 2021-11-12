package com.furkanaxx34.gmail.conshashing;

import org.checkerframework.common.value.qual.IntRange;

import java.io.InputStream;
import java.util.Map;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        readLogConfig();

        ConsistentHashing consistentHashing = new ConsistentHashing(3000);
        for (Node node : Node.nodes) {
            consistentHashing.addNode(node);
        }

        Random random = new Random();

        IntStream.range(0, 3000)
                .forEach(
                        i -> consistentHashing.addData(Map.entry(String.format("player-%s-balance", i), random.nextInt(1000)))
                );

        IntStream.range(0, 3000)
                .forEach(
                        i -> consistentHashing.getData(String.format("player-%s-balance", i))
                );

        for (Node node : Node.nodes) {
            System.out.printf("%s size: %s%n", node.getName(), node.size());
        }
    }


    static void readLogConfig() {
        try {
            InputStream stream = Main.class.getClassLoader().
                    getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(stream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Logger getLogger() {
        return Logger.getLogger(Main.class.getName());
    }

}
