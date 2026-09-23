package com.example.ejemplodialogopersonalizado.ej;

import java.util.ArrayList;

public class Ej11JavaPuro {

    // Devuelve true si el numero es par
    static boolean esPar(int n) {
        return n % 2 == 0;
    }

    // Suma 1 + 2 + ... + n
    static int sumarHasta(int n) {
        int total = 0;
        for (int i = 1; i <= n; i++) {
            total = total + i;
        }
        return total;
    }

    public static void main(String[] args) {
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Ana");
        nombres.add("Luis");
        nombres.add("Marta");

        for (int i = 0; i < nombres.size(); i++) {
            System.out.println(i + ": " + nombres.get(i));
        }

        System.out.println(esPar(4));       // true
        System.out.println(sumarHasta(5));  // 15
    }
}
