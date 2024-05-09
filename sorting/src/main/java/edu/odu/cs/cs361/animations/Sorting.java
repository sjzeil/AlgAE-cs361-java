package edu.odu.cs.cs361.animations;

import java.awt.Color;
import java.util.List;
import java.util.Stack;

import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;//!
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;
import edu.odu.cs.AlgAE.Server.Rendering.CanBeRendered;
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;
import edu.odu.cs.AlgAE.Server.Utilities.ArrayList;
import edu.odu.cs.AlgAE.Server.Utilities.DiscreteInteger;//!
import edu.odu.cs.AlgAE.Server.Utilities.Index;//!
import edu.odu.cs.AlgAE.Server.Utilities.SimpleReference;

import static edu.odu.cs.AlgAE.Server.LocalServer.activate;//!


public class Sorting {

	
/*
* Several sorting routines.
* Arrays are rearranged with smallest item first.
*/


    public static <T> void swap(T[] array, int x, int y) {
        T temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    // From OpenDSA Data Structures and Algorithms, Chapter 13,
    // https://opendsa-server.cs.vt.edu/OpenDSA/Books/Everything/html/InsertionSort.html
    public static <T extends Comparable<T>> void inssort(T[] A) {
        ActivationRecord aRec = activate(Sorting.class);//!
        aRec.refParam("A", A);//!
    	aRec.breakHere("starting insertion sort");//!

        for (int i = 1; i < A.length; i++) { // Insert i'th record
            int lastJ = i; T v = A[i];//!
            Index ii = new Index(i, A);//!
            aRec.var("i", ii).breakHere("Start moving element [i], " + v + " into position.");//!
            aRec.pushScope();//!
            for (int j = i; (j > 0) && (A[j].compareTo(A[j - 1]) < 0); j--) {
                Index jj = new Index(j, A);//!
                aRec.var("j", jj).breakHere("Swap elements " + j + " and " + (j-1));//!
                swap(A, j, j - 1);
                lastJ = j;//!
            }
            aRec.breakHere(v.toString() + " has settled in position " + lastJ);//!
            aRec.popScope();//!
        }
        aRec.breakHere("Completed insertion sort.");//!
    }



/*
* Internal insertion sort routine for subarrays
* that is used by quicksort.
* a is an array of DiscreteInteger.
* left is the left-most index of the subarray.
* right is the right-most index of the subarray.
*/
//!template <typename Comparable>
void insertionSort( ArrayList<DiscreteInteger> a, DiscreteInteger left, DiscreteInteger right )//!
//!void insertionSort( vector<Comparable> & a, int left, int right )
{
	ActivationRecord aRec = activate(getClass());//!
	aRec.param("a","").param("left", left).param("right",right);//!
	for (int i = left.get(); i <= right.get(); ++i) aRec.highlight(a.get(i), Color.blue.brighter().brighter());//!
	aRec.breakHere("begin insertionSort");//!
	
	for( int p = left.get() + 1; p <= right.get(); ++p )
    {
		aRec.var("p", new Index(p, a));//!
		
		int tmp = a.get(p).get();//!
//!        Comparable tmp = std::move( a[ p ] );
        int j = 789;//!
//!        int j;
        aRec.var("j", new Index(j, a));//!
//        aRec.breakHere("look for the position to put tmp");//!
      
        for( j = p; j > left.get() && tmp < a.get( j - 1 ).get(); --j )//!
//!        for( j = p; j > left && tmp < a[ j - 1 ]; --j )
        {
//      	   aRec.breakHere("scan down from j");//!
      	   a.get(j).set(a.get(j-1).get());//!
//!           a[ j ] = std::move( a[ j - 1 ] );
        }
//        aRec.breakHere("Put tmp at a[j]");//!
        a.get(j).set(tmp);//!
//!        a[ j ] = std::move( tmp );
    }
	aRec.breakHere("done sorting");//!
}

/*
* Shellsort, using Shell's (poor) increments.
*/
//!template <typename Comparable>
void shellSort(DiscreteInteger[] a, int n )//!
//!void shellsort( vector<Comparable> & a )
{
	ActivationRecord aRec = activate(getClass());//!
	for (int k = 0; k < n; ++k)//!
		aRec.highlight(a[k], Color.lightGray);//!
	aRec.refParam("a", a).param("n", n);//!
	aRec.breakHere("starting Shell's sort");//!
	
	for(int Gap = n / 2; Gap > 0;Gap = Gap == 2 ? 1 : (int) (Gap / 2.2))//!
//!    for( int gap = a.size( ) / 2; gap > 0; gap /= 2 )
	{
		aRec.pushScope();//!
		aRec.var("Gap", Gap);//!
		aRec.breakHere("Gap has been chosen");//!
		
		for( int i = Gap; i < n; i++ )//!
//!        for( int i = gap; i < a.size( ); ++i )      
        {
			aRec.var("i", new Index(i,a));//!
			for (int j = i; j >= 0; j-=Gap)//!
				aRec.highlight(a[j], Color.green);//!
			aRec.breakHere("'insertion sort' these elements");//!
			
			int Tmp = a[i].get();//!
			aRec.var("Tmp",Tmp);//!
//!            Comparable tmp = std::move( a[ i ] );
			
            int j = i;
            aRec.var("j", new Index(j,a));//!
			aRec.breakHere("where to put Tmp?");//!
			
			
			for(; j >= Gap && Tmp < a[ j - Gap ].get(); j -= Gap)//!
//!            for( ; j >= gap && tmp < a[ j - gap ]; j -= gap )
			{
				aRec.var("j", new Index(j,a));//!
				aRec.breakHere("shift a[j-Gap] up");//!
				a[j].set(a[j - Gap].get());//!
//!                a[ j ] = std::move( a[ j - gap ] );
				aRec.breakHere("drop down Gap positions");//!
			}
			aRec.breakHere("put Tmp in a[j]");//!
			a[j] = new DiscreteInteger(Tmp);//!
//!	        a[ j ] = std::move( tmp );
			for (int k = i; k >= 0; k-=Gap)//!
				aRec.highlight(a[k], Color.lightGray);//!
                           
         }
		 aRec.breakHere("The array is now " + Gap + "-sorted.");//!
		 aRec.popScope();//!
	}
	aRec.breakHere("Finished Shell's sort");//!
}



/*
 * Standard heapsort.
*/
//!template <typename Comparable>
//!void heapsort( vector<Comparable> & a )
//!{
//!    for( int i = a.size( ) / 2 - 1; i >= 0; --i )  /* buildHeap */
//!       percDown( a, i, a.size( ) );
//!    for( int j = a.size( ) - 1; j > 0; --j )
//!    {
//!        std::swap( a[ 0 ], a[ j ] );               /* deleteMax */
//!        percDown( a, 0, j );
//!    }
//!}

/*
 * Internal method for heapsort.
 * i is the index of an item in the heap.
 * Returns the index of the left child.
 */
//!inline int leftChild( int i )
//!{
//!    return 2 * i + 1;
//!}

/*
 * Internal method for heapsort that is used in
 * deleteMax and buildHeap.
 * i is the position from which to percolate down.
 * n is the logical size of the binary heap.
 */
//!template <typename Comparable>
//!void percDown( vector<Comparable> & a, int i, int n )
//!{
//!    int child;
//!    Comparable tmp;

//!    for( tmp = std::move( a[ i ] ); leftChild( i ) < n; i = child )
//!    {
//!        child = leftChild( i );
//!        if( child != n - 1 && a[ child ] < a[ child + 1 ] )
//!            ++child;
//!        if( tmp < a[ child ] )
//!           a[ i ] = std::move( a[ child ] );
//!        else
//!           break;
//!    }
//!    a[ i ] = std::move( tmp );
//!}



    // Based upon OpenDSA Data Structures and Algorithms, Chapter 13,
    // https://opendsa-server.cs.vt.edu/OpenDSA/Books/Everything/html/MergesortImpl.html
    // Changes by S Zeil: made it generic, divided into separate functions
    private static <T extends Comparable<T>> void mergesort(T[] A, Object[] temp, int left, int right) {
        ActivationRecord aRec = activate(Sorting.class);//!
        aRec.param("A", "").param("temp", "").param("left", left).param("right", right);//!
        aRec.clearRenderings();//!
        for (int i = left; i <= right; ++i) aRec.highlight(A[i]); //!
        aRec.breakHere("starting mergesort recursive call on range " + left + ".." + right);//!
        if (left == right) {
            return;
        } // List has one record
        int mid = (left + right) / 2; // Select midpoint
        for (int i = left; i <= mid; ++i) aRec.highlight(A[i], Color.blue); //!
        for (int i = mid+1; i <= right; ++i) aRec.highlight(A[i], Color.yellow); //!
        aRec.var("mid", mid).breakHere("Computed midpoint. Now sort the left half.");//!
        mergesort(A, temp, left, mid); // Mergesort first half
        aRec.breakHere("Now sort the right half.");//!
        mergesort(A, temp, mid + 1, right); // Mergesort second half
        aRec.breakHere("Now merge the two halves.");//!
        merge(A, temp, left, right, mid);
        aRec.breakHere("Merged");//!
    }

    public static <T extends Comparable<T>> void merge(T[] A, Object[] temp, int left, int right, int mid) {
        ActivationRecord aRec = activate(Sorting.class);//!
        aRec.refParam("A", A).refParam("temp", temp).param("left", left).param("right", right).param("mid", mid);//!
        for (int i = left; i <= mid; ++i) aRec.highlight(A[i], Color.blue); //!
        for (int i = mid+1; i <= right; ++i) aRec.highlight(A[i], Color.yellow); //!
        aRec.breakHere("Starting merge");//!
        // Do the merge operation into temp
        int i1 = left;
        int i2 = mid + 1;
        int curr = left;
        Index ii1 = new Index(i1, A);//!
        Index ii2 = new Index(i2,A);//!
        Index iCurr = new Index(curr, temp);//!
        aRec.var("i1", ii1).var("i2", ii2).var("curr", iCurr).breakHere("Ready to start comparing");//!
        for (; i1 <= mid && i2 <= right; curr++) {
            aRec.breakHere("Compare elements " + i1 + " and " + i2);//!
            ii1.set(i1); ii2.set(i2); iCurr.set(curr);//!
            if (A[i1].compareTo(A[i2]) <= 0) { // Get smaller value
                aRec.breakHere("A[i1] is smaller. Copy it to temp;");//!
                temp[curr] = A[i1++];
            } else {
                aRec.breakHere("A[i2] is smaller. Copy it to temp;");//!
                temp[curr] = A[i2++];
            }
            aRec.breakHere("Smaller value has been copied to temp[curr]");//!
        }
        // Exhausted one sublist or the other. Copy the remaining elements from the
        // non-emptied sublist.
        aRec.breakHere("Copy any remaining elements from left half.");//!
        System.arraycopy(A, i1, temp, curr, mid - i1 + 1);
        aRec.breakHere("Copy any remaining elements from right half.");//!
        System.arraycopy(A, i2, temp, curr, right - i2 + 1);

        // Copy merged data from temp back to A
        aRec.breakHere("Copy merged data from temp back to A.");//!
        System.arraycopy(temp, left, A, left, right - left + 1);
    }

    public static <T extends Comparable<T>> void mergesort(T[] A) {
        ActivationRecord aRec = activate(Sorting.class);//!
        aRec.refParam("A", A);//!
        aRec.breakHere("starting mergesort");//!
        Object[] temp = new Object[A.length];
        for(int i = 0; i < A.length; ++i) temp[i] = new DiscreteInteger(0);//!
        aRec.refVar("temp", temp).breakHere("Allocated temporary array");//!
        mergesort(A, temp, 0, A.length - 1);
        aRec.breakHere("Completed mergesort");//!
    }



    // Iterative Merge Sort
    public static <T extends Comparable<T>> void mergesort2(T[] array) {
        ActivationRecord aRec = activate(Sorting.class);//!
        aRec.refParam("list", array).breakHere("Starting iterative mergesort");//!

        ArrayList<SimpleQueue<T>> temps = setUpTempLists(array.length);

        aRec.refVar("temps", temps).breakHere("Temporary lists have been set up");//!

        mergeDataFromList(array, temps);
        aRec.breakHere("All data has been processed from the input list");//!

        SimpleQueue<T> sorted = mergeAllTempLists(temps);
        aRec.refVar("sorted",sorted).breakHere("All temporary lists have been merged");//!
        copyToArray(array, sorted);
        aRec.breakHere("Completed mergesort");//!
    }

    private static <T extends Comparable<T>> 
    ArrayList<SimpleQueue<T>> setUpTempLists(int inputSize) {
        ArrayList<SimpleQueue<T>> results = new ArrayList<>();
        // Compute ceil(log_2(inputSize))
        int size = 1;
        while (size <= inputSize) {
            size *= 2;
            results.add(new SimpleQueue<T>());
        }
        results.setDirection(Directions.Vertical);//!
        return results;
    }



    private static <T extends Comparable<T>>
    void mergeDataFromList(T[] array, ArrayList<SimpleQueue<T>> temps) {
        ActivationRecord aRec = activate(Sorting.class);//!
        aRec.refParam("array", array).refParam("temps", temps).breakHere("starting mergeDataFromList");//!
        for (T data: array) {
            aRec.pushScope();//!
            SimpleQueue<T> inHand = new SimpleQueue<T>();
            aRec.var("data", data).var("inHand",inHand).breakHere("Add next data item from array to inHand");//!
            inHand.add(data);
            int k = 0;
            aRec.var("k", k).breakHere("Begin merging inHand with the temps lists");//!
            while (!temps.get(k).isEmpty()) {
                inHand.swap(merge(temps.get(k), inHand));
                aRec.refParam("inHand", inHand).breakHere("Merged temps[" + k + "] into inHand");//!
                ++k;
            }
            aRec.breakHere("Swap empty temps[" + k + "] with inHand");//!
            temps.set(k, inHand);
            aRec.popScope();//!
            aRec.highlight(data, Color.gray);//!
        }
    }

    private static <T extends Comparable<T>> 
    SimpleQueue<T> mergeAllTempLists(ArrayList<SimpleQueue<T>> temps) {
        ActivationRecord aRec = activate(Sorting.class);//!
        SimpleQueue<T> result = temps.get(0);
        aRec.refVar("result", result).breakHere("Merge each temp list into result");//!
        for (int i = 1; i < temps.size(); ++i) {
            result.swap(merge(result, temps.get(i)));
            aRec.var("i", i).breakHere("Merged temps[" + i + "] into result");//!
        }
        aRec.breakHere("done with mergeAllTempLists");//!
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>>
    void copyToArray(T[] array, SimpleQueue<T> inHand) {
        int i = 0;
        for (T data: inHand) {
            array[i] = data;
            ++i;
        }
    }




    private static <T extends Comparable<T>>
    SimpleQueue<T> merge(SimpleQueue<T> list1, SimpleQueue<T> list2) {
        ActivationRecord aRec = activate(Sorting.class);//!
        aRec.refParam("list1", list1).refParam("list2", list2);//!
        SimpleQueue<T> result = new SimpleQueue<T>();
        aRec.var("result",result).breakHere("merge the lists into result");//!
        while ((!list1.isEmpty()) && (!list2.isEmpty())) {
            @SuppressWarnings("unchecked")
            T t1 = list1.peek();
            @SuppressWarnings("unchecked")
            T t2 = list2.peek();
            aRec.var("t1",  t1).var("t2", t2).breakHere("Compare t1 and t2");//!
            if (t1.compareTo(t2) <= 0) {
                result.add(list1.remove());
                aRec.breakHere("t1 was smaller. Added to result");//!
            } else {
                result.add(list2.remove());
                aRec.breakHere("t2 was smaller. Added to result");//!
            }
        }
        aRec.breakHere("Copy any data remaining in list1");//!
        while (!list1.isEmpty()) {
            result.add(list1.remove());
        }
        aRec.breakHere("Copy any data remaining in list2");//!
        while (!list2.isEmpty()) {
            result.add(list2.remove());
        }
        aRec.breakHere("done with merge");//!
        return result;
    }

    private static <T extends Comparable<T>> int findpivot(T[] A, int i, int j) {
        return (i + j) / 2;
    }

    public static <T extends Comparable<T>>
    int partition(T[] A, int left, int right, T pivot) {
        ActivationRecord aRec = activate(Sorting.class);//!
        Index iLeft = new Index(left, A);//!
        Index iRight = new Index(right, A);//!
        aRec.refParam("A", A).param("left", left).param("right", right).param("pivot", pivot);//!
        aRec.var("left", iLeft).var("right", iRight);//!
        aRec.breakHere("Starting partition");//!
        while (left <= right) { // Move bounds inward until they meet
            aRec.breakHere("Move bounds inward");//!
            while (A[left].compareTo(pivot) < 0) {
                aRec.breakHere("Move left up");//!
                left++;
                iLeft.set(left);//!
            }
            while ((right >= left) && (A[right].compareTo(pivot) >= 0)) {
                aRec.breakHere("Move right down");//!
                right--;
                iRight.set(right);//!
            }
            if (right > left) {
                aRec.breakHere("Swap left and right values");//!
                swap(A, left, right);
            } // Swap out-of-place values
            aRec.breakHere("Have left and right met?");//!
        }
        aRec.breakHere("Done with partition");//!
        return left; // Return first position in right partition
    }

    public static <T extends Comparable<T>>
    int partition0(T[] A, int left, int right, T pivot) {
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
        ActivationRecord aRec = activate(Sorting.class);//!
        aRec.refParam("A", A).param("i", i).param("j", j);//!
        for (int k = i; k <= j; ++k) aRec.highlight(A[k]); //!
        aRec.breakHere("Starting quicksort recursion");//!
        if (i < j + 1) {
            int pivotindex = findpivot(A, i, j); // Pick a pivot
            aRec.var("pivotindex", pivotindex).breakHere("selected pivot");//!
            swap(A, pivotindex, j); // Stick pivot at end
            aRec.breakHere("Moved pivot to end");//!
            // k will be the first position in the right subarray
            int k = partition0(A, i, j - 1, A[j]);//!            int k = partition(A, i, j - 1, A[j]);
            aRec.clearRenderings();//!
            for (int m = i; m < k; ++m) aRec.highlight(A[m], Color.cyan); //!
            for (int m = k+1; m <= j; ++m) aRec.highlight(A[m], Color.green); //!
            aRec.var("k", k).breakHere("partitioned");//!
            swap(A, k, j); // Put pivot in place
            aRec.breakHere("Ready to recurse on the left part.");//!
            quicksort(A, i, k - 1); // Sort left partition
            aRec.clearRenderings();//!
            for (int m = i; m < k; ++m) aRec.highlight(A[m], Color.cyan); //!
            for (int m = k+1; m <= j; ++m) aRec.highlight(A[m], Color.green); //!
            aRec.breakHere("Ready to recurse on the right part.");//!
            quicksort(A, k + 1, j); // Sort right partition
            aRec.clearRenderings();//!
            for (int m = i; m <= j; ++m) aRec.highlight(A[m]); //!
            aRec.breakHere("Returned from both recursions");//!
        }
    }

    public static <T extends Comparable<T>> void quicksort(T[] A) {
        ActivationRecord aRec = activate(Sorting.class);//!
        aRec.refParam("A", A).breakHere("starting quicksort");//!
        quicksort(A, 0, A.length - 1);
        aRec.breakHere("done with quicksort");//!
    }




    private static class Range
    implements Renderer<Range>, CanBeRendered<Range> //!
    {
        public int left;
        public int right;

        public Range(int left0, int right0) {
            left = left0;
            right = right0;
        }

        @Override
        public Renderer<Range> getRenderer() {
            return this;
        }

        @Override
        public Boolean getClosedOnConnections() {
            return false;
        }

        @Override
        public Color getColor(Range arg0) {
            return null;
        }

        @Override
        public List<Component> getComponents(Range arg0) {
            return new ArrayList<>();
        }

        @Override
        public List<Connection> getConnections(Range arg0) {
            return new ArrayList<>();
        }

        @Override
        public Directions getDirection() {
            return Directions.Horizontal;
        }

        @Override
        public Double getSpacing() {
            return 1.0;
        }

        @Override
        public String getValue(Range r) {
            return "" + left + "," + right;
        }
    }

    public static <T extends Comparable<T>> void quicksort2(T[] A) {
        ActivationRecord aRec = activate(Sorting.class);//!
        aRec.refParam("A", A).breakHere("Starting iterative quicksort");;//!
        Stack<Range> stack = new Stack<>();
        stack.push(new Range(0, A.length - 1));
        aRec.var("stack",stack).breakHere("Stack has been initialized");//!
        while (!stack.isEmpty()) {
            Range range = stack.pop();

            int i = range.left;
            int j = range.right;
            aRec.clearRenderings(); for (int m = 0; m < A.length; ++m) if (m < i || m > j) aRec.highlight(A[m], Color.gray);//!
            aRec.var("i", i).var("j", j).breakHere("Work on range from " + i + " to " + j);//!

            if (i < j + 1) {
                int pivotindex = findpivot(A, i, j); // Pick a pivot
                aRec.var("pivotindex", pivotindex).breakHere("pivot selected");//!
                swap(A, pivotindex, j); // Stick pivot at end
                // k will be the first position in the right subarray
                int k = partition0(A, i, j - 1, A[j]);//!                int k = partition(A, i, j - 1, A[j]);
                for (int m = i; m < k; ++m) aRec.highlight(A[m], Color.cyan);//!
                for (int m = k+1; m <= j; ++m) aRec.highlight(A[m], Color.green);//!
                aRec.var("k", k).breakHere("partitioned");//!
                swap(A, k, j); // Put pivot in place
                // quicksort(A, i, k - 1);
                stack.push(new Range(i, k - 1)); // Sort left partition
                aRec.breakHere("pushed left subrange");//!
                stack.push(new Range(k + 1, j)); // Sort right partition
                aRec.breakHere("pushed right subrange");//!
            }
        }
    }

        
}
