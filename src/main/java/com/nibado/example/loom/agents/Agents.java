package com.nibado.example.loom.agents;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class Agents {
    private final List<Agent> agents;
    private final List<Thread> threads;

    public Agents(int amount, World world) {
        agents = IntStream.rangeClosed(0, amount).mapToObj(i ->
            new Agent(world)
        ).toList();

        threads = agents.stream().map(Thread::startVirtualThread).toList();
    }

    public Collection<Agent> agents() {
        return agents;
    }
}
