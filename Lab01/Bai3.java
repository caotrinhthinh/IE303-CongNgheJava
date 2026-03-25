package Lab01;

import java.util.*;

public class Bai3 {
    static long cross(int[] O, int[] A, int[] B) {
        return (long) (A[0] - O[0]) * (B[1] - O[1])
                - (long) (A[1] - O[1]) * (B[0] - O[0]);
    }

    static List<int[]> grahamScan(int[][] points) {
        int n = points.length;
        if (n <= 2)
            return Arrays.asList(points);

        // Pivot: điểm thấp nhất, nếu bằng thì lấy trái nhất
        int[] pivot = points[0];
        for (int[] p : points)
            if (p[1] < pivot[1] || (p[1] == pivot[1] && p[0] < pivot[0]))
                pivot = p;

        final int[] piv = pivot;
        List<int[]> pts = new ArrayList<>(Arrays.asList(points));
        pts.sort((a, b) -> {
            double angA = Math.atan2(a[1] - piv[1], a[0] - piv[0]);
            double angB = Math.atan2(b[1] - piv[1], b[0] - piv[0]);
            if (Math.abs(angA - angB) > 1e-9)
                return Double.compare(angA, angB);
            long dA = (long) (a[0] - piv[0]) * (a[0] - piv[0]) + (long) (a[1] - piv[1]) * (a[1] - piv[1]);
            long dB = (long) (b[0] - piv[0]) * (b[0] - piv[0]) + (long) (b[1] - piv[1]) * (b[1] - piv[1]);
            return Long.compare(dA, dB);
        });

        Stack<int[]> hull = new Stack<>();
        for (int[] p : pts) {
            while (hull.size() >= 2 && cross(hull.get(hull.size() - 2), hull.peek(), p) <= 0)
                hull.pop();
            hull.push(p);
        }
        return new ArrayList<>(hull);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            points[i][0] = sc.nextInt();
            points[i][1] = sc.nextInt();
        }
        sc.close();

        List<int[]> hull = grahamScan(points);
        int sz = hull.size();

        // Tìm điểm trái nhất (nếu bằng x thì lấy y lớn nhất)
        int start = 0;
        for (int i = 1; i < sz; i++) {
            int[] a = hull.get(i), b = hull.get(start);
            if (a[0] < b[0] || (a[0] == b[0] && a[1] > b[1]))
                start = i;
        }

        // In theo chiều CW từ điểm trái nhất
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sz; i++) {
            int[] p = hull.get((start - i + sz) % sz);
            if (sb.length() > 0)
                sb.append(" ");
            sb.append(p[0]).append(" ").append(p[1]);
        }
        System.out.print(sb);
    }
}