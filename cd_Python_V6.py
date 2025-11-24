import pandas as pd
import multiprocessing
import time
import numpy as np
import csv
import sys
from itertools import chain

# se aumenta el limite del campo del csv por lo que es un archivo grande
csv.field_size_limit(sys.maxsize)
pd.options.mode.chained_assignment = None  # con esto se desactiva la advertencia de pandas

# algoritmos de ordenamiento y depuracion

    # implementacion de merge sort
def merge_sort(lista):
    if len(lista) <= 1:
        return lista
    
    mitad = len(lista) // 2
    lista_izquierda = merge_sort(lista[:mitad])
    lista_derecha = merge_sort(lista[mitad:])

    return unir_listas(lista_izquierda, lista_derecha)

def unir_listas(lista1, lista2):

    # funcion auxuliar para unir dos listas ordenadas
    resultado = []
    i = j = 0
    while i < len(lista1) and j < len(lista2):
        # de mayor a menor temperatura
        if lista1[i][2] >= lista2[j][2]:
            resultado.append(lista1[i])
            i += 1
        else:
            resultado.append(lista2[j])
            j += 1
    resultado.extend(lista1[i:])
    resultado.extend(lista2[j:])
    return resultado

    # quick sort que depura la lista
    # se filtran valores que no son num validos o son 0.0
def quick_sort(lista):

    n = len(lista)
    i = -1 # indice del ultimo elemento valido encontrado
    for j in range(n):
        valor_temp = lista[j][2]
        # si es numerico y diferente de 0.0 entonces es valido
        if isinstance(valor_temp, (int, float)) and valor_temp != 0.0:
            i += 1
            lista[i], lista[j] = lista[j], lista[i]
    return i + 1

# implementacion de la formula para promedio anual
def calcular_promedios(lista_datos):

    # se calcula la temp promedio anual por ciudad
    # diccionario para guardar la suma de las temp
    sumas = {}
    # diccionario para contar los dias n por ciudad/anio
    conteo = {}

    for ciudad, anio, temp in lista_datos:
        clave = f"{ciudad}|{anio}"

        #logica para sumar las temp
        sumas[clave] = sumas.get(clave, 0.0) + temp

        #logica para contar los dias
        conteo[clave] = conteo.get(clave, 0) + 1

    lista_promedios = []

    # formula promedio = suma / n dias
    for clave in sumas.keys():
        suma_total = sumas[clave]
        num_dias = conteo[clave]

        promedio = suma_total / num_dias

        #descomponer la clave
        ciudad, anio = clave.split("|")

        #se agrega el registro del prom anual (ciudad, anio, promedio)
        lista_promedios.append((ciudad, anio, promedio))

    return lista_promedios

# logica de procesamiento ahora con multiprocessing
def procesa_bloque(bloque):
    # se hace la limpieza a nivel de fila de forma paralela y devuelve filas limpias
    if bloque.empty:
        return pd.DataFrame()

    try:
        bloque = bloque.copy()

        #limpieza y conversion a numerico
        bloque['TempPromedio'] = bloque['TempPromedio'].replace(r'["\(\)-]', '', regex=True)
        bloque['TempPromedio'] = pd.to_numeric(bloque['TempPromedio'], errors='coerce')

        # limpieza de fecha y extraccion del anio 
        bloque['Fecha'] = bloque['Fecha'].replace(r'["\(\)]', '', regex=True)
        bloque = bloque.dropna(subset=['TempPromedio', 'Fecha'])

        bloque['Anio'] = bloque['Fecha'].str.split('/').str[2]
        bloque = bloque.dropna(subset=['Anio'])

        # filtrado de adeveras para eliminar valores ilogicos
        bloque = bloque[bloque['TempPromedio'] > -90]

        # devolver columnas necesarias para el calculo del promedio
        return bloque[['Ciudad', 'Anio', 'TempPromedio']]
    
    except Exception as e:
        return pd.DataFrame()
    
def procesar_en_paralelo(bloques_a_procesar, funcion_procesamiento):

    #se divide y mapea la funcion a los bloques en paralelo
    num_nucleos = multiprocessing.cpu_count()
    print(f"\n[ PROCESAMIENTO PARALELO] Usando {num_nucleos} núcleos para {len(bloques_a_procesar)} bloques.")

    with multiprocessing.Pool(num_nucleos) as pool:
        resultados = pool.map(funcion_procesamiento, bloques_a_procesar)

    return resultados

