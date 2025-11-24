import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;


public class procesamientoTemperaturas{
    //clase interna para describir cada registro
    static class Registro{
        String ciudad;
        String anio;
        double avgTemp;
        boolean esValido; //ayudara a depurar

        //constructor..
        public Registro(String ciudad, String anio, double avgTemp, boolean esValido){
            this.ciudad = ciudad;
            this.anio = anio;
            this.avgTemp = avgTemp;
            this.esValido = esValido;
        }
    }

    /*
     * Depuracion utilizando logica QuickSort
     * en vez de que el pivote sea un numero, sera el criterio de 
     * validez del dato, asi se mueven todos los datos validos a la izquierda
     * y los invalidos a la derecha
     */

    public static int Depuracion(Registro[] arr){
        int i = -1; //ultimo elemento valido encontrado

        for(int j = 0; j < arr.length; j++){
            //si el dato es valido y no es 0 se intercambia
            if(arr[j].esValido && arr[j].avgTemp != 0.0){
                i++;

                //intercamibo
                Registro temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        return i + 1; //desde 0 hasta i son datos validos
    }


public static Registro[] calcularPromediosAnuales(Registro[] datos) {
        //mapa para guardar la suma de las temperaturas de ciudad-anio
        Map<String, Double> sumas = new HashMap<>();
        
        //conteo de dias por ciudad
        Map<String, Integer> conteos = new HashMap<>();

        for (Registro r : datos) {
            String clave = r.ciudad + "|" + r.anio;

            //si esta clave ya existe en el mapa de sumas
            if (sumas.containsKey(clave)) {
                double sumaActual = sumas.get(clave);
                sumas.put(clave, sumaActual + r.avgTemp);
            } else {
                //si no existia crea la entrada en el mapa con la temperatura actual como valor inicial
                sumas.put(clave, r.avgTemp);
            }

            //misma logica para los dias
            if (conteos.containsKey(clave)) {
                int conteoActual = conteos.get(clave);
                conteos.put(clave, conteoActual + 1);
            } else {
                conteos.put(clave, 1);
            }
        }

        ArrayList<Registro> listaTemporal = new ArrayList<>();

        //por cada clave, obtener la suma total y los dias para usar la formula
        for (String clave : sumas.keySet()) {
            double sumaTotal = sumas.get(clave);
            int cantidadDias = conteos.get(clave);

            //formula
            double promedio = sumaTotal / cantidadDias;

            String[] partes = clave.split("\\|");
            String ciudad = partes[0];
            String anio = partes[1];

            listaTemporal.add(new Registro(ciudad, anio, promedio, true));
        }

        return listaTemporal.toArray(new Registro[0]);
    }

    public static Registro[] mergeSort(Registro[] arr){
        //caso base
        if (arr.length <= 1) {
            return arr;
        }

        int medio = arr.length / 2;
        Registro[] izq = Arrays.copyOfRange(arr, 0, medio);
        Registro[] der = Arrays.copyOfRange(arr, medio, arr.length);

        izq = mergeSort(izq);
        der = mergeSort(der);

        return merge(izq, der);
    }

    public static Registro[] merge(Registro[] izq, Registro[] der){
        Registro[] resultado = new Registro[izq.length + der.length];
        int i = 0, j = 0, k = 0;

        while (i < izq.length && j < der.length) {
            //ordenar de mayor a menor las temperaturas
            if (izq[i].avgTemp >= der[j].avgTemp) {
                resultado[k++] = izq[i++];
            } else {
                resultado[k++] = der[j++];
            }
        }

        while (i < izq.length) {
            resultado[k++] = izq[i++];
        }
        while (j < der.length) {
            resultado[k++] = der[j++];
        }
        return resultado;
    }

    public static void main(String[] args) {
        //inicio del programa calculado al inicio
        long inicioPrograma = System.nanoTime();

        String archivoEntrada = "nuevo.csv";
        String archivoSalida = "procesamiento_java.csv";

        System.out.println("Procesamiento Simple");
        long inicioLectura = System.nanoTime();

        ArrayList<Registro> bufferLectura = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoEntrada))){
            String linea = br.readLine(); //leer la primera linea osea el encabezaod

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(",");

                //si faltan columnas se considera invalido
                if (partes.length < 4) {
                    bufferLectura.add(new Registro("", "", 0.0, false));
                    continue;
                }

                try {
                    String brutoTemp = partes[2]
                                        .replace("\"", "")
                                        .replace("(", "")
                                        .replace(")", "");
                    
                    Double temperatura = Double.parseDouble(brutoTemp);

                    String brutoFecha = partes[3]
                                        .replace("\"", "")
                                        .replace("(", "")
                                        .replace(")", "");

                    String[] parteFecha = brutoFecha.split("/");
                    String anio = (parteFecha.length >= 3) ? parteFecha[2] : "0000";

                    //limpiar ciudades de las comillas
                    String ciudad = partes[1].replace("\"", "");

                    //filtro de las temperaturas
                    if (temperatura > -90) {
                        bufferLectura.add(new Registro(ciudad, anio, temperatura, true));
                    } else {
                        bufferLectura.add(new Registro("", "", 0.0, false));
                    }

                } catch (Exception e ){
                    bufferLectura.add(new Registro("", "", 0.0, false));

                }
            }
        } catch (IOException e ){
            System.out.println("Error al procesar " + e.getMessage());
            return;
        }

        Registro[] datosCrudos = bufferLectura.toArray(new Registro[0]);
        bufferLectura = null;

        long finLectura = System.nanoTime();
        System.out.println("Tiempo de lectura de datos: " + (finLectura - inicioLectura) / 1_000_000_000.0);
        System.out.println("Cantidad de datos: " + datosCrudos.length);

        //depuracion y tiempos de depuracion
        System.out.println("Depuracion con logica del algoritmo QuickSort");
        long inicioDepuracion = System.nanoTime();

        int dValidos = Depuracion(datosCrudos);
        Registro[] datosLimpios = Arrays.copyOfRange(datosCrudos, 0, dValidos);
        datosCrudos = null;

        long finDepuracion = System.nanoTime();
        System.out.printf("Tiempo de depuracion: %.4f s\n", (finDepuracion - inicioDepuracion) / 1_000_000_000.0);
        System.out.println("Datos Validos: " + dValidos);


        //crear los promedios 
        System.out.println("Calculando promedios...");
        Registro[] datosPromediados = calcularPromediosAnuales(datosLimpios);

        //ordenar en base al criterio de la formula
        System.out.println("Ordenamiento utilizando MergeSort");
        long inicioOrdenamiento = System.nanoTime();

        Registro[] datosOrdenados = mergeSort(datosPromediados);

        long finOrdenamiento = System.nanoTime();
        System.out.printf("Tiempo de Ordenamiento %.4f s\n", (finOrdenamiento - inicioOrdenamiento) / 1_000_000_000.0);
        System.out.println("Datos ordenados: " + datosOrdenados.length);

        //escribir en el archivo 
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))){
            bw.write("Anio, Ciudad, TemperaturaPromedio");
            bw.newLine();

            for(Registro r : datosOrdenados){
                // formato de 2 decimales
                String tempFormateada = String.format("%.2f", r.avgTemp).replace(',', '.');
                bw.write(r.anio + ",\"" + r.ciudad + "\"," + tempFormateada);
                bw.newLine();
            }

        } catch(IOException e ){
            System.out.println("Error al escribir: " + e.getMessage());
        }

        long finPrograma = System.nanoTime();
        System.out.println("Procesamiento Completado");
        System.out.printf("Tiempo total del programa %.4f segundos .\n", (finPrograma - inicioPrograma) / 1_000_000_000.0);



    }
}