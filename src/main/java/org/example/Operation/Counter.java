package org.example.Operation;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;

public class Counter {
    private static final Covariance COVARIANCE = new Covariance();
    /*Z-value for 90% confidence interval */
    private static final double Z_VALUE = 1.65;

    public static double getGeometricMean(double[] data) {
        return StatUtils.geometricMean(data);
    }

    public static double getArithmeticMean(double[] data) {
        return StatUtils.mean(data);
    }

    public static double getStandardDeviation(double[] data){
        return Math.sqrt(StatUtils.variance(data));
    }

    public static double getRange (double[] data){
        return StatUtils.max(data) - StatUtils.min(data);
    }

    public static double getCovariance(double[] data1, double[] data2){
        return COVARIANCE.covariance(data1, data2);
    }

    public static int getSampleSize(double[] data){
        return data.length;
    }

    public static double getCoefficientOfVariation(double[] data){
        return (getStandardDeviation(data) / getArithmeticMean(data));
    }

    public static double getLowerConfidenceInterval(double[] data) {
        return getArithmeticMean(data) - Z_VALUE * (getStandardDeviation(data) / Math.sqrt(data.length));
    }

    public static double getUpperConfidenceInterval(double[] data) {
        return getArithmeticMean(data) + Z_VALUE * (getStandardDeviation(data) / Math.sqrt(data.length));
    }

    public static double getVariance(double[] data) {
        return StatUtils.variance(data);
    }

    public static double getMax(double[] data) {
        return StatUtils.max(data);
    }

    public static double getMin(double[] data) {
        return StatUtils.min(data);
    }
}
