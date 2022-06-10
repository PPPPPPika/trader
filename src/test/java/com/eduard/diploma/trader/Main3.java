package com.eduard.diploma.trader;

import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;

import java.util.*;

public class Main3 {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++){
            System.out.println("i = " + i);
            for (int j = 0; j < 10; j++){
                System.out.println("j = " + j);
                if (j == 1)
                    break;
            }
        }

    }
}
