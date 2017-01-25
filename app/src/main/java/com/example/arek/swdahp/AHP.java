package com.example.arek.swdahp;

import android.util.Log;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Arek on 2016-12-07.
 */
public class AHP
{
    // ustalenie ilosci kolumn/wierszy macierzy (jedna wiecej dla wartosci parametrow samochodu)
    //amount of columns in matrix definition
    private int numberOfColumns = 4;
    //metoda tworzenia macierzy kryteriów z wektora P pobranego z interfejsu (pierwszy element albo ostatni jest ucinany!)
    public static double[][] initializeCriteriaMatrix(double[] p)
    {
        double a[][]=new double[p.length][p.length];
        int k;
        for(int i=0; i<p.length; i++)
        {
            k = 0;
            for(int j=0; j<p.length;j++)
            {
                if(i==j)
                    a[i][j]=1;
                else if(i<j)
                {
                    a[i][j]=p[k];
                    k++;
                }
                else if(i>j)
                    a[i][j]=1/a[j][i];
            }
        }
        return a;
    }
    //tworzenie macierzy decyzji
    //TODO: argument wyboru metody klasy CarSpecification - wybór getter
    public double[][] initializeDecisionMatrix(CarSpecification [] cars, double criteriaMin, double criteriaMax, String carParameter) {

        double totalValueScale [] = new double[] {1, 3, 5, 7, 9};
        double fractionValueScale [] = new double[] {0.8, 0.6, 0.4, 0.2, 0.1};
        double distanceLeft = 0, distanceUp = 0;
        double result[][]=new double[numberOfColumns][numberOfColumns];
        //uzupelnienie zerowego wiersza i zerowej kolumny wartosciami wybranych cech samochodu
        for(int i=1; i<numberOfColumns; i++) {
            result[0][i] = cars[i].returnParameter(carParameter);
            result[i][0] = cars[i].returnParameter(carParameter);
        }

        //uzupelnienie reszty pol macierzy
        for(int i=1; i<numberOfColumns; i++)
        {

            for(int j=1; j<numberOfColumns;j++)
            {
                if((i==j)||((result[0][i]>criteriaMin && result[0][i]<criteriaMax)&&(result[j][0]>criteriaMin && result[j][0]<criteriaMax)))
                    result[i][j]=1;
                else if(i<j)
                {
                    if(result[0][i]<criteriaMin)
                        distanceLeft = criteriaMin - result[0][i];
                    else if(result[0][i]>criteriaMax)
                        distanceLeft = result[0][i]-criteriaMax;
                    if(result[j][0]<criteriaMin)
                        distanceUp = criteriaMin - result[j][0];
                    else if(result[j][0]>criteriaMax)
                        distanceUp= result[j][0]-criteriaMax;
//                    uzywanie skali w gore
                    if (distanceLeft<distanceUp){
                        if(distanceLeft<=10)
                            result[i][j] = totalValueScale[4];
                        if(distanceLeft>10 && distanceLeft<=20)
                            result[i][j] = totalValueScale[3];
                        if(distanceLeft>20 && distanceLeft<=30)
                            result[i][j] = totalValueScale[2];
                        if(distanceLeft>30 && distanceLeft<=40)
                            result[i][j] = totalValueScale[1];
                        if(distanceLeft>40)
                            result[i][j] = totalValueScale[0];

                    }
                    else if (distanceLeft>distanceUp) {
                        if(distanceUp<=10)
                            result[i][j] = fractionValueScale[0];
                        if(distanceUp>10 && distanceUp<=20)
                            result[i][j] = fractionValueScale[1];
                        if(distanceUp>20 && distanceUp<=30)
                            result[i][j] = fractionValueScale[2];
                        if(distanceUp>30 && distanceUp<=40)
                            result[i][j] = fractionValueScale[3];
                        if(distanceUp>40)
                            result[i][j] = fractionValueScale[4];
                    }
                }
                else if(i>j)
                    result[i][j]=1/result[j][i];
            }
        }
        //printowanie macierzy
//        showMatrix(result);

        normalizeMatrix(result);
        return result;
    }

    public void showMatrix(double [][] matrix)
    {
        //display the elements of the matrix
//        double [] array = new double[6];
//        array[0] = 2;
//        array[1] = 3;
//        array[2] = 5;
//        array[3] = 7;
//        array[4] = 9;
//        array[5] = 10000;
//        AHP ahpp = new AHP();
//        double [][] newMatrix = ahpp.initializeCriteriaMatrix(array);

        for(int i=0; i<matrix.length;i++)
        {
            for(int j=0; j<matrix[i].length; j++)
                Log.i("wiadomosc", matrix[i][j]+"    ");
        }
    }

    private double [][] normalizeMatrix(double [][] matrix){

        double columnSum [] = new double[numberOfColumns];

        for (int i=1; i< numberOfColumns; i++ ){

            for (int j=1; j<numberOfColumns; j++){

               columnSum[i] += matrix[j][i];

            }

        }

        for (int i=1; i< numberOfColumns; i++ ){

            for (int j=1; j<numberOfColumns; j++){

                matrix[j][i] /= columnSum[i];

            }

        }

//        showMatrix(matrix);
//        Log.d("cokolwiek",columnSum[1] + " wynik");
        preferenceVector(matrix);
        return matrix;

    }

    private double[] preferenceVector(double[][] matrix){

        double[] meanOfRows = new double[numberOfColumns];

        for (int i=1; i< numberOfColumns; i++ ){

            for (int j=1; j<numberOfColumns; j++){

                meanOfRows[i] += matrix[i][j]/(numberOfColumns-1);

            }

                Log.d("mean of rows:", meanOfRows[i]+" wynik");

        }

        return meanOfRows;
    }


    private void startMethods(){



    }

}