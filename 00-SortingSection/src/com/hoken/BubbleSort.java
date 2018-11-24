package com.hoken;

import java.util.Arrays;

public class BubbleSort {
    enum Order { ASC, DESC }

    public static void sort(int[] arr, Order order) {
        int tries = 0;
        int traverseLength = arr.length - 1; // bubble sort uses arr.length-1 main loop
        // new int[5], length=5, traverseLength shud not include the last data in the array so instead of original traverseLength(5), u need to use traverseLength=(length-1)=4
        for (int i = 0; i < traverseLength; i++) { // u have to loop 4lengths only so (i=0 to i<4) = i=0,1,2,3
            for (int j = 0; j < traverseLength-i; j++) { // 1st loop, full length, 2nd loop less 1, and so on
                // i=0, 1st traverse: j=0 to j<4-i(4) = j=0,1,2,3 full traverse of 4
                // i=1, 2nd traverse: j=0 to j<4-i(3) = j=0,1,2
                // i=2, 3rd traverse: j=0 to j<4-i(2) = j=0,1
                // i=3, 4th traverse: j=0 to j<4-i(1) = j=0
                switch (order) {
                    case ASC:
                        if (arr[j] > arr[j+1]) swap(arr, j);
                        break;
                    case DESC:
                        if (arr[j] < arr[j+1]) swap(arr, j);
                        break;
                    default:
                        // do nothing
                        break;
                }
                /* IF-ELSE version of SWTICH-CASE above
                    where SWITCH-CASE uses ENUM values while
                    IF-ELSE uses Enum type dereference
                if (order == Order.ASC) {
                    // swap smaller to 1st
                    if (arr[j] > arr[j+1]) swap(arr, j);
                }
                else {
                    // swap bigger to 1st
                    if (arr[j] < arr[j+1]) swap(arr, j);
                }
                */
                tries++;
            }
        }
        System.out.println("BubbleSort: after " + tries + " tries, sorted array [" + order + "] is " + Arrays.toString(arr));
    }

    private static void swap(int[] arr, int j) {
        int temp = arr[j];
        arr[j] = arr[j+1];
        arr[j+1] = temp;
    }

    /*
        another variation of for-loop above:
        
        original: arr[5]
        for (int i = 0; i < traverseLength; i++) { // u have to loop 4lengths only so (i=0 to i<4) = i=0,1,2,3
            for (int j = 0; j < traverseLength-i; j++) { // 1st loop @0 full length, 2nd loop @0 less 1, and so on
            // i=0, 1st traverse: j=0 to j<4-i(4) = j=0,1,2,3 full traverse of 4 (for arr[5])
                => comparison is arr[0] vs arr[1] swap, 
                                    arr[1] vs arr[2] swap 
                                    arr[2] vs arr[3] swap 
                                    arr[3] vs arr[4] swap 
                                    arr[4] vs arr[5] swap 
            // i=1, 2nd traverse: j=0 to j<4-i(3) = j=0,1,2
            // i=2, 3rd traverse: j=0 to j<4-i(2) = j=0,1
            // i=3, 4th traverse: j=0 to j<4-i(1) = j=0

        for (int i = 0; i < traverseLength; i++) // same rule as before, a 5-index array will have a 5-1=4comparison to traverse
            for (int j = i+1; j < arr.length; j++) // this one is different. using outer loop i as the index to compare, u calculate the j index u will compare if from. its always i (the data ur comparing), move next, j=i+1 is the  starting index for comparison with i
            ie: arr[5]; i@0, j@1,2,3,4
                        i@1, j@2,3,4
                        i@2, j@2,3
                        i@3, j@2
    */
    public static void letMeSortItMyWay(List<? extends Theatre.Seat> list) {
        for (int i = 0; i < list.size()-1; i++) {
            for (int j = i+1; j < list.size(); j++) {
                if ( (list.get(i).getSeatNumber().compareToIgnoreCase(list.get(j).getSeatNumber())) > 0 ) {
                    Collections.swap(list, i, j);
                }
            }
        }
    }
}
