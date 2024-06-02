package edu.odu.cs.cs361.animations;

import static edu.odu.cs.AlgAE.Server.LocalServer.activate;//!

import java.util.LinkedList;//!

import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;//!
import edu.odu.cs.AlgAE.Server.Rendering.HorizontalRenderer;//!
import edu.odu.cs.AlgAE.Server.Utilities.SimpleReference;//!

public class BinaryTrees {

    BinNode<String> root = null;
    SimpleReference rootRef = new SimpleReference(root, 180, 180);//!

    // preorder recursive output of the nodes in a binary tree.
    // output separator after each node value. default value
    // of separator is " "
    public void preorderOutput(BinNode<String> t, String separator) {
        ActivationRecord aRec = activate(getClass());//!
        if (t != null)
            aRec.highlight(t);//!
        aRec.refParam("t", t).param("separator", separator);//!
        aRec.breakHere("entered preOrder()");//!
        // the recursive scan terminates on a empty subtree
        if (t == null)
            return;

        aRec.out().print(t.value + separator);//!System.out.println(t.value + separator); // output the node
        aRec.breakHere("printed - now go left");//!
        preorderOutput(t.left, separator); // descend left
        aRec.breakHere("Returned from the left - now go right");//!
        preorderOutput(t.right, separator); // descend right

        aRec.breakHere("All done here");//!
    }

    // postorder recursive output of the nodes in a binary tree.
    // output separator after each node value. default value
    // of separator is " "
    public void postorderOutput(BinNode<String> t, String separator) {
        ActivationRecord aRec = activate(getClass());//!
        if (t != null)
            aRec.highlight(t);//!
        aRec.refParam("t", t).param("separator", separator).breakHere("entered postOrder()");//!
        // the recursive scan terminates on a empty subtree
        if (t == null)
            return;

        aRec.breakHere("First go left");//!
        postorderOutput(t.left, separator); // descend left
        aRec.breakHere("Returned from the left - now go right");//!
        postorderOutput(t.right, separator); // descend right
        aRec.breakHere("Back from the right - print");//!
        aRec.out().print(t.value + separator);//!System.out.println("" + t.value + separator); // output the node
        aRec.breakHere("All done here");//!
    }

    // inorder recursive output of the nodes in a binary tree.
    // output separator after each node value. default value
    // of separator is " "
    public void inorderOutput(BinNode<String> t, String separator) {
        ActivationRecord aRec = activate(getClass());//!
        if (t == null) return;
        aRec.highlight(t);//!
        aRec.refParam("t", t).param("separator", separator).breakHere("entered postOrder()");//!

        aRec.breakHere("First go left");//!
        inorderOutput(t.left, separator);  // descend left
        aRec.breakHere("Returned from the left - print");//!
        aRec.out().print(t.value + separator);//!System.out.println("" + t.value + separator); // output the node
        aRec.breakHere("Printed - now go right");//!
        inorderOutput(t.right, separator); // descend right
        aRec.breakHere("All done here");//!
    }

    @SuppressWarnings("unchecked")
    void levelorderOutput(BinNode<String> t, String separator) {
        ActivationRecord aRec = activate(getClass());//!
        // store siblings of each node in a queue so that they are
        // visited in order at the next level of the tree
        aRec.refParam("t", t).param("separator", separator).breakHere("entered levelorderOutput()");//!
        LinkedList<SimpleReference> q = new LinkedList<SimpleReference>();//! queue<tnode<T> *> q;
        aRec.render(new HorizontalRenderer<LinkedList<SimpleReference>>(q));//!
        BinNode<String> p = null;

        // initialize the queue by inserting the root in the queue
        aRec.var("q", q).refVar("p", p).breakHere("initialize the queue");//!
        q.add(new SimpleReference(t, 160, 200));//! q.push(t);

        aRec.breakHere("ready to enter loop");//!
        // continue the iterative process until the queue is empty
        while (!q.isEmpty())
        {
            // delete front node from queue and output the node value
            aRec.breakHere("get front element");//!
            p = (BinNode<String>) q.peek().get();//! p = q.peek();
            aRec.refVar("p", p).breakHere("remove front element from queue");//!
            q.pop();
            aRec.breakHere("print");//!
            aRec.out().print(p.value + separator);//! System.out.println("" + p.value + separator);
            // if a left child exists, insert it in the queue
            aRec.breakHere("add left to queue");//!
            if (p.left != null)
                q.add(new SimpleReference(p.left, 160, 200));//! q.add(p->left);
            // if a right child exists, insert next to its sibling
            aRec.breakHere("add right to queue");//!
            if (p.right != null)//! if(p->right != null)
                q.add(new SimpleReference(p.right, 160, 200));//! q.add(p->right);

        }
        aRec.breakHere("All done here");//!
    }

}
