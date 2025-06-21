import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class FiguraFractal5 extends JPanel {
    private int depth;

    public FiguraFractal5(int depth) {
        this.depth = depth;
        setPreferredSize(new Dimension(800, 800));
    }

    private void drawFractal(Graphics2D g, double x, double y, double size, int level, int parentDirection) {
        if (level == 0) {
            drawHexagon(g, x, y, size);
            return;
        }

        double newSize = size / 3;
        double height = Math.sqrt(3) * newSize;

        // Offsets: centro (i=0) + 6 direcciones (i=1..6)
        double[][] offsets = {
            { 0, 0 },                  // i = 0 (centro)
            { -newSize, -height },     // i = 1
            {  newSize, -height },     // i = 2
            {  newSize * 2, 0 },       // i = 3
            {  newSize, height },      // i = 4
            { -newSize, height },      // i = 5
            { -newSize * 2, 0 }        // i = 6
        };

        // En el nivel más externo (level == depth), omitimos el centro (i=0).
        // En niveles internos, permitimos dibujar i=0 para rellenar.
        for (int i = (level == depth ? 1 : 0); i < offsets.length; i++) {
            // Evitar la dirección opuesta a la del “padre”
            if (parentDirection != 0 && i == getOpposite(parentDirection)) {
                continue;
            }

            double dx = offsets[i][0];
            double dy = offsets[i][1];
            drawFractal(g, x + dx, y + dy, newSize, level - 1, i);
        }
    }

    // Indica la dirección opuesta (1 <-> 4, 2 <-> 5, 3 <-> 6)
    private int getOpposite(int direction) {
        switch (direction) {
            case 1: return 4;
            case 2: return 5;
            case 3: return 6;
            case 4: return 1;
            case 5: return 2;
            case 6: return 3;
            default: return -1; // Sin opuesto si direction=0 (centro)
        }
    }

    private void drawHexagon(Graphics2D g, double x, double y, double size) {
        Path2D.Double hexagon = new Path2D.Double();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(i * 60);
            double dx = x + size * Math.cos(angle);
            double dy = y + size * Math.sin(angle);
            if (i == 0) {
                hexagon.moveTo(dx, dy);
            } else {
                hexagon.lineTo(dx, dy);
            }
        }
        hexagon.closePath();
        g.fill(hexagon);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Fondo negro
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Color del fractal: azul
        g2d.setColor(Color.BLUE);

        // Trasladar el origen al centro
        g2d.translate(getWidth() / 2.0, getHeight() / 2.0);

        // Rotar 90 grados
        g2d.rotate(Math.toRadians(90));

        double size = 240;
        drawFractal(g2d, 0, 0, size, depth, 0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Fractal Hexagonal Anillado");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Ajusta la profundidad a tu gusto (por ejemplo, 4 o 5):
            frame.add(new FiguraFractal5(4));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}