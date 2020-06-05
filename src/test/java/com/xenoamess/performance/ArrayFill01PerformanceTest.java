package com.xenoamess.performance;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Test to show witch way to fill array with its first two elements is faster.
 * the array to be used named {@code a} have at least {@code length >= 2}, and {@code a[0] == A0, a[1] == A1} before
 * it enters these functions.
 * Conclusion: please always use fill01_0.
 * it is amazingly faster than all others at least 10%.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
public class ArrayFill01PerformanceTest {
    public static double A0 = 1.23456789101112131415;
    public static double A1 = 15.1413121110987654321;

    public static void fill01_0(double[] a) {
        final int len = a.length;
        for (int i = 2; i < len; ++i) {
            a[i] = a[i - 2];
        }
    }

    public static void fill01_1(double[] a) {
        final int len = a.length;
        final double a0 = a[0];
        final double a1 = a[1];
        for (int i = 2; i < len; i += 2) {
            a[i] = a0;
        }
        for (int i = 3; i < len; i += 2) {
            a[i] = a1;
        }
    }

    public static void fill01_2(double[] a) {
        final int len = a.length;
        if (len > 2) {
            int nowLen = 2;
            while (true) {
                int nextLen = nowLen << 1;
                if (nextLen <= len) {
                    System.arraycopy(a, 0, a, nowLen, nowLen);
                } else {
                    System.arraycopy(a, 0, a, nowLen, len - nowLen);
                    break;
                }
                nowLen = nextLen;
            }
        }
    }

    public static void fill01_3(double[] a) {
        final int len = a.length;
        if (len > 16) {
            int len_4 = (len >> 2);
            if ((len_4 & 1) != 0) {
                len_4++;
            }
            for (int i = 2; i < len_4; ++i) {
                a[i] = a[i - 2];
            }
            System.arraycopy(a, 0, a, len_4, len_4);
            final int len_2 = len_4 << 1;
            System.arraycopy(a, 0, a, len_2, len - len_2);
        } else {
            for (int i = 2; i < len; ++i) {
                a[i] = a[i - 2];
            }
        }
    }

    public static void fill01_4(double[] a) {
        final int len = a.length;
        if (len > 16) {
            int len_2 = (len >> 1);
            if ((len_2 & 1) != 0) {
                len_2++;
            }
            for (int i = 2; i < len_2; ++i) {
                a[i] = a[i - 2];
            }
            System.arraycopy(a, 0, a, len_2, len - len_2);
        } else {
            for (int i = 2; i < len; ++i) {
                a[i] = a[i - 2];
            }
        }
    }

    public static void fill01_5(final double[] a) {
        Arrays.setAll(a, value -> a[value & 1]);
    }

    private double[] a = new double[10000000];

    {
        a[0] = A0;
        a[1] = A1;
    }

    @Benchmark
    public void testUsingFill01_0() {
        fill01_0(a);
    }

    @Benchmark
    public void testUsingFill01_1() {
        fill01_1(a);
    }

    @Benchmark
    public void testUsingFill01_2() {
        fill01_2(a);
    }

    @Benchmark
    public void testUsingFill01_3() {
        fill01_3(a);
    }

    @Benchmark
    public void testUsingFill01_4() {
        fill01_4(a);
    }

    @Benchmark
    public void testUsingFill01_5() {
        fill01_5(a);
    }
}
