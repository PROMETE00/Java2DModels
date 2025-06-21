import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class FiguraFractal3 extends JPanel {
    private final int depth;
    private final Color color = new Color(0, 150, 255);
    private double rotationAngle = 0; // Ángulo de rotación en grados

    public FiguraFractal3(int depth) {
        this.depth = depth;
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.BLACK);
    }

    // Método para modificar el ángulo de rotación
    public void setRotationAngle(double angle) {
        this.rotationAngle = angle;
        repaint(); // Vuelve a dibujar el panel con el nuevo ángulo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double size = 300;
        
        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(rotationAngle)); // Aplica la rotación
        g2d.setColor(color);
        drawFiguraFractal3(g2d, depth, 0, 0, size);
    }

    private void drawFiguraFractal3(Graphics2D g, int currentDepth, double x, double y, double size) {
        if (currentDepth == 0) {
            drawHexagon(g, x, y, size);
        } else {
            double newSize = size / 3;
            double distance = newSize * 2;
            
            // Hexágono central
            drawFiguraFractal3(g, currentDepth - 1, x, y, newSize);
            
            // Seis hexágonos alrededor
            for (int i = 0; i < 6; i++) {
                double angle = Math.toRadians(60 * i);
                double dx = distance * Math.cos(angle);
                double dy = distance * Math.sin(angle);
                drawFiguraFractal3(g, currentDepth - 1, x + dx, y + dy, newSize);
            }
        }
    }

    private void drawHexagon(Graphics2D g, double x, double y, double size) {
        Path2D.Double path = new Path2D.Double();
        double angle = -Math.PI/2; // Comenzar desde arriba
        
        for (int i = 0; i < 6; i++) {
            double xi = x + size * Math.cos(angle);
            double yi = y + size * Math.sin(angle);
            if (i == 0) {
                path.moveTo(xi, yi);
            } else {
                path.lineTo(xi, yi);
            }
            angle += Math.PI/3; // 60 grados
        }
        path.closePath();
        g.fill(path);
    }

    public static void main(String[] args) {
        int depth = 4; // Profundidad de recursión
        
        JFrame frame = new JFrame("FiguraFractal3 Snowflake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FiguraFractal3 FiguraFractal3 = new FiguraFractal3(depth);
        frame.add(FiguraFractal3);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Ejemplo de cómo modificar el ángulo de rotación desde el código
        FiguraFractal3.setRotationAngle(90); // Rota la figura 45 grados
    }
}