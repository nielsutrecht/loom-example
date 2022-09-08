package com.nibado.example.loom.agents;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.*;

public class Agent implements Runnable {
    private static final double TURN_RATE = 0.06;
    private static final double TURN_CHANCE = 0.5;
    private static final double VELOCITY = 0.003;
    public final AtomicBoolean run = new AtomicBoolean(true);

    private double x;
    private double y;
    private double angle;
    private double velocity = VELOCITY;

    private final World world;

    public Agent(World world) {
        x = Math.random();
        y = Math.random();
        angle = Math.random() * TAU;
        this.world = world;
    }

    private void think() {
        if(world.distanceToAvoid(this) < World.AVOID_DISTANCE) {
            setAngle(world.angleToAvoid(this));
        } else if (Math.random() <= TURN_CHANCE) {
            var toAdd = Math.random() >= 0.5 ? TURN_RATE : -TURN_RATE;
            addAngle(toAdd);
        }
    }

    private void setAngle(double angle) {
        if(angle > TAU) {
            angle -= TAU;
        } else if(angle < 0) {
            angle += TAU;
        }
        this.angle = angle;
    }

    private void addAngle(double angle) {
        setAngle(this.angle + angle);
    }

    private void move() {
        x += cos(angle) * velocity;
        y += sin(angle) * velocity;

        if (x < 0 || x > 1.0) {
            angle = PI - angle;
        } else if (y < 0 || y > 1.0) {
            angle = (PI * 2) - angle;
        }
    }

    private void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void run() {
        while (run.get()) {
            think();
            move();
            sleep();
        }
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double angle() {
        return angle;
    }
}
