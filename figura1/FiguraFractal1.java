import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class FiguraFractal1 extends JPanel {
    private int depth;
    private double hexagramRotation; // Ángulo de rotación del hexágono (en radianes)
    private double triangleSizeFactor; // Factor de tamaño de los triángulos

    public FiguraFractal1(int depth, double hexagramRotation, double triangleSizeFactor) {
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

        double size = Math.min(width, height) * 0.7;
        double x1 = (width - size) / 2;
        double y1 = height * 0.7;
        double x2 = x1 + size;
        double y2 = y1;
        double x3 = x1 + size / 2;
        double y3 = y1 - Math.sqrt(3) * size / 2;

        // Dibujar el copo de nieve de Koch
        Path2D path = new Path2D.Double();
        drawKochCurve(path, x1, y1, x2, y2, depth);
        drawKochCurve(path, x2, y2, x3, y3, depth);
        drawKochCurve(path, x3, y3, x1, y1, depth);
        g2.draw(path);

        // Dibujar el hexágono central con triángulos equiláteros pegados a sus caras
        if (depth == 5) {
            drawHexagonWithTriangles(g2, x1, y1, x2, y2, x3, y3);
        }
    }

    private void drawKochCurve(Path2D path, double x1, double y1, double x2, double y2, int depth) {
        if (depth == 0) {
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            return;
        }

        double dx = (x2 - x1) / 3;
        double dy = (y2 - y1) / 3;
        double xA = x1 + dx;
        double yA = y1 + dy;
        double xB = x1 + 2 * dx;
        double yB = y1 + 2 * dy;

        double angle = Math.PI / 3;
        double xC = xA + Math.cos(angle) * (xB - xA) - Math.sin(angle) * (yB - yA);
        double yC = yA + Math.sin(angle) * (xB - xA) + Math.cos(angle) * (yB - yA);

        drawKochCurve(path, x1, y1, xA, yA, depth - 1);
        drawKochCurve(path, xA, yA, xC, yC, depth - 1);
        drawKochCurve(path, xC, yC, xB, yB, depth - 1);
        drawKochCurve(path, xB, yB, x2, y2, depth - 1);
    }

    private void drawHexagonWithTriangles(Graphics2D g, double x1, double y1, double x2, double y2, double x3, double y3) {
        // Calcular el centro del triángulo
        double centerX = (x1 + x2 + x3) / 3;
        double centerY = (y1 + y2 + y3) / 3;

        // Radio del hexágono central (ajustado al tamaño de la figura)
        double radius = Math.min(getWidth(), getHeight()) * 0.23;

        // Dibujar el hexágono central
        Path2D.Double hexagonPath = new Path2D.Double();
        double angle = Math.toRadians(-90) + hexagramRotation; // Aplicar rotación al hexágono

        for (int i = 0; i < 6; i++) {
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            if (i == 0) {
                hexagonPath.moveTo(x, y);
            } else {
                hexagonPath.lineTo(x, y);
            }
            angle += Math.toRadians(60); // 60 grados para formar el hexágono
        }
        hexagonPath.closePath();
        g.draw(hexagonPath); // Dibujar el hexágono en azul

        // Dibujar triángulos equiláteros pegados a cada cara del hexágono
        angle = Math.toRadians(-90) + hexagramRotation; // Reiniciar el ángulo con rotación
        for (int i = 0; i < 6; i++) {
            // Puntos de la cara del hexágono
            double xStart = centerX + radius * Math.cos(angle);
            double yStart = centerY + radius * Math.sin(angle);
            double xEnd = centerX + radius * Math.cos(angle + Math.toRadians(60));
            double yEnd = centerY + radius * Math.sin(angle + Math.toRadians(60));

            // Calcular la altura del triángulo equilátero
            double triangleHeight = (Math.sqrt(3) / 2) * (radius * triangleSizeFactor);

            // Calcular el punto exterior del triángulo
            double exteriorX = (xStart + xEnd) / 2 + triangleHeight * Math.cos(angle + Math.toRadians(30));
            double exteriorY = (yStart + yEnd) / 2 + triangleHeight * Math.sin(angle + Math.toRadians(30));

            // Dibujar el triángulo equilátero
            Path2D.Double trianglePath = new Path2D.Double();
            trianglePath.moveTo(xStart, yStart);
            trianglePath.lineTo(xEnd, yEnd);
            trianglePath.lineTo(exteriorX, exteriorY);
            trianglePath.closePath();
            g.draw(trianglePath);

            angle += Math.toRadians(60); // Siguiente cara
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Koch Snowflake con Hexágono y Triángulos Equiláteros");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Parámetros ajustables manualmente
        int depth = 2; // Nivel del copo de nieve
        double hexagramRotation = 30; // Rotación del hexágono en grados
        double triangleSizeFactor = 1; // Tamaño de los triángulos (factor)

        FiguraFractal1 panel = new FiguraFractal1(depth, hexagramRotation, triangleSizeFactor);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}