import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


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

                    
                }
            }
        }
    }
}