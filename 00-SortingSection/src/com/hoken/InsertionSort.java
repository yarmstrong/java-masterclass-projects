package com.hoken;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InsertionSort {
    public static void sort(int[] arr) {
        // BubbleSort (either ASC/DESC: moves from i=0 to i<length-1 (if 5data, til i<4 only) left to right direction, with target value being saved at the end of the array, so that on the recurring loop, u exclude this previously processed value and only process til the penultimate item before it (j=0 to j<4-(processedAlready))

        // InsertionSort (from i=1 to i<length, u start from i=1 bec u want to process the i=0 vs i=1. arr[1] for processing is saved in a variable (temp) and compares it with arr[i<1], meaning at i=4, save it in temp, traverse from i=3,2,1,0 to do comparison if temp value will displace any of them
        // ie: i=4 so value for processing is temp=arr[4]=5, so traversing backwards is j=3,2,1,0. for temp=5 vs arr[3]=10, value@3 is saved into arr[4] for sure. but then, u cant save temp=5 to arr[3] just yet, need to repeat temp=5 vs arr[2] and so on

        int tries = 0;
//        for (int i = 1; i < arr.length; i++) { // 1 to end
//            int valueToProcess = arr[i];
//            int traverseBackwards = i-1;
//            while (traverseBackwards >= 0 && valueToProcess < arr[traverseBackwards]) {
//                // boolean1: as long as u still have traverseBackwards index to process
//                // boolean2: DESC, valueToProcess must be swapped in earlier
//
//                arr[traverseBackwards+1] = arr[traverseBackwards--];
//                // traverseBackwards+1=4, traverseBackwards=3, do @4 = @3 value saving
//                // traverseBackwards+1=3, traverseBackwards=2, do @3 = @2
//                // traverseBackwards+1=2, traverseBackwards=1, do @2 = @1
//                // traverseBackwards+1=1, traverseBackwards=0, do @1 = @0
//                // traverseBackwards+1=0, traverseBackwards=-1, now break on this loop and save @0 = temp
//                tries++;
//            }
//            arr[traverseBackwards+1] = valueToProcess;
//            System.out.println("out of while loop: i = " +i+ ", j = " +traverseBackwards);
//        }
//        System.out.println("InsertionSort: after " + tries + " tries, sorted array " + Arrays.toString(arr));


        // SOLUTION TO ALL THE LIST ISSUES (found below): 

        /**
         * 1. Converting normal <T>array into modifiable List<T> collection
         */
        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        // methodology: 1) stream() receives an int[] so it returns an IntStream 2) which u then call .boxed() from IntStream which will do boxing for u, returning Stream<Integer> 3) which u now call collect method of the Stream and finally put it into a list
        // list.add(1000); // addable
        // System.out.println(list.toString()); // list has their toString() modified same as Arrays.toString(arr)

        /**
         * 2. Converting List<T> collection into normal <T>array which ofc is non-modifiable since fixed size
         */
        // List<String> objData;
        // String[] arr = objData.toArray(new String[objData.size()]);
        // methodology: 1) declare ur array name and its type 2) call List.toArray 3) put arg of initializing an array of specific size where: 1 and 3 is justthe combi of usual String[] arr = new String[objData.size()];
        // System.out.println("Saved data is: " + Arrays.toString(arr) + '\n'); // original arr.toString() will output the default code while Arrays.toString(arr) is the one modified to be readable for user

        
        System.out.println(list.toString());
        for (int i = 1; i < list.size(); i++) { // 1 to end
            int valueToProcess = list.get(i);
            int traverseBackwards = i-1;

            while (traverseBackwards >= 0 && valueToProcess < list.get(traverseBackwards)) {
                traverseBackwards--;
                tries++;
            }
            if ((traverseBackwards+1)!=i) { // if same index, means that it is already at the index it is supposed to be
                System.out.println("out of while loop: arr[" + i + "]: " + arr[i] + " to exhange with arr[" + (traverseBackwards+1) + "]: " + list.get(traverseBackwards+1));
                list.add(traverseBackwards+1, list.remove(i));
                System.out.println(list.toString());
            }
        }
        System.out.println("InsertionSort: after " + tries + " tries, sorted array " + list.toString());
    }
}


/********************
 * ISSUES RELATED TO PRIMITIVE ARRAY CONVERSION TO LIST<T>
 * 
 * the list to array and array to list issue
 * 
 */
//        LinkedList<Integer> list = new LinkedList<>();
// issue1: uses reference type same as object constructor, and instantiate it with empty. now for compiler to be appeased, need to provide the reference type with the element type that it shud expect (since u initialized it as empty). list.add(1); works fine, will do the autoboxing for u

//        List<Integer> list = new LinkedList<>();
// issue2: uses higher polymorph of List instead of the specific object LinkedList. since instantiated as empty, u also need to tell the reference type what element type it shud expect. list.add(2); works fine, will do the autoboxing for u

//        List list = Arrays.asList(arr);
// issue3: yes, u converted it to type List but as per docu, this List is a fixed-size list and so cannot add or remove index


//        List<Integer> list = Arrays.asList(1,2,3,4);
// issue4: list.set(0,5); ok but not list.add(5) cant be, same rule, fixed index, since u start from int to Integer

//        List<Integer> list = new ArrayList<>(Arrays.asList(new Integer[] {1,2,3,4}));
// issue5: u can specifically use ArrayList instead so u can add new index, but first it has to be Integer not int primitive. list.add(1); work ok now that u change to ArrayList and ur array is not primitive int but object Integer