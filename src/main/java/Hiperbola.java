
import java.util.Scanner;

public class Hiperbola {

    public void manejarHiperbola(Scanner scanner) {
        System.out.println("Ingrese los coeficientes A, B, C, D, E, F para la hipérbola (Ax² + Bxy + Cy² + Dx + Ey + F = 0):");
        double A = scanner.nextDouble();
        double B = scanner.nextDouble();
        double C = scanner.nextDouble();
        double D = scanner.nextDouble();
        double E = scanner.nextDouble();
        double F = scanner.nextDouble();
        
        if (B * B - 4 * A * C > 0) {
            System.out.println("Los coeficientes corresponden a una hipérbola.");
            mostrarHiperbola(A, B, C, D, E, F);
        } else {
            System.out.println("Los coeficientes ingresados no corresponden a una hipérbola.");
        }
    }

    private static void mostrarHiperbola(double a, double b, double c, double d, double e, double f){
    }

}
