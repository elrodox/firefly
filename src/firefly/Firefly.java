/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firefly;

/**
 *
 * @author gestion pc01
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Firefly {

    public static void main(String[] args) {
        //Parte 1: Leer Archivo
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int cantidad = 0; //cantidad de filas que lleva el texto
        int restr = 0; // cantidad de restricciones
        int costos = 0;// cantidad de variables
        int canNum = 0; // cantidad de numeros entrando al vector de costos
        int colRest = 0;//numero de restricciones por columna
        int i = 0;// columnas recorridas de la matriz
        int j = 0;// filas recorridas de la matriz
        int[][] matrizRest = null;
        ArrayList<String> arrayTemp = new ArrayList<String>();
        ArrayList<String> vecCost = new ArrayList<String>(); //vector de costos

        String delimitadores = "[ .,;?!¡¿\'\"\\[\\]]+";

        try {
            archivo = new File("scp41.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                if (cantidad < 1) {
                    String[] datos = linea.split(delimitadores);
                    restr = Integer.parseInt(datos[1]); //se le asigna el primer valor del txt
                    costos = Integer.parseInt(datos[2]); //se le asigna el segundo valor del txt

                    matrizRest = new int[restr][costos];
                } else {
                    if (canNum < costos) { //agrega valores a vector de costos
                        String[] temp = linea.split(delimitadores);
                        canNum = canNum + temp.length - 1;
                        for (int x = 1; x <= temp.length - 1; x++) {
                            vecCost.add(temp[x]);
                        }
                    } else if (colRest == 0) {//recibir la cantidad de columnas con restricciones
                        String[] temp2 = linea.split(delimitadores);
                        colRest = Integer.parseInt(temp2[1]);
                        //System.out.println("cantidad: "+colRest);
                    } else {//agregamos posiciones de restricciones a arraylist temporal
                        String[] temp3 = linea.split(delimitadores);
                        for (int x = 1; x <= temp3.length - 1; x++) {
                            //System.out.println("valor: "+ temp3[x]);
                            arrayTemp.add(temp3[x]);
                        }
                        if (temp3.length - 1 < colRest) {//traspaso de array a matriz
                            colRest = colRest - (temp3.length - 1);
                        } else if (temp3.length - 1 == colRest) {//si el arreglo se llena, pasa a la siguiente restriccion
                            for (int x = 0; x < arrayTemp.size(); x++) {
                                j = Integer.parseInt(arrayTemp.get(x));
                                matrizRest[i][j - 1] = 1;
                                //System.out.println("segundo if, posicion [" + i + "],[" + j + "]: " + matrizRest[i][j-1]);

                            }
                            arrayTemp.clear();
                            i++;
                            colRest = 0;
                        }

                    }
                }
                cantidad++;
            }
            Iterator<String> nombreIterator = vecCost.iterator();
            int cont = 0;
            while (nombreIterator.hasNext()) {
                cont++;
                String elemento = nombreIterator.next();
                System.out.print("\n Elemento: " + elemento + " cont: " + cont + " / ");
            }
            for (int x = 0; x < matrizRest.length; x++) {
                System.out.print("|");
                for (int y = 0; y < matrizRest[x].length; y++) {
                    System.out.print(matrizRest[x][y]);
                    if (y != matrizRest[x].length - 1) {
                        System.out.print("\t");
                    }
                }
                System.out.println("|");
            }

            //System.out.println(restr + " " + costos);
            //System.out.println("ultima: " + linea);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //parte 2: 
        int semillain = 10; //cantidad de semillas iniciales 
        Random aleatorio = new Random();
        int[][] randomseed = new int[semillain][costos]; //vector de semillas iniciales
        int v = 0;
        long seed = System.currentTimeMillis();
        aleatorio.setSeed(seed);
        float rand;
        System.out.println("semilla: " + seed);
        System.out.println("random original: ");
        for (int y = 0; y < randomseed.length; y++) {
            for (int x = 0; x < randomseed[y].length; x++) {
                rand = aleatorio.nextFloat();
                if (rand <= 0.5) {
                    randomseed[y][x] = 0;
                } else {
                    randomseed[y][x] = 1;
                }

                System.out.print("" + randomseed[y][x]);
            }
            System.out.print("\n");
        }
        //evaluar semilla por cada restriccion
        //si no cumple se debe reparar
        int contador = 0;
        int y;
        int o = 0;
        while (o < semillain) {
            while (v < restr) {//Función para recorrer las filas de la matriz de la restricción
                for (int w = 0; w < costos; w++) {//recorre las columnas de la matriz de restriccion
                    if (matrizRest[v][w] == 1) {//recorremos la matriz hasta encontrar un 1
                        if (matrizRest[v][w] == randomseed[o][w]) {//se compara el 1 con el vector de semillas iniciales
                            //v++;//quiere decir que se cumplió y se pasa a la siguiente restricción
                            System.out.println("factible");//es factible
                            contador++;
                            w = costos;//Si no se hace, sigue recorriendo el arreglo
                        }
                    }
                    //reparar
                    if (w == costos - 1) {
                        y = 0;
                        while (matrizRest[v][y] == 0) {
                            y++;
                        }
                        randomseed[o][y] = 1;
                        System.out.println("se ha reparado en la posición: " + (y + 1));
                        //v++;
                        contador++;
                    }
                }
                v++;
            }
            o++;
        }
        System.out.println("contador " + contador);
        System.out.println("randoms reparados: ");

        for (int l = 0; l < randomseed.length; l++) {
            for (int m = 0; m < randomseed[l].length; m++) {
                System.out.print("" + randomseed[l][m]);
            }
            System.out.print("\n");
        }
    }

}
