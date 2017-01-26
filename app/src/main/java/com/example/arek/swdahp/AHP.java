package com.example.arek.swdahp;

import android.util.Log;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import android.os.AsyncTask;

/**
 * Created by Arek on 2016-12-07.
 */
public class AHP
{
    // ustalenie ilosci kolumn/wierszy macierzy (jedna wiecej dla wartosci parametrow samochodu)
    //amount of columns in matrix definition
    private int numberOfColumns = 7;
    private double columnSum [][] = new double[numberOfColumns][numberOfColumns];
    private double meanOfRows [][] = new double[numberOfColumns][numberOfColumns];
    private double bestResults [] = new double[numberOfColumns];
    private double consistencyRatioValue [] = new double[numberOfColumns];
    private double totalValueScale[] = new double[]{1, 3, 5, 7, 9};
    private double fractionValueScale[] = new double[]{0.9, 0.333, 0.2, 0.143, 0.111};


    double [][] criteriaMatrix = new double[numberOfColumns][numberOfColumns];
    double [][] decisionMatrixHorsePower = new double[numberOfColumns][numberOfColumns];
    double [][] decisionMatrixSafetyLevel = new double[numberOfColumns][numberOfColumns];
    double [][] decisionMatrixCost = new double[numberOfColumns][numberOfColumns];
    double [][] decisionMatrixYear = new double[numberOfColumns][numberOfColumns];
    double [][] decisionMatrixComfort = new double[numberOfColumns][numberOfColumns];
    double [][] decisionMatrixKilometersDone = new double[numberOfColumns][numberOfColumns];


