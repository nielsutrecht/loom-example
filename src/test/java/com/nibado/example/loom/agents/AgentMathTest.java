package com.nibado.example.loom.agents;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static com.nibado.example.loom.agents.AgentMath.*;
import static java.lang.Math.PI;
import static org.assertj.core.api.Assertions.assertThat;

class AgentMathTest {
    private static final Dimension SCREEN_SIZE = new Dimension(800, 600);

    private static final Point SCREEN_TOP_LEFT = point(0, 0);
    private static final Point SCREEN_TOP_RIGHT = point(SCREEN_SIZE.width, 0);
    private static final Point SCREEN_BOTTOM_LEFT = point(0, SCREEN_SIZE.height);
    private static final Point SCREEN_BOTTOM_RIGHT = point(SCREEN_SIZE.width, SCREEN_SIZE.height);
    private static final Point SCREEN_MIDDLE = point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2);

    @Test
    void toScreen_should_return_correct_results() {
        assertThat(toScreen(point(0.5, 0.5), SCREEN_SIZE)).isEqualTo(SCREEN_MIDDLE);
        assertThat(toScreen(point(0.0, 1.0), SCREEN_SIZE)).isEqualTo(SCREEN_TOP_LEFT);
        assertThat(toScreen(point(1.0, 1.0), SCREEN_SIZE)).isEqualTo(SCREEN_TOP_RIGHT);
        assertThat(toScreen(point(0.0, 0.0), SCREEN_SIZE)).isEqualTo(SCREEN_BOTTOM_LEFT);
        assertThat(toScreen(point(1.0, 0.0), SCREEN_SIZE)).isEqualTo(SCREEN_BOTTOM_RIGHT);
    }

    @Test
    void fromScreen_should_return_correct_results() {
        assertThat(fromScreen(SCREEN_MIDDLE, SCREEN_SIZE)).isEqualTo(point(0.5, 0.5));
        assertThat(fromScreen(SCREEN_TOP_LEFT, SCREEN_SIZE)).isEqualTo(point(0.0, 1.0));
        assertThat(fromScreen(SCREEN_TOP_RIGHT, SCREEN_SIZE)).isEqualTo(point(1.0, 1.0));
        assertThat(fromScreen(SCREEN_BOTTOM_LEFT, SCREEN_SIZE)).isEqualTo(point(0.0, 0.0));
        assertThat(fromScreen(SCREEN_BOTTOM_RIGHT, SCREEN_SIZE)).isEqualTo(point(1.0, 0.0));
    }

    @Test
    void distance_should_be_what_we_expect() {
        var pointA = point(0.0, 0.0);

        assertThat(pointA.distance(point(0.0, -1.0))).isEqualTo(1.0);
        assertThat(pointA.distance(point(0.0, 1.0))).isEqualTo(1.0);
        assertThat(pointA.distance(point(1.0, 1.0))).isEqualTo(Math.sqrt(2.0));
    }

    @Test
    void angle_should_return_correct_results() {
        var pointA = point(0.0, 0.0);

        assertThat(angle(pointA, pointA)).isEqualTo(0.0);
        assertThat(angle(pointA, point(1.0, 0.0))).isEqualTo(PI / 2.0);
        assertThat(angle(pointA, point(0.0, -1.0))).isEqualTo(PI);
        assertThat(angle(pointA, point(-1.0, 0.0))).isEqualTo(PI * 1.5);
        assertThat(angle(pointA, point(0.0, 1.0))).isEqualTo(0.0);
    }
    @Test
    void invertAngle_should_return_correct_results() {
        assertThat(invertAngle(0.0)).isEqualTo(PI);
        assertThat(invertAngle(PI)).isEqualTo(0.0);
    }
}
