#include <iostream>

using namespace std;

#define	INT_MAX		2147483647
#define	INT_MIN		(-2147483647-1)

int main() {
    int NUMBERS;
    cin >> NUMBERS;
    int array[NUMBERS], maxArr[NUMBERS], minArr[NUMBERS];
    int max = INT_MIN;
    for (int i = 0; i < NUMBERS; i++) {
        int current;
        cin >> current;
        max = current > max ? current : max;
        maxArr[i] = max;
        array[i] = current;
    }

    int min = INT_MAX;
    for (int i = NUMBERS - 1; i >= 0; i--) {
        min = min < array[i] ? min : array[i];
        minArr[i] = min;
    }

    int count = 0;
    for (int i = 0; i < NUMBERS; i++) {
        if (array[i] <= minArr[i] && array[i] >= maxArr[i]) {
            count++;
        }
    }
    cout << count;
    return 0;
}