package Lab01;

import java.util.*;

public class Bai4 {
    private List<Integer> res = new ArrayList<>();
    private int maxLen = 0;

    public List<Integer> solve(int[] arr, int k) {
        res = new ArrayList<>();
        maxLen = 0;
        backtrack(arr, k, 0, new ArrayList<>(), 0);
        return res;
    }

    private void backtrack(int[] arr, int k, int index, List<Integer> path, int sum) {
        // Tìm được tổng = k → cập nhật nếu dài hơn rồi dừng
        // (vì toàn số dương, thêm phần tử chỉ làm tổng tăng)
        if (sum == k) {
            if (path.size() > maxLen) {
                maxLen = path.size();
                res = new ArrayList<>(path);
            }
            return; // ← thêm return để tránh đệ quy thừa
        }

        // Hết mảng hoặc tổng vượt k → dừng
        if (index >= arr.length || sum > k)
            return;

        // Chọn phần tử hiện tại
        path.add(arr[index]);
        backtrack(arr, k, index + 1, path, sum + arr[index]);
        path.remove(path.size() - 1);

        // Bỏ qua phần tử hiện tại
        backtrack(arr, k, index + 1, path, sum);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = sc.nextInt();
        sc.close();

        Bai4 sol = new Bai4();
        List<Integer> result = sol.solve(arr, k);

        if (result.isEmpty()) {
            System.out.println("Khong ton tai day con");
        } else {
            System.out.println("Do dai: " + result.size());
            System.out.println("Day con: " + result);
        }
    }
}