# finalmente el main
def main():
    tiempo_INICIAL = time.time()
    archivo_entrada = 'nuevo.csv'
    archivo_salida = 'promedios_ordenados.csv'
    TAMANIO_BLOQUE = 100000

    print(" INICIANDO PROCESADOR DE DATOS DE TEMPERATURA")
    
    # lectura por bloques y limpieza paralela
    print("\n[PASO 1]  Leyendo y Limpiando filas crudas en paralelo...")
    inicio_lectura = time.time()
    bloques = []
    try:
        iterador_csv = pd.read_csv(
            archivo_entrada,
            usecols = ['Ciudad', 'TempPromedio', 'Fecha'],
            chunksize = TAMANIO_BLOQUE,
            encodign = 'utf-8',
            low_memory = True,
            skip_blank_lines = True
        )
        for bloque in iterador_csv:
            bloques.append(bloque)
    except Exception as e:
        print(f"\n ERROR: Al leer el archivo {archivo_entrada}. Detalles: {e}")
        return
    
    resultados = procesar_en_paralelo(bloques, procesa_bloque)
    resultados_validos = [res for res in resultados if not res.empty]
    if not resultados_validos:
        print("\n ADVERTENCIA: No se obtuvieron datos validos de los procesos paralelos")
        return
    
    #consolidar todos los dataframes de filas limpias
    df_limpio = pd.concat(resultados_validos, ignore_index=True)

    #convertir el df a lista de tuplas
    lista_datos_limpia = df_limpio.to_records(index=False).tolist()
    fin_lectura = time.time()

    print(f"   Tiempo de lectura y limpieza paralela: {fin_lectura - inicio_lectura:.4f} segundos")
    print(f"   Filas crudas validas consolidadas: {len(lista_datos_limpia):,} filas")

    #calculo de promedios anuales usando la formula
    print("\n[PASO 2]  Calculando Promedios Anuales (Suma/N)...")
    inicio_promedios = time.time()

    #el resultado es la lista de promedios
    lista_promedios_calculados = calcular_promedios(lista_datos_limpia)

    fin_promedios = time.time()

    print(f"   Tiempo de calculo de promedios: {fin_promedios - inicio_promedios:.4f} segundos")
    print(f"   Registros de promedios anuales: {len(lista_promedios_calculados):,} registros")

    #depuracion con quick sort
    print("\n[PASO 3]  Depuracion final (Quick Sort) de los promedios...")
    inicio_depuracion = time.time()
    num_validos = quick_sort(lista_promedios_calculados)
    lista_depurada = lista_promedios_calculados[:num_validos]
    fin_depuracion = time.time()

    print(f"   Tiempo de depuracion: {fin_depuracion - inicio_depuracion:.4f} segundos")
    print(f"   Promedios finales a ordenar: {num_validos:,} registros")

    #ordenamiento con merge sort
    print("\n[PASO 4]  Ordenamiento (Merge Sort) de los promedios...")
    inicio_ordenamiento = time.time()
    datos_ordenados = merge_sort(lista_depurada)
    fin_ordenamiento = time.time()
    print(f"   Tiempo de ordenamiento: {fin_ordenamiento - inicio_ordenamiento:.4f} segundos")

    #reporte a csv
    print(f"\n[PASO 5]  Generando archivo CSV: {archivo_salida}")
    inicio_escritura = time.time()
    try:
        #se usa codificacion utf-8 para evitar errores con char especiales
        with open(archivo_salida, 'w', newline='', encoding='utf-8') as f:
            writer = csv.writer(f)
            writer.writerow(['Anio', 'Ciudad', 'TemperaturaPromedio'])

            for row in datos_ordenados:
                ciudad, anio, temp = row
                writer.writerow([anio, f'"{ciudad}"', f'{temp:.2f}'])

    except Exception as e:
        print(f"\n ERROR: Al escribir el archivo. Detalles: {e}")
        return
    fin_escritura = time.time()
    print(f"    Tiempo de escritura: {fin_escritura - inicio_escritura:.4f} segundos")

    tiempo_FINAL = time.time()

    print(f" PROCESO COMPLETADO. Tiempo total: {tiempo_FINAL - tiempo_INICIAL:.4f} segundos")

#esto para que el multiprocessing funcione
if __name__ == '__main__':
    multiprocessing.freeze_support()
    main()