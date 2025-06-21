import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class FigurasFractal7 extends JPanel {
    private final int depth;
    private final Color color = new Color(0, 150, 255); // Azul más vibrante
    
    public FigurasFractal7(int depth) {
        this.depth = depth;
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.BLACK); // Fondo negro
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double initialSize = 125; // Tamaño inicial más grande para mejor visualización
        
        g2d.translate(centerX, centerY);
        g2d.setColor(color);
        drawSnowflakes(g2d, depth, 0, 0, initialSize);
    }

    private void drawSnowflakes(Graphics2D g, int currentDepth, double x, double y, double size) {
        if (currentDepth == 0) {
            drawHexagon(g, x, y, size);
        } else {
            double newSize = size / 2.5;
            double distance = size * 1.5;
            
            // Seis copos alrededor perfectamente espaciados
            for (int i = 0; i < 6; i++) {
                double angle = Math.toRadians(60 * i + 30); // Offset de 30° para mejor alineación
                double dx = distance * Math.cos(angle);
                double dy = distance * Math.sin(angle);
                drawSnowflakes(g, currentDepth - 1, x + dx, y + dy, newSize);
            }
        }
    }

    private void drawHexagon(Graphics2D g, double x, double y, double size) {
        Path2D.Double path = new Path2D.Double();
        double angle = -Math.PI/2;
        
        for (int i = 0; i < 6; i++) {
            double xi = x + size * Math.cos(angle);
            double yi = y + size * Math.sin(angle);
            if (i == 0) path.moveTo(xi, yi);
            else path.lineTo(xi, yi);
            angle += Math.PI/3;
        }
        path.closePath();
        g.fill(path);
    }

    public static void main(String[] args) {
        int depth = 5; // Profundidad recomendada: 3-5
        
        JFrame frame = new JFrame("Hexagonal Snowflakes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new FigurasFractal7(depth));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}