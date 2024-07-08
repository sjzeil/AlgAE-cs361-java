package edu.odu.cs.cs361.animations;

import static edu.odu.cs.AlgAE.Server.LocalServer.activate;//!
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;//!

public class avlNode<T extends Comparable<T>> {

    T value;
    avlNode<T> leftChild;
    avlNode<T> rightChild;
    int balanceFactor;

    public avlNode(T v, avlNode<T> left, avlNode<T> right) {
        value = v;
        leftChild = left;
        rightChild = right;
        balanceFactor = 0;
    }

    public avlNode(T v) {
        value = v;
        leftChild = null;
        rightChild = null;
        balanceFactor = 0;
    }

    private avlNode<T> singleRotateLeft() {
        // perform single rotation rooted at current node

        // U is the Unbalanced node, H is the Higher child,
        // and I is the interior grandchild
        ActivationRecord aRec = activate(getClass());// !
        aRec.refParam("this", this).breakHere("entered singleRotateLeft");// !

        avlNode<T> U = this;
        avlNode<T> H = U.rightChild;
        avlNode<T> I = H.leftChild;
        aRec.refVar("U", U);// !
        aRec.refVar("H", H);// !
        aRec.refVar("I", I);// !

        aRec.breakHere("Ready to hoist H to replace U as subtree root.");// !
        U.rightChild = I;
        aRec.breakHere("....");// !
        H.leftChild = U;

        // now update the balance factors
        int Ubf = U.balanceFactor;
        int Hbf = H.balanceFactor;
        aRec.var("Ubf", Ubf).var("Hbf", Hbf).breakHere("Update the balance factors");// !
        if (Hbf <= 0) {
            if (Ubf >= 1)
                H.balanceFactor = Hbf - 1;
            else
                H.balanceFactor = Ubf + Hbf - 2;

            U.balanceFactor = Ubf - 1;
        } else {
            if (Ubf <= Hbf)
                H.balanceFactor = Ubf - 2;
            else
                H.balanceFactor = Hbf - 1;
            U.balanceFactor = (Ubf - Hbf) - 1;
        }
        aRec.breakHere("Return H as the new subtree root");// !
        return H;
    }

    private avlNode<T> singleRotateRight() {
        // perform single rotation rooted at current node

        // U is the Unbalanced node, H is the Higher child,
        // and I is the interior grandchild
        ActivationRecord aRec = activate(getClass());// !
        aRec.refParam("this", this).breakHere("entered singleRotateLeft");// !
        avlNode<T> U = this;
        avlNode<T> H = U.leftChild;
        avlNode<T> I = H.rightChild;
        aRec.refVar("U", U);// !
        aRec.refVar("H", H);// !
        aRec.refVar("I", I);// !

        aRec.breakHere("Ready to hoist H to replace U as subtree root.");// !
        U.leftChild = I;
        aRec.breakHere("....");// !
        H.rightChild = U;

        // now update the balance factors
        int Ubf = U.balanceFactor;
        int Hbf = H.balanceFactor;
        aRec.var("Ubf", Ubf).var("Hbf", Hbf).breakHere("Update the balance factors");// !
        if (Hbf >= 0) {
            if (Ubf <= 1)
                H.balanceFactor = Hbf + 1;
            else
                H.balanceFactor = Ubf + Hbf + 2;

            U.balanceFactor = Ubf + 1;
        } else {
            if (Ubf <= Hbf)
                H.balanceFactor = Ubf + 2;
            else
                H.balanceFactor = Hbf + 1;
            U.balanceFactor = (Ubf - Hbf) + 1;
        }
        aRec.breakHere("Return H as the new subtree root");// !
        return H;
    }

