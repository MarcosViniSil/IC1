package com.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class Main {
    final static int SEASONS = 1000000;

    public static void main(String[] args) throws IOException, CsvException {
        // CONVENTION = 0 -> NO, 1 -> YES
        CSVReader reader = new CSVReader(new FileReader("abalone_train_normalized.csv"));
        //CSVReader reader = new CSVReader(new FileReader("inflamations_train_normalized.csv"));
        List<String[]> allData = reader.readAll();

        List<List<Double>> train = convertCsvToDouble(allData);
        
        CSVReader readerTest = new CSVReader(new FileReader("abalone_test_normalized.csv"));
        //CSVReader readerTest = new CSVReader(new FileReader("inflamations_test_normalized.csv"));
        List<String[]> allDataTest = readerTest.readAll();
       
        List<List<Double>> test = convertCsvToDouble(allDataTest);
        
        reader.close();
        readerTest.close();
        //executePerceptron(7, 2, 0.3, train,test,7);
        executePerceptron(10, 1, 0.1, train,test,10);
    }

    public static void executePerceptron(int in, int outP, double mi, List<List<Double>> base,List<List<Double>> test,int hiddenQuantity) {
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(in, outP, mi, hiddenQuantity);

        System.out.printf("%-10s %-12s %-10s%n", "Época", "Aproximação", "Classificação");
        for (int e = 0; e < SEASONS; e++) {
            double errorTrain = 0;
            double classificationErrorTrain = 0;
            double errorTest = 0;
            double classificationErrorTest = 0;
            for (int a = 0; a < base.size(); a++) {

                double[] x = base.get(a).subList(0, in).stream().mapToDouble(Double::doubleValue).toArray();
                double[] y = base.get(a).subList(in, in + outP).stream().mapToDouble(Double::doubleValue).toArray();
                double[] out = mlp.train(x, y);

                double[] adjustedValues = new double[out.length];

                for (int j = 0; j < out.length; j++) {
                    adjustedValues[j] = out[j] > 0.5 ? 1 : 0;
                }

                int sumClassification = 0;

                for (int j = 0; j < out.length; j++) {
                    sumClassification += Math.abs(adjustedValues[j] - y[j]);
                }

                double errorA = 0;
                for (int j = 0; j < y.length; j++) {
                    errorA += Math.abs(y[j] - out[j]);
                }

                errorTrain += errorA;

                classificationErrorTrain += sumClassification > 0 ? 1 : 0;

            }
            
            for (int a = 0; a < test.size(); a++) {

                double[] x = test.get(a).subList(0, in).stream().mapToDouble(Double::doubleValue).toArray();
                double[] y = test.get(a).subList(in, in + outP).stream().mapToDouble(Double::doubleValue).toArray();
                double[] out = mlp.test(x, y);

                double[] adjustedValues = new double[out.length];

                if (out.length == 1) {
                    adjustedValues[0] = out[0] > 0.5 ? 1 : 0;
                } else {
                    int maxIndex = 0;
                    for (int j = 1; j < out.length; j++) {
                        if (out[j] > out[maxIndex]) {
                            maxIndex = j;
                        }
                    }
                    for (int j = 0; j < out.length; j++) {
                        adjustedValues[j] = (j == maxIndex) ? 1 : 0;
                    }
                }

                int sumClassification = 0;

                for (int j = 0; j < out.length; j++) {
                    sumClassification += Math.abs(adjustedValues[j] - y[j]);
                }

                double errorA = 0;
                for (int j = 0; j < y.length; j++) {
                    errorA += Math.abs(y[j] - out[j]);
                }

                errorTest += errorA;

                classificationErrorTest += sumClassification > 0 ? 1 : 0;

            }

            System.out.printf("%-10d %-14.6f %-10.1f %-14.6f %-10.1f%n", e, errorTrain, classificationErrorTrain,errorTest,classificationErrorTest);
        }
    }

    
    public static List<List<Double>> convertCsvToDouble(List<String[]> allData){
        List<List<Double>> allDataDouble = new ArrayList<>();

        for (String[] row : allData) {
            List<Double> rowConverted = new ArrayList<>();
            for (String element : row) {
                Double elementConverted = Double.parseDouble(element);
                rowConverted.add(elementConverted);
            }

            allDataDouble.add(rowConverted);
        }
    
        return allDataDouble;
    }

    
}