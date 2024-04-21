package edu.odu.cs.cs361.animations;//!

import java.awt.Color;//!

import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;//!
import edu.odu.cs.AlgAE.Server.Utilities.ArrayList;
import edu.odu.cs.AlgAE.Server.Utilities.DiscreteInteger;//!
import edu.odu.cs.AlgAE.Server.Utilities.Index;//!
import static edu.odu.cs.AlgAE.Server.LocalServer.activate;//!


public class Sorting {//!

//!#ifndef SORT_H
//!#define SORT_H
	
/*
* Several sorting routines.
* Arrays are rearranged with smallest item first.
*/

//!#include <vector>
//!#include <functional>
//!using namespace std;


/*
* Simple insertion sort.
*/
//!template <typename Comparable>xxx
public void insertionSort (ArrayList<DiscreteInteger> v, int n)//!
//!void insertionSort( vector<Comparable> & a )
{
	ActivationRecord aRec = activate(getClass());//!
	aRec.refParam("a", v);//!
	v.pushIndices();
	aRec.breakHere("starting insertion sort");//!
//!	
	DiscreteInteger tmp = new DiscreteInteger(-97);//!
//!	
    DiscreteInteger p = new DiscreteInteger(-1);//!
    aRec.var("p", p);//!
    v.indexedBy(p, "p");//!
//!		
	for(int p0 = 1; p0< n; p0++)//!
//!    for( int p = 1; p < a.size( ); ++p )
	{
		    p.set(p0);//!
			aRec.breakHere("Where should we put a[p]?");//!
		    tmp.set(v.get(p0));//!
//!           Comparable tmp = std::move( a[ p ] );
		    aRec.var("tmp", tmp);//!
		    
	        DiscreteInteger j = new DiscreteInteger(468);//!
	        v.indexedBy(j, "j");//!
//!           int j;
	        aRec.var("j", j);//!

			aRec.breakHere("scan down from j");//!   
			j.set(p0);//!
			int j0 = p0;//!
	        for(j0 = p0; j0 > 0 && tmp.get() < v.get(j0-1).get(); j0--)//!
//!           for( j = p; j > 0 && tmp < a[ j - 1 ]; --j )
	        {
	        	  j.set(j0);//!
	        	  aRec.breakHere("Not there yet: shift a[j-1] up");//!
	        	  v.get(j0).set(v.get(j0-1).get());//!
//!                 a[ j ] = std::move( a[ j - 1 ] );
	        	  aRec.breakHere("and continue scanning");//!
	        }
	        j.set(j0);//!
		    aRec.breakHere("Put the tmp at a[j]");//!
	        v.get(j0).set(tmp.get());//!
//!           a[ j ] = std::move( tmp );
	        v.removeIndex("j");//!
	    }
	    aRec.breakHere("done sorting");//!
	    v.popIndices();//!
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



/*
* Internal method that makes recursive calls.
* a is an array of DiscreteInteger
* tmpA is an array to place the merged result.
* left is the left-most index of the subarray.
* right is the right-most index of the subarray.
*/
//!template <typename Comparable>
void mergeSort (ArrayList<DiscreteInteger> a, ArrayList<DiscreteInteger> tmpArray, int left, int right)//!
//!void mergeSort( vector<Comparable> & a,vector<Comparable> & tmpArray, int left, int right )
{    
	ActivationRecord aRec = activate(getClass());//!
	DiscreteInteger left0 = new DiscreteInteger(left);
	DiscreteInteger right0 = new DiscreteInteger(right);
	aRec.refParam("a", a).refParam("tmpArray",tmpArray).param("left", left0).param("right", right0);//!
	a.pushIndices();
	a.indexedBy(left0, "left");//!
	a.indexedBy(right0, "right");//!
	aRec.breakHere("starting mergeSort");//!
	if( left < right )
    {
//!
		int center = ( left + right ) / 2;
      
		DiscreteInteger center0 = new DiscreteInteger(center);//!
		a.indexedBy(center0, "center");//!
		aRec.var("center", center);//!
		aRec.breakHere("sort to left of center");//!
        mergeSort( a, tmpArray, left, center );
        aRec.breakHere("sort to right of center");//!
        mergeSort( a, tmpArray, center + 1, right );
        aRec.breakHere("merge sorted subarrays");//!
        merge( a, tmpArray, left, center + 1, right );
    }
	a.popIndices();//!
}

/*
* Mergesort algorithm (driver).
*/
//!template <typename Comparable>
void mergeSort( ArrayList<DiscreteInteger> a, int length )//!
//!void mergeSort( vector<Comparable> & a )
{
	ActivationRecord aRec = activate(getClass());//!
	aRec.refParam("a", a);//!
	aRec.breakHere("starting mergeSort");//!
//!	
	ArrayList<DiscreteInteger> tmpArray = new ArrayList<DiscreteInteger>();//!
	for (int i = 0; i < a.size(); ++i) tmpArray.add(new DiscreteInteger(-99));//!
	aRec.refVar("tmpArray",tmpArray);//!
	aRec.breakHere("allocated temporary array");//!
//!     vector<Comparable> tmpArray( a.size( ) );
     mergeSort( a, tmpArray, 0, length-1 );
}



/*
 * Internal method that merges two sorted halves of a subarray.
 * a is an array of Comparable items.
 * tmpArray is an array to place the merged result.
 * leftPos is the left-most index of the subarray.
 * rightPos is the index of the start of the second half.
 * rightEnd is the right-most index of the subarray.
 */
//!template <typename Comparable>
void merge(ArrayList<DiscreteInteger> a,  ArrayList<DiscreteInteger> tmpArray, int leftPos0, int rightPos0, int rightEnd0){//!
//!void merge( vector<Comparable> & a, vector<Comparable> & tmpArray,int leftPos, int rightPos, int rightEnd ){
    DiscreteInteger leftPos = new DiscreteInteger(leftPos0);//!
    DiscreteInteger rightPos = new DiscreteInteger(rightPos0);//!
    DiscreteInteger rightEnd = new DiscreteInteger(rightEnd0);//!
	ActivationRecord aRec = activate(getClass());//!
	aRec.param("a", "").refParam("tmpArray", tmpArray).param("leftPos", leftPos).param("rightPos", rightPos).param("rightEnd", rightEnd);//!
	aRec.breakHere("starting merge");//!
	a.pushIndices();//!
	tmpArray.pushIndices();//!
	a.indexedBy(leftPos, "leftPos");//!
	a.indexedBy(rightPos, "rightPos");//!
	a.indexedBy(rightEnd, "rightEnd");//!
	DiscreteInteger leftEnd = new DiscreteInteger(rightPos0 - 1);//!
	a.indexedBy(rightEnd, "leftEnd");//!
//!    int leftEnd = rightPos - 1;
    DiscreteInteger tmpPos = new DiscreteInteger(leftPos0);//!
    tmpArray.indexedBy(tmpPos, "tmpPos");//!
//!    int tmpPos = leftPos;
    int numElements = rightEnd.get() - leftPos.get() + 1;//!
//!    int numElements = rightEnd - leftPos + 1;
//!    
	aRec.var("leftEnd", leftEnd).var("tmpPos", tmpPos).var("numElements", numElements);//!

	for (int i = leftPos.get(); i < rightPos.get(); ++i)//!
		aRec.highlight(a.get(i), Color.yellow);//!
	for (int i = rightPos.get(); i < Math.min(rightEnd.get()+1, a.size()); ++i)//!
		aRec.highlight(a.get(i), Color.blue);//!
	aRec.breakHere("leftPos and rightPos point to start of sublists");//!
	
    // Main loop
	while( leftPos.get() <= leftEnd.get() && rightPos.get() <= rightEnd.get() )//!
//!    while( leftPos <= leftEnd && rightPos <= rightEnd )
	{
		aRec.breakHere("choose smaller of a[leftPos] and a[leftPos]");//!
        if(a.get(leftPos).get() <= a.get( rightPos).get())//!
//!        if( a[ leftPos ] <= a[ rightPos ] )
        {
        	aRec.breakHere("add a[leftPos++] to tmpArray");//!
        	tmpArray.get(tmpPos).set(a.get(leftPos));tmpPos.incr();leftPos.incr();//!
        	aRec.highlight(tmpArray.get(tmpPos.get()-1));//!
//!            tmpArray[ tmpPos++ ] = std::move( a[ leftPos++ ] );
        	aRec.highlight(a.get(leftPos.get()-1), Color.gray);//!
        }
        else
        {
        	aRec.breakHere("add a[rightPos++] to tmpArrayfdfd");//!
        	tmpArray.get(tmpPos).set(a.get(rightPos)); tmpPos.incr(); rightPos.incr();//!
        	aRec.highlight(tmpArray.get(tmpPos.get()-1));//!
//!            tmpArray[ tmpPos++ ] = std::move( a[ rightPos++ ] );
        	aRec.highlight(a.get(rightPos.get()-1), Color.gray);//!
        }
        aRec.breakHere("repeat if both sublists are non-empty");//!
	}//!
	
	aRec.breakHere("one or both sublists is empty");//!

    
//!	while( leftPos <= leftEnd )    // Copy rest of first half
	while( leftPos.get() <= leftEnd.get() )    // Copy rest of first half//!
    {
		aRec.breakHere("copy remaining left element");//!
		tmpArray.get(tmpPos).set(a.get( leftPos)); tmpPos.incr();leftPos.incr();//!
		aRec.highlight(tmpArray.get(tmpPos.get()-1));//!
//!        tmpArray[ tmpPos++ ] = std::move( a[ leftPos++ ] );
		aRec.highlight(a.get(leftPos.get()-1), Color.gray);//!
    }
    
	aRec.breakHere("all elements have been copied from the left sublist");//!
    while( rightPos.get() <= rightEnd.get() )  // Copy rest of right half//!
//!    while( rightPos <= rightEnd )  // Copy rest of right half
    {
    	aRec.breakHere("copy remaining right element");//!
		tmpArray.get(tmpPos).set(a.get(rightPos)); tmpPos.incr();rightPos.incr();//!
    	aRec.highlight(tmpArray.get(tmpPos.get()-1));//!
//!        tmpArray[ tmpPos++ ] = std::move( a[ rightPos++ ] );
    	aRec.highlight(a.get(rightPos.get()-1), Color.gray);//!
    }
        
    aRec.breakHere("all elements have been copied from the right sublist");//!
//!    
    // Copy tmpArray back
    aRec.pushScope();//!
    DiscreteInteger i = new DiscreteInteger();//!
    a.indexedBy(i, "i");//!
    for( int i0 = 0; i0 < numElements; ++i0, rightEnd.decr() )//!
//!    for( int i = 0; i < numElements; ++i, --rightEnd )
    {
    	i.set(i0);//!
    	aRec.var("i", i);//!
		aRec.breakHere("copy temp element back to original vector");//!
    	a.get(rightEnd).set(tmpArray.get(rightEnd));//!
//!        a[ rightEnd ] = std::move( tmpArray[ rightEnd ] );
        
    }
    aRec.popScope();//!
	aRec.breakHere("finished merge");//!
	a.popIndices();//!
	tmpArray.popIndices();//!
}



/*
* Return median of left, center, and right.
* Order these and hide the pivot.
*/
//!template <typename Comparable>
final DiscreteInteger median3( ArrayList<DiscreteInteger> a, DiscreteInteger left, DiscreteInteger right )//!
//!const Comparable & median3( vector<Comparable> & a, int left, int right )
{
	ActivationRecord aRec = activate(getClass());//!
	aRec.param("a","").param("left", left).param("right",right);//!

	DiscreteInteger center = new DiscreteInteger( (left.get() + right.get() ) / 2);
//!    int center = ( left + right ) / 2;
	aRec.var("center", center);//!
	a.pushIndices();//!
	a.indexedBy(left, "left");//!
	a.indexedBy(right, "right");//!
	a.indexedBy(center, "center");//!
 
	if( a.get( center ).get() < a.get( left ).get() ) //!
//!    if( a[ center ] < a[ left ] )
	{
		aRec.breakHere("swap  a[ left ] and  a[ center ]");//!
//!        std::swap( a[ left ], a[ center ] );
		a.get( left ).swap( a.get(center));//!
		aRec.breakHere("done with swapping left and center");//!
		
	}
	if( a.get( right ).get() < a.get( left ).get() )//!
//!    if( a[ right ] < a[ left ] )
	{
		aRec.breakHere("swap  a[ right ] and  a[ left ]");//!
//!        std::swap( a[ left ], a[ right ] );
		a.get( left ).swap( a.get(right) );//!
		aRec.breakHere("done with swapping right and left");//!
	}
	if( a.get( right ).get() < a.get( center ).get() )//!
//!    if( a[ right ] < a[ center ] )
	{
		aRec.breakHere("swap  a[ right ] and  a[ center ]");//!
//!        std::swap( a[ right ], a[ center ] );
		a.get( right ).swap(a.get( center ));//!
		aRec.breakHere("done with swapping right and center");//!
	}

 // Place pivot at position right - 1
	aRec.breakHere("Place pivot at position right - 1");//!
	aRec.breakHere("swap  a[ center ] and  a[ right - 1 ]");//!
//!    std::swap( a[ center ], a[ right - 1 ] );
	a.get( center ).swap( a.get( right.get() - 1 ));//!
	aRec.breakHere("done with swapping center and right - 1");//!
 
    return a.get( right.get() - 1 );//!
//!    return a[ right - 1 ];
}





/*
* Internal quicksort method that makes recursive calls.
* Uses median-of-three partitioning and a cutoff of 10.
* a is an array of DiscreteInteger
* left is the left-most index of the subarray.
* right is the right-most index of the subarray.
*/
//!template <typename Comparable>
void quicksort(ArrayList<DiscreteInteger> a, int left0, int right0) {//!
//!void quicksort( vector<Comparable> & a, int left, int right ) {
	ActivationRecord aRec = activate(getClass());//!
	DiscreteInteger left = new DiscreteInteger(left0);//!
	DiscreteInteger right = new DiscreteInteger(right0);//!
	a.pushIndices();//!
	a.indexedBy(left, "left");//!
	a.indexedBy(right, "right");//!
	aRec.refParam("a", a).param("left", left).param("right",right);//!
	for (int i = left0; i <= right0; ++i)
		aRec.highlight(a.get(i), Color.blue.brighter().brighter());
	
	if( left.get() + 4 <= right.get() )//!
//!	if( left + 4 <= right )
    {
		aRec.breakHere("Choose the pivot.");//!
		DiscreteInteger pivot = median3( a, left, right );//!
//!        const Comparable & pivot = median3( a, left, right );
		aRec.var("pivot", pivot);//!
        // Begin partitioning
		aRec.breakHere("begin partitioning");//!
        DiscreteInteger i = new DiscreteInteger(left.get());//!
        DiscreteInteger j = new DiscreteInteger(right.get() - 1);//!
        a.indexedBy(i, "i");//!
        a.indexedBy(j, "j");//!
//!        int i = left, j = right - 1;
        aRec.var("i", i).var("j", j);//!
        aRec.breakHere("look for elements to swap");//!
        for( ; ; )
        {
        	i.incr();//!
        	while( a.get(i).get() < pivot.get() ) {i.incr();//! 
        		aRec.breakHere("scan up from the left");//!
//!            while( a[ ++i ] < pivot ) { }
        	}//!
        	j.decr();//!
            while( pivot.get() < a.get(j).get() ) {j.decr();//!
        	aRec.breakHere("scan down from the right");//!
//!            while( pivot < a[ --j ] ) { }
            }//!
            aRec.breakHere("Either we are ready to swap or we are done pivoting");//!
            if( i.get() < j.get() )//!
//!            if( i < j )
            {
            	aRec.breakHere("Swap the out-of-position elements");//!
//!                std::swap( a[ i ], a[ j ] );
            	a.get(i).swap(a.get(j));//!
            }
            else
                break;
        }

        aRec.breakHere("Restore pivot");//!
//!        std::swap( a[ i ], a[ right - 1 ] ); // Restore pivot
    	a.get(i).swap(a.get(right.get() - 1));//!
        
    	aRec.breakHere("Sort small elements");//!
    	quicksort( a, left.get(), i.get() - 1 );//!
//!    	quicksort( a, left, i - 1 );        // Sort small elements
    	
    	aRec.breakHere("Sort large elements");//!
        quicksort( a, i.get() + 1, right.get() );//!
//!        quicksort( a, i + 1, right );       // Sort large elements
    }
    else  
    {// Do an insertion sort on the subarray
    	aRec.breakHere("Do an insertion sort on the subarray");//!
        insertionSort( a, left, right );
    }
	a.popIndices();//!
}






/*
*  Quicksort algorithm (driver).
*/
//!template <typename Comparable>
void quicksort( ArrayList<DiscreteInteger> a, int size)//!
//!void quicksort( vector<Comparable> & a )
{
	ActivationRecord aRec = activate(getClass());//!
	aRec.refParam("a",a);//!
	aRec.breakHere("start quicksorting");//!
	
	quicksort( a, 0, size - 1 );//!
//!    quicksort( a, 0, a.size( ) - 1 );
}


/*
 * Internal selection method that makes recursive calls.
 * Uses median-of-three partitioning and a cutoff of 10.
 * Places the kth smallest item in a[k-1].
 * a is an array of Comparable items.
 * left is the left-most index of the subarray.
 * right is the right-most index of the subarray.
 * k is the desired rank (1 is minimum) in the entire array.
 */
//!template <typename Comparable>
//!void quickSelect( vector<Comparable> & a, int left, int right, int k )
//!{
//!    if( left + 10 <= right )
//!    {
//!        const Comparable & pivot = median3( a, left, right );

            // Begin partitioning
//!        int i = left, j = right - 1;
//!        for( ; ; )
//!        {
//!            while( a[ ++i ] < pivot ) { }
//!            while( pivot < a[ --j ] ) { }
//!            if( i < j )
//!                std::swap( a[ i ], a[ j ] );
//!            else
//!                break;
//!        }

//!        std::swap( a[ i ], a[ right - 1 ] );  // Restore pivot

            // Recurse; only this part changes
//!        if( k <= i )
//!            quickSelect( a, left, i - 1, k );
//!        else if( k > i + 1 )
//!            quickSelect( a, i + 1, right, k );
//!    }
//!    else  // Do an insertion sort on the subarray
//!        insertionSort( a, left, right );
//!}

/*
 * Quick selection algorithm.
 * Places the kth smallest item in a[k-1].
 * a is an array of Comparable items.
 * k is the desired rank (1 is minimum) in the entire array.
 */
//!template <typename Comparable>
//!void quickSelect( vector<Comparable> & a, int k )
//!{
//!    quickSelect( a, 0, a.size( ) - 1, k );
//!}


//!template <typename Comparable>
//!void SORT( vector<Comparable> & items )
//!{
//!    if( items.size( ) > 1 )
//!    {
//!        vector<Comparable> smaller;
//!        vector<Comparable> same;
//!        vector<Comparable> larger;
        
//!        auto chosenItem = items[ items.size( ) / 2 ];
        
//!        for( auto & i : items )
//!        {
//!            if( i < chosenItem )
//!                smaller.push_back( std::move( i ) );
//!            else if( chosenItem < i )
//!                larger.push_back( std::move( i ) );
//!            else
//!                same.push_back( std::move( i ) );
//!        }
        
//!        SORT( smaller );     // Recursive call!
//!        SORT( larger );      // Recursive call!
        
//!        std::move( begin( smaller ), end( smaller ), begin( items ) );
//!        std::move( begin( same ), end( same ), begin( items ) + smaller.size( ) );
//!        std::move( begin( larger ), end( larger ), end( items ) - larger.size( ) );

/*
        items.clear( );
        items.insert( end( items ), begin( smaller ), end( smaller ) );
        items.insert( end( items ), begin( same ), end( same ) );
        items.insert( end( items ), begin( larger ), end( larger ) );
*/
//!    }
//!}

/*
 * This is the more public version of insertion sort.
 * It requires a pair of iterators and a comparison
 * function object.
 */
//!template <typename RandomIterator, typename Comparator>
//!void insertionSort( const RandomIterator & begin,
//!                    const RandomIterator & end,
//!                    Comparator lessThan )
//!{
//!    if( begin == end )
//!        return;
        
//!    RandomIterator j;

//!    for( RandomIterator p = begin+1; p != end; ++p )
//!    {
//!        auto tmp = std::move( *p );
//!        for( j = p; j != begin && lessThan( tmp, *( j-1 ) ); --j )
//!            *j = std::move( *(j-1) );
//!        *j = std::move( tmp );
//!    }
//!}

/*
 * The two-parameter version calls the three parameter version, using C++11 decltype
 */
//!template <typename RandomIterator>
//!void insertionSort( const RandomIterator & begin,
//!                    const RandomIterator & end )
//!{
//!    insertionSort( begin, end, less<decltype(*begin )>{ } );
//!}


//!#endif  
        
        
}//!
