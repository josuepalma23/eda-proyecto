## Caso de Estudio: Análisis de temperaturas globales

Este proyecto plantea analizar las temperaturas globales diarias de las grandes ciudades del mundo utilizando como base el conjunto de datos **"Daily Temperature of Major Cities"** que se encuentra disponible en la plataforma de Kaggle: [https://www.kaggle.com/datasets/sudalairajkumar/daily-temperature-of-major-cities/data](https://www.kaggle.com/datasets/sudalairajkumar/daily-temperature-of-major-cities/data).

Este dataset tiene en su haber registros históricos de temperatura promedio diaria en diferentes ciudades del planeta, además de contener también información de la fecha en que se registra y su ubicación geográfica. Con este dataset disponible, se considerará un subconjunto de más de **novecientos mil datos registrados** los cuales representarán distintos días y ciudades, cada registro incluirá estas tres columnas principales:

| Columna | Tipo de dato | Descripción |
| :--- | :--- | :--- |
| Ciudad | String | Nombre de la ciudad |
| Fecha | Date | Fecha del registro de la temperatura |
| TemperaturaPromedio | Float | Temperatura en grados Celsius promedio del día |

---

### Desarrollo de la Fórmula y Objetivo del Proyecto

Con esto en mente, el objetivo del proyecto se basa en **ordenar los registros de la columna `TemperaturaPromedio` en orden descendente**, con el fin de identificar aquellas ciudades con mayor temperatura y en base a estos datos realizar un análisis.

Este proceso permitirá analizar el comportamiento de los algoritmos de ordenación **Merge Sort** y **Quick Sort**, tanto en su implementación simple (en este caso, **Java**) y su versión paralelizada (que sería **Python con `multiprocessing`**), midiendo así el tiempo total de ejecución en diferentes equipos.


![Fórmula de Temperatura promedio por Año](https://github.com/user-attachments/assets/4ef7bacf-cdb9-4461-9423-d56daac95cbb)

---

## Características Técnicas de las computadoras del Grupo

| Persona | CPU | RAM | SO |
| :--- | :--- | :--- | :--- |
| Zenán | Intel Core i5-13420H (8 núcleos) | 16gb | Windows 11 |
| Josue | AMD Ryzen 5 5500U with Radeon Graphics (6 núcleos) | 8gb | Linux (Ubuntu) |
| Jhon | AMD Ryzen 5 7520U with Radeon Graphics (4 núcleos) | 16gb | Windows 11 |

---

## Explicación de los Tipos de Ordenamiento (Zenán)

En el proyecto se utilizaron dos tipos de ordenamiento de caso promedio, Quick Sort y Merge Sort. Sus conceptos a continuación:

- Quick Sort: Usa una estrategia de 'divide y vencerás' para ordenar un arreglo, es decir, divide un gran arreglo de datos en subarreglos más pequeños. Su funcionamiento es el siguiente, selecciona un elemento que servirá como pivote y posteriormente ordena los valores mayores que esté a un lado y los menores al otro lado, repitiendo estos pasos hasta que esté ordenado el arreglo. Esta técnica es altamente eficiente en conjuntos de datos muy grandes debido a que su complejidad promedio es O(n*logn).
- Merge Sort: De igual manera, implementa la estrategia de 'divide y vencerás' ya que divide el conjunto de datos en mitades cada vez más pequeñas, las ordena y luego las fusiona de nuevo en una sola lista ordenada. Su tiempo de ejecución es similar al de Quick Sort que se mencionó anteriormente, ya que es O(n*logn).

---

## Librerías y Funciones Java (Josue)

**Lectura y Escritura de archivos de entrada y salida**
- java.io.BufferedReader 
- java.io.BufferedWriter 
- java.io.FileReader
- java.io.FileWriter

**Manejo de Excepciones**
- java.util.IOException 

**Control de Arreglos y Listas**
- java.util.ArrayList
- java.util.Arrays

**Librerías de Mapas y Hashing para pares de entrada y clave**
- java.util.Map
- java.util.HashMap


---

## Librerías y Funciones Python (Jhon)

---

## Tablas Comparativas (Jhon)

### Tabla 1: Procesamiento simple (Java vs Python)

### Tabla 2: Procesamiento simple vs multiprocesamiento (Python)

---

## Análisis de las Tablas (Zenán)

---

## Análisis de Temperaturas posterior al ordenamiento

Al completarse el ordenamiento, se generarán archivos que cumplen con el objetivo de mostrar las temperaturas de ciudades en orden **descendente**. Esto genera el siguiente análisis:
**Extremos Absolutos:** Los datos generados describen una serie de extremos absolutos con respecto a la temperatura de las ciudades en función a la **Fórmula de Temperatura Promedio por Año**, lo que nos permite hacer un "ranking" o "top" de ciudades más calurosas en los datos elegidos para estudiar, los datos del archivo ordenado generado podría responder preguntas tales como: "¿Cuál fue el evento de ciudad-año más caluroso en una muestra de 25 años?", "¿Cuáles son las ciudades-año más calientes?" o por otro lado, "¿Cuáles son los eventos ciudad-año más fríos de la muestra?", en este último caso, se refiere al final de los datos, ya que se ubican en orden descendente.

**Nota:** Los datos generados después del ordenamiento no pueden describir alguna tendencia con respecto a la problemática del calentamiento global, dado que para esto, se necesitaría también tomar en cuenta a las fechas o el cambio de tiempo en el desarrollo de las fórmulas, más bien, podría ser interpretado como un "record histórico" de las ciudades-años más calientes en un período de 25 años.

---

## Conclusiones finales (Zenán)
