package com.nibado.example.loom;

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

    public Agent() {
        x = Math.random();
        y = Math.random();
        angle = Math.random() * TAU;
    }

    private void think() {
        if (Math.random() <= TURN_CHANCE) {
            var toAdd = Math.random() >= 0.5 ? TURN_RATE : -TURN_RATE;
            addAngle(toAdd);
        }
    }

    private void addAngle(double angle) {
        this.angle += angle;
        if (this.angle > TAU) {
            this.angle -= TAU;
        } else if (this.angle < 0) {
            this.angle += TAU;
        }
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
