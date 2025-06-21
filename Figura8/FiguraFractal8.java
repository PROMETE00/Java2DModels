package Figura8;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class FiguraFractal8 extends JPanel {
    private List<double[]> coordinates;
    private int depth;

    public FiguraFractal8(List<double[]> coordinates, int depth) {
        this.coordinates = coordinates;
        this.depth = depth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Fondo negro
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.setColor(Color.BLUE); // Color de los copos de nieve (azul)

        double scale = getWidth() / 10.0; // Aumentar la escala para hacer las figuras más grandes
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        for (double[] point : coordinates) {
            double x = centerX + (point[0] - 5.23) * scale * 0.6; // Reducir la distancia entre los puntos
            double y = centerY - (point[1] - 5.23) * scale * 0.6; // Reducir la distancia entre los puntos
            drawKochSnowflake(g2d, x, y, scale / 2, depth); // Ajustar el tamaño de los copos
        }
    }

    private void drawKochSnowflake(Graphics2D g2d, double x, double y, double size, int depth) {
        double x1 = x - size / 2, y1 = y + size * Math.sqrt(3) / 6;
        double x2 = x + size / 2, y2 = y + size * Math.sqrt(3) / 6;
        double x3 = x, y3 = y - size * Math.sqrt(3) / 3;

        drawKochCurve(g2d, x1, y1, x2, y2, depth);
        drawKochCurve(g2d, x2, y2, x3, y3, depth);
        drawKochCurve(g2d, x3, y3, x1, y1, depth);
    }

    private void drawKochCurve(Graphics2D g2d, double x1, double y1, double x2, double y2, int depth) {
        if (depth == 0) {
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
            return;
        }

        double dx = (x2 - x1) / 3.0;
        double dy = (y2 - y1) / 3.0;
        double xA = x1 + dx;
        double yA = y1 + dy;
        double xB = x1 + 2 * dx;
        double yB = y1 + 2 * dy;

        double midX = (x1 + x2) / 2.0;
        double midY = (y1 + y2) / 2.0;
        double height = Math.sqrt(3) / 6.0 * Math.hypot(x2 - x1, y2 - y1);

        double xC = midX + height * (y1 - y2) / Math.hypot(y2 - y1, x2 - x1);
        double yC = midY + height * (x2 - x1) / Math.hypot(y2 - y1, x2 - x1);

        drawKochCurve(g2d, x1, y1, xA, yA, depth - 1);
        drawKochCurve(g2d, xA, yA, xC, yC, depth - 1);
        drawKochCurve(g2d, xC, yC, xB, yB, depth - 1);
        drawKochCurve(g2d, xB, yB, x2, y2, depth - 1);
    }

    public static void main(String[] args) {
        List<double[]> coordinates = new ArrayList<>();
        double[][] points = {
            {5.23,1.89},{5.23,4.19},{5.23,5.32},{5.23,6.37},{5.23,7.50},{5.23,8.67},{5.23,10.30},
            {4.23,2.41},{4.23,3.57},{4.23,9.21},{4.23,10.36},{3.33,4.12},{3.33,5.29},{3.33,6.40},
            {3.33,7.50},{3.33,8.50},{2.38,9.18},{2.38,7.00},{2.38,5.82},{2.38,3.55},{1.41,4.12},
            {1.41,5.29},{1.41,7.48},{1.41,8.63},{6.17,2.43},{6.17,3.60},{6.17,9.18},{6.17,10.34},
            {7.12,8.63},{7.12,7.47},{7.12,6.39},{7.12,5.24},{7.12,4.13},{8.07,3.57},{8.07,5.83},
            {8.07,6.94},{8.07,9.21},{9.04,8.64},{9.04,7.50},{9.04,5.50},{9.04,4.16}
        };

        for (double[] point : points) {
            coordinates.add(point);
        }

        JFrame frame = new JFrame("Copo de Nieve en Coordenadas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);

        FiguraFractal8 panel = new FiguraFractal8(coordinates, 6);
        frame.add(panel);

        frame.setVisible(true);
    }
}