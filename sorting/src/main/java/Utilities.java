import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Utilities {

    public static int insertInOrder(int value, int[] intoArray, int size) {
        int i = size;
        while (i > 0 && value < intoArray[i - 1]) {
            intoArray[i] = intoArray[i - 1];
            --i;
        }
        intoArray[i] = value;
        return i;
    }

    public static int insertInOrder(double value, double[] intoArray, int size) {
        int i = size;
        while (i > 0 && value < intoArray[i - 1]) {
            intoArray[i] = intoArray[i - 1];
            --i;
        }
        intoArray[i] = value;
        return i;
    }

    public static <T extends Comparable<T>> int insertInOrder(T value, T[] intoArray, int size) {
        int i = size;
        while (i > 0 && value.compareTo(intoArray[i - 1]) < 0) {
            intoArray[i] = intoArray[i - 1];
            --i;
        }
        intoArray[i] = value;
        return i;
    }

    /*
     * public static int insertInOrder(Comparable value, Comparable[] intoArray, int
     * size) {
     * int i = size;
     * while(i > 0 && value.compareTo(intoArray[i-1]) < 0) {
     * intoArray[i] = intoArray[i-1];
     * --i;
     * }
     * intoArray[i] = value;
     * return i;
     * }
     */

    public static <T extends Comparable<T>> int insertInOrder(T value, ArrayList<T> intoList) {
        int i = intoList.size();
        intoList.add(value);
        while (i > 0 && value.compareTo(intoList.get(i - 1)) < 0) {
            intoList.set(i, intoList.get(i - 1));
            --i;
        }
        intoList.set(i, value);
        return i;
    }

    public static <T> void swap(T[] array, int x, int y) {
        T temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    // From OpenDSA Data Structures and Algorithms, Chapter 13,
    // https://opendsa-server.cs.vt.edu/OpenDSA/Books/Everything/html/InsertionSort.html
    public static <T extends Comparable<T>> void inssort(T[] A) {
        for (int i = 1; i < A.length; i++) { // Insert i'th record
            for (int j = i; (j > 0) && (A[j].compareTo(A[j - 1]) < 0); j--) {
                swap(A, j, j - 1);
            }
        }
    }


    public static <T extends Comparable<T>> void inssort2(T[] A) {
        for (int i = 1; i < A.length; i++) { // Insert i'th record
            insertInOrder(A[i], A, i);
        }
    }



    // Based upon OpenDSA Data Structures and Algorithms, Chapter 13,
    // https://opendsa-server.cs.vt.edu/OpenDSA/Books/Everything/html/MergesortImpl.html
    // Changes by S Zeil: made it generic, divided into separate functions
    private static <T extends Comparable<T>> void mergesort(T[] A, Object[] temp, int left, int right) {
        if (left == right) {
            return;
        } // List has one record
        int mid = (left + right) / 2; // Select midpoint
        mergesort(A, temp, left, mid); // Mergesort first half
        mergesort(A, temp, mid + 1, right); // Mergesort second half
        merge(A, temp, left, right, mid);
    }

    public static <T extends Comparable<T>> void merge(T[] A, Object[] temp, int left, int right, int mid) {
        // Do the merge operation into temp
        int i1 = left;
        int i2 = mid + 1;
        int curr = left;
        for (; i1 <= mid && i2 <= right; curr++) {
            if (A[i1].compareTo(A[i2]) <= 0) { // Get smaller value
                temp[curr] = A[i1++];
            } else {
                temp[curr] = A[i2++];
            }
        }
        // Exhausted one sublist or the other. Copy the remaining elements from the
        // non-emptied sublist.
        System.arraycopy(A, i1, temp, curr, mid - i1 + 1);
        System.arraycopy(A, i2, temp, curr, right - i2 + 1);

        // Copy merged data from temp back to A
        System.arraycopy(temp, left, A, left, right - left + 1);
    }

    public static <T extends Comparable<T>> void mergesort(T[] A) {
        Object[] temp = new Object[A.length];
        mergesort(A, temp, 0, A.length - 1);
    }


    private static class SimpleListNode {
        public Object data;
        public SimpleListNode next;

        public SimpleListNode(Object data) {
            this.data = data;
            this.next = null;
        }
    }

    private static class SimpleList {
        private SimpleListNode first;
        private SimpleListNode last;
        private int theSize;

        public SimpleList() {
            first = last = null;
            theSize = 0;
        }

        public boolean isEmpty() {return theSize == 0;}

        public void addToEnd(SimpleListNode nd) {
            if (first == null) {
                first = nd;
            }
            if (last != null) {
                last.next = nd;
            }
            last = nd;
            nd.next = null;
            ++theSize;
        }

        public SimpleListNode removeFront() {
            SimpleListNode front = first;
            first = front.next;
            if (first == null) {
                last = null;
            }
            --theSize;
            front.next = null;
            return front;
        }

        public void swap(SimpleList other) {
            SimpleListNode temp = first;
            first = other.first;
            other.first = temp;
            temp = last;
            last = other.last;
            other.last = temp;
            int tmp = theSize;
            theSize = other.theSize;
            other.theSize = tmp;
        }
    }

    // Iterative Merge Sort
    public static <T extends Comparable<T>> void mergesort(List<T> list) {
        SimpleList[] temps = setUpTempLists(list.size());

        SimpleList inHand = new SimpleList();

        mergeDataFromList(list, temps, inHand);

        inHand = mergeAllTempLists(temps);
        copyToList(list, inHand);
    }

    private static SimpleList[] setUpTempLists(int inputSize) {
        // Compute ceil(log_2(inputSize))
        int tempSize = 1;
        int size = 1;
        while (size <= inputSize) {
            ++tempSize;
            size *= 2;
        }
        
        // Allocate and initialize temporary lists
        SimpleList[] temps = new SimpleList[tempSize];
        for (int i = 0; i < tempSize; ++i)
            temps[i] = new SimpleList();
        return temps;
    }


    private static <T extends Comparable<T>> void mergeDataFromList(List<T> list, SimpleList[] temps, SimpleList inHand) {
        for (T data: list) {
            inHand.addToEnd(new SimpleListNode(data));
            int k = 0;
            while (!temps[k].isEmpty()) {
                inHand = merge(temps[k], inHand, (T)null);
                ++k;
            }
            temps[k].swap(inHand);
        }
    }

    private static <T extends Comparable<T>> SimpleList mergeAllTempLists(SimpleList[] temps) {
        SimpleList inHand;
        inHand = temps[0];
        for (int i = 1; i < temps.length; ++i) {
            inHand = merge(inHand, temps[i], (T)null);
        }
        return inHand;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void copyToList(List<T> list, SimpleList inHand) {
        list.clear();
        for (SimpleListNode nd = inHand.first; nd != null; nd = nd.next) {
            list.add((T)nd.data);
        }
    }




    private static <T extends Comparable<T>>
    SimpleList merge(SimpleList list1, SimpleList list2, T t) {
        SimpleList result = new SimpleList();
        while ((!list1.isEmpty()) && (!list2.isEmpty())) {
            @SuppressWarnings("unchecked")
            T t1 = (T)list1.first.data;
            @SuppressWarnings("unchecked")
            T t2 = (T)list2.first.data;
            if (t1.compareTo(t2) <= 0) {
                result.addToEnd(list1.removeFront());
            } else {
                result.addToEnd(list2.removeFront());
            }
        }
        while (!list1.isEmpty()) {
            result.addToEnd(list1.removeFront());
        }
        while (!list2.isEmpty()) {
            result.addToEnd(list2.removeFront());
        }
        return result;
    }

    private static <T extends Comparable<T>> int findpivot(T[] A, int i, int j) {
        return (i + j) / 2;
    }

    private static <T extends Comparable<T>> int partition(T[] A, int left, int right, T pivot) {
        while (left <= right) { // Move bounds inward until they meet
            while (A[left].compareTo(pivot) < 0) {
                left++;
            }
            while ((right >= left) && (A[right].compareTo(pivot) >= 0)) {
                right--;
            }
            if (right > left) {
                swap(A, left, right);
            } // Swap out-of-place values
        }
        return left; // Return first position in right partition
    }

    // From OpenDSA Data Structures and Algorithms, Chapter 13,
    // https://opendsa-server.cs.vt.edu/OpenDSA/Books/Everything/html/Quicksort.html
    private static <T extends Comparable<T>> void quicksort(T[] A, int i, int j) { // Quicksort
        int pivotindex = findpivot(A, i, j); // Pick a pivot
        swap(A, pivotindex, j); // Stick pivot at end
        // k will be the first position in the right subarray
        int k = partition(A, i, j - 1, A[j]);
        swap(A, k, j); // Put pivot in place
        if ((k - i) > 1) {
            quicksort(A, i, k - 1);
        } // Sort left partition
        if ((j - k) > 1) {
            quicksort(A, k + 1, j);
        } // Sort right partition
    }

    public static <T extends Comparable<T>> void quicksort(T[] A) {
        quicksort(A, 0, A.length - 1);
    }

    private static class Range {
        public int left;
        public int right;

        public Range(int left0, int right0) {
            left = left0;
            right = right0;
        }
    }

    public static <T extends Comparable<T>> void quicksort2(T[] A) {
        Stack<Range> stack = new Stack<>();
        stack.push(new Range(0, A.length - 1));
        while (!stack.isEmpty()) {
            Range range = stack.pop();

            int i = range.left;
            int j = range.right;

            int pivotindex = findpivot(A, i, j); // Pick a pivot
            swap(A, pivotindex, j); // Stick pivot at end
            // k will be the first position in the right subarray
            int k = partition(A, i, j - 1, A[j]);
            swap(A, k, j); // Put pivot in place
            if ((k - i) > 1) {
                // quicksort(A, i, k - 1);
                stack.push(new Range(i, k - 1));
            } // Sort left partition
            if ((j - k) > 1) {
                // quicksort(A, k + 1, j);
                stack.push(new Range(k + 1, j));
            } // Sort right partition
        }
    }

}
