/*
 * elegimos los algoritmos quicksort y mergesort
 * por su velocidad y flexibilidad
 * Quick Sort --> O(N log N)
 * Merge Sort --> O(N log N)
 */

public class algoritmosOrdenamiento {
    //implementacion de ambos algoritmos:


    //Quicksort implementado.
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

    //Merge Sort implementado.
    public static void mergeSort(ciudadesYPromedio[] arr, int n){

        //si el arreglo tiene 1 o 0 elementos sale del algoritmo
        if (n < 2) {
            return;
        }

        int medio = n / 2; //encontramos el punto medio del arreglo

        /*
         * arreglos temporales, uno para la izquierda (l)
         * otro para la derecha (r)
         */
        ciudadesYPromedio[] l = new ciudadesYPromedio[medio];
        ciudadesYPromedio[] r = new ciudadesYPromedio[n - medio];

        //copiar los datos a los arreglos temporales
        System.arraycopy(arr, 0, l, 0, medio); //copia la mitad de los datos de "a" al arreglo "l"
        System.arraycopy(arr, medio, r, 0, n - medio); //copiar la mitad de los datos de "a" al arreglo "r"

        //ordenar las dos mitades (izquierda y derecha)
        mergeSort(l, medio); //ordenar izquierda
        mergeSort(r, n - medio); //ordenar derecha

        //hacer "merge" a las mitades ya ordenadas
        merge(arr, l, r, medio, n - medio);


        
    }
    

    public static void merge(ciudadesYPromedio[] a, ciudadesYPromedio[] l, 
    ciudadesYPromedio[] r, int izquierda, int derecha){
        
        /*
         * ciudadesYPromedio[] a --> arreglo donde va el resultado
         * ciudadesYPromedio[] l --> mitad izquierda ordenada
         * ciudadesYPromedio[] r --> mitad derecha ordenada
         */

        int i = 0, j = 0, k = 0;
        //comparar los elementos de las dos mitades mientras haya elementos en ambos arrelgos.
        while (i < izquierda && j < derecha) {
            //la temperatura mas caliente va primero (elemento menor)
            if (l[i].compareTo(r[j]) <= 0) {
                //como l[i] es de mayor temperatura, se coloca en el arreglo final
                a[k++] = l[i++]; //aqui no mas se mueven los punteros i y k al siguiente elemento
            } else {
                //en cambio aqui si r[j] es de mayor temperatura se pone en el arreglo final
                a[k++] = r[j++]; //igual mover los punteros
            }
        }

        //para elementos restantes

        while (i < izquierda) { //si hay elementos sobrantes en el arreglo de la izquierda...
            a[k++] = l[i++]; //copiarlos al final del arreglo final 
        }
        while (j < derecha) { //si hay elementos sobrantes en el arreglo de la derecha...
            a[k++] = r[j++]; //copiarlos al final del arrelgo final
        }
    }
}