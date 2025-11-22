import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

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
        
    }
}