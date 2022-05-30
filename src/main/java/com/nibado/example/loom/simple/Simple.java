package com.nibado.example.loom.simple;

import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Simple {

    public static void main(String[] args) throws Exception {
        printThreads();

        System.in.read();

        var latch = new CountDownLatch(1000);

        IntStream.range(0, 1000).forEach(i -> {
            Thread.startVirtualThread(() -> {
                System.out.printf("Virtual thread %3s started\n", i);
                sleep((int) (Math.random() * 5000.0));
                latch.countDown();
                System.out.printf("Virtual thread %3s finished\n", i);
            });
        });

        latch.await();

        printThreads();
    }

    private static void printThreads() {
        var groups = Thread.getAllStackTraces().keySet().stream()
            .collect(Collectors.groupingBy(t -> t.getThreadGroup().getName()));

        groups.forEach((key, value) -> {
            System.out.println(key);
            value.forEach(t -> System.out.printf("- %s\n", t.getName()));
        });
    }

    private static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
        }
    }
}
