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
import edu.odu.cs.AlgAE.Server.Utilities.DiscreteInteger;//!
import edu.odu.cs.AlgAE.Server.Utilities.Index;//!
import edu.odu.cs.AlgAE.Server.Utilities.SimpleReference;

public class hash<T> {

    int hSize; // number of buckets in the table
    int theSize; // the number of data values in the table
    ArrayList<ArrayList<T>> table;


    public hash(int nBuckets) {
        hSize = nBuckets;
        table = new ArrayList<ArrayList<T>>();
        table.renderHorizontally(false);//!
        for (int i = 0; i < nBuckets; ++i) {
            ArrayList<T> b = new ArrayList<>();
            table.add(b);
        }
        theSize = 0;
        wrapper = new Wrapper<T>();//!
    }

    public boolean empty() {
        return theSize == 0;
    }

    public int size() {
        return theSize;
    }

    public boolean find(T item)//!
    {
        ActivationRecord aRec = activate(getClass());//!

        // hashIndex is the bucket number (index of the linked list)
        aRec.param("item", item);//!
        aRec.breakHere("Starting find - compute the hash function");//!
        int hashIndex = item.hashCode() % hSize;
        // Get the bucket that we will be working with
        aRec.var("hashIndex", new Index(hashIndex, wrapper)).breakHere("Get the indicated bucket");//!
        ArrayList<T> myBucket = table.get(hashIndex);
        aRec.refVar("myBucket", myBucket);//!
        aRec.breakHere("Search the myBucket");//!
        int k = myBucket.indexOf(item);
        aRec.var("k", new Index(k, wrapper)).breakHere("Did we find it?");
        return k >= 0;
    }

  public void insert(T item) {
	  ActivationRecord aRec = activate(getClass());//!

      // hashIndex is the bucket number
      aRec.param("item", item);//!
      aRec.breakHere("Starting insert - compute the hash function");//!
      int hashIndex = item.hashCode() % hSize;
      aRec.var("hashIndex", new Index(hashIndex, wrapper));//!
      aRec.breakHere("Get the indicated bucket");//!
      // Get the bucket we will be working with
       ArrayList<T> myBucket = table.get(hashIndex);
       aRec.refVar("myBucket",myBucket);//!
       aRec.breakHere("Search myBucket");//!
       int k = myBucket.indexOf(item);
       aRec.var("k", new Index(k, myBucket)).breakHere("did we find it?");//!
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
}

    public void clear() {//!
        table = new ArrayList<ArrayList<T>>();//!
        for (int i = 0; i < hSize; ++i) //!
            table.add(new ArrayList<T>());//!
        theSize = 0;//!
    }//!

    public void quickInsert(T item)//!
    {
        int hashIndex = item.hashCode() % hSize;//!
        ArrayList<T> myBucket = table.get(hashIndex);//!
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

    class Wrapper<E> implements CanBeRendered<Wrapper<E>>, Renderer<Wrapper<E>> {//!

        SimpleReference[] buckets;//!

        public Wrapper() {//!
            buckets = new SimpleReference[hSize];//!
            for (int i = 0; i < hSize; ++i) {//!
                buckets[i] = new SimpleReference(null);//!
            }//!
        }//!

        @Override//!
        public Boolean getClosedOnConnections() {//!
            return false;//!
        }//!

        @Override//!
        public Color getColor(hash<T>.Wrapper<E> arg0) {//!
            return null;//!
        }//!

        @Override//!
        public List<Component> getComponents(hash<T>.Wrapper<E> wrapper) {//!
            List<Component> components = new java.util.ArrayList<>();//!
            for (int i = 0; i < hSize; ++i) {//!
                if (buckets[i].get() != table.get(i)) {//!
                    buckets[i].set(table.get(i));//!
                }//!
                components.add(new Component(buckets[i], ""+i));//!
            }//!
            return components;//!
        }

        @Override//!
        public List<Connection> getConnections(hash<T>.Wrapper<E> arg0) {//!
            return new java.util.ArrayList<>();//!
        }//!

        @Override//!
        public Directions getDirection() {//!
            return Directions.Vertical;//!
        }//!

        @Override//!
        public Double getSpacing() {//!
            return null;//!
        }//!

        @Override//!
        public String getValue(hash<T>.Wrapper<E> arg0) {//!
            return "";//!
        }//!

        @Override//!
        public Renderer<hash<T>.Wrapper<E>> getRenderer() {//!
            return this;//!
        }//!

    }//!
    Wrapper<T> wrapper;//!
}
