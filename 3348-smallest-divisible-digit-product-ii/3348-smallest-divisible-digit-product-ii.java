class Solution {
    private static final int[][] DIGIT_FACTORS = {
        {0, 0, 0, 0}, // 0, unused
        {0, 0, 0, 0}, // 1
        {1, 0, 0, 0}, // 2
        {0, 1, 0, 0}, // 3
        {2, 0, 0, 0}, // 4
        {0, 0, 1, 0}, // 5
        {1, 1, 0, 0}, // 6
        {0, 0, 0, 1}, // 7
        {3, 0, 0, 0}, // 8
        {0, 2, 0, 0}  // 9
    };

    public String smallestNumber(String num, long t) {
        int[] target = factorize(t);

        // t has a prime factor other than 2, 3, 5, or 7.
        if (target == null) {
            return "-1";
        }

        MinDigitsSolver solver = new MinDigitsSolver(target);

        int n = num.length();

        // Prefix factor counts.
        int[][] prefix = new int[4][n + 1];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < 4; k++) {
                prefix[k][i + 1] = prefix[k][i];
            }

            int digit = num.charAt(i) - '0';

            if (digit != 0) {
                for (int k = 0; k < 4; k++) {
                    prefix[k][i + 1] = Math.min(
                        target[k],
                        prefix[k][i + 1] + DIGIT_FACTORS[digit][k]
                    );
                }
            }
        }

        // Check whether num itself is already a valid answer.
        boolean zeroFree = true;
        boolean divisible = true;

        for (int i = 0; i < n; i++) {
            int digit = num.charAt(i) - '0';

            if (digit == 0) {
                zeroFree = false;
                break;
            }
        }

        if (zeroFree) {
            for (int k = 0; k < 4; k++) {
                if (prefix[k][n] < target[k]) {
                    divisible = false;
                    break;
                }
            }

            if (divisible) {
                return num;
            }
        }

        /*
         * Try to create a number of the same length.
         *
         * If position i is changed, all positions before i must remain equal
         * to num. Therefore, i cannot be after the first zero.
         *
         * Trying i from right to left gives the smallest possible candidate.
         */
        int firstZero = n;

        for (int i = 0; i < n; i++) {
            if (num.charAt(i) == '0') {
                firstZero = i;
                break;
            }
        }

        for (int i = Math.min(firstZero, n - 1); i >= 0; i--) {
            int originalDigit = num.charAt(i) - '0';
            int minDigit = Math.max(1, originalDigit + 1);
            int remainingPositions = n - i - 1;

            for (int digit = minDigit; digit <= 9; digit++) {
                int[] required = requiredAfter(
                    target,
                    prefix,
                    i,
                    digit
                );

                if (solver.minimumDigits(required) <= remainingPositions) {
                    StringBuilder answer = new StringBuilder(n);

                    answer.append(num, 0, i);
                    answer.append(digit);

                    appendSmallestSuffix(
                        answer,
                        required,
                        remainingPositions,
                        solver
                    );

                    return answer.toString();
                }
            }
        }

        /*
         * No answer of the same length exists.
         * Try the smallest valid number with n + 1 digits.
         */
        int[] required = target.clone();

        if (solver.minimumDigits(required) > n + 1) {
            return "-1";
        }

        StringBuilder answer = new StringBuilder(n + 1);

        appendSmallestSuffix(
            answer,
            required,
            n + 1,
            solver
        );

        return answer.toString();
    }

    private static int[] factorize(long t) {
        int[] exponents = new int[4];
        int[] primes = {2, 3, 5, 7};

        for (int i = 0; i < 4; i++) {
            while (t % primes[i] == 0) {
                exponents[i]++;
                t /= primes[i];
            }
        }

        return t == 1 ? exponents : null;
    }

    private static int[] requiredAfter(
        int[] target,
        int[][] prefix,
        int position,
        int digit
    ) {
        int[] required = new int[4];

        for (int k = 0; k < 4; k++) {
            int alreadyAvailable =
                prefix[k][position] + DIGIT_FACTORS[digit][k];

            required[k] = Math.max(0, target[k] - alreadyAvailable);
        }

        return required;
    }

    private static void appendSmallestSuffix(
        StringBuilder answer,
        int[] required,
        int length,
        MinDigitsSolver solver
    ) {
        for (int position = 0; position < length; position++) {
            int remaining = length - position - 1;

            for (int digit = 1; digit <= 9; digit++) {
                int[] nextRequired = new int[4];

                for (int k = 0; k < 4; k++) {
                    nextRequired[k] = Math.max(
                        0,
                        required[k] - DIGIT_FACTORS[digit][k]
                    );
                }

                if (solver.minimumDigits(nextRequired) <= remaining) {
                    answer.append(digit);
                    required = nextRequired;
                    break;
                }
            }
        }
    }

    private static class MinDigitsSolver {
        private final int[] target;
        private final int b2;
        private final int b3;
        private final int b5;
        private final int totalStates;
        private final int[] dp;

        MinDigitsSolver(int[] target) {
            this.target = target;

            b2 = target[0] + 1;
            b3 = target[1] + 1;
            b5 = target[2] + 1;

            int b7 = target[3] + 1;

            totalStates = b2 * b3 * b5 * b7;
            dp = new int[totalStates];

            java.util.Arrays.fill(dp, Integer.MAX_VALUE / 2);
            dp[0] = 0;

            /*
             * Digits 2..9 are unbounded choices.
             * The encoded state always moves forward because exponents
             * only increase, so one forward DP pass is sufficient.
             */
            for (int state = 0; state < totalStates; state++) {
                if (dp[state] >= Integer.MAX_VALUE / 2) {
                    continue;
                }

                int[] current = decode(state);

                for (int digit = 2; digit <= 9; digit++) {
                    int[] next = new int[4];

                    for (int k = 0; k < 4; k++) {
                        next[k] = Math.min(
                            target[k],
                            current[k] + DIGIT_FACTORS[digit][k]
                        );
                    }

                    int nextState = encode(next);

                    dp[nextState] = Math.min(
                        dp[nextState],
                        dp[state] + 1
                    );
                }
            }
        }

        int minimumDigits(int[] required) {
            return dp[encode(required)];
        }

        private int encode(int[] state) {
            return (((state[0] * (target[1] + 1) + state[1])
                    * (target[2] + 1) + state[2])
                    * (target[3] + 1) + state[3]);
        }

        private int[] decode(int state) {
            int[] result = new int[4];

            result[3] = state % (target[3] + 1);
            state /= target[3] + 1;

            result[2] = state % (target[2] + 1);
            state /= target[2] + 1;

            result[1] = state % (target[1] + 1);
            state /= target[1] + 1;

            result[0] = state;

            return result;
        }
    }
}