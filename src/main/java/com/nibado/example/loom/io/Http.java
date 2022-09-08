package com.nibado.example.loom.io;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.nibado.example.loom.Util.printThreads;

public class Http {
    private WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());

    public void run() throws Exception {
        wireMockServer.start();
        wireMockServer.stubFor(get(urlEqualTo("/endpoint")).willReturn(
            aResponse()
                .withBody("Hello, World!")
                .withStatus(200)
                .withFixedDelay(1000)));

        var uri = URI.create(wireMockServer.url("/endpoint"));
        var latch = new CountDownLatch(500);
        var counter = new AtomicInteger((int)latch.getCount());
        for (var i = 0; i < latch.getCount(); i++) {
            Thread.startVirtualThread(client(uri, latch, counter));
        }

        latch.await();

        wireMockServer.stop();
    }

    public static void main(String[] args) throws Exception {
        printThreads();
        new Http().run();
        printThreads();
        System.out.println(Thread.activeCount());

        Thread.sleep(5000);

        printThreads();
        System.out.println(Thread.activeCount());
    }

    public static Runnable client(URI uri, CountDownLatch latch, AtomicInteger counter) {
        var client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        var request = HttpRequest.newBuilder()
            .uri(uri)
            .timeout(Duration.ofMinutes(2))
            .header("Content-Type", "application/json")
            .GET()
            .build();

        return () -> {
            try {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.printf("%s: %s%n", counter.getAndDecrement(), response.body());
            } catch (Exception e) {
                System.err.printf("Error on GET %s: %s%n", uri, e.getMessage());
            }

            latch.countDown();
        };
    }
}
