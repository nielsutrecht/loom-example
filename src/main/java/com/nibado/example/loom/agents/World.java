package com.nibado.example.loom.agents;

import static java.lang.Math.sqrt;

public class World {
    public static final double AVOID_DISTANCE = 0.1;

    private double avoidX = Double.MIN_VALUE;
    private double avoidY = Double.MIN_VALUE;

    public void setAvoid(double avoidX, double avoidY) {
        this.avoidX = avoidX;
        this.avoidY = avoidY;
    }

    public double getAvoidX() {
        return avoidX;
    }

    public double getAvoidY() {
        return avoidY;
    }

    public double distanceToAvoid(Agent agent) {
        if(!avoid()) {
            return Double.MAX_VALUE;
        }

        return distance(agent.x(), agent.y(), avoidX, avoidY);
    }

    public double angleToAvoid(Agent agent) {
        return angleBetweenPoints(agent.x(), agent.y(), avoidX, avoidY);
    }

    private static double angleBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.atan2((x2 - x1), (y2 - y1));
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public boolean avoid() {
        return avoidX != Double.MIN_VALUE && avoidY != Double.MIN_VALUE;
    }
}
