package org.example.Operation;

import org.example.Counter.Counter;

public class Repository {
    private static final int NUM_STATISTICS = 11;
    private static Repository INSTANCE;
    private double[][] matrix;
    private double[][] parameters;

    public static Repository getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix){
        this.matrix = matrix;
    }

    public double[][] getParameters(){
        return parameters;
    }

    public double getCov(int i, int j){
        return Counter.getCovariance(matrix[i],matrix[j]);
    }

    public void setParameters(){
        if(matrix==null){
            System.out.println("Данные не импортированы");
            return;
        }
        parameters = new double[NUM_STATISTICS][matrix.length];
        for(int i=0;i<NUM_STATISTICS;i++){
            for(int j=0;j< matrix.length;j++){
                parameters[i][j]=decider(i, j, matrix);
            }
        }
    }

    public double decider(int i,int j,double[][] matrix){
        switch (i){
            case 0:
                return Counter.getGeometricMean(matrix[j]);
            case 1:
                return Counter.getArithmeticMean(matrix[j]);
            case 2:
                return Counter.getStandardDeviation(matrix[j]);
            case 3:
                return Counter.getRange(matrix[j]);
            case 4:
                return Counter.getCoefficientOfVariation(matrix[j]);
            case 5:
                return Counter.getSampleSize(matrix[j]);
            case 6:
                return Counter.getLowerConfidenceInterval(matrix[j]);
            case 7:
                return Counter.getUpperConfidenceInterval(matrix[j]);
            case 8:
                return Counter.getVariance(matrix[j]);
            case 9:
                return Counter.getMax(matrix[j]);
            case 10:
                return Counter.getMin(matrix[j]);
            default:
                return 0;
        }
    }
}
