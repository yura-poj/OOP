package ru.nsu.pozhidaev;

import java.util.Arrays;
import java.util.Scanner;

/*
The main class
 */
public class Main {

    /*
    swap the elements of array with indexes a and b
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /*
    sift probably small element to the right position
    */
    private static void siftup(int[] arr, int n, int head) {
        int largest = head;
        int left = 2 * head + 1;
        int right = 2 * head + 2;
        if (left < n && arr[largest] < arr[left]) {
            largest = left;
        }
        if (right < n && arr[largest] < arr[right]) {
            largest = right;
        }


        if (largest != head) {
            swap(arr, head, largest);
            siftup(arr, n, largest);
        }
    }

    /*
    heapsort is a type of sorting algorithm which use binary tree where on the top is the largest element
    on each step we delete this largest element and add it to the new sorted array
    */
    public static int[] heapsort(int[] arr) {
        // Practically this cycle usually create in other function by name heapify
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            siftup(arr, arr.length, i); // We are create heap in the right order
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, 0, i); // swap the largest element with ith element
            siftup(arr, i, 0); // sift this element to avoid smaller number was upper than bigger
        }
        return arr; // return sorted array
    }

    /*
    user should enter length of array, array and will get sorted array by heapsort
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter length of array:");
        int n = scanner.nextInt();
        System.out.println("Enter your numbers like(1 2 3 4 5):");
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }
        long start = System.nanoTime();
        heapsort(numbers);
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);


        System.out.println("Sorted array(by " + timeElapsed + ") :");
        System.out.println(Arrays.toString(numbers));
    }
}