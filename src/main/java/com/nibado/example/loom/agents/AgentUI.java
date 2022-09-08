package com.nibado.example.loom.agents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AgentUI {
    private static final int AGENT_AMOUNT = 10_000;

    private final JFrame frame = new JFrame("Agent UI");

    private final World world = new World();
    private final Agents agents = new Agents(AGENT_AMOUNT, world);
    private final AgentPanel panel = new AgentPanel(agents, world);

    //Repaints the main panel 50 times per second
    private final Timer timer = new Timer(1000 / 50, e -> {
        panel.repaint();
    });

    public void run() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setPreferredSize(frame.getSize());
        panel.setBackground(Color.BLACK);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        timer.start();
    }

    public static void main(String[] args) {
        var ui = new AgentUI();
        ui.run();
    }

    private static class AgentPanel extends JPanel {
        private static final int AGENT_SIZE = 6;
        private final Agents agents;
        private final World world;

        private AgentPanel(Agents agents, World world) {
            this.agents = agents;
            this.world = world;

            this.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    var x = (double)e.getX() / (double) getWidth();
                    var y = (double)e.getY() / (double) getHeight();

                    world.setAvoid(x, y);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    world.setAvoid(Double.MIN_VALUE, Double.MIN_VALUE);
                }
            });
        }

        public void paintComponent(Graphics g) {
            var g2d = (Graphics2D) g;
            super.paintComponent(g);

            g2d.setColor(Color.WHITE);

            agents.agents().forEach(a -> drawAgent(a, g2d));

            if(world.avoid()) {
                g2d.setColor(Color.RED);

                var distance = (int)(World.AVOID_DISTANCE * this.getWidth());
                var x = (int)(this.getWidth() * world.getAvoidX()) - distance / 2;
                var y = (int)(this.getHeight() * world.getAvoidY()) - distance / 2;

                g2d.drawOval(x, y, distance, distance);
            }
        }

        private void drawAgent(Agent a, Graphics2D g) {
            var agentX = (int) (this.getWidth() * a.x());
            var agentY = (int) (this.getHeight() * a.y());
            var x = (int) (Math.cos(a.angle()) * 10);
            var y = (int) (Math.sin(a.angle()) * 10);

            g.setColor(Color.WHITE);
            g.drawLine(agentX, agentY, agentX + x, agentY + y);

            g.setColor(Color.WHITE);
            g.fillOval(agentX - (AGENT_SIZE / 2), agentY - (AGENT_SIZE / 2), AGENT_SIZE, AGENT_SIZE);
        }
    }
}