    public void process(final double [] userPreferences,final CarSpecification [] cars,final double [] userParametersMin,final double [] userParametersMax ){

        new Thread(new Runnable() {
            @Override
            public void run() {

                criteriaMatrix = initializeCriteriaMatrix(userPreferences);
                //TODO:skopiowac 6 razy dla wszystkich parametrów

                decisionMatrixHorsePower = initializeDecisionMatrix(cars, userParametersMin[0], userParametersMax[0], "horsePower", 1);
                decisionMatrixSafetyLevel = initializeDecisionMatrix(cars, userParametersMin[1], userParametersMax[1], "safetyLevel", 2);
                decisionMatrixCost = initializeDecisionMatrix(cars, userParametersMin[2], userParametersMax[2], "cost", 3);
                decisionMatrixYear = initializeDecisionMatrix(cars, userParametersMin[3], userParametersMax[3], "year", 4);
                decisionMatrixComfort = initializeDecisionMatrix(cars, userParametersMin[4], userParametersMax[4], "comfort", 5);
                decisionMatrixKilometersDone = initializeDecisionMatrix(cars, userParametersMin[5], userParametersMax[5], "kilometersDone", 6);

                bestResult();
            }
        }).start();


    }
    //metoda tworzenia macierzy kryteriów z wektora P pobranego z interfejsu (pierwszy element albo ostatni jest ucinany!)
    public double[][] initializeCriteriaMatrix(double[] p)
    {
        double a[][]=new double[p.length+1][p.length+1];
        int k;
        for(int i=1; i<p.length+1; i++)
        {
            k = 0;
            for(int j=1; j<p.length+1;j++)
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



        public double[][] initializeDecisionMatrix (CarSpecification[]cars,double criteriaMin,
        double criteriaMax, String carParameter,int indexNumber){

            double distanceLeft = 0, distanceUp = 0;
            double result[][] = new double[numberOfColumns][numberOfColumns];
            //uzupelnienie zerowego wiersza i zerowej kolumny wartosciami wybranych cech samochodu
            for (int i = 1; i < numberOfColumns; i++) {
                result[0][i] = cars[i - 1].returnParameter(carParameter);
                result[i][0] = cars[i - 1].returnParameter(carParameter);
            }

            //uzupelnienie reszty pol macierzy
            for (int i = 1; i < numberOfColumns; i++) {

                for (int j = 1; j < numberOfColumns; j++) {
                    if ((i == j) || ((result[0][i] > criteriaMin && result[0][i] < criteriaMax) && (result[j][0] > criteriaMin && result[j][0] < criteriaMax)))
                        result[i][j] = 1;
                    else if (i < j) {
                        if (result[0][i] < criteriaMin)
                            distanceLeft = criteriaMin - result[0][i];
                        else if (result[0][i] > criteriaMax)
                            distanceLeft = result[0][i] - criteriaMax;
                        if (result[j][0] < criteriaMin)
                            distanceUp = criteriaMin - result[j][0];
                        else if (result[j][0] > criteriaMax)
                            distanceUp = result[j][0] - criteriaMax;
//                    uzywanie skali w gore
                        if (distanceLeft < distanceUp) {
                            if (distanceLeft <= 0.02*result[0][i])
                                result[i][j] = totalValueScale[4];
                            if (distanceLeft > 0.02*result[0][i] && distanceLeft <= 0.05*result[0][i])
                                result[i][j] = totalValueScale[3];
                            if (distanceLeft > 0.05*result[0][i] && distanceLeft <= 0.08*result[0][i])
                                result[i][j] = totalValueScale[2];
                            if (distanceLeft > 0.08*result[0][i] && distanceLeft <= 0.1*result[0][i])
                                result[i][j] = totalValueScale[1];
                            if (distanceLeft > 0.1*result[0][i])
                                result[i][j] = totalValueScale[0];
                            else
                                result[i][j] = 1;

                        } else if (distanceLeft > distanceUp) {
                            if (distanceUp <= 0.02*result[j][0])
                                result[i][j] = fractionValueScale[0];
                            if (distanceUp > 0.02*result[j][0] && distanceUp <= 0.05*result[j][0])
                                result[i][j] = fractionValueScale[1];
                            if (distanceUp > 0.05*result[j][0] && distanceUp <= 0.08*result[j][0])
                                result[i][j] = fractionValueScale[2];
                            if (distanceUp > 0.08*result[j][0] && distanceUp <= 0.1*result[j][0])
                                result[i][j] = fractionValueScale[3];
                            if (distanceUp > 0.1*result[j][0])
                                result[i][j] = fractionValueScale[4];

                        }
                        else
                            result[i][j] = 1;
                    } else if (i > j)
                        result[i][j] = 1 / result[j][i];
                }
            }

            normalizeMatrix(result, indexNumber);
            return result;
        }

    public void showMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++)
                Log.i("wiadomosc", matrix[i][j] + "    ");
        }
    }

    private void normalizeMatrix(double[][] matrix, int indexNumber) {
        for (int i = 1; i < matrix.length; i++) {

            for (int j = 1; j < matrix.length; j++) {

                columnSum[indexNumber][i] += matrix[j][i];
            }

        }

        for (int i = 1; i < matrix.length; i++) {

            for (int j = 1; j < matrix.length; j++) {

                matrix[j][i] /= columnSum[indexNumber][i];
            }

        }

//        showMatrix(matrix);
//        Log.d("cokolwiek",columnSum[1] + " wynik");
        preferenceVector(matrix, indexNumber);

    }

    private void preferenceVector(double[][] matrix, int indexNumber) {

        for (int i = 1; i < matrix.length; i++) {

            for (int j = 1; j < matrix.length; j++) {

                meanOfRows[indexNumber][i] += matrix[i][j] / (matrix.length - 1);

            }
        }
    }

    private int bestResult() {

        //tylko na meanOfRows
        for (int i = 1; i < numberOfColumns; i++) {
            for (int j = 1; j < numberOfColumns; j++) {
                bestResults[i] += (meanOfRows[0][j] * meanOfRows[j][i]);

            }
        }
        Log.i("cos:", bestResults[0] + "");
        consistencyRatio();
        return 1;
    }

    private double consistencyRatio() {
        double tempResult = 0;
        for(int i = 1; i<numberOfColumns; i++) {
            tempResult+=(columnSum[0][i]*meanOfRows[0][i]);
        }
        consistencyRatioValue[0] = ((tempResult-(numberOfColumns-1))/(numberOfColumns-2))/1.24;
        tempResult=0;

        for(int i = 1; i<numberOfColumns; i++) {
            for(int j = 1; j<numberOfColumns; j++){
                tempResult +=columnSum[i][j]*meanOfRows[i][j];
            }
            consistencyRatioValue[i] = ((tempResult-(numberOfColumns-1))/(numberOfColumns-2))/1.24;
            tempResult = 0;
        }

        return 1.0;
    }



}