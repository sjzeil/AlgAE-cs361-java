package edu.odu.cs.cs361.animations;//!

import static edu.odu.cs.AlgAE.Server.LocalServer.activate;//!

import java.awt.Color;//!
import java.util.LinkedList;
import java.util.List;//!

import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;//!
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;//!
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;//!
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;//!
import edu.odu.cs.AlgAE.Server.Rendering.CanBeRendered;//!
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;//!
import edu.odu.cs.AlgAE.Server.Utilities.IndexedArray;//!

public class hash_set_LP<T> {

    enum HashStatus {
        Occupied, Empty, Deleted
    };

    static class HashEntry
            implements Renderer<HashEntry>, CanBeRendered<HashEntry> //!
    {
        Object data;
        HashStatus info;
        public int pos;//!

        HashEntry() {
            info = (HashStatus.Empty);
            pos = 0;//!
        }

        HashEntry(Object v, HashStatus status) {
            data = v;
            info = status;
        }

        @Override //!
        public Color getColor(HashEntry obj) {//!
            return null;//!
        }//!

        @Override //!
        public List<Component> getComponents(HashEntry obj) {//!
            LinkedList<Component> comps = new LinkedList<Component>();//!
            comps.add(new Component(info));//!
            if (data != null)//!
                comps.add(new Component(data));//!
            return comps;//!
        }//!

        @Override //!
        public List<Connection> getConnections(HashEntry obj) {//!
            return new LinkedList<Connection>();//!
        }//!

        @Override //!
        public String getValue(HashEntry obj) {//!
            return "" + pos;//!
        }//!

        @Override //!
        public Renderer<HashEntry> getRenderer() {//!
            return this;//!
        }//!

        @Override //!
        public Boolean getClosedOnConnections() {//!
            return false;//!
        }//!

        @Override //!
        public Directions getDirection() {//!
            return Directions.Vertical;//!
        }//!

        @Override
        public Double getSpacing() {//!
            return null;//!
        }
    }

    int hSize = 11;
    HashEntry[] table;
    IndexedArray<HashEntry> shadow;//!
    int theSize;

    hash_set_LP() {
        table = new HashEntry[hSize];
        theSize = 0;
        for (int i = 0; i < hSize; ++i) {
            table[i] = new HashEntry();
            table[i].pos = i;//!
        }
        shadow = new IndexedArray<>(table);//!
    }

    boolean empty() {
        return theSize == 0;
    }

    int size() {
        return theSize;
    }

    boolean insert(T element) {
        ActivationRecord aRec = activate(getClass());//!
        aRec.param("element", element);//!
        shadow.pushIndices();//!
        aRec.breakHere("Starting insert");//!
        int h0 = element.hashCode();
        aRec.var("h0", h0);//!
        shadow.indexedBy(h0, "h0");//!
        aRec.breakHere("Computed hash code - search for element");//!
        int h = find(element, h0);
        aRec.var("h", h);//!
        shadow.indexedBy(h, "h");//!
        aRec.breakHere("Returned from find");//!
        if (h == hSize) {
            aRec.breakHere("element is not in the table - look for an empty slot in which to put it.");//!
            int count = 0;
            h = h0;
            shadow.indexedBy(h, "h");//!
            aRec.var("count", count);//!
            aRec.breakHere("While looking, count how many probes we have done.");//!
            while (table[h].info == HashStatus.Occupied && count < hSize) {
                aRec.breakHere("table[h] is occupied - keep probing.");//!
                ++count;
                aRec.var("count", count);//!
                h = (h0 + /* f(count) */ count) % hSize;
                shadow.indexedBy(h, "h");//!
                aRec.breakHere("Next possibility for h.");//!
            }
            aRec.breakHere("Finished searching.");//!
            if (count >= hSize) {
                aRec.breakHere("Could not find an open slot.");//!
                shadow.popIndices();//!
                return false; // could not add
            } else {
                aRec.breakHere("Put the element into slot h.");//!
                table[h].info = HashStatus.Occupied;
                table[h].data = element;
                ++theSize;
                aRec.breakHere("Done.");//!
                shadow.popIndices();//!
                return true;
            }
        } else { // replace
            table[h].data = element;
            shadow.popIndices();//!
            return true;
        }
    }

