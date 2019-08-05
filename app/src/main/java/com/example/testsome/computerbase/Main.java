package com.example.testsome.computerbase;

/**
 * Created by philer on 2019/7/12.
 * Describe:
 */
public class Main {
    public static void main(String[] args){
        //8G = 8 * 1024 * 1024 kb = 8 * 1024 * 1024 * 1024 * 8 bit
        // 8G = 8 * 1000 * 1000 kb = 8 * 1000 * 1000 * 1000 * 8bit
        long true8G = 8l * 1024 * 1024;
        long buy8G = 8l * 1000 * 1000;
        System.out.println("true8g : "+true8G+" bit");
        System.out.println("buy8g : "+buy8G+" bit");
        double trueSize = buy8G * 1.0 / (1024 * 1024);
        System.out.println("buy8g true size : "+trueSize);
    }
}
