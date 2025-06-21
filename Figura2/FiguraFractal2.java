package Figura2;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class FiguraFractal2 extends JPanel {
    private int depth;
    private double hexagramRotation;  // Ángulo de rotación del hexágono (en radianes)
    private double triangleSizeFactor; // Factor de tamaño de los triángulos

    public FiguraFractal2(int depth, double hexagramRotation, double triangleSizeFactor) {
        this.depth = depth;
        this.hexagramRotation = Math.toRadians(hexagramRotation); // Convertir a radianes
        this.triangleSizeFactor = triangleSizeFactor;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 150, 255)); // Color azul

        int width = getWidth();
        int height = getHeight();

        // Ajustamos el tamaño del triángulo base
        double size = Math.min(width, height) * 0.7;
        double x1 = (width - size) / 2;
        double y1 = height * 0.7;
        double x2 = x1 + size;
        double y2 = y1;
        double x3 = x1 + size / 2;
        double y3 = y1 - Math.sqrt(3) * size / 2;

        // 1. Dibujar el copo de nieve de Koch con menor profundidad
        Path2D path = new Path2D.Double();
        drawKochCurve(path, x1, y1, x2, y2, depth);
        drawKochCurve(path, x2, y2, x3, y3, depth);
        drawKochCurve(path, x3, y3, x1, y1, depth);
        g2.draw(path);

        // 2. Dibujar siempre el hexágono central con triángulos equiláteros
        drawHexagonWithTriangles(g2, x1, y1, x2, y2, x3, y3);
    }

    /**
     * Dibuja un lado de la curva de Koch con recursión.
     */
    private void drawKochCurve(Path2D path, double x1, double y1, double x2, double y2, int depth) {
        if (depth == 0) {
            // Si no hay más profundidad, se dibuja la línea base
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            return;
        }

        // Dividimos el segmento en tres partes
        double dx = (x2 - x1) / 3;
        double dy = (y2 - y1) / 3;
        double xA = x1 + dx;
        double yA = y1 + dy;
        double xB = x1 + 2 * dx;
        double yB = y1 + 2 * dy;

        // Punto que forma el “pico” del triángulo
        double angle = Math.PI / 3; // 60 grados
        double xC = xA + Math.cos(angle) * (xB - xA) - Math.sin(angle) * (yB - yA);
        double yC = yA + Math.sin(angle) * (xB - xA) + Math.cos(angle) * (yB - yA);

        // Recursión en cada segmento
        drawKochCurve(path, x1, y1, xA, yA, depth - 1);
        drawKochCurve(path, xA, yA, xC, yC, depth - 1);
        drawKochCurve(path, xC, yC, xB, yB, depth - 1);
        drawKochCurve(path, xB, yB, x2, y2, depth - 1);
    }

    /**
     * Dibuja un hexágono central y triángulos equiláteros en cada lado del hexágono.
     */
    private void drawHexagonWithTriangles(Graphics2D g,
    double x1, double y1,
    double x2, double y2,
    double x3, double y3) {

        // Calculamos el centro del triángulo base (para ubicar ahí el hexágono)
        double centerX = (x1 + x2 + x3) / 3.0;
        double centerY = (y1 + y2 + y3) / 3.0;

        // Radio del hexágono central
        double radius = Math.min(getWidth(), getHeight()) * 0.23;

        // Dibuja el hexágono rotado
        Path2D.Double hexagonPath = new Path2D.Double();
        double angle = Math.toRadians(-90) + hexagramRotation;

        for (int i = 0; i < 6; i++) {
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            if (i == 0) {
                hexagonPath.moveTo(x, y);
            } else {
                hexagonPath.lineTo(x, y);
            }
            angle += Math.toRadians(60);
        }
        hexagonPath.closePath();
        g.draw(hexagonPath);

        // Dibuja triángulos equiláteros saliendo de cada cara del hexágono
        angle = Math.toRadians(-90) + hexagramRotation; // Reiniciamos ángulo
        for (int i = 0; i < 6; i++) {
            // Extremos de cada lado del hexágono
            double xStart = centerX + radius * Math.cos(angle);
            double yStart = centerY + radius * Math.sin(angle);
            double xEnd = centerX + radius * Math.cos(angle + Math.toRadians(60));
            double yEnd = centerY + radius * Math.sin(angle + Math.toRadians(60));

            // Altura del triángulo equilátero
            double triangleHeight = (Math.sqrt(3) / 2) * (radius * triangleSizeFactor);

            // Punto exterior del triángulo
            double exteriorX = (xStart + xEnd) / 2.0
                    + triangleHeight * Math.cos(angle + Math.toRadians(30));
            double exteriorY = (yStart + yEnd) / 2.0
                    + triangleHeight * Math.sin(angle + Math.toRadians(30));

            // Dibuja el triángulo
            Path2D.Double trianglePath = new Path2D.Double();
            trianglePath.moveTo(xStart, yStart);
            trianglePath.lineTo(xEnd, yEnd);
            trianglePath.lineTo(exteriorX, exteriorY);
            trianglePath.closePath();
            g.draw(trianglePath);

            angle += Math.toRadians(60);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fractal de Koch Simplificado con Hexágono");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ajusta 'depth' a 2 o 3 para reducir la complejidad en las puntas
        int depth = 3;
        double hexagramRotation = 30;
        double triangleSizeFactor = 1;

        FiguraFractal2 panel =
                new FiguraFractal2(depth, hexagramRotation, triangleSizeFactor);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}