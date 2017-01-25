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
    private double columnSum [][] = new double[numberOfColumns][numberOfColumns];
    private double meanOfRows [][] = new double[numberOfColumns][numberOfColumns];


    double [][] criteriaMatrix = new double[numberOfColumns][numberOfColumns];
    double [][] decisionMatrixHorsePower = new double[numberOfColumns][numberOfColumns];
    double [][] decisionMatrixSafetyLevel = new double[numberOfColumns][numberOfColumns];
//    double [][] decisionMatrixCost = new double[numberOfColumns][numberOfColumns];
//    double [][] decisionMatrixYear = new double[numberOfColumns][numberOfColumns];
//    double [][] decisionMatrixFuel = new double[numberOfColumns][numberOfColumns];
//    double [][] decisionMatrixKilometersDone = new double[numberOfColumns][numberOfColumns];

    public AHP(double [] userPreferences, CarSpecification [] cars, double [] userParametersMin, double [] userParametersMax ){

        this.criteriaMatrix = initializeCriteriaMatrix(userPreferences);
        //TODO:skopiowac 6 razy dla wszystkich parametrów
        this.decisionMatrixHorsePower = initializeDecisionMatrix(cars, userParametersMin[0], userParametersMax[0], "horsePower", 1);
        this.decisionMatrixSafetyLevel = initializeDecisionMatrix(cars, userParametersMin[1], userParametersMax[1], "safetyLevel", 2);
    }
    //metoda tworzenia macierzy kryteriów z wektora P pobranego z interfejsu (pierwszy element albo ostatni jest ucinany!)
    public double[][] initializeCriteriaMatrix(double[] p)
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
        normalizeMatrix(a ,0);
        return a;
    }
    //tworzenie macierzy decyzji

    public double [][] initializeDecisionMatrix(CarSpecification [] cars, double criteriaMin, double criteriaMax, String carParameter, int indexNumber) {

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

        normalizeMatrix(result, indexNumber);
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

    private void normalizeMatrix(double [][] matrix, int indexNumber){

        for (int i=1; i< numberOfColumns; i++ ){

            for (int j=1; j< numberOfColumns; j++){

               columnSum[indexNumber][i] += matrix[j][i];
            }

        }

        for (int i=1; i< numberOfColumns; i++ ){

            for (int j=1; j<numberOfColumns; j++){

                matrix[j][i] /= columnSum[indexNumber][i];
            }

        }

//        showMatrix(matrix);
//        Log.d("cokolwiek",columnSum[1] + " wynik");
        preferenceVector(matrix, indexNumber);

    }

    private void preferenceVector(double[][] matrix, int indexNumber){

        for (int i=1; i< this.numberOfColumns; i++ ){

            for (int j=1; j<this.numberOfColumns; j++){

                meanOfRows[indexNumber][i] += matrix[i][j]/(numberOfColumns-1);

            }
        }
        Log.i("mean of rows:", meanOfRows[1]+" "+meanOfRows[2]+ " "+ meanOfRows[3]+" wynik");

    }




}