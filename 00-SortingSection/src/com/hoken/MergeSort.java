package com.hoken;

import java.util.Arrays;
import java.util.StringJoiner;

public class MergeSort {

    private static int sortCtr = 0;

    public static void printThisArray(String note, int[] completeArray, int leftIndex, int middleIndex, int rightIndex) {
        StringJoiner sj = new StringJoiner(", ", "{", "}");
        StringBuilder sb = new StringBuilder(note);
        sb.append("your two arrays\n\t1) index ").append(leftIndex).append(" to ").append(middleIndex).append(": ");
        for (int i = leftIndex; i <= middleIndex ; i++) {
            sj.add(Integer.toString(completeArray[i]));
        }
        sb.append(sj).append("\n\t2) index ").append(middleIndex+1).append(" to ").append(rightIndex).append(": ");
        sj = new StringJoiner(", ", "{", "}");
        for (int i = middleIndex+1; i <= rightIndex ; i++) {
            sj.add(Integer.toString(completeArray[i]));
        }
        System.out.println(sb.append(sj).toString());
    }

    public static void sort(int[] toSort) {
        sort(toSort, 0, toSort.length-1);
        System.out.println("final sorting: " + Arrays.toString(toSort) + " with " + sortCtr + " sort/splitting tries.");
    }

//    private static void sort(int[] toSort, int leftIndex, int rightIndex) {
//        if (leftIndex >= rightIndex) {
//            return;
//        }
//        int mid = leftIndex + (rightIndex - leftIndex) / 2;
//        sort(toSort, leftIndex, mid);
//        sort(toSort, mid+1, rightIndex);
//        merge(toSort, leftIndex, rightIndex, mid);
//    }

    private static void sort(int[] toSort, int leftIndex, int rightIndex) {
        sortCtr++; // num of times sort is called, even including when u have singles where auto exit

        if (leftIndex < rightIndex) { // as previously taught, if there is doubles, u still need to invoke sort method for them so that the  by singles for 1st half and 2nd half will happen to eventually trigger merging where the left and right singles will be ordered. bec we were concerned on the enhancement, we thought that doubles no longer need to be sorted. we thought we only need the current L, M and R data. but is not. when split in, say index 0-1, the split will be 0 0 1, 1st half is 0 0, and 2nd half is 1 1. this if in the start of sort will do immediate exit, since it knows that it is singles already. just think that if u dont execute sort for doubles, yes, the current L and M is ok, but ur not sure of the order since merge is not called at all. what will happen is with the current L and M, u pass it to merge together with M+1 and R, but then u learn that u are not even sorted at all

            int middleIndex = (leftIndex + rightIndex) / 2;
            // aka get average of 2 nums
            // more intuitive based on indexing: int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
            // ie between index 4 & 9 shud be 6 => 4 5 [6] //split here 7 8 9

            // spitting for 0 3 is: 0 1 3 => 0 1 => 0 0 1 => 0 0 singles and 1 1 singles
            //                            => 2 3 => 2 2 3 => 2 2 singles and 3 3 singles
            // spitting for 4 6 is: 4 5 6 => 4 5 => 4 4 5 => 4 4 singles and 5 5 singles
            //                            => 6 6 => singles

            System.out.println(sortCtr+": "+leftIndex+" "+middleIndex+" "+rightIndex); // those with valid splitting, ctr 1,2,3,6,9,10
            sort(toSort, leftIndex, middleIndex);
            sort(toSort, middleIndex+1, rightIndex);
            merge(toSort, leftIndex, middleIndex, rightIndex);
        }
        else {
            System.out.println(sortCtr+": "+leftIndex+" "+rightIndex); // ctr 4,5, 7,8, 11,12, 13 are already singles
        }
    }

    private static void merge(int[] toSort, int left, int mid, int right) {
        printThisArray("to merge ", toSort, left, mid, right);

        // for every left-mid and mid-right arrays u receive here, u need to merge them in order
        // since the sorting will loop until forced for left, middle, right to be singles,
        // its easier, til it build up to bigger, but by then, most are already in order

        // step 1: setup variables data setup
        // a) leftArr and rightArr as 2 separate temp array (index starts @ 0) where u copy initial data
        // b) toSort will be replaced with the correct order in step2

        int leftLen = mid-left+1;
        int rightLen = right-mid;

        int[] leftArr = new int[leftLen];
        int[] rightArr = new int[rightLen];

        for (int i = 0; i < leftLen ; i++)
            leftArr[i] = toSort[left+i];
        for (int j = 0; j < rightLen ; j++)
            rightArr[j] = toSort[mid+1+j];

        // step 2: the merging by comparing leftArr and rightArr
        // let i = index for leftArr @ 0
        // let j = index for rightArr @ 0
        // let k = index for toSort (when we do replacement)
//        int i = 0;
//        int j = 0;
//        int k = left;
//        while (i<leftLen && j<rightLen) { // reminder: i,j are index start @ 0 while length is index+1 (thus the < comparison and not <=) as long as we have not exceeded the available index means we can still traverse the array // also && used since if either of the 2 exceeded, the i++ or j++ will error with NULL pointer. if say, only leftArr is traversed, then leftArr are the 1st data to the left in order, while rightArr (which is by default already ordered, will be added afterwards. ie: when singles, if leftArr works, then i++ = leftLen, and while loop will stop, thats why need to do step3, to save the copy from rightArr not processes || also, if leftArr traversed fully, then u will have null value to compare with rightArr, thats why it breaks the while loop)
//            if (leftArr[i] <= rightArr[j]) { // left will win even if equal, this is what it means to order preserved even when same value
//                toSort[k] = leftArr[i];
//                i++;
//            }
//            else {
//                toSort[k] = rightArr[j];
//                j++;
//            }
//            k++;
//        }
//
//        // step 3: un-traversed index from leftArr and rightArr shud be added to toSort, processing 1st left before right
//        while (i<leftLen) { // still has remaining index from last i iteration in step2
//            toSort[k] = leftArr[i];
//            i++;
//            k++;
//        }
//        while (j<rightLen) { // still has remaining index from last i iteration in step2
//            toSort[k] = rightArr[j];
//            j++;
//            k++;
//        }

        // combined code for step 2 and 3:
        int i = 0, j = 0;
        for (int k = left; k <= right; k++) { // til we traversed from left to right to fill up k (index for toSort)
            if (i<leftLen && j<rightLen) { // til either of them can be traversed (til has index for comparison)
                if (leftArr[i] <= rightArr[j]) {
                    toSort[k] = leftArr[i];
                    i++;
                }
                else {
                    toSort[k] = rightArr[j];
                    j++;
                }
            }
            else if (i<leftLen && j==rightLen) {
                toSort[k] = leftArr[i++]; // i++ is combi of leftArr[i] and i++ afterwards
            }
            else if (j<rightLen && i==leftLen) {
                toSort[k] = rightArr[j++];
            }
        }
        printThisArray("merged ", toSort, left, mid, right);
    }
}
