// 더 나은 버전!!!!

public class myTree implements BinaryTree {
    BinaryTreeNode root;

    public myTree() {
        this.root = null;
    }

    public myTree(BinaryTreeNode node) {
        root = node;
    }


    public static class BinaryTreeNode {
        BinaryTreeNode left;
        BinaryTreeNode right;
        int val;

        public BinaryTreeNode(int n) {
            this.val = n;
        }
    }

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


    @Override
    public boolean isEmpty() {
        return root() == null;
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public BinaryTree removeLeftSubtree() {
        myTree temp;

        if(isEmpty()) throw new IllegalArgumentException();
        else {
            temp = new myTree(root.left);
            root.left = null;
        }

        return temp;
    }

    @Override
    public BinaryTree removeRightSubtree() {
        myTree temp;

        if(isEmpty()) throw new IllegalArgumentException();
        else {
            temp = new myTree(root.right);
            root.right = null;
        }
        return temp;
    }

    @Override
    public String preOrder() {
        String preOrder = "";

        if(root != null) {
            preOrder += root.val + " ";
            myTree tmp = new myTree(root.left);
            preOrder += tmp.preOrder();
            tmp = new myTree(root.right);
            preOrder += tmp.preOrder();
        }
        return preOrder;
    }

    @Override
    public String inOrder() {
        String inOrder = "";

        if(root != null) {
            myTree tmp = new myTree(root.left);
            inOrder += tmp.inOrder();
            inOrder += root.val + " ";
            tmp = new myTree(root.right);
            inOrder += tmp.inOrder();
        }
        return inOrder;
    }

    @Override
    public String postOrder() {
        String postOrder = "";

        if(root != null) {
            myTree tmp = new myTree(root.left);
            postOrder += tmp.postOrder();
            tmp = new myTree(root.right);
            postOrder += tmp.postOrder();
            postOrder += root.val + " ";
        }
        return postOrder;
    }

    @Override
    public int size() {
        int size = 0;

        if(root != null) {
            size++;
            myTree tmp = new myTree(root.left);
            size += tmp.size();
            tmp = new myTree(root.right);
            size += tmp.size();
        }
        return size;
    }

    @Override
    public int height() {
        int height = 0;
        int hL = 0;
        int hR = 0;

        if(root != null) {
            height++;
            myTree tmp = new myTree(root.left);
            hL += tmp.height();
            tmp = new myTree(root.right);
            hR += tmp.height();

            height += (hL > hR) ? hL : hR;
        }

        return height;
    }
}
