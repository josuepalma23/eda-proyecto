/*
 Esta clase es auxiliar y ayuda a mantener un registro 
 o una suma acumulada de los promedios.

 Es decir, aqui se declara la abstraccion de la formula #1
 Temperatura promedio por anio.
 */

public class agregadorTemperatura{
    double sumaTotal = 0.0;
    int conteo = 0;

    //agregar una temperatura al conteo.
    public void agregar(double valor){
        if(valor > -90){
            this.sumaTotal += valor;
            this.conteo++;
        }
    }


    /*
    evitamos la excepcion aritmetica con un operador ternario
    si no se contaron dias, el promedio sera 0, sin embargo,
    si se cuentan los dias, se calcula el promedio de acuerdo a la formula.
    */
    public double getPromedio(){
        return (conteo == 0) ? 0 : sumaTotal / conteo;
    }



}