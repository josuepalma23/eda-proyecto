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

Con esto en mente, el objetivo del proyecto se basa en **ordenar los registros de la columna `TemperaturaPromedio` en orden descendente**, con el fin de identificar los días y lugares con las temperaturas más altas a nivel global.

Este proceso permitirá analizar el comportamiento de los algoritmos de ordenación **Merge Sort** y **Quick Sort**, tanto en su implementación simple (en este caso, **Java**) y su versión paralelizada (que sería **Python con `multiprocessing`**), midiendo así el tiempo total de ejecución en diferentes equipos. (EN DESARROLLO DEPENDIENDO DE LO QUE DIGA EL ING)


![Fórmula de Temperatura promedio por Año](https://github.com/user-attachments/assets/4ef7bacf-cdb9-4461-9423-d56daac95cbb)

---


| Persona | CPU | RAM | SO |
| :--- | :--- | :--- | :--- |
| Zenán | Intel Core i5-13420H (8 núcleos) | 16gb | Windows 11 |
| Josue | AMD Ryzen 5 5500U with Radeon Graphics (6 núcleos) | 8gb | Linux (Ubuntu) |
| Jhon | AMD Ryzen 5 7520U with Radeon Graphics (4 núcleos) | 16gb | Windows 11 |