    private avlNode<T> balance() {
        // balance tree rooted at node
        // using single or double rotations as appropriate
        ActivationRecord aRec = activate(getClass());// !
        aRec.refParam("this", this).breakHere("entered balance");// !
        if (balanceFactor < 0) {
            if (leftChild.balanceFactor <= 0) {
                // perform single rotation
                aRec.breakHere("Need a single right rotation");// !
                return singleRotateRight();
            } else {
                // perform double rotation
                aRec.breakHere("Need a double right rotation");
                leftChild = leftChild.singleRotateLeft();// !
                return singleRotateRight();
            }
        } else {
            if (rightChild.balanceFactor >= 0) {
                aRec.breakHere("Need a single left rotation");// !
                return singleRotateLeft();
            } else {
                // perform double rotation
                aRec.breakHere("Need a double left rotation");// !
                rightChild = rightChild.singleRotateRight();
                return singleRotateLeft();
            }
        }
    }

    public avlNode<T> insert(T val)
    // insert a new element into balanced AVL tree
    {
        ActivationRecord aRec = activate(getClass());// !
        aRec.refParam("this", this).param("val", val).breakHere("entered insert");// !
        if (val.compareTo(value) < 0) {
            if (leftChild != null) {
                int oldbf = leftChild.balanceFactor;
                aRec.var("oldbf", oldbf);// !
                aRec.breakHere("Recurse to the left");// !
                leftChild = leftChild.insert(val);
                // check to see if tree grew
                aRec.breakHere("Did the left subtree grow?");
                if ((leftChild.balanceFactor != oldbf) &&
                        leftChild.balanceFactor != 0)
                    balanceFactor--;
            } else {
                aRec.breakHere("Insert on the left");// !
                leftChild = new avlNode<T>(val);
                aRec.breakHere("Change the balance factor");// !
                balanceFactor--;
            }
        } else { // insert into right subtree
            if (rightChild != null) {
                int oldbf = rightChild.balanceFactor;
                aRec.var("oldbf", oldbf);// !
                aRec.breakHere("Recurse to the right");// !
                rightChild = rightChild.insert(val);
                // check to see if tree grew
                aRec.breakHere("Did the right subtree grow?");// !
                if ((rightChild.balanceFactor != oldbf) &&
                        rightChild.balanceFactor != 0)
                    balanceFactor++;
            } else {
                aRec.breakHere("Insert on the right");// !
                rightChild = new avlNode<T>(val);
                aRec.breakHere("Insert on the left");// !
                balanceFactor++;
            }
        }

        // check if we are now out of balance, if so balance
        aRec.breakHere("Are we out of balance?");// !
        if ((balanceFactor < -1) || (balanceFactor > 1))
            return balance();
        else
            return this;
    }

    /* ! */
    public avlNode<T> quickInsert(T val)
    // insert a new element into balanced AVL tree
    {
        if (val.compareTo(value) < 0) {
            if (leftChild != null) {
                int oldbf = leftChild.balanceFactor;
                leftChild = leftChild.quickInsert(val);
                // check to see if tree grew
                if ((leftChild.balanceFactor != oldbf) &&
                        leftChild.balanceFactor != 0)
                    balanceFactor--;
            } else {
                leftChild = new avlNode<T>(val);
                balanceFactor--;
            }
        } else { // insert into right subtree
            if (rightChild != null) {
                int oldbf = rightChild.balanceFactor;
                rightChild = rightChild.quickInsert(val);
                // check to see if tree grew
                if ((rightChild.balanceFactor != oldbf) &&
                        rightChild.balanceFactor != 0)
                    balanceFactor++;
            } else {
                rightChild = new avlNode<T>(val);
                balanceFactor++;
            }
        }

        // check if we are now out of balance, if so balance
        if ((balanceFactor < -1) || (balanceFactor > 1))
            return quickBalance();
        else
            return this;
    }

