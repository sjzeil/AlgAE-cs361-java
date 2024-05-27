package edu.odu.cs.cs361.animations;//!

import static edu.odu.cs.AlgAE.Server.LocalServer.activate;//!

import java.awt.Color;//!
import java.util.LinkedList;
import java.util.List;//!

import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;//!
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;//!
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;//!
import edu.odu.cs.AlgAE.Server.Rendering.CanBeRendered;//!
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;//!
import edu.odu.cs.AlgAE.Server.Utilities.ArrayList;//!
import edu.odu.cs.AlgAE.Server.Utilities.DiscreteInteger;
import edu.odu.cs.AlgAE.Server.Utilities.Index;//!

public class hash_set_LP<T> {

    enum HashStatus {
        Occupied, Empty, Deleted
    };

    class HashEntry
            implements Renderer<HashEntry>, CanBeRendered<HashEntry> //!
    {
        T data;
        HashStatus info;
        public int pos; //

        HashEntry() {
            info = (HashStatus.Empty);
            pos = 0;//!
        }

        HashEntry(T v, HashStatus status) {
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
    ArrayList<HashEntry> table;
    int theSize;

    hash_set_LP() {
        table = new ArrayList<HashEntry>();
        theSize = 0;
        for (int i = 0; i < hSize; ++i) {
            table.add(new HashEntry());
            table.get(i).pos = i;//!
        }
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
        table.pushIndices();//!
        aRec.breakHere("Starting insert");//!
        int h0 = element.hashCode();
        aRec.var("h0", h0);//!
        DiscreteInteger h0d = new DiscreteInteger(h0);//!
        table.indexedBy(h0d, "h0");//!
        aRec.breakHere("Computed hash code - search for element");//!
        int h = find(element, h0);
        DiscreteInteger hV = new DiscreteInteger(h);//!
        aRec.var("h", hV);//!
        table.indexedBy(hV, "h");//!
        aRec.breakHere("Returned from find");//!
        if (h == hSize) {
            aRec.breakHere("element is not in the table - look for an empty slot in which to put it.");//!
            int count = 0;
            h = h0;
            hV.set(h);//!
            aRec.var("count", count);//!
            aRec.breakHere("While looking, count how many probes we have done.");//!
            while (table.get(h).info == HashStatus.Occupied && count < hSize) {
                aRec.breakHere("table[h] is occupied - keep probing.");//!
                ++count;
                aRec.var("count", count);//!
                h = (h0 + /* f(count) */ count) % hSize;
                hV.set(h);//!
                aRec.breakHere("Next possibility for h.");//!
            }
            aRec.breakHere("Finished searching.");//!
            if (count >= hSize) {
                aRec.breakHere("Could not find an open slot.");//!
                table.popIndices();//!
                return false; // could not add
            } else {
                aRec.breakHere("Put the element into slot h.");//!
                table.get(h).info = HashStatus.Occupied;
                table.get(h).data = element;
                ++theSize;
                aRec.breakHere("Done.");//!
                table.popIndices();//!
                return true;
            }
        } else { // replace
            table.get(h).data = element;
            table.popIndices();//!
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
            while (table.get(h).info == HashStatus.Occupied && count < hSize)//!
            {//!
                ++count;//!
                h = (h0 + /* f(count) */ count) % hSize;//!
            } //!
            if (count >= hSize)//!
                return false; // could not add//!
            else//!
            { //!
                table.get(h).info = HashStatus.Occupied;//!
                table.get(h).data = element;//!
                ++theSize;// +
                return true;//!
            } //!
        } //!
        else { // replace//!
            table.get(h).data = element;//!
            return true;//!
        } //!
    }//!

    int count(T element) {
        ActivationRecord aRec = activate(getClass());//!
        aRec.param("element", element);//!
        table.pushIndices();//!
        aRec.breakHere("Starting count");//!
        int h0 = element.hashCode();
        aRec.var("h0", h0);//!
        DiscreteInteger h0d = new DiscreteInteger(h0);//!
        table.indexedBy(h0d, "h0");//!
        aRec.breakHere("Computed hash code - search for element");//!
        int h = find(element, h0);
        DiscreteInteger hV = new DiscreteInteger(h);//!
        table.indexedBy(hV, "h");
        ;
        aRec.breakHere("Returned from find - return 0 or 1");//!
        table.popIndices();//!
        return (h != hSize) ? 1 : 0;
    }

    void erase(T element)//! void erase (const T& element)
    {
        ActivationRecord aRec = activate(getClass());//!
        aRec.param("element", element);//!
        table.pushIndices();//!
        aRec.breakHere("Starting erase");//!
        int h0 = element.hashCode();
        DiscreteInteger h0V = new DiscreteInteger(h0);//!
        aRec.var("h0", h0V);//!
        table.indexedBy(h0V, "h0");//!
        aRec.breakHere("Computed hash code - search for element");//!
        int h = find(element, h0);
        DiscreteInteger hV = new DiscreteInteger(h);//!
        table.indexedBy(hV, "h");
        ;//!
        aRec.breakHere("Returned from find");//!
        if (h != hSize) {
            aRec.breakHere("Found the element - mark its slot as Deleted");//!
            table.get(h).info = HashStatus.Deleted;
            --theSize;
        }
        aRec.breakHere("Done");//!
        table.popIndices();//!
    }

    void clear() {//! {
                  //! table.clear();
        for (int i = 0; i < hSize; ++i)
            table.set(i, new HashEntry());//!
    }

    int find(T element, int h0) {
        ActivationRecord aRec = activate(getClass());//!
        aRec.param("element", element);//!
        DiscreteInteger h0V = new DiscreteInteger(h0);
        aRec.param("h0", h0V);//!
        table.pushIndices();//!
        table.indexedBy(h0V, "h0");
        aRec.breakHere("Starting insert");//!
        int h = h0 % hSize;
        DiscreteInteger hV = new DiscreteInteger(h);//!
        table.indexedBy(hV, "h");//!
        aRec.var("h", hV);
        aRec.breakHere("Computed hash code - search for element");//!
        int count = 0;
        aRec.var("count", count);//!
        aRec.breakHere("While looking, count how many probes we have done.");//!
        while ((table.get(h).info == HashStatus.Deleted ||
                (table.get(h).info == HashStatus.Occupied
                        && (!table.get(h).data.equals(element))))
                && count < hSize) {
            aRec.breakHere("table[h] is occupied or deleted - keep probing.");//!
            ++count;
            aRec.var("count", count);//!
            h = (h0 + /* f(count) */ count) % hSize;
            hV.set(h);//!
            aRec.breakHere("Next possibility for h.");//!
        }
        aRec.breakHere("Finished searching.");//!
        if (count >= hSize || table.get(h).info == HashStatus.Empty) {
            aRec.breakHere("Could not find the element.");//!
            table.popIndices();//!
            return hSize;
        } else {
            aRec.breakHere("Found it!");//!
            table.popIndices();//!
            return h;
        }
    }

    int quickFind(T element, int h0)//!
    {//!
        int h = h0 % hSize;//!
        int count = 0;//!
        while ((table.get(h).info == HashStatus.Deleted || //!
                (table.get(h).info == HashStatus.Occupied //!
                        && (!table.get(h).data.equals(element))))//!
                && count < hSize)//!
        {//!
            ++count;//!
            h = (h0 + /* f(count) */ count) % hSize;//!
        } //!
        if (count >= hSize//!
                || table.get(h).info == HashStatus.Empty)//!
            return hSize;//!
        else //!
            return h;//!
    }//!
}
