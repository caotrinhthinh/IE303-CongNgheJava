package Lab01;

import java.util.Random;
import java.util.Scanner;

public class Bai2 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            long numPoints = sc.nextLong();
            if (numPoints <= 0) {
                System.out.print((double) 0);
                return;
            }

            // Dẫn xuất hằng số từ r, không dùng literal
            double r = (double) numPoints / numPoints; // 1.0
            double two = r + r; // 2.0
            double four = two + two; // 4.0

            Random rnd = new Random();
            long insideCount = 0;

            for (long i = 0; i < numPoints; i++) {
                double x = (two * rnd.nextDouble() - r) * r;
                double y = (two * rnd.nextDouble() - r) * r;
                if (x * x + y * y <= r * r)
                    insideCount++;
            }

            System.out.print(four * ((double) insideCount / numPoints));
        }
    }
}