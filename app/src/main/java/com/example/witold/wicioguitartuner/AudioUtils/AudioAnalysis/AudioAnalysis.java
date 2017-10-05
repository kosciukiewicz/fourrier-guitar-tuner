package com.example.witold.wicioguitartuner.AudioUtils.AudioAnalysis;

/**
 * Fast Fourrier Transforamtion implementation
 */
public class AudioAnalysis {

    /**
     * Bit reverse transformation for result presetation
     */
    private static int bitReverse(int n, int bits) {
        int reversedN = n;
        int count = bits - 1;

        n >>= 1;
        while (n > 0) {
            reversedN = (reversedN << 1) | (n & 1);
            count--;
            n >>= 1;
        }

        return ((reversedN << count) & ((1 << bits) - 1));
    }

    /**
     * Fast Fourrier Transforamtion method.
     */
    public static Complex[] fft(Complex[] buffer) {

        int bits = (int) (Math.log(buffer.length) / Math.log(2));
        for (int j = 1; j < buffer.length / 2; j++) {

            int swapPos = bitReverse(j, bits);
            Complex temp = buffer[j];
            buffer[j] = buffer[swapPos];
            buffer[swapPos] = temp;
        }

        for (int N = 2; N <= buffer.length; N <<= 1) {
            for (int i = 0; i < buffer.length; i += N) {
                for (int k = 0; k < N / 2; k++) {

                    int evenIndex = i + k;
                    int oddIndex = i + k + (N / 2);
                    Complex even = buffer[evenIndex];
                    Complex odd = buffer[oddIndex];

                    double term = (-2 * Math.PI * k) / (double) N;
                    Complex exp = (new Complex(Math.cos(term), Math.sin(term)).mult(odd));

                    buffer[evenIndex] = even.add(exp);
                    buffer[oddIndex] = even.sub(exp);
                }
            }
        }
        return buffer;
    }

    public static Complex[] hanningWindow(Complex[] data, int size) {
        Complex[] result = new Complex[size];
        for (int i = 0; i < size; i++) {
            result[i] = new Complex(data[i].re * 0.5 * (1.0 - Math.cos(2.0 * Math.PI * i / (size - 1))),0.0);
        }
        return result;
    }

    public static Complex[] getComplexResult(double[] sample, int size){
        Complex[] complexResult = new Complex[size];
        for(int i= 0; i < size; i++)
        {
            complexResult[i] = new Complex(sample[i], 0.0);
        }
        return complexResult;
    }

    public static int getMax(Complex[] data)
    {
        int bucket = 0;
        double max = Math.abs(data[0].re);
        int size = data.length;

        for(int i = 0; i < size/4; i++ )
        {
            if (max < Math.abs(data[i].re))
            {
                max = Math.abs(data[i].re);
                bucket = i;
            }
        }
        return bucket;
    }
}
