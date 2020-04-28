
public class myTree {

    public static void main(String[] args) {

        myTree tree = new myTree();
        tree.root = new BinaryTreeNode(1);
        tree.root.left = new BinaryTreeNode(2);
        tree.root.right = new BinaryTreeNode(3);
        tree.root.left.left = new BinaryTreeNode(4);
        tree.root.left.right = new BinaryTreeNode(5);
        tree.root.right.left = new BinaryTreeNode(6);
        tree.root.right.right = new BinaryTreeNode(7);
        tree.root.left.left.left = new BinaryTreeNode(8);
        tree.root.left.left.right = new BinaryTreeNode(9);
        tree.root.left.right.left = new BinaryTreeNode(10);
        tree.root.left.right.right = new BinaryTreeNode(11);
        tree.root.left.right.left.left = new BinaryTreeNode(12);
        tree.root.left.right.left.right = new BinaryTreeNode(13);


        System.out.println("Preorder sequence is ");
        System.out.println(tree.preOrder());
        System.out.println();

        System.out.println("Inorder sequence is ");
        System.out.println(tree.inOrder());
        System.out.println();

        System.out.println("Postorder sequence is ");
        System.out.println(tree.postOrder());
        System.out.println();

        System.out.println("Number of nodes = " + tree.size());
        System.out.println();

        System.out.println("Height = " + tree.height());
        System.out.println();

        myTree subTree = new myTree(tree.root.left.right);
        subTree.removeLeftSubtree();

        System.out.println("Preorder sequence is ");
        System.out.println(tree.preOrder());
        System.out.println();

        System.out.println("Number of nodes = " + tree.size());
        System.out.println();

        System.out.println("Height = " + tree.height());
        System.out.println();

        tree.removeLeftSubtree();

        System.out.println("Postorder sequence is ");
        System.out.println(tree.postOrder());
        System.out.println();

        System.out.println("Number of nodes = " + tree.size());
        System.out.println();

        System.out.println("Height = " + tree.height());
        System.out.println();



    }



}
