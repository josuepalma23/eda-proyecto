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

                    String ciudad = columnas[1];
                    double temp = Double.parseDouble(columnas[2]);
                    String anioStr = columnas[3].substring(0, 4);
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

            //procesar el resultado

            /*
             * Estas lineas son importantes porq nos ayuda a reconstruir los datos desde la clave
             * "Portoviejo-2018" --> ["Portoviejo", "2018"]
             * asi se recupera de nuevo la informacion.
             */
            String[] claveSplit = clave.split("-");
            String ciudad = claveSplit[0];
            int anio = Integer.parseInt(claveSplit[1]);
            double promedio = agregador.getPromedio();

            resultados.add(new ciudadesYPromedio(ciudad, anio, promedio));

        }
        return resultados;
    }
}