    private avlNode<T> quickBalance()
    // !avlNode<T>* avlNode<T>::balance ()
    { // balance tree rooted at node
      // using single or double rotations as appropriate
        if (balanceFactor < 0) {
            if (leftChild.balanceFactor <= 0)// ! if (leftChild->balanceFactor <= 0)
                // perform single rotation
                return quickSingleRotateRight();
            else {
                // perform double rotation
                leftChild = leftChild.quickSingleRotateLeft();// ! leftChild = leftChild->singleRotateLeft();
                return quickSingleRotateRight();
            }
        } else {
            if (rightChild.balanceFactor >= 0)// ! if (rightChild->balanceFactor >= 0)
                return quickSingleRotateLeft();
            else {
                // perform double rotation
                rightChild = rightChild.quickSingleRotateRight();// ! rightChild = rightChild->singleRotateRight();
                return quickSingleRotateLeft();
            }
        }
    }

    private avlNode<T> quickSingleRotateLeft()// !avlNode<T>* avlNode<T>::singleRotateLeft ()
    // perform single rotation rooted at current node
    {
        // [I have renamed the nodes from what appears in the text to follow
        // the convention that U is the Unbalanced node, H is the Higher child,
        // and I is the interior grandchild - S Zeil]
        avlNode<T> U = this;// ! avlNode<T>* U = this;
        avlNode<T> H = U.rightChild;// ! avlNode<T>* H = U->rightChild;
        avlNode<T> I = H.leftChild;// ! avlNode<T>* I = H->leftChild;

        U.rightChild = I;// ! U->rightChild = I;
        H.leftChild = U;// ! H->leftChild = U;

        // now update the balance factors
        int Ubf = U.balanceFactor;// ! int Ubf = U->balanceFactor;
        int Hbf = H.balanceFactor;// ! int Hbf = H->balanceFactor;
        if (Hbf <= 0) {
            if (Ubf >= 1)
                H.balanceFactor = Hbf - 1;// ! H->balanceFactor = Hbf - 1;
            else
                H.balanceFactor = Ubf + Hbf - 2;// ! H->balanceFactor = Ubf + Hbf - 2;

            U.balanceFactor = Ubf - 1;// ! U->balanceFactor = Ubf - 1;
        } else {
            if (Ubf <= Hbf)
                H.balanceFactor = Ubf - 2;// ! H->balanceFactor = Ubf - 2;
            else
                H.balanceFactor = Hbf - 1;// ! H->balanceFactor = Hbf - 1;
            U.balanceFactor = (Ubf - Hbf) - 1;// ! U->balanceFactor = (Ubf - Hbf) - 1;
        }
        return H;
    }

    // !
    // !template <class T>
    private avlNode<T> quickSingleRotateRight()// !avlNode<T>* avlNode<T>::singleRotateRight ()
    // perform single rotation rooted at current node
    {
        // [I have renamed the nodes from what appears in the text to follow
        // the convention that U is the Unbalanced node, H is the Higher child,
        // and I is the interior grandchild - S Zeil]
        avlNode<T> U = this;// ! avlNode<T>* U = this;
        avlNode<T> H = U.leftChild;// ! avlNode<T>* H = U->leftChild;
        avlNode<T> I = H.rightChild;// ! avlNode<T>* I = H->rightChild;

        U.leftChild = I;// ! U->leftChild = I;
        H.rightChild = U;// ! H->rightChild = U;

        // now update the balance factors
        int Ubf = U.balanceFactor;// ! int Ubf = U->balanceFactor;
        int Hbf = H.balanceFactor;// ! int Hbf = H->balanceFactor;
        if (Hbf >= 0) {
            if (Ubf <= 1)
                H.balanceFactor = Hbf + 1;// ! H->balanceFactor = Hbf + 1;
            else
                H.balanceFactor = Ubf + Hbf + 2;// ! H->balanceFactor = Ubf + Hbf + 2;

            U.balanceFactor = Ubf + 1;// ! U->balanceFactor = Ubf + 1;
        } else {
            if (Ubf <= Hbf)
                H.balanceFactor = Ubf + 2;// ! H->balanceFactor = Ubf + 2;
            else
                H.balanceFactor = Hbf + 1;// ! H->balanceFactor = Hbf + 1;
            U.balanceFactor = (Ubf - Hbf) + 1;// ! U->balanceFactor = (Ubf - Hbf) + 1;
        }
        return H;
    }

    /* ! */
}
