import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class FigurasFractal4 extends JPanel {
    private int depth = 4; // Nivel de recursión del fractal
    private double rotationAngle; // Ángulo de rotación en grados

    public FigurasFractal4(int depth, double rotationAngle) {
        this.depth = depth;
        this.rotationAngle = rotationAngle;
        setPreferredSize(new Dimension(800, 800));
    }

    private void drawFractal(Graphics2D g, double x, double y, double size, int level) {
        if (level == 0) {
            drawHexagon(g, x, y, size);
            return;
        }

        double newSize = size / 3;
        double height = Math.sqrt(3) * newSize;

        double[][] offsets = {
            { -newSize, -height }, { newSize, -height }, { 2 * newSize, 0 },
            { newSize, height }, { -newSize, height }, { -2 * newSize, 0 }
        };

        for (double[] offset : offsets) {
            drawFractal(g, x + offset[0], y + offset[1], newSize, level - 1);
        }
    }

    private void drawHexagon(Graphics2D g, double x, double y, double size) {
        Path2D.Double hexagon = new Path2D.Double();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(i * 60 + rotationAngle); // Aplicar rotación
            double dx = x + size * Math.cos(angle);
            double dy = y + size * Math.sin(angle);
            if (i == 0) {
                hexagon.moveTo(dx, dy);
            } else {
                hexagon.lineTo(dx, dy);
            }
        }
        hexagon.closePath();
        g.draw(hexagon);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(0, 150, 255));
        setBackground(Color.BLACK);

        double size = 200;
        double startX = getWidth() / 2;
        double startY = getHeight() / 2;

        // Aplicar rotación global al Graphics2D
        AffineTransform oldTransform = g2d.getTransform();
        g2d.rotate(Math.toRadians(rotationAngle), startX, startY);
        drawFractal(g2d, startX, startY, size, depth);
        g2d.setTransform(oldTransform); // Restaurar la transformación original
    }

    public static void main(String[] args) {
        double rotationAngle = 90; // Cambia este valor para modificar la rotación
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hexagonal Snowflake Fractal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new FigurasFractal4(4, rotationAngle));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}