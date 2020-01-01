public class myTree implements BinaryTree {
    BinaryTreeNode root;

    public myTree() {
        root = null;
    }

    public myTree(BinaryTreeNode node) {
        this.root = node;
    }

        public boolean isEmpty () {
            return root()==null;
        }

        public BinaryTreeNode root() {
            return root;
        }

        public BinaryTree removeLeftSubtree () {
            myTree temp;

            if(isEmpty()) throw new IllegalArgumentException();
            else {
                temp = new myTree(root.left);
                root.left = null;
            }
            return temp;
        }

        public BinaryTree removeRightSubtree () {
            myTree temp;

            if(isEmpty()) throw new IllegalArgumentException();
            else {
                temp = new myTree(root.right);
                root.right = null;
            }

            return temp;
        }

        public String preOrder () {
            String preorder = "";
            if(root != null)
            {
                preorder += root.val + " ";

                if(root.left != null) {
                    myTree temp = new myTree();
                    temp = new myTree(root.left);
                    preorder += temp.preOrder();
                }
                if(root.right != null){
                    myTree temp = new myTree();
                    temp = new myTree(root.right);
                    preorder += temp.preOrder();
                }

            }
            return preorder;
        }

        public String inOrder () {
            String inOrder = "";
            if(root != null){
                if(root.left != null){
                    myTree temp = new myTree();
                    temp = new myTree(root.left);
                    inOrder += temp.inOrder();
                }
                inOrder += root.val + " ";

                if(root.right != null){
                    myTree temp = new myTree();
                    temp = new myTree(root.right);
                    inOrder += temp.inOrder();
                }
            }

            return inOrder;
        }

        public String postOrder () {
            String postorder = "";
            if(root != null) {
                if(root.left != null) {
                    myTree temp = new myTree(root.left);
                    postorder += temp.postOrder();
                }
                if(root.right != null) {
                    myTree temp = new myTree(root.right);
                    postorder += temp.postOrder();
                }
                postorder += root.val + " ";
            }


            return postorder;
        }

        public int size () {
         int count = 0;

            if(root != null) {
                count++;

                if(root.left != null) {
                    myTree temp = new myTree(root.left);
                    count += temp.size();
                }

                if(root.right != null) {
                    myTree temp = new myTree(root.right);
                    count += temp.size();
                }
            }
            return count;
        }

        public int height () {
            int count = 0;
            int tempR = 0;
            int tempL = 0;

                if(root != null) {
                    count++;

                    if(root.left != null){
                        myTree temp = new myTree(root.left);
                        tempR += temp.height();
                    }
                    if(root.right != null) {
                        myTree temp = new myTree(root.right);
                        tempL += temp.height();
                    }
                        count += (tempL > tempR) ? tempL : tempR;

                }

            return count;
        }



    public static class BinaryTreeNode {
        BinaryTreeNode left;
        BinaryTreeNode right;
        Object val;

        public BinaryTreeNode(int n) {
            this.val = n;
            this.left = null;
            this.right = null;
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



}
