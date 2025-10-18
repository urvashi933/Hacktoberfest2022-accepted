package Backtracking;

import java.util.*;

public class CoinChangeWrtCoin {

    public static void main(String[] args) {
        int[] denom = {1, 2, 3};
        int target = 4;
        Arrays.sort(denom);

        // Generate and print all combinations
        List<List<Integer>> results = new ArrayList<>();
        coinChangeCombinations(denom, 0, target, new ArrayList<>(), results);
        System.out.println("All combinations:");
        for (List<Integer> comb : results) {
            System.out.println(comb);
        }

        // Count total combinations
        int totalWays = countCombinationsDP(denom, target);
        System.out.println("\nTotal combinations: " + totalWays);

        // Find minimum coins required
        int minCoins = minCoinsMemo(denom, target, new HashMap<>());
        System.out.println("Minimum coins required: " + (minCoins == Integer.MAX_VALUE ? -1 : minCoins));
    }

    // Backtracking to generate all combinations
    static void coinChangeCombinations(int[] denom, int index, int amount, List<Integer> combination, List<List<Integer>> results) {
        if (amount == 0) {
            results.add(new ArrayList<>(combination));
            return;
        }
        if (amount < 0 || index >= denom.length) return;

        // Include coin at denom[index]
        combination.add(denom[index]);
        coinChangeCombinations(denom, index, amount - denom[index], combination, results);
        combination.remove(combination.size() - 1);

        // Exclude coin at denom[index]
        coinChangeCombinations(denom, index + 1, amount, combination, results);
    }

    // Dynamic Programming approach to count total combinations
    static int countCombinationsDP(int[] denom, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;  // base case

        for (int coin : denom)
            for (int i = coin; i <= amount; i++)
                dp[i] += dp[i - coin];

        return dp[amount];
    }

    // Memoized recursive method to find minimum coins needed
    static int minCoinsMemo(int[] denom, int amount, Map<Integer, Integer> memo) {
        if (amount == 0) return 0;
        if (amount < 0) return Integer.MAX_VALUE;
        if (memo.containsKey(amount)) return memo.get(amount);

        int minCoins = Integer.MAX_VALUE;
        for (int coin : denom) {
            int res = minCoinsMemo(denom, amount - coin, memo);
            if (res != Integer.MAX_VALUE && res + 1 < minCoins) {
                minCoins = res + 1;
            }
        }
        memo.put(amount, minCoins);
        return minCoins;
    }
}
