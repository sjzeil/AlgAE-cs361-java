package edu.odu.cs.cs361.animations;

public class BinNode<T> {

    T value;
    BinNode<T> left;
    BinNode<T> right;

    // default constructor. data not initialized
    public BinNode(T value) {
        this.value = value;
        left = null;
        right = null;
    }

    public BinNode(T value, BinNode<T> left, BinNode<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    // return TRUE if a leaf node, FALSE otherwise
    public boolean isLeaf() {
        return left == null && right == null;
    }
}
