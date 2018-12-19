import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class BinaryTree<T extends Comparable<T>> implements Iterable<T> {
    private Node root;
    private int size;

    public BinaryTree() {

    }

    //basic methods
    public void add(T element) {
        if (root == null) {
            root = new Node(element);
            size++;//don't forget this part!!
        } else {
            //we need recursively find the spot
            root = add(element, root);
        }
    }

    private Node add(T element, Node curent) {
        //if twe have a null current node, we found needed spot. BASE CASE
        if (curent == null) {
            size++;
            return new Node(element);
        }

        //are we looking to the left and right
        int compare = curent.data.compareTo(element);

        if (compare < 0) {
            curent.right = add(element, curent.right);
        } else if (compare > 0) {
            curent.left = add(element, curent.left);
        }
        return curent;
    }

    //returns true if the elements is found and removes it.
    public void remove(T element) {
        root = remove(element, root);
    }

    public Node remove(T element, Node curent) {
        //base case
        if (curent == null) {
            return null;//not found
        }

        int compare = curent.data.compareTo(element);

        if (compare < 0) {
            curent.right = remove(element, curent.right);
        } else if (compare > 0) {
            curent.left = remove(element, curent.left);

        } else {
            //two childrens
            if (curent.left != null && curent.right != null) {
                //replace the data at our current node
                Node maxLeft = findMax(curent.left);
                curent.data = maxLeft.data;

                //remove largest element in the left subtree
                curent.left = remove(maxLeft.data, curent.left);

                //size-- is not necessary
            } else if (curent.left != null) {//one child
                curent = curent.left;
                size--;
            } else if (curent.right != null) {//one child
                curent = curent.right;
                size--;
            } else {//no children
                curent = null;
                size--;
            }
        }
        return curent;
    }

    private Node findMax(Node curent){
        if(curent.right != null){
            return findMax(curent.right);
        }

        return curent;
    }

    public boolean contains(T element) {
        return contains(element, root);
    }

    public boolean contains(T element, Node curent) {
        if (curent == null) {
            return false;
        }

        int compare = curent.data.compareTo(element);
        if (compare < 0) {
            return contains(element, curent.right);
        }
        if (compare > 0) {
            return contains(element, curent.left);
        } else {
            return true;
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }


    public void inOrder(){
        inOrder(root);
    }

    private void inOrder(Node curent){
        if(curent != null){
            inOrder(curent.left);
            System.out.println(curent.data);//node
            inOrder(curent.right);
        }
    }

    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node curent){
        if(curent != null){
            postOrder(curent.left);
            postOrder(curent.right);
            System.out.println(curent.data);//node
        }
    }

    public void preOrder(){
        preOrder(root);

    }

    private void preOrder(Node curent){
        if(curent != null){
            System.out.println(curent.data);//node
            preOrder(curent.left);
            preOrder(curent.right);
        }
    }

    public List<T> toList(){
        ArrayList<T> results = new ArrayList<>();
        toList(root, results);
        return results;

    }

    private void toList(Node curent, List<T> results){
        if(curent != null){
            inOrder(curent.left);
            results.add(curent.data);
            inOrder(curent.right);
        }
    }

    private class BSTIterator implements Iterator<T>{
        private Stack<Node> nodeStack = new Stack<>();

        public BSTIterator(Node curent){
            //move to the first node
            while (curent != null){
                nodeStack.push(curent);
                curent = curent.left;
            }
        }

        public boolean hasNext(){
            return !nodeStack.isEmpty();
        }

        public T next(){
            //1. get the next element to report
            Node next = nodeStack.pop();

            //2. if there is a right sub-tree, find the smallest element
            if(next.right!=null){
                //add the right child
                nodeStack.push(next.right);

                //and dive to the left of out right child
                Node curent = next.right;
                while (curent.left != null){
                    nodeStack.push(curent.left);
                    curent = curent.left;
                }
            }
            return next.data;
        }
    }

    private class NaiveIterator implements Iterator<T>{
        private Object[] data;
        private int position;

        public NaiveIterator(BinaryTree owner){
            data = owner.toList().toArray();
        }

        public boolean hasNext(){
            return position < data.length;
        }

        public T next(){
            return (T)data[position++];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new BSTIterator(root);
    }

    //binary tree node
    private class Node {
        private T data;
        private Node left;
        private Node right;

        public Node(T data) {
            this.data = data;
        }

        public String toString() {
            String dataString = (data == null) ? "null" : data.toString();
            String leftChild = (left == null) ? "null" : left.data.toString();
            String rightChild = (right == null) ? "null" : right.data.toString();

            return leftChild + " <-- " + dataString + " --> " + rightChild;


        }

    }
}
