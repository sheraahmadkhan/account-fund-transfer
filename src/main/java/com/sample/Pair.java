package com.sample;

public class Pair<LEFT, RIGHT> {
    LEFT left;
    RIGHT right;

    public Pair(LEFT left, RIGHT right) {
        this.left = left;
        this.right = right;
    }

    public LEFT getLeft() {
        return left;
    }

    public RIGHT getRight() {
        return right;
    }
}
