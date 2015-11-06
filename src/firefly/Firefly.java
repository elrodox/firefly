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
    static Random random = new Random();
    static long seed;
    static int[][] matrix = null;
    static int cantRestricciones, cantCostos;
    static ArrayList<String> vectorCostos = new ArrayList<String>(); //vector de costos
    public static void main(String[] args) {
        //Parte 1: Leer Archivo
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int cantidad = 0; //cantidad de filas que lleva el texto
        int canNum = 0; // cantidad de numeros entrando al vector de costos
        int colRest = 0;//numero de restricciones por columna
        int i = 0;// columnas recorridas de la matriz
        int j = 0;// filas recorridas de la matriz
        
        ArrayList<String> arrayTemp = new ArrayList<String>();
        

        String delimitadores = "[ .,;?!¡¿\'\"\\[\\]]+";

        try {
            archivo = new File("input/scp41.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                if (cantidad < 1) {
                    String[] datos = linea.split(delimitadores);
                    cantRestricciones = Integer.parseInt(datos[1]); //se le asigna el primer valor del txt
                    cantCostos = Integer.parseInt(datos[2]); //se le asigna el segundo valor del txt

                    matrix = new int[cantRestricciones][cantCostos];
                } else {
                    if (canNum < cantCostos) { //agrega valores a vector de costos
                        String[] temp = linea.split(delimitadores);
                        canNum = canNum + temp.length - 1;
                        for (int x = 1; x <= temp.length - 1; x++) {
                            vectorCostos.add( Integer.parseInt(temp[x]) );
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
                                matrix[i][j - 1] = 1;
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
        System.out.println("Matriz de: "+matrix.length+"x"+matrix[0].length);
        
        
        //parte 2: 
        System.out.println("costos:");
        for(String costo: vectorCostos){
            System.out.println(costo);
        }
//               
//        seed = System.currentTimeMillis();
//        random.setSeed(seed);
//        ArrayList<int[]> luciernagas = new ArrayList();
//        for (int k = 0; k < 10; k++)
//            luciernagas.add( 
//                    validarYReparar(
//                        generarLuciernagaAleatoria()) );
        
            
        
        
    }
    public static long getFitness(int[] luciernaga){
//        for (int i = 0; i < luciernaga; i++) {
//            
//        }
        return 0;
    }
    
    public static int[] validarYReparar(int[] luciernaga){
        boolean factible = false;
        int indiceJPrimerUno = -1;
        for (int i = 0; i < cantRestricciones; i++) { // recorriendo restricciones
            for (int j = 0; j < cantCostos; j++) { // recorriendo componentes de restriccion
                if( matrix[i][j] == 1) {
                    indiceJPrimerUno=j;
                    if (luciernaga[j]==1 ){
                        factible= true;
                        break;
                    }
                }
            }
        }
        if(!factible && indiceJPrimerUno!=-1){
            luciernaga[indiceJPrimerUno] = 1;
        }
        return luciernaga;
    }
    
    public static int[] generarLuciernagaAleatoria(){
        int[] nuevaLuciernaga = new int[cantCostos];
        for (int i = 0; i < cantCostos; i++) {
            nuevaLuciernaga[i] = Math.round(random.nextFloat());
        }
        return nuevaLuciernaga;
    }
    
    public int[] aplicarMovimiento(int[] luciernaga){
        int[] nuevaLuciernaga = new int[luciernaga.length];
        for (int i = 0; i < luciernaga.length; i++) {
            nuevaLuciernaga[i] = Math.round(random.nextFloat());
        }
        return nuevaLuciernaga;
    } 

}
