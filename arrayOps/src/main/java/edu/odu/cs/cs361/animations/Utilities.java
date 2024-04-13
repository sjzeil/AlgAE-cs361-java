package edu.odu.cs.cs361.animations;//!

import static edu.odu.cs.AlgAE.Server.LocalServer.activate;//!
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;//!
import edu.odu.cs.AlgAE.Server.Utilities.DiscreteInteger;//!
import edu.odu.cs.AlgAE.Server.Utilities.Index;//!

public class Utilities {

    /*
     *
     * Assume the elements of the array are already in order
     * Find the position where value could be added to keep
     * everything in order, and insert it there.
     * Return the position where it was inserted
     * - Assumes that we have a separate integer (size) indicating how
     * many elements are in the array
     * - and that the "true" size of the array is at least one larger
     * than the current value of that counter
     *
     * @param value value to add into the array
     * 
     * @param intoArray array into which to add an element
     * 
     * @param size number of data elements in hte array. Must be less than
     * the number of elements allocated for the array. Incremented
     * upon output from this function.
     * 
     * @return the position where the element was added
     */
    public static Index insertInOrder(int value, DiscreteInteger[] intoArray, int size) {//!
        //!public static int insertInOrder (int value, int[] intoArray, int size) {
        ActivationRecord aRec = activate(Utilities.class);//!
        aRec.param("value", value).refParam("intoArray", intoArray).param("size", size)
                .breakHere("starting addInOrder");//!
        //! int i = size;
        Index i = new Index(size, intoArray);//!
        aRec.var("i", i).breakHere("start at high end of the data");//!
        //! while(i > 0 && value < intoArray[i-1]) {
        while (i.get() >= 0 && value < intoArray[i.get() - 1].get()) {//!
            aRec.breakHere("in loop: ready to move an element up");//!
            //! intoArray[i] = intoArray[i-1];
            intoArray[i.get()] = intoArray[i.get() - 1];//!
            aRec.breakHere("in loop: Moved the element");//!
            //! --i;
            i.set(i.get() - 1);//!
            aRec.breakHere("in loop: decremented");//!
        }
        // Insert the new value
        aRec.breakHere("exited loop: insert the new value");//!
        //! intoArray[i] = value;
        intoArray[i.get()] = new DiscreteInteger(value);//!
        aRec.breakHere("Inserted new value");//!
        //! return i;
        return new Index(i.get(), intoArray);//!
    }

    public static <E, T> void arraycopy(E[] src, int srcPos,
            T[] dest, int destPos, int length) {
        ActivationRecord aRec = activate(Utilities.class);//!
        aRec.refParam("sec", src).param("srcPos", srcPos).refParam("dest", dest)
                .param("destPos", destPos).param("length", length)
                .breakHere("starting arraycopy");//!
        if ((Object) src != (Object) dest || srcPos >= destPos) {
            aRec.breakHere("Copy forwards");//!
            for (int k = 0; k < length; ++k) {
                aRec.var("k", k).breakHere("Copy k'th item");//!
                Object toCopy = src[srcPos + k];
                dest[destPos + k] = (T) toCopy;
            }
            aRec.breakHere("done");//!
        } else {
            aRec.breakHere("Copy backwards");//!
            for (int k = length - 1; k >= 0; --k) {
                aRec.var("k", k).breakHere("Copy k'th item");//!
                Object toCopy = src[srcPos + k];
                dest[destPos + k] = (T) toCopy;
            }
            aRec.breakHere("done");//!
        }
    }

    // Return the position of an element in array A with value K.
    // If K is not in A, return A.length.
    static int sequential(DiscreteInteger[] A, DiscreteInteger K) {//! static int sequential(int[] A, int K) {
        ActivationRecord aRec = activate(Utilities.class);//!
        aRec.refParam("A", A).param("K", K)//!
                .breakHere("starting sequential");//!
        for (int i = 0; i < A.length; i++) { // For each element
            Index ii = new Index(i, A);//!
            aRec.var("i", ii).breakHere("Look at element [i]");//!
            if (A[i].get() == K.get()) {//!
                //! if (A[i] == K) { // if we found it
                aRec.breakHere("Found it!");//!
                return i; // return this position
            }
        }
        aRec.breakHere("Could not find it.");//!
        return A.length; // Otherwise, return the array length
    }

    // Return the position of an element in a sorted array A with value K.
    // If K is not in A, return A.length.
    static int orderedSequential(DiscreteInteger[] A, DiscreteInteger K) {//!    static int orderedSequential(int[] A, int K) {
        ActivationRecord aRec = activate(Utilities.class);//!
        aRec.refParam("A", A).param("K", K)//!
                .breakHere("starting orderedSequential");//!
        for (int i = 0; i < A.length; i++) {
            Index ii = new Index(i, A);//!
            aRec.var("i", ii).breakHere("Look at element [i]");//!
            if (A[i].get() == K.get()) {//!            if (A[i] == K) {
                aRec.breakHere("Found it!");//!
                return i;
            } else if (A[i].get() > K.get()) {//!            } else if (A[i] > K) {
                aRec.breakHere("It must not be in here.");//!
                return A.length;
            }
        }
        aRec.breakHere("Could not find it.");//!
        return A.length;
    }

