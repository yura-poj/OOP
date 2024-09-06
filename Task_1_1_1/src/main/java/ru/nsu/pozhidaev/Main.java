package ru.nsu.pozhidaev;

import java.lang.reflect.Array;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private static void siftup(int[] arr, int N, int head) {
        int largest = head;
        int left = 2 * head + 1;
        int right = 2 * head + 2;
        if (left < N && arr[largest] < arr[left])
            largest = left;
        if (right < N && arr[largest] < arr[right])
            largest = right;

        if (largest != head) {
            swap(arr, head, largest);
            siftup(arr, N, largest);
        }
    }

    public static int[] heapsort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            siftup(arr, arr.length, i);
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, 0, i);
            siftup(arr, i, 0);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] array = {1, 8, 3, 9, 1, 6};
        heapsort(array);
        System.out.println(Arrays.toString(array));
    }
}