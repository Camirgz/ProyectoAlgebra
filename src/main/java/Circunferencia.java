import java.util.Scanner;

public class Circunferencia {

    public void manejarCircunferencia(Scanner scanner) {
        System.out.println("Ingrese los coeficientes A, B, C, D, E, F para la circunferencia (Ax² + Bxy + Cy² + Dx + Ey + F = 0):");
        double A = scanner.nextDouble();
        double B = scanner.nextDouble();
        double C = scanner.nextDouble();
        double D = scanner.nextDouble();
        double E = scanner.nextDouble();
        double F = scanner.nextDouble();
        
        if (A == C && B == 0) {
            System.out.println("Los coeficientes corresponden a una circunferencia.");
            mostrarCircunferencia(A, B, C, D, E, F);
        } else {
            System.out.println("Los coeficientes ingresados no corresponden a una circunferencia.");
        }
    }

    private static void mostrarCircunferencia(double a, double b, double c, double d, double e, double f){
    }


}
