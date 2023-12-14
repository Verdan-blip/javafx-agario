package ru.kpfu.itis.bagaviev.agario.engine.math;

public class Math {

    public static long factorial(int number) {
        long result = 1;
        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }
        return result;
    }

    public static long combinationsWithNoRepetitions(int k, int n) {
        if (k > n) throw new IllegalArgumentException("'k' can't be more than 'n'");
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

}
