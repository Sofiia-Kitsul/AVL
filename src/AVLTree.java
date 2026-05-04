import java.util.ArrayList;
import java.util.List;

/**
 * AVL Tree Implementation
 * * Student Name: ___________________________
 * Student ID: _____________________________
 * Date: ___________________________________
 * * An AVL tree is a self-balancing binary search tree where the heights
 * of the two child subtrees of any node differ by at most one.
 */
public class AVLTree {

    /**
     * Inner Node class representing each node in the AVL tree
     */
    private class Node {
        // TODO: Add instance variables for data, left child, right child, and height
        int data;
        Node left;
        Node right;
        int height;

        /**
         * Constructor for Node
         * @param data the integer value to store in this node
         */
        public Node(int data) {
            // TODO: Initialize the node with the given data
            // TODO: Set left and right children to null
            // TODO: Initialize height (hint: a new leaf node has height 0)
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 0;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    // Root of the AVL tree
    private Node root;

    /**
     * Constructor - creates an empty AVL tree
     */
    public AVLTree() {
        // TODO: Initialize root to null
        this.root = null;
    }

    // ==================== PUBLIC METHODS ====================

    public void insert(int data) {
        // TODO: Call the recursive helper method
        // TODO: Update root with the returned node  //ask!???
        if(root==null) {
            root = new Node(data);
            return;
        }
       root = insertHelperV2(root, data);


    }


    public Node insertHelperV2(Node n, int value) {
        if(n == null) {
            Node helper = new Node(value);
            return helper;
        }
        if(value < n.data) {
            n.setLeft(insertHelperV2(n.left, value));
        }
        else if(value > n.data) {
            n.setRight(insertHelperV2(n.right, value));
        }
        else {
            // Duplicate value — reject it, return the node unchanged
            return n;
        }
        //update height,
        n.setHeight(updateHeight(n));
        //balance
        int balance = balanceHelper(n);
        //find rotation case and do rotation if needed;
        n = rotating(balance, n);

        return n;
    }


    public Node rotating(int balance, Node n){
        //keep working on these!!!!!!!!!!!!!!!!!!!
        if((balance > 1) || (balance < -1)){
            if (balance > 1){
                if(balanceHelper(n.getLeft()) < 0){
                    // redo here
                    //left right
                    // part 1
                    Node leftChild = n.getLeft();
                    Node middle = leftChild.getRight();
                    leftChild.setRight(middle.getLeft());
                    middle.setLeft(leftChild);
                    n.setLeft(middle);
                    leftChild.setHeight(updateHeight(leftChild));

                    //part 2
                    Node newRoot = n.getLeft();
                    n.setLeft(newRoot.getRight());
                    newRoot.setRight(n);
                    n.setHeight(updateHeight(n));
                    newRoot.setHeight(updateHeight(newRoot));
                    System.out.println("pt2");
                    return newRoot;
                }
                else{
                    //single right
                    System.out.println("I am here at single right case the node is: " + n.getData());
                    Node helper = n;
                    n = n.getLeft();
                    helper.setLeft(n.getRight());
                    n.setRight(helper);
                    helper.setHeight(updateHeight(helper));
                    n.setHeight(updateHeight(n));
                    return n;
                }
            }
            else {
                if(balanceHelper(n.getRight()) > 0){
                    //redo here
                    //right left
                    // part 1
                    Node rightChild = n.getRight();
                    Node middle = rightChild.getLeft();
                    rightChild.setLeft(middle.getRight());
                    middle.setRight(rightChild);
                    n.setRight(middle);
                    rightChild.setHeight(updateHeight(rightChild));

                    //part 2
                    Node newRoot = n.getRight();
                    n.setRight(newRoot.getLeft());
                    newRoot.setLeft(n);
                    n.setHeight(updateHeight(n));
                    newRoot.setHeight(updateHeight(newRoot));
                    return newRoot;
                }
                else{
                    //single left
                    Node helper = n;
                    n = n.getRight();
                    helper.setRight(n.getLeft());
                    n.setLeft(helper);
                    helper.setHeight(updateHeight(helper));
                    n.setHeight(updateHeight(n));
                    return n;
                }
            }
        }
        return n;
    }

    public int updateHeight(Node n){
        int height = Math.max(getHeight(n.getRight()), getHeight(n.getLeft())) + 1;
        return height;
    }

    public int balanceHelper(Node n){
        //basically need to check the balance
        //left-right
        System.out.println("Getting balance for: " + n.data);
        int balance = getHeight(n.left) - getHeight(n.right);
        return balance;
    }

    public void delete(int data) {
        // TODO: Call the recursive helper method
        // TODO: Update root with the returned node
        root = deleteHelper(root, data);
    }
    public Node deleteHelper( Node n, int value) {
        System.out.println("I'm on node: " + n.data);
        if(n == null) {
            return null;
        }
        if(n.getData() > value){
            n.setLeft(deleteHelper(n.getLeft(), value));
        }
        else if(n.getData() < value){
            n.setRight(deleteHelper(n.getRight(), value));
        }
        else {
            if (n.getLeft() == null && n.getRight() == null) {
                return null;
            }
            if(n.getLeft() == null){
                return n.getRight();
            }
            if(n.getRight() == null){
                return n.getLeft();
            }
            Node newN = findMinDeleteHelper(n.getRight());
            n.setData(newN.getData());
            n.setRight(deleteHelper(n.getRight(), newN.getData()));
        }

        //edge cases here

        //update height
        n.setHeight(updateHeight(n));
        //balance
        int balance = balanceHelper(n);
        //find rotation case and do rotation if needed;
        n = rotating(balance, n);

        return n;

    }
    public Node findMinDeleteHelper(Node n) {
        while (n.getLeft() != null) {
            n = n.getLeft();
        }
        return n;

    }

    public boolean search(int data) {
        // TODO: Call the recursive helper method (or implement iteratively)
        boolean verdict = searchHelper(root, data);
        return verdict;

    }

    public int getHeight(Node n) {
        // TODO: Return the height of the root
        // TODO: Handle the case where tree is empty
        if(root == null){
            return -1;
        }
        if(n == null){
            return -1;
        }
        return heightHelper(0, n);
    }

    public int getSize() {
        // TODO: Call recursive helper method or implement iteratively
        return sizeHelper(root); //supposed to work??
    }

    public boolean isEmpty() {
        // TODO: Check if root is null
        //supposed to work??
        if(root == null){
            return true;
        }
        return false;
    }

    public List<Integer> inorderTraversal() {
        ArrayList<Integer> result = new ArrayList<>();
        // TODO: Call recursive helper method with result list
        return inorderTraversalHelper(root, result);
    }

    public List<Integer> preorderTraversal() {
        ArrayList<Integer> result = new ArrayList<>();
        // TODO: Call recursive helper method with result list
        return preorderTraversalHelper(root, result);
    }

    public List<Integer> postorderTraversal() {
        ArrayList<Integer> result = new ArrayList<>();
        // TODO: Call recursive helper method with result list
        return postorderTraversalHelper(root, result);
    }

    public int getMin() {
        // TODO: Check if tree is empty, throw exception if so
        // TODO: Traverse to the leftmost node
        // TODO: Return the minimum value
        if(root == null){
            throw new IllegalStateException("Tree is empty");
        }
        Node n = root;
        while(n.getLeft() != null){
            n = n.getLeft();
        }
        return n.getData();
    }

    public int getMax() {
        // TODO: Check if tree is empty, throw exception if so
        // TODO: Traverse to the rightmost node
        // TODO: Return the maximum value
        if(root == null){
            throw new IllegalStateException("Tree is empty");
        }
        Node n = root;
        while(n.getRight() != null){
            n = n.getRight();
        }
        return n.getData();
    }

    //HELPERS:::::
    public int sizeHelper(Node n){
        if(n == null) {
            return 0;
        }
        else {
            return 1 + sizeHelper(n.getLeft()) + sizeHelper(n.getRight());
        }
    }
    public int heightHelper(int maxHeight, Node n) {
        if(n == null) {
            return -1;
        }
        int leftHeight = heightHelper(0, n.getLeft());
        int rightHeight = heightHelper(0, n.getRight());
        if(leftHeight > rightHeight){
            maxHeight = leftHeight;
        }
        else {
            maxHeight = rightHeight;
        }
        return maxHeight + 1;
    }
    public boolean searchHelper(Node n, int value) {
        boolean searchResult = false;
        if(n != null){
            System.out.println("I'm checking: " + n.getData());
            if(n.getData() == value){
                System.out.println("true!");
                searchResult = true;
                return true;
            }
            else if(n.getData() > value){
                System.out.println("I'm gonna go left");
                n = n.getLeft();
            }
            else if(n.getData() < value){
                System.out.println("I'm gonna go right");
                n = n.getRight();
            }
            searchResult = searchHelper(n, value);
        }
        return searchResult;
    }
    private List<Integer> inorderTraversalHelper(Node n, ArrayList<Integer> myList) {
        if (n == null) {
            return myList;
        }

        inorderTraversalHelper(n.getLeft(), myList);
        myList.add(n.getData());
        inorderTraversalHelper(n.getRight(), myList);

        return myList;
    }
    private List<Integer> preorderTraversalHelper(Node n, ArrayList<Integer> myList) {
        if (n == null) {
            return myList;
        }

        myList.add(n.getData());
        preorderTraversalHelper(n.getLeft(), myList);
        preorderTraversalHelper(n.getRight(), myList);

        return myList;
    }
    private List<Integer> postorderTraversalHelper(Node n, ArrayList<Integer> myList) {
        if (n == null) {
            return myList;
        }

        postorderTraversalHelper(n.getLeft(), myList);
        postorderTraversalHelper(n.getRight(), myList);
        myList.add(n.getData());

        return myList;
    }

}
