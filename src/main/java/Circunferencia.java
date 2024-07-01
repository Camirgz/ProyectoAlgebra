import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Circunferencia extends JPanel {

    private double A, B, C, D, E, F;
    //establecemos variables para el centro de la circunferencia (h,k) y su radio
    private double h, k, r;

    public Circunferencia() {
    }

    public Circunferencia(double A, double B, double C, double D, double E, double F) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.E = E;
        this.F = F;

        //Revisamos que no solo cumpla con la condición A=C para ser circunferencia
        //Si no que también estos formen una circunferencia válida desde un inicio 

     double[] canonica = calcularFormaCanonica(A, C, D, E, F);
        if (canonica != null) {
              this.h = canonica[0];
             this.k = canonica[1];
             this.r = canonica[2];
          } else {
            throw new IllegalArgumentException("Los coeficientes proporcionados no forman una circunferencia válida, el radio es menor o igual que 0");
    }
 }

    public void menu(Scanner scanner) {
        try {
            System.out.println("Ingrese los coeficientes A, B, C, D, E, F para la circunferencia (Ax² + Bxy + Cy² + Dx + Ey + F = 0):");
            double A = scanner.nextDouble();
            double B = scanner.nextDouble();
            double C = scanner.nextDouble();
            double D = scanner.nextDouble();
            double E = scanner.nextDouble();
            double F = scanner.nextDouble();

            if (A == C && B == 0) {
                System.out.println("Los coeficientes corresponden a una circunferencia.");
                valores(A, B, C, D, E, F);
                grafica(A, B, C, D, E, F);
            } else {
                System.out.println("Los coeficientes ingresados no corresponden a una circunferencia.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Por favor, ingrese valores numéricos válidos.");
            scanner.nextLine(); // Limpiar el buffer
        } catch (Exception e) {
            System.out.println("Se ha producido un error inesperado: " + e.getMessage());
        }
    }

    public static void valores(double A, double B, double C, double D, double E, double F) {
        // Forma cuadrática
        StringBuilder formaCuadratica = new StringBuilder();
        if (A != 0) formaCuadratica.append(A).append("x² ");
        if (B != 0) formaCuadratica.append((B > 0 && formaCuadratica.length() > 0 ? "+ " : "")).append(B).append("xy ");
        if (C != 0) formaCuadratica.append((C > 0 && formaCuadratica.length() > 0 ? "+ " : "")).append(C).append("y² ");
        if (D != 0) formaCuadratica.append((D > 0 && formaCuadratica.length() > 0 ? "+ " : "")).append(D).append("x ");
        if (E != 0) formaCuadratica.append((E > 0 && formaCuadratica.length() > 0 ? "+ " : "")).append(E).append("y ");
        if (F != 0) formaCuadratica.append((F > 0 && formaCuadratica.length() > 0 ? "+ " : "")).append(F).append(" ");
        formaCuadratica.append("= 0");

        System.out.println("Forma cuadrática: " + formaCuadratica.toString().trim());

        
        //con las coordenadas del centro y el radio formamos la forma cánonica:(x-h)²+(y-)²=r² de una circunferencia
        double[] canonica = calcularFormaCanonica(A, C, D, E, F);
        if (canonica != null) {
            System.out.printf("Forma canónica: (x - %.2f)² + (y - %.2f)² = %.2f²\n", canonica[0], canonica[1], canonica[2]);
        }

        // Matriz asociada
        double[][] matrizDatos = {{A, B / 2}, {B / 2, C}};
        RealMatrix matriz = new Array2DRowRealMatrix(matrizDatos);
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

    //@param: coeficientes de la forma cúadratica de la circunferencia
    //retorna double[] con los valores correspondientes a la coordenas del centro de la circunferencia y el radio

    //Apartir de la ecuación cuádratica de la circunferencia, pasamos a la ecuación general, 
    //utilizamos las fórmulas para el centro y radio de la ecuación general
    public static double[] calcularFormaCanonica(double A, double C, double D, double E, double F) {
      
        //obtenemos coordenada x del centro 
        double h = -D / (2 * A);
        
        //obtenemos coordenada y del centro
        double k = -E / (2 * A);

        //Fórmula que nos da el radio al cuadrado
        double radio = (D * D + E * E - 4 * A* F) / (4 * A * A);


        //la ecuación no corresponde a una circunferencia si el radio es menor que cero, y si es 0, sí es válida pero no existe y no se puede graficar
        if (radio <= 0) {
            return null; 
        }

        //aplicamos raiz para obtener el radio
        double r = Math.sqrt(radio);

        //retornamos array {x,y,r}
        return new double[]{h, k, r};
    }

    public static void grafica(double A, double B, double C, double D, double E, double F) {
        JFrame frame = new JFrame("Gráfica de Circunferencia");
        Circunferencia panel = new Circunferencia(A, B, C, D, E, F);
        frame.add(panel);
        frame.setSize(1000, 1000); // Tamaño inicial
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double centroX = this.h;
        double centroY = this.k;
        double radio = this.r;

        // Escala en base al tamaño del panel
        double escala = Math.min(getWidth(), getHeight()) / (2 * radio + 100); // Márgenes
        if (Double.isInfinite(escala) || Double.isNaN(escala)) {
            System.out.println("Error: La escala calculada es infinita o NaN.");
            return;
        }

        if (radio <= 1) {
            g2d.setStroke(new BasicStroke(0.5f)); // Ajuste del grosor para radios pequeños
            System.out.println("Aviso: El radio de la circunferencia es muy pequeño.");
        } else {
            g2d.setStroke(new BasicStroke(1.5f)); // Grosor estándar
        }

        AffineTransform transform = new AffineTransform();
        transform.scale(escala, -escala); // Invertir el eje y
        transform.translate(getWidth() / (2 * escala) - centroX, -getHeight() / (2 * escala) - centroY);
        g2d.setTransform(transform);

        

        // Dibujar la circunferencia
        g2d.setColor(Color.BLUE);
        Path2D.Double path = new Path2D.Double();
        path.moveTo(centroX + radio, centroY);
        for (double t = 0; t <= 2 * Math.PI; t += 0.1) {
            double x = centroX + radio * Math.cos(t);
            double y = centroY + radio * Math.sin(t);
            path.lineTo(x, y);
        }
        path.closePath();
        g2d.draw(path);
        g2d.setColor(Color.lightGray);
        g2d.setStroke(new BasicStroke(1)); // Grosor de las líneas
        g2d.drawLine(getWidth() / 2, 0, -getWidth() / 2, 0); // Eje X
        g2d.drawLine(0, getHeight() / 2, 0, -getHeight() / 2); // Eje Y
        // Restaurar transformación para dibujar correctamente los ejes
        g2d.setTransform(new AffineTransform());

        // Dibujar números en los ejes
        Font font = new Font("Arial", Font.PLAIN, 12);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);

        int paso = (int) Math.ceil(1 / escala); //esto es lo que hay entre los nums de la grafic
        

        // Números en el eje X
        for (int x = -getWidth() / (2 * (int) escala); x <= getWidth() / (2 * (int) escala); x += paso+5) {
            if (x != 0) {
                int posX = (int) (x * escala + getWidth() / 2);
                g2d.drawString(String.valueOf(x), posX , getHeight() / 2 ); // Ajuste para que esté encima del eje X
                //sume o reste a posX para moverlo
            }
        }
    
        // Dibujar números en el eje Y
        for (int y = -getHeight() / (2 * (int) escala); y <= getHeight() / (2 * (int) escala); y += paso+5) {
            if (y != 0) {
                int posY = (int) (-y * escala + getHeight() / 2);
                g2d.drawString(String.valueOf(y), getWidth() / 2 , posY); // Ajuste para que esté encima del eje Y
            }//sume o reste a posY para moverlo
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Circunferencia circunferencia = new Circunferencia();
        circunferencia.menu(scanner);
    }
}
