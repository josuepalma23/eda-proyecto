import java.util.Arrays;

/*
 * elegimos los algoritmos quicksort y mergesort
 * por su velocidad y flexibilidad
 * Quick Sort --> O(N log N)
 * Merge Sort --> O(N log N)
 */

public class algoritmosOrdenamiento {
    //implementacion de ambos algoritmos:

    public static void quickSort(ciudadesYPromedio[] arr, int low, int high){
        if(low < high){
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(ciudadesYPromedio[] arr, int low, int high){
        ciudadesYPromedio pivote = arr[high];
        int i = (low - 1);
        for(int j = low; j < high; j++){
            //uso del compareTo, si arr[j] es menor (mayor temperatura)
            //que el pivote, se mueve a la izquierda
            if (arr[j].compareTo(pivote) <= 0) {
                i++;
                ciudadesYPromedio temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        ciudadesYPromedio temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
    
}