    /*
     * Search an ordered array for a given value, returning the index where
     * found or -1 if not found.
     * 
     * @param list the array to be searched. Must be ordered.
     * 
     * @param listLength the number of data elements in the array
     * 
     * @param searchItem the value to search for
     * 
     * @return the position at which value was found, or -1 if not found
     */
    //!template <typename Comparable>
    //!int seqOrderedSearch(const Comparable list[], int listLength, Comparable
    // searchItem)
    public Index seqOrderedSearch(DiscreteInteger list[], int listLength, int searchItem)//!
    {
        ActivationRecord aRec = activate(Utilities.class);//!
        aRec.refParam("list", list).param("listLength", listLength).param("searchItem", searchItem)
                .breakHere("starting seqOrderedSearch");//!
        //! int loc = 0;
        Index loc = new Index(0, list);//!

        aRec.var("loc", loc).breakHere("start searching from the beginning");//!
        //! while (loc < listLength && list[loc] < searchItem)
        while (loc.get() < listLength && list[loc.get()].get() < searchItem)//!
        {
            aRec.breakHere("move forward");//!
            //! ++loc;
            loc.set(loc.get() + 1);//!
        }
        aRec.breakHere("Out of the loop: did we find it?");//!
        //! if (loc < listLength && list[loc] == searchItem)
        if (loc.get() < listLength && list[loc.get()].equals(searchItem)) { //!
            aRec.breakHere("Found It! Return " + loc.get());//!
            return loc;
        } //!
        else {//!
            aRec.breakHere("Could not find it. Return -1");//!
            //! return -1;
            return new Index(-1, list);//!
        } //!
    }

    /*
     * Removes an element from the indicated position in the array, moving
     * all elements in higher positions down one to fill in the gap.
     * 
     * @param array array from which to remove an element
     * 
     * @param size number of data elements in the array. Decremented
     * upon output from this function.
     * 
     * @param index position from which to remove the element. Must be < size
     */

    //!template <typename T>
    //!void removeElement (T* array, int& size, int index)
    public void removeElement(DiscreteInteger[] array, int size, Index index)//!
    {
        ActivationRecord aRec = activate(Utilities.class);//!
        aRec.refParam("array", array).refParam("size", size).param("index", index).breakHere("starting removeElement");//!
        if (index.get() < 0 || index.get() >= array.length) { //!
            aRec.breakHere("index is out of bounds - program may crash");//!
            return; //!
        } //!
          //! int toBeMoved = index + 1;
        Index toBeMoved = new Index(index.get() + 1, array); //!
        aRec.var("toBeMoved", toBeMoved).breakHere("start above index");//!
        //! while (toBeMoved < size) {
        while (toBeMoved.get() < size) {//!
            aRec.breakHere("move an element down");//!
            //! array[toBeMoved-1] = array[toBeMoved];
            array[toBeMoved.get() - 1] = array[toBeMoved.get()]; //!
            aRec.breakHere("moved");//!
            //! ++toBeMoved;
            toBeMoved.set(toBeMoved.get() + 1);//!
        }
        aRec.breakHere("Done moving elements");//!
        //! --size;
        size = size - 1;//!
    }

    public static int binarySearch(DiscreteInteger[] A, int K) {//!    public static int binarySearch(int[] A, int K) {
        ActivationRecord aRec = activate(Utilities.class);//!
        aRec.refParam("A", A).param("K", K);//!
        aRec.breakHere("starting binarySearch");//!
        int low = 0;
        int high = A.length - 1;
        aRec.var("low", new Index(low, A)).var("high", new Index(high, A));//!
        aRec.breakHere("start the loop");//!
        while (low <= high) {
          for (int i = low; i <= high; i++) aRec.highlight(A[i]); //!
          int mid = (low + high) / 2;
          aRec.var("mid", new Index(mid, A)).breakHere("Examine middle element");//!
          if (A[mid].get() < K) {//!          if (A[mid] < K) {
            aRec.breakHere("middle value is too low");//!
            low = mid + 1;
            aRec.var("low", new Index(low, A));//!
          } else if (A[mid].get() > K) {//!          } else if (A[mid] > K) {
            aRec.breakHere("middle value is too high");//!
            high = mid - 1;
            aRec.var("high", new Index(high, A));//!
          } else {
            aRec.breakHere("Found it!");//!
            return mid;
          }
          aRec.clearRenderings();//!
        }
        aRec.breakHere("Could not find it!");//!
        return -1;
      }

}