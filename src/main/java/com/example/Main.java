package com.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class Main {
    final static int SEASONS = 10000;

    public static void main(String[] args) throws IOException, CsvException {
        // CONVENTION = 0 -> NO, 1 -> YES
        CSVReader reader = new CSVReader(new FileReader("base_numeric.csv"));
        List<String[]> allData = reader.readAll();

        List<List<Double>> allDataDouble = new ArrayList<>();

        for (String[] row : allData) {
            List<Double> rowConverted = new ArrayList<>();
            for (String element : row) {
                Double elementConverted = Double.parseDouble(element);
                rowConverted.add(elementConverted);
            }
            allDataDouble.add(rowConverted);
        }

        reader.close();
        executePerceptron(6, 2, 0.3, allDataDouble);
    }

    public static void executePerceptron(int in, int outP, double mi, List<List<Double>> base) {
        Perceptron perceptron = new Perceptron(in, outP, mi);
        System.out.printf("%-10s %-12s %-10s%n", "Época", "Aproximação", "Classificação");
        for (int e = 0; e < SEASONS; e++) {
            double error = 0;
            double classificationError = 0;

            for (int a = 0; a < base.size(); a++) {

                double[] x = base.get(a).subList(0, in).stream().mapToDouble(Double::doubleValue).toArray();
                double[] y = base.get(a).subList(in, in + outP).stream().mapToDouble(Double::doubleValue).toArray();

                double[] out = perceptron.train(x, y);

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

                error += errorA;

                classificationError += sumClassification > 0 ? 1 : 0;

            }

            System.out.printf("%-10d %-14.6f %-10.1f%n", e, error, classificationError);

        }
    }
}