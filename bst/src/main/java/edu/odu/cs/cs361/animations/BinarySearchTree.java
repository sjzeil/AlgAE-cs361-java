package edu.odu.cs.cs361.animations;//!

import static edu.odu.cs.AlgAE.Server.LocalServer.activate;//!
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;//!

public class BinarySearchTree<T extends Comparable<T>> {
			
	BinaryNode<T> root;
	BinarySearchTree() { root = null; }
	
	   BinarySearchTree( BinarySearchTree<T> rhs ) {
       root = clone( rhs.root );
    }
	

   /**
    * Find the smallest item in the tree.
    * Throw UnderflowException if empty.
    */
   T findMin( ) {
       if( isEmpty( ) )
    	   throw  new RuntimeException ("underflow");
       return findMin( root ).element;
   }


   /**
    * Find the largest item in the tree.
    * Throw UnderflowException if empty.
    */
   T findMax( ) {
       if( isEmpty( ) )
    	   throw  new RuntimeException ("underflow");
       return findMax( root ).element;
   }

   /**
    * Returns true if x is found in the tree.
    */
   boolean contains( T x) {
	   ActivationRecord aRec = activate(getClass());//!
	   aRec.param("x", x).breakHere("entered contains");//!
       return contains( x, root );
   }

   /**
    * Test if the tree is logically empty.
    * Return true if empty, false otherwise.
    */
   boolean isEmpty( ) {
	   return root == null;
   }


   /**
    * Make the tree logically empty.
    */
   void makeEmpty( ) {
       makeEmpty( root );
   }

   /**
    * Insert x into the tree; duplicates are ignored.
    */
   void insert( T  x ) {
	   root = insert (x, root);
   }

   /**
    * Remove x from the tree. Nothing is done if x is not found.
    */
   void remove( T x ) {
       root = remove( x, root );
   }


   /**
    * Internal method to insert into a subtree.
    * x is the item to insert.
    * t is the node that roots the subtree.
    * Set the new root of the subtree.
    */
   BinaryNode<T> insert( T x, BinaryNode<T> t ) {
	   ActivationRecord aRec = activate(getClass());//!
	   aRec.param("x", x).refParam("t", t).breakHere("entered insert");//!
	   if( t == null) {
		   aRec.breakHere("subtree is null, create the node");//!
		   return new BinaryNode<T>( x, null, null );
	   } else if( x.compareTo(t.element)< 0 ) {
		   aRec.breakHere("Go left.");//!
		   aRec.highlight(t.left);//!
		   t.left = insert( x, t.left);
		   aRec.refParam("t", t);//!
		   return t;
	   } else if( x.compareTo(t.element)> 0 ) {
		   aRec.breakHere("Go right.");//!
		   aRec.highlight(t.right);//!
		   t.right = insert( x, t.right);
		   aRec.refParam("t", t);//!
		   return t;
	   } else {
    	   aRec.breakHere("We found a duplicate item. Do nothing.");//!
    	   return t; // Duplicate; do nothing
       }
      
   }


   /**
    * Internal method to remove from a subtree.
    * x is the item to remove.
    * t is the node that roots the subtree.
    * Set the new root of the subtree.
    */
   BinaryNode<T> remove( T x, BinaryNode<T> t) {
	   ActivationRecord aRec = activate(getClass());//!
	   aRec.param("x", x).refParam("t", t).breakHere("begin remove");//!
	   if( t == null) {
		   aRec.breakHere("Item not found; do nothing");//!
	       return t;  
	   }
	   if( x.compareTo(t.element)< 0 ) {
		   aRec.breakHere("Go left");//!
		   aRec.highlight(t.left);//!
		   t.left = remove( x, t.left );
		   aRec.refParam("t", t);//!
	   }
	   else if( x.compareTo(t.element)> 0 ) {
		   aRec.breakHere("Go right");//!
		   aRec.highlight(t.right);//!
		   t.right = remove( x, t.right );
		   aRec.refParam("t", t);//!
	   } else if( t.left != null && t.right != null ) {
    	   aRec.breakHere("Find minimum item in t's right");//!
    	   t.element = findMin( t.right ).element;
    	   aRec.breakHere("Remove the minimum item");//!
    	   t.right  = remove( t.element, t.right);
           aRec.refParam("t", t);//!
       } else {
    	   aRec.breakHere("Delete t");//!
    	   t = ( t.left != null ) ? t.left : t.right;
       }
	   aRec.breakHere("Done.");//!
	   return t;
   }


   /**
    * Internal method to find the smallest item in a subtree t.
    * Return node containing the smallest item.
    */
   BinaryNode<T> findMin( BinaryNode<T> t ) {
	   if(t==null) {
		   return null;
	   }
	   if( t.left == null )
           return t;
	   return findMin( t.left );
   }



   /**
    * Internal method to find the largest item in a subtree t.
    * Return node containing the largest item.
    */
   BinaryNode<T> findMax( BinaryNode<T> t ) {
	   if( t != null )
		   while( t.right != null )
			   t = t.right;
       return t;
   }

   /**
    * Internal method to test if an item is in a subtree.
    * x is item to search for.
    * t is the node that roots the subtree.
    */
   boolean contains( T x, BinaryNode<T> t ) {
	  ActivationRecord aRec = activate(getClass());//!
	  if (t != null) aRec.highlight(t);//!
	  boolean result = false;
	  aRec.param("x", x).refParam("t", t).breakHere("entered recursive contains");//!
      if ( t == null ) {
    	  aRec.breakHere("Can't find x - it's not in the tree.");//!
         return false;
      } else if( x .compareTo(t.element) < 0 ) {
    	  aRec.breakHere("Look to the left.");//!
    	 result = contains( x, t.left );
    	 aRec.breakHere("returning");//!
    	 return result;
      } else if( x.compareTo(t.element) > 0 ) {
    	  aRec.breakHere("Look to the right.");//!
    	 result = contains( x, t.right );
    	 aRec.breakHere("returning");//!
    	 return result;
      } else {
    	  aRec.breakHere("Found it!");//!
          return true;    // Match
      }
   }


   /**
    * Internal method to make subtree empty.
    */
   void makeEmpty( BinaryNode<T>  t ) {
	   if( t != null ) {
		   makeEmpty( t.left );
           t.left = null;
		   makeEmpty( t.right );
           t.right = null;
       }
   }



   /**
    * Internal method to clone subtree.
    */
   BinaryNode<T> clone( BinaryNode<T> t ) {
       if( t == null )
	      return null;
       else
    	  return new BinaryNode<T>(t.element, clone( t.left ), clone( t.right )) ;
   }


}
