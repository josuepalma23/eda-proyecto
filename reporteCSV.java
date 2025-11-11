import java.io.FileWriter;   
import java.io.IOException;  
import java.util.Arrays;     
import java.util.List;     

public class reporteCSV {

    public static void main(String[] args) {
        
        //hora en la que comenzo el programa
        long tiempoINICIAL = System.nanoTime();
        
        String archivoEntrada = "nuevo.csv";
        String archivoSalida = "reporte_ordenado.csv";

        System.out.println("Procesamiento Simple: ");
        
        //instanciamos el procesador de los datos
        procesadorDatos procesador = new procesadorDatos();
        
        List<ciudadesYPromedio> todosLosResultados; // variable para la lista de los resultados
        long tiempoProcesamiento;

        try {
            long inicioProcesamiento = System.nanoTime(); //inicio del procesamiento 
            
            procesador.procesarArchivo(archivoEntrada);
            todosLosResultados = procesador.getResultadosFormula(); //obtener los resultados de promedios
            long finProcesamiento = System.nanoTime(); //final del procesamiento 
            tiempoProcesamiento = finProcesamiento - inicioProcesamiento;


        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("\n--- TIEMPOS DE PROCESAMIENTO ---");
        System.out.println(String.format("Tiempo del procesamiento Simple: \t%d nanosegundos", tiempoProcesamiento));


        int n = todosLosResultados.size(); //cantidad de promedios calculados

        ciudadesYPromedio[] arrParaQuick = todosLosResultados.toArray(new ciudadesYPromedio[0]); //converitr la lista en un arreglo
        ciudadesYPromedio[] arrParaMerge = Arrays.copyOf(arrParaQuick, n); //comparar ambos arreglos de los dos algoritmos

        System.out.println("\n Comparando algoritmos sobre " + n + " promedios calculados.");

        //Quick
        long startQuick = System.nanoTime(); 
        algoritmosOrdenamiento.quickSort(arrParaQuick, 0, arrParaQuick.length - 1);
        long endQuick = System.nanoTime();
        long tiempoQuick = endQuick - startQuick; // Calcula tiempo
        System.out.println(String.format("Ordenado con QuickSort -> \t%d nanosegundos", tiempoQuick));

        //Merge
        long startMerge = System.nanoTime();
        algoritmosOrdenamiento.mergeSort(arrParaMerge, arrParaMerge.length);
        long endMerge = System.nanoTime(); 
        long tiempoMerge = endMerge - startMerge; //Misma logica
        System.out.println(String.format("Ordenado con MergeSort -> \t%d nanosegundos", tiempoMerge));
        
        // --- CONCLUSIÓN de Tiempos ---
        System.out.println("\n--- CONCLUSIÓN DE TIEMPOS DE EJECUCION---");
        // Compara los dos tiempos de ejecución.
        if (tiempoMerge < tiempoQuick) {
            System.out.println("CONCLUSIÓN: MergeSort fue mas efectivo computacionalmente al ser mas rapido");
        } else {
            System.out.println("CONCLUSIÓN: QuickSort fue mas efectivo computacionalmente al ser mas rapido");
        }
        System.out.println("fin de la comparacion de tiempos de ejecucion");

        System.out.println("----------------------");

        System.out.println("\nGenerando nuevo archivo CSV ordenado... " + archivoSalida);
        

        try (FileWriter writer = new FileWriter(archivoSalida)) {

            writer.write("Anio,Ciudad,TemperaturaPromedio\n"); //columnas
            
            for (ciudadesYPromedio res : arrParaQuick) {
                // Escribe una línea de texto en el archivo por cada objeto.
                writer.write(
                    res.anio + "," + //Escribir el anio
                    "\"" + res.ciudad + "\"" + "," + //Escribir la ciudad
                    String.format("%.2f", res.promedioTemperatura) + "\n" //Escribir temperatura a 2 decimales
                );
            }

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo... " + e.getMessage());
        }

        //calculo del tiempo del programa
        long tiempoFINAL = System.nanoTime();
        long duracionNANOSegundos = tiempoFINAL - tiempoINICIAL;
        
        System.out.println("\n--- PROCESO COMPLETADO ---");
        System.out.println(String.format("Tiempo total del programa: \t%d nanosegundos.", duracionNANOSegundos));
    }
}