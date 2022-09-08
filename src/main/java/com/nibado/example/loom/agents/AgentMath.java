package com.nibado.example.loom.agents;

import java.awt.*;
import java.awt.geom.Point2D;

import static java.lang.Math.PI;
import static java.lang.Math.TAU;

public class AgentMath {
    public static Point toScreen(Point2D.Double point, Dimension dimension) {
        var x = (int)(point.x * dimension.width);
        var y = dimension.height - (int)(point.y * dimension.height);

        return new Point(x, y);
    }

    public static Point2D.Double fromScreen(Point point, Dimension dimension) {
        var x = (double)point.x / (double) dimension.width;
        var y = 1.0 - (double)point.y / (double) dimension.height;

        return new Point2D.Double(x, y);
    }

    public static final Point2D.Double point(double x, double y) {
        return new Point2D.Double(x, y);
    }

    public static final Point point(int x, int y) {
        return new Point(x, y);
    }

    public static double angle(Point2D pointA, Point2D pointB) {
        return normalizeAngle(Math.atan2((pointB.getX() - pointA.getX()), (pointB.getY() - pointA.getY())));
    }

    public static double invertAngle(double angle) {
        return normalizeAngle(angle + PI);
    }

    public static double normalizeAngle(double angle) {
        angle = angle % TAU;

        if(angle < 0) {
            angle += TAU;
        }

        return angle;
    }
}
