package edu.odu.cs.cs361.animations;//!

import static edu.odu.cs.AlgAE.Server.LocalServer.activate;//!

import java.awt.Color;
import java.util.List;
import java.util.ListIterator;//!

import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;//!
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;
import edu.odu.cs.AlgAE.Server.Rendering.CanBeRendered;
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;
import edu.odu.cs.AlgAE.Server.Utilities.ArrayList;//!
import edu.odu.cs.AlgAE.Server.Utilities.IndexedArray;
import edu.odu.cs.AlgAE.Server.Utilities.SimpleReference;

public class hash<T> {

    int hSize; // number of buckets in the table
    int theSize; // the number of data values in the table
    Object[] table;
    IndexedArray<Object> shadow;//!
    SimpleReference shadowRef;//!


    public hash(int nBuckets) {
        hSize = nBuckets;
        table = new Object[hSize];
        shadow = new IndexedArray<>(table);//!
        shadow.renderHorizontally(false);//!
        shadow.showNumbers(false);//!
        for (int i = 0; i < nBuckets; ++i) {
            table[i] = new ArrayList<>();
        }
        theSize = 0;
        shadowRef = new SimpleReference(shadow, 90, 90);//!
    }

    public boolean empty() {
        return theSize == 0;
    }

    public int size() {
        return theSize;
    }

    public boolean contains(T item)//!
    {
        ActivationRecord aRec = activate(getClass());//!

        // hashIndex is the bucket number (index of the linked list)
        aRec.param("item", item);//!
        aRec.breakHere("Starting find - compute the hash function");//!
        int hashIndex = item.hashCode() % hSize;
        // Get the bucket that we will be working with
        shadow.pushIndices();//!
        shadow.indexedBy(hashIndex, "hashIndex");//!
        aRec.var("hashIndex", hashIndex).breakHere("Get the indicated bucket");//!
        ArrayList<T> myBucket = (ArrayList<T>)table[hashIndex];
        aRec.refVar("myBucket", myBucket);//!
        aRec.breakHere("Search the myBucket");//!
        int k = myBucket.indexOf(item);
        shadow.indexedBy(k, "k");//!
        aRec.var("k", k).breakHere("Did we find it?");//!
        shadow.popIndices();//!
        return k >= 0;
    }

  public void insert(T item) {
	  ActivationRecord aRec = activate(getClass());//!

      // hashIndex is the bucket number
      aRec.param("item", item);//!
      aRec.breakHere("Starting insert - compute the hash function");//!
      int hashIndex = item.hashCode() % hSize;
      shadow.pushIndices();//!
      shadow.indexedBy(hashIndex, "hashIndex");//!
      aRec.var("hashIndex", hashIndex);//!
      aRec.breakHere("Get the indicated bucket");//!
      // Get the bucket we will be working with
       ArrayList<T> myBucket = (ArrayList<T>)table[hashIndex];
       aRec.refVar("myBucket",myBucket);//!
       aRec.breakHere("Search myBucket");//!
       int k = myBucket.indexOf(item);
       shadow.indexedBy(k, "k");//!
       aRec.var("k", k).breakHere("did we find it?");//!
       if (k < 0) {
          // Did not find it.
          aRec.breakHere("Did not find it. Add to the bucket");//!
          myBucket.add(item);
          ++theSize;
          aRec.breakHere("Added");//!
        } else {
          // item is in the hash table. duplicates not allowed.
          // no insertion
	      aRec.breakHere("Found it. Ignore this item.");//!
        }
        shadow.popIndices();//!
}

    public void clear() {//!
        for (int i = 0; i < hSize; ++i) //!
            ((ArrayList<T>)table[i]).clear();
        theSize = 0;//!
    }//!

    public void quickInsert(T item)//!
    {
        int hashIndex = item.hashCode() % hSize;//!
        ArrayList<T> myBucket = (ArrayList<T>)table[hashIndex];//!
        ListIterator<T> bucketIter = null;//!
        bucketIter = myBucket.listIterator();//!
        while (bucketIter.hasNext())//!
        {//!
            T current = bucketIter.next();//!
            if (current.equals(item)) {//!
                return;//!
            } //!
        } //!
        bucketIter.add(item);//!
        theSize++;//!
    }//!

}
