import java.util.Scanner;

//https://www.hackerrank.com/contests/target-samsung-13-nov19/challenges/burst-balloons-1
public class BurstBalloons {

    public static int max(int a, int b) {
        return (a>=b) ? a : b;
    }

    public static int burstBalloons(int i, int j, int dp[][], int[] nums) {
        if(i > j)
            return 0;

        if(dp[i][j] != -1)
            return dp[i][j];

        int maxPoints = 0;
        for(int k=i; k<=j; k++) {
            int pts;
            if((i-1) == 0 && (j+1) == nums.length-1) // only one balloon left
                pts = nums[k] + burstBalloons(i, k-1, dp, nums) + burstBalloons(k+1, j, dp, nums);
            else
                pts = (nums[i-1] * nums[j+1]) + burstBalloons(i, k-1, dp, nums) + burstBalloons(k+1, j, dp, nums);

            maxPoints = max(maxPoints, pts);
        }

        return dp[i][j] = maxPoints;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n+2];
        nums[0] = nums[n+1] = 1;
        int[][] dp = new int[n+2][n+2];

        for(int i=1; i<=n; i++) {
            int num = sc.nextInt();
            nums[i] = num;
        }

        for(int i=0; i<nums.length; i++) {
            for(int j=0; j<nums.length; j++) {
                dp[i][j] = -1;
            }
        }

        System.out.println(burstBalloons(1, nums.length-2, dp, nums));
    }
}
