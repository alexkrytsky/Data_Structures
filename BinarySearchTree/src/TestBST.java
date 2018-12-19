public class TestBST {
    public static void main(String[] args) {
        BinaryTree<Integer> bst = new BinaryTree<>();

        bst.add(60);
        bst.add(41);
        bst.add(74);
        bst.add(16);
        bst.add(53);
        bst.add(46);
        bst.add(55);
        bst.add(42);


        System.out.println(bst.contains(60));
        System.out.println(bst.contains(74));
        System.out.println(bst.contains(9));

//        bst.remove(60);
        System.out.println(bst.contains(60));

//        bst.inOrder();
//        bst.preOrder();
//        bst.postOrder();

        for (int element : bst){
            System.out.println(element);
            String str = "asf";
            str.length();
        }


    }
}
