import java.util.Scanner;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

public class Hiperbola {

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
            System.out.printf("%.10f\n", valorPropio); //Redondeo
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
       System.out.println("Acá debe ir el código de la gráfica");
   }

}
