import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import java.util.Scanner;

public class Hiperbola extends JPanel {

    private double A, B, C, D, E, F;

    public Hiperbola(){

    }

    public Hiperbola(double A, double B, double C, double D, double E, double F) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.E = E;
        this.F = F;
    }

    public void menu(Scanner scanner) {
        System.out.println("Ingrese los coeficientes A, B, C, D, E, F para la hipérbola (Ax² + Bxy + Cy² + Dx + Ey + F = 0):");
        double A = scanner.nextDouble();
        double B = scanner.nextDouble();
        double C = scanner.nextDouble();
        double D = scanner.nextDouble();
        double E = scanner.nextDouble();
        double F = scanner.nextDouble();
        
        if (B * B - 4 * A * C > 0) {
            System.out.println("Los coeficientes corresponden a una hipérbola.");
            valores(A, B, C, D, E, F);
            grafica(A, B, C, D, E, F);
        } else {
            System.out.println("Los coeficientes ingresados no corresponden a una hipérbola.");
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

        // Matriz asociada
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
        JFrame frame = new JFrame("Gráfica de Hipérbola");
        Hiperbola panel = new Hiperbola(A, B, C, D, E, F);
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform transform = new AffineTransform();
        transform.scale(1, -1); // Invertir el eje y
        transform.translate(getWidth() / 2, -getHeight() / 2);
        g2d.setTransform(transform);

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLUE);

        // Dibujar el eje X y el eje Y
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawLine(-getWidth() / 2, 0, getWidth() / 2, 0); // Eje X
        g2d.drawLine(0, -getHeight() / 2, 0, getHeight() / 2); // Eje Y

        // Dibujar la hipérbola
        g2d.setColor(Color.RED);
        Path2D.Double path = new Path2D.Double();

        double step = 0.1;
        for (double x = -getWidth() / 2; x <= getWidth() / 2; x += step) {
            double[] yValues = calcularY(x);
            for (double y : yValues) {
                if (Double.isFinite(y)) {
                    path.moveTo(x, y);
                    path.lineTo(x + step, calcularY(x + step)[0]);
                }
            }
        }

        g2d.draw(path);
    }

    private double[] calcularY(double x) {
        double[] yValues = new double[2];
        double discriminante = B * B - 4 * A * C;
        double sqrtDiscriminante = Math.sqrt(discriminante);
        double den = 2 * A;
        yValues[0] = (-B * x + sqrtDiscriminante) / den;
        yValues[1] = (-B * x - sqrtDiscriminante) / den;
        return yValues;
    }

    
}
