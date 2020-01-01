public class BinarySearchTree {
    TreeNode root;
    int[] inorderNum;

    public BinarySearchTree() {
        this.root = null;
        this.inorderNum = new int[1000];
        for(int i = 0; i<1000; i++)
            inorderNum[i] = -1;
    }
    public BinarySearchTree(TreeNode root) {
        this.inorderNum = new int[1000];
        this.root = root;
        for(int i = 0; i<1000; i++)
            inorderNum[i] = -1;
    }

    public void insert(int n) {


        if(root == null)
            root = new TreeNode(n);
        else if(root.data < n) {
            BinarySearchTree rightT = new BinarySearchTree(root.right);
            rightT.insert(n);
            if(root.right == null) {
                root.right = rightT.root;
                rightT.root.parent = root;
            }
        } else {
            BinarySearchTree leftT = new BinarySearchTree(root.left);
            leftT.insert(n);
            if(root.left == null) {
                root.left = leftT.root;
                leftT.root.parent = root;
            }
        }
    }

    public void deleteKey(int n) {
        TreeNode target = search(root, n);

        if(target == null)
            return;
        else {
            if(target.parent != null) {
                if(target.left == null && target.right == null) {
                    if(target.parent.left.equals(target)){
                        target.parent.left = null;
                    } else if(target.parent.right.equals(target)) {
                        target.parent.right = null;
                    }
                    target = new TreeNode();
                    return;
                }
                else if(target.left == null) {
                        TreeNode R = target.right;
                        TreeNode P = target.parent;
                        if(target.parent.left.equals(target)){
                            R.parent = P;
                            P.left = R;
                        } else if (target.parent.right.equals(target)){
                            R.parent = P;
                            P.right = R;
                        }
                        return;
                }
                else if(target.right == null) {
                    TreeNode L = target.left;
                    TreeNode P = target.parent;
                    if (target.parent.left.equals(target)) {
                        L.parent = P;
                        P.left = L;
                    } else if(target.parent.right.equals(target)) {
                        L.parent = P;
                        P.parent = L;
                    }
                    return;
                }
            } else if(target.parent == null) {
                BinarySearchTree rightT = new BinarySearchTree(root.right);
                BinarySearchTree leftT = new BinarySearchTree(root.left);
                String inorderS = rightT.getInorder(rightT.root) + leftT.getInorder(leftT.root);

                BinarySearchTree newT = new BinarySearchTree();
                int lastIndex = 0;
                for(int i = 0; i<inorderS.length(); i++) {
                    if(inorderS.substring(i, i+1).equals(" ")){
                        newT.insert(Integer.parseInt(inorderS.substring(lastIndex, i)));
                        lastIndex = i+1;
                    }
                    if(lastIndex == inorderS.length())
                        break;
                }
                this.root = null;
                this.root = newT.root;
            }
        }
    }

    public TreeNode search(int n) {
        return search(root, n);
    }

    public TreeNode search(TreeNode root, int n) {
        if(root == null)
            return null;
        else if(root.data == n)
            return root;
        else if(root.data < n)
            return search(root.right, n);
        else
            return search(root.left, n);
    }

    public void inorder() {
        System.out.println(getInorder(root));
    }

    public String getInorder(TreeNode root) {
        String line = "";
        if(root != null) {
            line += getInorder(root.left);
            line += root.data + " ";
            line += getInorder(root.right);
        }
        return line;
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);

        System.out.println("Inorder traversal of the given tree");
        tree.inorder();

        System.out.println("\nDelete 20");
        tree.deleteKey(20);
        System.out.println("Inorder traversal of the modified tree");
        tree.inorder();

        System.out.println("\nDelete 30");
        tree.deleteKey(30);
        System.out.println("Inorder traversal of the modified tree");
        tree.inorder();

        System.out.println("\nDelete 50");
        tree.deleteKey(50);
        System.out.println("Inorder traversal of the modified tree");
        tree.inorder();

    }

    public static class TreeNode {
        int data;
        TreeNode right;
        TreeNode left;
        TreeNode parent;

        public TreeNode() {
            this.right = this.left = this.parent = null;
            this.data = 0;
        }

        public TreeNode(int n) {
            this.data = n;
        }

    }


}
