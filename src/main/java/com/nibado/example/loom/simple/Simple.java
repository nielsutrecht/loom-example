package com.nibado.example.loom.simple;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.nibado.example.loom.Util.sleep;

public class Simple {

    public static void main(String[] args) throws Exception {
        executorServiceExample();
    }

    public static void startVirtualExample() throws Exception {
        var amount = 1_000_000;

        var latch = new CountDownLatch(amount);

        IntStream.range(0, amount).forEach(i -> {
            Thread.startVirtualThread(() -> {
                System.out.println("Runnable " + i);
                sleep(1000);
                latch.countDown();
            });
        });

        latch.await();

        System.out.println("startVirtualExample: Done!");
    }

    public static void executorServiceExample() throws Exception {
        var amount = 1_000_000;
        var ex = Executors.newFixedThreadPool(
            amount, Thread.ofVirtual().factory()
        );

        var latch = new CountDownLatch(amount);
        IntStream.range(0, amount).forEach(i -> {
            ex.submit(() -> {
                System.out.println("Runnable " + i);
                sleep(1000);
                latch.countDown();
            });
        });

        latch.await();
        ex.shutdown();

        System.out.println("executorServiceExample: Done!");
    }
}
