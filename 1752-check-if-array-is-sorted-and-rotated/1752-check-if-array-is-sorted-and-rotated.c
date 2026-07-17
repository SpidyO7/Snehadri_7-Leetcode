#include <stdio.h>
#include <stdbool.h>
bool check(int* nums, int numsSize) {
    int breaks = 0;

    for (int i = 0; i < numsSize - 1; i++) {
        if (nums[i] > nums[i + 1]) {
            breaks++;
        }
    }

    if (nums[numsSize - 1] > nums[0]) {
        breaks++;
    }

    if (breaks <= 1) {
        return true;
    }

    return false;
}