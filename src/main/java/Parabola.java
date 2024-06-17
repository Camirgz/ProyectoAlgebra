import javax.swing.*;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Scanner;

public class Parabola extends JPanel {

    private double A, B, C, D, E, F;

    public Parabola(){}
    public Parabola(double A, double B, double C, double D, double E, double F) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.E = E;
        this.F = F;
    }

    public void menu(Scanner scanner) {
        System.out.println("Ingrese los coeficientes A, B, C, D, E, F para la parábola (Ax² + Bxy + Cy² + Dx + Ey + F = 0):");
        double A = scanner.nextDouble();
        double B = scanner.nextDouble();
        double C = scanner.nextDouble();
        double D = scanner.nextDouble();
        double E = scanner.nextDouble();
        double F = scanner.nextDouble();

        if (B * B - 4 * A * C == 0) {
            System.out.println("Los coeficientes corresponden a una parábola.");
            valores(A, B, C, D, E, F);
            grafica(A, B, C, D, E, F);
        } else {
            System.out.println("Los coeficientes ingresados no corresponden a una parábola.");
        }
    }

    public static void valores(double A, double B, double C, double D, double E, double F) {
        // Forma cuadrática, se usa string builder para no mostrar monomios con coeficientes 0
        StringBuilder formaCuadratica = new StringBuilder();
        if (A != 0) formaCuadratica.append(A).append("x² ");
        if (B != 0) formaCuadratica.append((B > 0 && formaCuadratica.length() > 0 ? "+ " : "")).append(B).append("xy ");
        if (C != 0) formaCuadratica.append((C > 0 && formaCuadratica.length() > 0 ? "+ " : "")).append(C).append("y² ");
        if (D != 0) formaCuadratica.append((D > 0 && formaCuadratica.length() > 0 ? "+ " : "")).append(D).append("x ");
        if (E != 0) formaCuadratica.append((E > 0 && formaCuadratica.length() > 0 ? "+ " : "")).append(E).append("y ");
        if (F != 0) formaCuadratica.append((F > 0 && formaCuadratica.length() > 0 ? "+ " : "")).append(F).append(" ");
        formaCuadratica.append("= 0");

        System.out.println("Forma cuadrática: " + formaCuadratica.toString().trim());

        RealMatrix matriz = new Array2DRowRealMatrix(new double[][] {{A, B / 2}, {B / 2, C}});
        System.out.println("Matriz asociada:");
        for (int i = 0; i < matriz.getRowDimension(); i++) {
            for (int j = 0; j < matriz.getColumnDimension(); j++) {
                System.out.printf("%.1f ", matriz.getEntry(i, j));
            }
            System.out.println();
        }

        // Valores propios y vectores propios
        EigenDecomposition eigenDecomposition = new EigenDecomposition(matriz);
        double[] valoresPropios = eigenDecomposition.getRealEigenvalues();
        RealMatrix vectoresPropios = eigenDecomposition.getV();

        System.out.println("Valores propios:");
        for (double valorPropio : valoresPropios) {
            System.out.printf("%.10f\n", valorPropio); // Redondeo
        }

        System.out.println("Vectores propios:");
        for (int i = 0; i < vectoresPropios.getRowDimension(); i++) {
            for (int j = 0; j < vectoresPropios.getColumnDimension(); j++) {
                System.out.printf("%.10f ", vectoresPropios.getEntry(i, j));
            }
            System.out.println();
        }
    }

    public static void grafica(double A, double B, double C, double D, double E, double F) {
        JFrame frame = new JFrame("Gráfica de Parábola");
        Parabola panel = new Parabola(A, B, C, D, E, F);
        frame.add(panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Configuración de la transformación para dibujar la parábola centrada
        AffineTransform transform = new AffineTransform();
        transform.translate(getWidth() / 2, getHeight() / 2); // Centrar en el panel
        transform.scale(3, -3); // Invertir el eje y y ajustar la escala para alejar la vista
        g2d.setTransform(transform);

        // Dibujar ejes coordenados
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.LIGHT_GRAY);

        // Dibujar cuadrícula
        int gridSpacing = 20;
        for (int i = -getWidth() / 2; i <= getWidth() / 2; i += gridSpacing) {
            g2d.drawLine(i, -getHeight() / 2, i, getHeight() / 2);
        }
        for (int i = -getHeight() / 2; i <= getHeight() / 2; i += gridSpacing) {
            g2d.drawLine(-getWidth() / 2, i, getWidth() / 2, i);
        }

        // Dibujar ejes
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(-getWidth() / 2, 0, getWidth() / 2, 0); // Eje X
        g2d.drawLine(0, -getHeight() / 2, 0, getHeight() / 2); // Eje Y


        // Dibujar la parábola
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.RED);

        double step = 0.01; // Reducir el paso para suavizar la línea
        double xAnterior = -getWidth() / 2 / 3; // Ajustar el valor inicial de x según la escala
        double yAnterior = calculateY(xAnterior);

        for (double x = -getWidth() / 2 / 3 + step; x <= getWidth() / 2 / 3; x += step) {
            double y = calculateY(x);
            g2d.drawLine((int) (xAnterior * 3), (int) (yAnterior * 3), (int) (x * 3), (int) (y * 3)); // Ajustar según la escala
            xAnterior = x;
            yAnterior = y;
        }
    }

    private double calculateY(double x) {
        // Resolver la ecuación de la parábola para y
        double discriminante = B * B - 4 * A * C;
        double sqrtDiscriminante = Math.sqrt(discriminante);
        double den = 2 * A;
        double y1 = (-B * x + sqrtDiscriminante) / den;
        double y2 = (-B * x - sqrtDiscriminante) / den;


        double y = Math.abs(y1) < Math.abs(y2) ? y1 : y2;

        // Aplicar la ecuación general de la parábola para obtener la coordenada y
        return A * x * x + B * x * y + C * y * y + D * x + E * y + F;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parabola parabola = new Parabola(1, 0, 1, 0, 0, 0); // Ejemplo de una parábola x^2 + y^2 = 0
        parabola.menu(scanner);
    }
}
