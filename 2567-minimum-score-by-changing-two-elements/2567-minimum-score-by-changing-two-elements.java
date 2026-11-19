class Solution {
    public int minimizeSum(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;

        if (n <= 3) {
            return 0;
        }

        int ans = Integer.MAX_VALUE;

        ans = Math.min(ans, nums[n - 3] - nums[0]);
        ans = Math.min(ans, nums[n - 2] - nums[1]);
        ans = Math.min(ans, nums[n - 1] - nums[2]);

        return ans;
    }
}