    boolean quickInsert(T element)//!
    {//!
        int h0 = element.hashCode();//!
        int h = quickFind(element, h0);//!
        if (h == hSize) {//!
            int count = 0;//!
            h = h0;//!
            while (table[h].info == HashStatus.Occupied && count < hSize)//!
            {//!
                ++count;//!
                h = (h0 + /* f(count) */ count) % hSize;//!
            } //!
            if (count >= hSize)//!
                return false; // could not add//!
            else//!
            { //!
                table[h].info = HashStatus.Occupied;//!
                table[h].data = element;//!
                ++theSize;// +
                return true;//!
            } //!
        } //!
        else { // replace//!
            table[h].data = element;//!
            return true;//!
        } //!
    }//!

    int count(T element) {
        ActivationRecord aRec = activate(getClass());//!
        aRec.param("element", element);//!
        shadow.pushIndices();//!
        aRec.breakHere("Starting count");//!
        int h0 = element.hashCode();
        aRec.var("h0", h0);//!
        shadow.indexedBy(h0, "h0");//!
        aRec.breakHere("Computed hash code - search for element");//!
        int h = find(element, h0);
        shadow.indexedBy(h, "h");
        aRec.breakHere("Returned from find - return 0 or 1");//!
        shadow.popIndices();//!
        return (h != hSize) ? 1 : 0;
    }

    void erase(T element)//! void erase (const T& element)
    {
        ActivationRecord aRec = activate(getClass());//!
        aRec.param("element", element);//!
        shadow.pushIndices();//!
        aRec.breakHere("Starting erase");//!
        int h0 = element.hashCode();
        aRec.var("h0", h0);//!
        shadow.indexedBy(h0, "h0");//!
        aRec.breakHere("Computed hash code - search for element");//!
        int h = find(element, h0);
        shadow.indexedBy(h, "h");
        aRec.breakHere("Returned from find");//!
        if (h != hSize) {
            aRec.breakHere("Found the element - mark its slot as Deleted");//!
            table[h].info = HashStatus.Deleted;
            --theSize;
        }
        aRec.breakHere("Done");//!
        shadow.popIndices();//!
    }

    void clear() {//! {
                  //! table.clear();
        for (int i = 0; i < hSize; ++i)
            table[i] = new HashEntry();//!
    }

    int find(T element, int h0) {
        ActivationRecord aRec = activate(getClass());//!
        aRec.param("element", element);//!
        aRec.param("h0", h0);//!
        shadow.pushIndices();//!
        shadow.indexedBy(h0, "h0");
        aRec.breakHere("Starting insert");//!
        int h = h0 % hSize;
        shadow.indexedBy(h, "h");//!
        aRec.var("h", h);
        aRec.breakHere("Computed hash code - search for element");//!
        int count = 0;
        aRec.var("count", count);//!
        aRec.breakHere("While looking, count how many probes we have done.");//!
        while ((table[h].info == HashStatus.Deleted ||
                (table[h].info == HashStatus.Occupied
                        && (!table[h].data.equals(element))))
                && count < hSize) {
            aRec.breakHere("table[h] is occupied or deleted - keep probing.");//!
            ++count;
            aRec.var("count", count);//!
            h = (h0 + /* f(count) */ count) % hSize;
            shadow.indexedBy(h, "h");//!
            aRec.breakHere("Next possibility for h.");//!
        }
        aRec.breakHere("Finished searching.");//!
        if (count >= hSize || table[h].info == HashStatus.Empty) {
            aRec.breakHere("Could not find the element.");//!
            shadow.popIndices();//!
            return hSize;
        } else {
            aRec.breakHere("Found it!");//!
            shadow.popIndices();//!
            return h;
        }
    }

    int quickFind(T element, int h0)//!
    {//!
        int h = h0 % hSize;//!
        int count = 0;//!
        while ((table[h].info == HashStatus.Deleted || //!
                (table[h].info == HashStatus.Occupied //!
                        && (!table[h].data.equals(element))))//!
                && count < hSize)//!
        {//!
            ++count;//!
            h = (h0 + /* f(count) */ count) % hSize;//!
        } //!
        if (count >= hSize//!
                || table[h].info == HashStatus.Empty)//!
            return hSize;//!
        else //!
            return h;//!
    }//!
}
