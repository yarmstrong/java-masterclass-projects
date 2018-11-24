package com.hoken;

public class Main {
    public static void main(String[] args) {
//        BubbleSort.sort(new int[] {2,8,23,3,1,19}, BubbleSort.Order.DESC);
//        BubbleSort.sort(new int[] {2,8,23,3,1,19}, BubbleSort.Order.ASC);
//        BubbleSort.sort(new int[] {38,27,43,3,9,82,10}, BubbleSort.Order.ASC); // 7 entries, 21 tries

//        MergeSort.sort(new int[] {38,27,43,3,9,82,10}); // 7 entries, 13 splitting + additional merging loops

        InsertionSort.sort(new int[] {38,27,43,3,9,82,10}); // 7 entries, 11 tries
    }
}
