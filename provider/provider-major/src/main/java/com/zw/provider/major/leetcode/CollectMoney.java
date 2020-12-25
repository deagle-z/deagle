package com.zw.provider.major.leetcode;

/**
 * 凑零钱问题
 *
 * @author zw
 * @date 2020/9/3
 */
public class CollectMoney {

	
	/**
	 * 给你 k 种面值的硬币，面值分别为 c1, c2 ... ck，每种硬币的数量无限，再给一个总金额 amount，问你最少需要几枚硬币凑出这个金额，
	 * 如果不可能凑出，算法返回 -1
	 *
	 * @param coins 可选的硬币的面值
	 * @param amount 总金额
	 * @return 最少的硬币数
	 */
	public int coinChange(int[] coins, int amount) {
		//base case
		if (0 == amount) {
			return 0;
		}
		if (amount < 0) {
			return -1;
		}
		Integer res = Integer.MAX_VALUE;
		for (int coin : coins) {
			final int result = amount - coin;
			if(result == 0){

			}
			// coinChange(coins,);
		}

		return 1;
	}
}
