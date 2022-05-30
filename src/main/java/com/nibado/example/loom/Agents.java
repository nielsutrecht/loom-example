package com.nibado.example.loom;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class Agents {
    private final List<Agent> agents;
    private final List<Thread> threads;

    public Agents(int amount) {
        agents = IntStream.rangeClosed(0, amount).mapToObj(i ->
            new Agent()
        ).toList();

        threads = agents.stream().map(Thread::startVirtualThread).toList();
    }

    public Collection<Agent> agents() {
        return agents;
    }

    public void tick() {

    }
}
