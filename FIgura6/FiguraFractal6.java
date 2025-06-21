import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Ludick Guadarrama Oscar Ivan
 * Función: Generar copos de nieve de Koch en cada coordenada
 */
public class FiguraFractal6 extends JPanel {

    private List<double[]> coordinates; // Lista de coordenadas (x, y)
    private int nivel; // Nivel de recursión (profundidad del fractal)
    private double sin60 = Math.sin(Math.PI / 3); // Seno de 60 grados

    public FiguraFractal6(List<double[]> coordinates, int nivel) {
        this.coordinates = coordinates;
        this.nivel = nivel;
    }

    public static void main(String[] args) {
        // Coordenadas proporcionadas (originales y nuevas)
        List<double[]> coordinates = new ArrayList<>();
        // Coordenadas originales
        coordinates.add(new double[]{24.60, 13.49});
        coordinates.add(new double[]{21.27, 15.39});
        coordinates.add(new double[]{17.88, 17.46});
        coordinates.add(new double[]{14.34, 19.25});
        coordinates.add(new double[]{10.90, 21.05});
        coordinates.add(new double[]{11.00, 25.29});
        coordinates.add(new double[]{11.00, 28.99});
        coordinates.add(new double[]{11.00, 32.64});
        coordinates.add(new double[]{11.00, 36.50});
        coordinates.add(new double[]{14.28, 38.51});
        coordinates.add(new double[]{17.30, 40.47});
        coordinates.add(new double[]{21.05, 42.21});
        coordinates.add(new double[]{24.54, 44.33});
        coordinates.add(new double[]{28.30, 42.37});
        coordinates.add(new double[]{31.58, 40.57});
        coordinates.add(new double[]{34.75, 38.51});
        coordinates.add(new double[]{38.40, 36.45});
        coordinates.add(new double[]{38.40, 32.59});
        coordinates.add(new double[]{38.35, 28.88});
        coordinates.add(new double[]{38.35, 24.97});
        coordinates.add(new double[]{38.35, 21.00});
        coordinates.add(new double[]{34.86, 19.04});
        coordinates.add(new double[]{31.53, 17.14});
        coordinates.add(new double[]{28.14, 15.23});
        // Nuevas coordenadas
        coordinates.add(new double[]{24.60, 17.46});
        coordinates.add(new double[]{21.32, 19.36});
        coordinates.add(new double[]{17.88, 21.37});
        coordinates.add(new double[]{14.44, 23.22});
        coordinates.add(new double[]{14.44, 27.03});
        coordinates.add(new double[]{14.44, 30.73});
        coordinates.add(new double[]{14.44, 34.47});
        coordinates.add(new double[]{17.93, 36.44});
        coordinates.add(new double[]{21.26, 38.23});
        coordinates.add(new double[]{24.66, 40.03});
        coordinates.add(new double[]{27.98, 38.17});
        coordinates.add(new double[]{31.50, 36.20});
        coordinates.add(new double[]{34.93, 34.46});
        coordinates.add(new double[]{34.77, 30.67});
        coordinates.add(new double[]{34.72, 26.96});
        coordinates.add(new double[]{34.72, 23.19});
        coordinates.add(new double[]{31.48, 21.24});
        coordinates.add(new double[]{27.99, 19.48});

        // Crear la ventana
        JFrame ventana = new JFrame("Copos de Nieve de Koch");
        ventana.add(new FiguraFractal6(coordinates, 4)); // Nivel de fractal = 4
        ventana.setSize(800, 800);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        // Obtener el centro de la ventana
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Escalar y mover los puntos para mejor visualización
        double scale = 10.0; // Escala para ampliar las coordenadas

        // Dibuja un copo de nieve en cada coordenada
        for (double[] coord : coordinates) {
            double x = (coord[0] * scale) + centerX - 200; // Ajustar para centrar
            double y = (coord[1] * scale) + centerY - 200; // Ajustar para centrar
            drawKochSnowflake(g, x, y, 20.0, nivel); // Tamaño del copo de nieve = 20.0
        }
    }

    // Dibuja un copo de nieve de Koch centrado en (centerX, centerY)
    private void drawKochSnowflake(Graphics g, double centerX, double centerY, double size, int nivel) {
        // Seis puntos para formar un hexágono (copo de nieve hexagonal)
        for (int i = 0; i < 6; i++) {
            double angle1 = Math.PI / 3 * i; // Ángulo para el punto actual
            double angle2 = Math.PI / 3 * (i + 1); // Ángulo para el siguiente punto

            // Coordenadas de los puntos del hexágono
            double x1 = centerX + size * Math.cos(angle1);
            double y1 = centerY + size * Math.sin(angle1);
            double x2 = centerX + size * Math.cos(angle2);
            double y2 = centerY + size * Math.sin(angle2);

            // Dibuja una curva de Koch entre los dos puntos del hexágono
            paintRecursivo(g, nivel, x1, y1, x2, y2);
        }
    }

    // Dibuja una curva de Koch entre dos puntos (x1, y1) y (x2, y2)
    private void paintRecursivo(Graphics g, int nivel, double xp1, double yp1, double xp2, double yp2) {
        if (nivel <= 0) {
            // Dibuja una línea recta si el nivel de recursión es 0
            g.setColor(new Color(0, 150, 255));
            g.drawLine((int) xp1, (int) yp1, (int) xp2, (int) yp2);
        } else {
            // Divide el segmento en tres partes
            double dx = (xp2 - xp1) / 3.0;
            double dy = (yp2 - yp1) / 3.0;

            // Calcula los puntos intermedios
            double xA = xp1 + dx;
            double yA = yp1 + dy;
            double xB = xp2 - dx;
            double yB = yp2 - dy;

            // Calcula el punto del triángulo equilátero
            double xx = xp1 + 3 * dx / 2 - dy * sin60;
            double yy = yp1 + 3 * dy / 2 + dx * sin60;

            // Llama recursivamente para los nuevos segmentos
            paintRecursivo(g, nivel - 1, xp1, yp1, xA, yA);
            paintRecursivo(g, nivel - 1, xA, yA, xx, yy);
            paintRecursivo(g, nivel - 1, xx, yy, xB, yB);
            paintRecursivo(g, nivel - 1, xB, yB, xp2, yp2);
        }
    }
}