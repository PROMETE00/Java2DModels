import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class FiguraFractalPentagono extends JPanel {
    private int numSteps = 50; // Iniciar en nivel 50
    private static final double SCALE_STEP = 0.92; // Escalado de la espiral
    private static final double ROTATION_STEP = Math.toRadians(10); // Rotación en radianes
    private static final int PENTAGON_SIZE = 150; // Tamaño del pentágono central

    // Colores de los pentágonos
    private static final Color[] COLORS = {
        new Color(221, 160, 221),   // Lila pastel
        new Color(144, 238, 144),
        new Color(173, 216, 230),
        new Color(255, 182, 193),
        Color.CYAN , Color.WHITE
    };

    public FiguraFractalPentagono() {
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.BLACK); // Fondo negro
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (numSteps == 0) return; // No dibujar si el nivel es 0

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        g2d.translate(width / 2, height / 2); // Centrar el dibujo

        // Calcular las posiciones de los 5 pentágonos exteriores
        double[][] positions = new double[6][2];
        positions[0] = new double[]{0, 0}; // Centro

        for (int i = 1; i <= 5; i++) {
            double angle = Math.toRadians(i * 72 - 18); // Rotar para alinear puntas
            double distanceMultiplier = 1.28; // Ajuste para acercar los pentágonos
            positions[i][0] = PENTAGON_SIZE * Math.cos(angle) * distanceMultiplier;
            positions[i][1] = PENTAGON_SIZE * Math.sin(angle) * distanceMultiplier;
        }

        // Dibujar el pentágono central + 5 exteriores con sus respectivos colores
        for (int i = 0; i < positions.length; i++) {
            g2d.setColor(COLORS[i % COLORS.length]); // Aplicar color diferente a cada uno
            AffineTransform transform = g2d.getTransform(); // Guardar estado
            g2d.translate(positions[i][0], positions[i][1]); // Mover pentágono
            g2d.rotate(Math.toRadians(i * -72)); // Rotar para mantener simetría
            drawPentagon(g2d, numSteps);
            g2d.setTransform(transform); // Restaurar estado original
        }
    }

    // Dibuja un pentágono en espiral
    private void drawPentagon(Graphics2D g2d, int steps) {
        Path2D pentagon = createPentagon(100);
        for (int i = 0; i < steps; i++) {
            g2d.draw(pentagon); // Dibujar pentágono
            g2d.scale(SCALE_STEP, SCALE_STEP); // Escalar
            g2d.rotate(ROTATION_STEP); // Rotar
        }
    }

    // Genera un pentágono con un tamaño específico
    private Path2D createPentagon(double size) {
        Path2D pentagon = new Path2D.Double();
        for (int i = 0; i < 5; i++) {
            double angle = Math.toRadians(i * 72);
            double x = size * Math.cos(angle);
            double y = size * Math.sin(angle);
            if (i == 0) {
                pentagon.moveTo(x, y);
            } else {
                pentagon.lineTo(x, y);
            }
        }
        pentagon.closePath();
        return pentagon;
    }

    // Ventana principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Spiral Pentagon");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            FiguraFractalPentagono panel = new FiguraFractalPentagono();
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}