package Lab01;

import java.util.Scanner;

public class Bai1 {
    public static double estimateCircleArea(double r) {
        int insideCount = 0;
        int numPoints = 10_000_000;

        for (int i = 0; i < numPoints; i++) {
            double x = (Math.random() * 2 - 1) * r;
            double y = (Math.random() * 2 - 1) * r;
            if (x * x + y * y <= r * r) {
                insideCount++;
            }
        }

        double squareArea = 4 * r * r;
        return ((double) insideCount / numPoints) * squareArea;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ban kinh: ");
        double r = sc.nextDouble();
        System.out.printf("Dien tich hinh tron la: %.2f%n", estimateCircleArea(r));
        sc.close();
    }
}