package com.nibado.example.loom.blockchain;

import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Blockchain {
    public static void main(String[] args) throws Exception {
        var latch = new CountDownLatch(1000);

        for(var i = 0;i < 1000;i++) {
            Thread.startVirtualThread(worker(i, latch));
        }

        latch.await();
    }

    public static Runnable worker(int worker, CountDownLatch latch) throws Exception {
        Random random = new Random();
        MessageDigest md = MessageDigest.getInstance("MD5");
        var buffer = new byte[256];

        return () -> {
            while (true) {
                random.nextBytes(buffer);
                var digest = md.digest(buffer);
                var hex = HexFormat.of().formatHex(digest);

                if (hex.endsWith("00000")) {
                    latch.countDown();
                    System.out.printf("Worker %3s found %s, %3s remain\n", worker, hex, latch.getCount());
                    break;
                }
            }
        };
    }
}
