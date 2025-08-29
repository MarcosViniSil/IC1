package com.example;
import java.util.Random;

public class Perceptron {
    private double[][] weights;
    private double mi;
    private int in;
    private int out;

    public Perceptron(int in, int out, double mi){
        this.in = in;
        this.out = out;
        this.mi = mi;

        this.weights = new double[in + 1][out];

        Random random = new Random();
        double min = -0.03;
        double max = 0.03;
        
        for(int i = 0 ; i < in + 1; i++){
            for(int j = 0; j < out; j++){
                double randomValue = min + (max - min) * random.nextDouble();
                weights[i][j] = randomValue;
            }
        }
    }


    public double[] train(double[] Xin,double[] y){
        double[] x = new double[Xin.length + 1];
        x[0] = 1;

        for(int i = 1; i < x.length ; i++){
            x[i] = Xin[i-1];
        }

        double[] out = new double[this.out];

        for(int j = 0 ; j < this.out ; j++){
            double u = 0;
            for(int i = 0 ; i < this.in + 1; i++){
                u += x[i] * weights[i][j];
            }

            out[j] = 1 / (1 + (Math.exp(u*(-1))));
        }

        double[][] deltaWeights = new double[this.weights.length][this.weights[0].length];

        for(int j = 0; j < this.out;j++){
            for(int i = 0 ; i < this.in + 1; i++){
                deltaWeights[i][j] = this.mi *(y[j] - out[j]) * x[i];
                weights[i][j] += deltaWeights[i][j];
            }
        }
        
        
        return out;
    }


}
