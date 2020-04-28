public interface BinaryTree {
    boolean isEmpty();

    Object root();

    BinaryTree removeLeftSubtree();

    BinaryTree removeRightSubtree();

    String preOrder();

    String inOrder();

    String postOrder();

    int size();

    int height();
}
