import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] array1 = new int[] {5,2,4,6,1,3,2,6};
        sort(array1, 1, array1.length);
        System.out.println(Arrays.toString(array1)); // [1, 2, 2, 3, 4, 5, 6, 6]

        int[] array2 = new int[] {5,4,3,2,1};
        sort(array2, 1, array2.length);
        System.out.println(Arrays.toString(array2)); // [1, 2, 3, 4, 5]

        int[] array3 = new int[] {2, 1};
        sort(array3, 1, array3.length);
        System.out.println(Arrays.toString(array3)); // [1, 2]

        int[] array4 = new int[] {10};
        sort(array4, 1, array4.length);
        System.out.println(Arrays.toString(array4)); // [10]
    }

    private static void sort(int[] array, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            sort(array, p, q);
            sort(array, q + 1, r);
            merge(array, p, q, r);
        }
    }

    private static void merge(int[] inputArray, int lowerBound, int mid, int upperBound) {
        // the index of second half of the input array
        int indexOfSecondHalf = mid;
        // the index of first half of the input array
        int indexOfFirstHalf = lowerBound - 1;
        // the array for merging
        int n = upperBound - lowerBound + 1;
        int[] mergeArray = new int[n];
        int indexOfMergeArray = 0;

        while (indexOfFirstHalf < mid && indexOfSecondHalf < upperBound) {
            if (inputArray[indexOfFirstHalf] < inputArray[indexOfSecondHalf]) {
                mergeArray[indexOfMergeArray++] = inputArray[indexOfFirstHalf++];
            } else {
                mergeArray[indexOfMergeArray++] = inputArray[indexOfSecondHalf++];
            }
        }
        while (indexOfFirstHalf < mid) {
            mergeArray[indexOfMergeArray++] = inputArray[indexOfFirstHalf++];
        }
        while (indexOfSecondHalf < upperBound) {
            mergeArray[indexOfMergeArray++] = inputArray[indexOfSecondHalf++];
        }

        System.arraycopy(mergeArray, 0, inputArray, lowerBound - 1, n);
    }
}
