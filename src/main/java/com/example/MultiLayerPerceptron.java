package com.example;

import java.util.Random;

public class MultiLayerPerceptron{
    private double[][] wh;
    private double[][] wo;
    private double mi;
    private int in;
    private int out;
    private int hiddenQuantity;
    
    public MultiLayerPerceptron(int in, int out, double mi,int hiddenQuantity){
        this.in = in;
        this.out = out;
        this.mi = mi;
        this.hiddenQuantity = hiddenQuantity;
        this.wh = new double[in+1][hiddenQuantity];
        this.wo = new double[hiddenQuantity+1][out];
        
        this.insertRandomValuesIntoWH();
        this.insertRandomValuesIntoWO();
     
    }
    
    public void insertRandomValuesIntoWH(){
        Random random = new Random();
        double min = -0.1;
        double max = 0.1;
        for(int i = 0 ; i < in + 1; i++){
            for(int j = 0; j < hiddenQuantity; j++){
                double randomValue = min + (max - min) * random.nextDouble();
                this.wh[i][j] = randomValue;
            }
        }
    }
    
    public void insertRandomValuesIntoWO(){
        Random random = new Random();
        double min = -0.1;
        double max = 0.1;
        for(int i = 0 ; i < hiddenQuantity+1; i++){
            for(int j = 0; j < out; j++){
                double randomValue = min + (max - min) * random.nextDouble();
                this.wo[i][j] = randomValue;
            }
        }
    }
    
    public double[] train(double[] Xin,double[] y){
        double[] x = new double[Xin.length + 1];
        for(int i = 0; i < x.length - 1; i++){
            x[i] = Xin[i];
        }
        x[x.length - 1] = 1;
        
        double[] H = new double[this.hiddenQuantity+1];
        
        for(int h = 0; h < this.wh[0].length; h++){
            double u = 0;
            
            for(int i = 0; i < this.wh.length; i++){
                u += x[i] * this.wh[i][h];
            }
            
            H[h] = 1 / (1 + (Math.exp(u*(-1))));
        }
        
        H[H.length - 1] = 1;
        
        double[] out = new double[this.out];
        
        for(int j = 0; j < this.wo[0].length;j++){
            double u = 0;
            for(int h = 0; h < this.wo.length; h++){
                u += H[h] * this.wo[h][j];
            }
            
            out[j] = 1 / (1 + (Math.exp(u*(-1))));
        }
        
        double[] deltaO = new double[this.out];
        for(int j = 0; j < this.out; j++){
            int signal = (y[j]-out[j]) >= 0 ? 1: -1;
            deltaO[j] = (out[j]*(1-out[j])*Math.pow(y[j]-out[j],2))*signal;
        }
        
        double[] deltaH = new double[this.hiddenQuantity];
        
        for(int h = 0; h < this.hiddenQuantity; h++){
            deltaH[h] = H[h] * (1 - H[h]);
            double sum = 0;
            
            for(int j = 0 ; j < this.out; j++){
                sum += deltaO[j] * this.wo[h][j];
            }
            
            deltaH[h] *= sum;
        }
        
        for(int j = 0; j < this.wo[0].length; j++){
            for(int h = 0 ; h < this.wo.length; h++){
                this.wo[h][j] += this.mi * deltaO[j] * H[h];
            }
        }
        
        for(int h = 0; h < this.wh[0].length; h++){
            for(int i = 0; i < this.wh.length; i++){
                this.wh[i][h] += this.mi * deltaH[h] * x[i];
            }
        }
        return out;
    }
    
    public double[] test(double[] Xin,double[] y){
        double[] x = new double[Xin.length + 1];
        for(int i = 0; i < x.length - 1; i++){
            x[i] = Xin[i];
        }
        x[x.length - 1] = 1;
        
        double[] H = new double[this.hiddenQuantity+1];
        
        for(int h = 0; h < this.wh[0].length; h++){
            double u = 0;
            
            for(int i = 0; i < this.wh.length; i++){
                u += x[i] * this.wh[i][h];
            }
            
            H[h] = 1 / (1 + (Math.exp(u*(-1))));
        }
        
        H[H.length - 1] = 1;
        
        double[] out = new double[this.out];
        
        for(int j = 0; j < this.wo[0].length;j++){
            double u = 0;
            for(int h = 0; h < this.wo.length; h++){
                u += H[h] * this.wo[h][j];
            }
            
            out[j] = 1 / (1 + (Math.exp(u*(-1))));
        }
        
        double[] deltaO = new double[this.out];
        for(int j = 0; j < this.out; j++){
            deltaO[j] = out[j]*(1-out[j])*(y[j]-out[j]);
        }

        
        return out;
    }
    
}