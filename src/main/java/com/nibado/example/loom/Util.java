package com.nibado.example.loom;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {
    public static Map<String, List<Thread>> getThreads() {
        return Thread.getAllStackTraces().keySet().stream()
            .collect(Collectors.groupingBy(t -> t.getThreadGroup().getName()));
    }
    public static void printThreads() {
        getThreads().forEach((key, value) -> {
            System.out.println(key);
            value.forEach(t -> System.out.printf("- %s\n", t.getName()));
        });
    }

    public static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
        }
    }
}
