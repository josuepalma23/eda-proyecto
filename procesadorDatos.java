import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class procesadorDatos{
    private Hashtable<String, agregadorTemperatura> promediosCiudadYAnio = new Hashtable<>();

    /*
     * procesamos el archivo csv
     */

    public void procesarArchivo(String archivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine();
            String linea;


            while ((linea = br.readLine()) != null) {
                try {
                    String[] columnas = linea.split(",");

                    //se quitan las comillas de los datos para que el procesamiento funcione
                    String ciudad = columnas[1].replace("\"", ""); 
                    String tempStr = columnas[2].replace("\"", "");
                    /*
                     * Solucion al bug de los datos con parentesis
                     * ahora tambien se quitan los parentesis de los datos
                     * para el procesamiento.
                     */
                    tempStr = tempStr.replace("(", "");
                    tempStr = tempStr.replace(")", "");
                    
                    String fechaStr = columnas[3].replace("\"", "");
                    
                    //se salta la linea del archivo si esta quedó vacía despues de las
                    //comprobaciones.
                    if (tempStr.isEmpty()) {
                        continue;
                    }

                    double temp = Double.parseDouble(tempStr);

                    //se obtiene el nuevo formato del anio
                    String[] fechaPartes = fechaStr.split("/");
                    String anioStr = fechaPartes[2];

                    String clave = ciudad + "-" + anioStr;

                    /*
                     * computeIfAbsent comprueba rapidamente si existe una una entrada del
                     * agregador para una clave, si existe, la obtiene, si no, crea una nueva,
                     *  la ubica en la tabla y luego la obtiene
                     */
                    agregadorTemperatura agregadorNuevo = promediosCiudadYAnio.computeIfAbsent(
                        clave, k -> new agregadorTemperatura());
                    agregadorNuevo.agregar(temp); //incrementar el conteo de la suma total del agregador

                } catch (Exception e){
                    System.err.println("Error al procesar la linea " + linea + e.getMessage());
                }
            }
        }
    }


    public List<ciudadesYPromedio> getResultadosFormula(){
        List<ciudadesYPromedio> resultados = new ArrayList<>();

        //obtenemos las claves
        for(String clave : promediosCiudadYAnio.keySet()){
            //obtener los valores de cada clave
            agregadorTemperatura agregador = promediosCiudadYAnio.get(clave);

            /*
             * BUG manejado, al intentar ejecutar el programa, causaba un error que lo terminaba instantaneamente
             * despues de un bucle infinito, resulta que una ciudad como "Ulan-Bator" tiene un guion
             * por lo que se escribe su clave de distinta forma, aqui se maneja este error.
             */

             //buscamos la posicion de un ultimo guion en la clave
             int ultimoGuion = clave.lastIndexOf("-");

             if (ultimoGuion == -1) { //esto quiere decir que la clave esta mal formada, entonces se ignoramos
                continue;                
             }

             //la ciudad ahora se define como todo hasta el ultimo guion
             String ciudad = clave.substring(0, ultimoGuion);

             //el anio ahora es todo DESPUES del ultimo guion
             String anioStr = clave.substring(ultimoGuion + 1);
             
             try {
                //ahora el anio es siempre un numero
                int anio = Integer.parseInt(anioStr);
                double promedio = agregador.getPromedio();

                resultados.add(new ciudadesYPromedio(ciudad, anio, promedio));

             } catch (Exception e) {
                System.err.println("Clave mal formada, ignorando... " + clave);
             }

        }
        return resultados;
    }
}