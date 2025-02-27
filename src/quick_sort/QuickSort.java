package quick_sort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = { 12, 5, 9, 8, 1, 3, 85, 2, 4, 5, 11, 13, 56, 87, 46 };
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort(int [] arr, int start, int end) {
        if (start >= end) return;

        int pivotIndex = getPivotIndex(arr, start, end);
        int pivot = arr[pivotIndex];
        int i = start, j = end;

        while (i <= j) {
            while (arr[i] < pivot) i++;
            while (arr[j] > pivot) j--;

            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        if (start < j) quickSort(arr, start, j);
        if (end > i) quickSort(arr, i, end);
    }

    private static int getPivotIndex(int[] arr, int start, int end) {
        int mid = start + (end - start) / 2;

        if (arr[start] > arr[mid]) swap(arr, start, mid);
        if (arr[mid] > arr[end]) swap(arr, mid, end);
        if (arr[start] > arr[mid]) swap(arr, start, mid);

        return mid;
    }

    private static void swap(int [] arr, int start, int end) {
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }
}
