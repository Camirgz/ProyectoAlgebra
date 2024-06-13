import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        //Inicializaciones
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        Circunferencia circunferencia = new Circunferencia();
        Parabola parabola = new Parabola();
        Elipse elipse = new Elipse();
        Hiperbola hiperbola = new Hiperbola();
        
        //Menu
        while (continuar) {
            System.out.println("Seleccione el tipo de cónica:");
            System.out.println("1. Parábola");
            System.out.println("2. Elipse");
            System.out.println("3. Hipérbola");
            System.out.println("4. Circunferencia");
            System.out.println("5. Salir");

            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    parabola.manejarParabola(scanner);
                    break;
                case 2:
                    elipse.manejarElipse(scanner);
                    break;
                case 3:
                    hiperbola.manejarHiperbola(scanner);
                    break;
                case 4:
                    circunferencia.manejarCircunferencia(scanner);
                    break;
                case 5:
                    System.out.println("Gracias!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        scanner.close();
    }
}
