import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

//https://www.hackerrank.com/contests/target-samsung-13-nov19/challenges/flip-columns/problem
public class FlipColumns {

    public static int max(int a, int b) {
        return (a >= b) ? a : b;
    }

    public static int findMaxIdenticalRows(int n, int m, int k, int[][] matrix) {
        int[] visRows = new int[n];
        int ans = 0;
        for(int row=0; row<n; row++) {
            if(visRows[row] == 1)
                continue;

            visRows[row] = 1;
            int rowFrequency = 1;
            for(int i=row+1; i<n; i++) {
                int matched = 0;
                for(int col=0; col<m; col++) {
                    if(matrix[row][col] == matrix[i][col])
                        matched++;
                }
                
                if(matched == m) {
                    rowFrequency++;
                    visRows[i] = 1;
                }
            }

            int zeroes = 0;
            for(int col=0; col<m; col++) {
                if(matrix[row][col] == 0)
                    zeroes++;
            }

            if(zeroes <= k && (k-zeroes)%2 == 0)
                ans = max(ans, rowFrequency);
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        int[][] matrix = new int[n][m];
        for(int row=0; row<n; row++) {
            for(int col=0; col<m; col++) {
                int value = sc.nextInt();
                matrix[row][col] = value;
            }
        }

        System.out.println(findMaxIdenticalRows(n, m, k, matrix));
    }
}
