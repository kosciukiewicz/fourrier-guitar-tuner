package com.example.witold.wicioguitartuner.AudioAnalysis;

/**
 * Created by Witold on 2016-12-01.
 */
public class SoundAnalysis {

    public static Complex[] hanningWindow(Complex[] data, int size) {
        Complex[] result = new Complex[size];
        for (int i = 0; i < size; i++) {
            result[i] = new Complex(data[i].re * 0.5 * (1.0 - Math.cos(2.0 * Math.PI * i / (size - 1))),0.0);
        }
        return result;
    }

    public static double[] fft(double[] x) {
        int n = x.length;
        int m = (int) (Math.log(n) / Math.log(2));
        int i, j, k, n1, n2, a;
        double c, s, t1, t2;
        double[] y = new double[n];
        j = 0;
        n2 = n / 2;
        for (i = 1; i < n - 1; i++) {
            n1 = n2;
            while (j >= n1) {
                j = j - n1;
                n1 = n1 / 2;
            }
            j = j + n1;

            if (i < j) {
                t1 = x[i];
                x[i] = x[j];
                x[j] = t1;
                t1 = y[i];
                y[i] = y[j];
                y[j] = t1;
            }
        }

        n1 = 0;
        n2 = 1;

        for (i = 0; i < m; i++) {
            n1 = n2;
            n2 = n2 + n2;
            a = 0;

            for (j = 0; j < n1; j++) {
                c = Math.cos(a);
                s =  Math.sin(a);
                a += 1 << (m - i - 1);

                for (k = j; k < n; k = k + n2) {
                    t1 = c * x[k + n1] - s * y[k + n1];
                    t2 = s * x[k + n1] + c * y[k + n1];
                    x[k + n1] = x[k] - t1;
                    y[k + n1] = y[k] - t2;
                    x[k] = x[k] + t1;
                    y[k] = y[k] + t2;
                }
            }
        }
        return x;
    }


}
