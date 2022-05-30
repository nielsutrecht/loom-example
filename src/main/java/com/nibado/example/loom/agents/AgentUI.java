package com.nibado.example.loom.agents;

import javax.swing.*;
import java.awt.*;

public class AgentUI {
    private final JFrame frame = new JFrame("Agent UI");
    private final Agents agents = new Agents(1_000);
    private final AgentPanel panel = new AgentPanel(agents);
    private final Timer timer = new Timer(1000 / 50, e -> {
        agents.tick();
        panel.repaint();
    });

    public void run() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
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

        private AgentPanel(Agents agents) {
            this.agents = agents;
        }

        public void paintComponent(Graphics g) {
            var g2d = (Graphics2D) g;
            super.paintComponent(g);
            g2d.setColor(Color.WHITE);

            agents.agents().forEach(a -> drawAgent(a, g2d));
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
