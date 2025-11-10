public class ciudadesYPromedio implements Comparable<ciudadesYPromedio>{
    //Atributos de una ciudad junto al promedio.
    String ciudad;
    int anio;
    double promedioTemperatura;


    //Constructor.
    public ciudadesYPromedio(String ciudad, int anio, double promedioTemperatura){
        this.ciudad = ciudad;
        this.anio = anio;
        this.promedioTemperatura = promedioTemperatura;
    }

    public double getPromedioTemperatura(){
        return this.promedioTemperatura;
    }


    /*
     * Nos ayuda a tener el ordenamiento de mayor a menor
     */
    public int compareTo(ciudadesYPromedio other){
        //comparar de mayor a menor las temperaturas en los algoritmos 
        //comparamos entonces de LA MAYOR temperatura a LA MENOR temperatura (analisis)
        return Double.compare(other.promedioTemperatura, this.promedioTemperatura);
    }

}
