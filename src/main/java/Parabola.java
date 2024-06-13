import javax.swing.*;
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

        // Matriz asociada: No es aplicable para una parábola

        // No se calculan valores propios ni vectores propios para una parábola
    }

    public static void grafica(double A, double B, double C, double D, double E, double F) {
        JFrame frame = new JFrame("Gráfica de Parábola");
        Parabola panel = new Parabola(A, B, C, D, E, F);
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

        // Configuración de la transformación para dibujar la parábola centrada
        AffineTransform transform = new AffineTransform();
        transform.translate(getWidth() / 2, getHeight() / 2); // Centrar en el panel
        transform.scale(1, -1); // Invertir el eje y
        g2d.setTransform(transform);

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.RED);

        double step = 0.1;
        double xAnterior = -getWidth() / 2;
        double yAnterior = 0;

        for (double x = -getWidth() / 2 + step; x <= getWidth() / 2; x += step) {
            double y = calculateY(x);
            g2d.drawLine((int) xAnterior, (int) yAnterior, (int) x, (int) y);
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

        // Elegir el valor de y correcto basado en la ubicación de la parábola
        // Puedes elegir el valor que necesites dependiendo de tu gráfica
        // Aquí se elige el valor más cercano al eje x
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
