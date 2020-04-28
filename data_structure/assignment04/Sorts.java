//2014-19498 jung eunjoo
import java.io.*;
import java.util.Random;
import java.io.FileNotFoundException;

public class Sorts {
    private static int inputNum;
    private static PrintStream writer;

    public static void main(String[] args) throws FileNotFoundException {
        inputNum = Integer.parseInt(args[0]);
        writer = new PrintStream(new File("output.txt"));
        System.out.println(timeMeasure("HEAP") + " ms heapsort (without heap initialization)");
        System.out.println();
        System.out.println(timeMeasure("HEAP_INIT") + " ms heapsort");
        System.out.println();
        System.out.println(timeMeasure("BST") + " ms BST sort");
        System.out.println();
        System.out.println(timeMeasure("SPLAY") + " ms splay tree sort");
        System.out.println();
        System.out.println("the sorted file is output.txt");
    }

    public static long timeMeasure(String name) {
        long startTime;
        long endTime;
        long totalTime = 0;
        long enlapsedTime;
        int i = 0;

        switch (name) {
            case ("BST"):
                while (i++ < 100) {
                    startTime = System.currentTimeMillis();

                    BST bstTree = new BST();
                    writer.println("BST sort : ");
                    writer.println(bstTree.bstSort());
                    writer.println();

                    endTime = System.currentTimeMillis();
                    enlapsedTime = endTime - startTime;
                    totalTime += enlapsedTime;
                    writer.println();
                }

            case ("HEAP_INIT"):
                while (i++ < 100) {
                    startTime = System.currentTimeMillis();

                    heapTable heap = new heapTable(1);
                    heap.heapSortWithInit();
                    writer.println( "Heap sort : ");
                    for (int j = 1; j < inputNum + 1; j++)
                        writer.print(heap.key[j] + " ");
                    writer.println();

                    endTime = System.currentTimeMillis();
                    enlapsedTime = endTime - startTime;
                    totalTime += enlapsedTime;
                    writer.println();
                }

            case ("HEAP"):
                while (i++ < 100) {
                    startTime = System.currentTimeMillis();

                    heapTable heap = new heapTable();
                    heap.heapSort();
                    writer.println("Heap sort(without initialization) : ");
                    for (int j = 1; j < inputNum + 1; j++)
                        writer.print(heap.key[j] + " ");
                    writer.println();

                    endTime = System.currentTimeMillis();
                    enlapsedTime = endTime - startTime;
                    totalTime += enlapsedTime;
                    writer.println();
                }

            case ("SPLAY"):
                while (i++ < 100) {
                    startTime = System.currentTimeMillis();

                    splayTree splay = new splayTree();
                    writer.println("Splay sort: ");
                    writer.println(splay.splaySort());
                    writer.println();

                    endTime = System.currentTimeMillis();
                    enlapsedTime = endTime - startTime;
                    totalTime += enlapsedTime;
                    writer.println();
                }
        }
        return totalTime;
    }

    public static class BST {
        TreeNode root;
        String[] bst;

        BST()
        {
            this.root = null;
            Random rand = new Random();

            for (int i = 0; i < inputNum; i++)
                insert(rand.nextInt(inputNum * 20));

            this.bst = new String[inputNum];
        }

        BST(TreeNode root)
        {
            this.root = root;
        }


        public String bstSort()
        {
            return Inorder(root);
        }

        public String Inorder(TreeNode root)
        {
            String line = "";
            if (root != null) {
                line += Inorder(root.left);
                line += root.key + " ";
                line += Inorder(root.right);
            }
            return line;
        }

        public void insert(int key)
        {
            if(root == null) {
                root = new TreeNode(key);
            }
            else if(root.key <= key)
            {
                BST rightT = new BST(root.right);
                rightT.insert(key);
                root.right = rightT.root;
            }
            else if(root.key > key)
            {
                BST leftT = new BST(root.left);
                leftT.insert(key);
                root.left = leftT.root;
            }
        }
    }

    public static class heapTable
    {
        int[] key;
        int index;
        int s;

        //keep inserting numbers to empty heap (without initialization)
        public heapTable()
        {
            key = new int[inputNum+1];
            key[0] = -1;
            index = 0;
            s = 0;

            Random rand = new Random();
            for(int i = 1; i< inputNum+1; i++)
                heapinsert(rand.nextInt(inputNum * 20));
        }

        // unsorted array (for the heapSort with Initialization)
        public heapTable(int n)
        {
            key = new int[inputNum+1];
            key[0] = -1;
            index = 0;
            s = 0;

            Random rand = new Random();
            for (int i = 1; i < inputNum+1; i++)
                key[i] = rand.nextInt(inputNum * 20);
        }

        public void heapSortWithInit()
        {
            int size = inputNum+1;
            buildheap(size);

            for (int i = inputNum; i >= 1; i--) {
                int tmp = key[i];
                key[i] = key[1];
                key[1] = tmp;
                buildheap(--size);
            }
        }

        public void heapSort()
        {
            int size = inputNum+1;
            for (int j = inputNum; j >= 1; j--)
            {
                int tmp = key[j];
                key[j] = key[1];
                key[1] = tmp;
                buildheap(--size);
            }
        }

        public void heapinsert(int n)
        {
            index = (s++)+1;
            key[index] = n;
            buildheap(s);
        }

        public void buildheap(int n)
        {
            for(int i = n / 2; i >= 1; i--)
                build(i, n);
        }

        public void build(int i, int n)
        {
            int left = 2 * i;
            int right = 2 * i + 1;
            int larger = i;

            if (right < n && key[right] >= key[larger])
                larger = right;

            if (left < n && key[left] >= key[larger])
                larger = left;

            if (larger != i)
            {
                int tmp = key[larger];
                key[larger] = key[i];
                key[i] = tmp;
                build(larger, n);
            }
        }
    }

    public static class splayTree
    {
        TreeNode root;
        int key;

        public splayTree()
        {
            this.root = null;
            this.key = 0;

            Random rand = new Random();
            for(int i = 0; i < inputNum; i++)
                this.insert(rand.nextInt(inputNum * 20));
        }

        public splayTree(TreeNode root)
        {
            this.root = root;
        }

        public TreeNode rotationRight(TreeNode t) {
            TreeNode parent = t.parent;
            TreeNode L = t.left;
            TreeNode LR = L.right;
            L.parent = t.parent;

            if(parent != null)
            {
                if(parent.left != null)
                    if (parent.left.equals(t)) parent.left = L;

                if(parent.right != null)
                    if(parent.right.equals(t)) parent.right = L;
            }
            t.left = LR;
            if(LR != null) LR.parent = t;

            t.parent = L;
            L.right = t;
            return L;
        }

        public TreeNode rotationLeft(TreeNode t) {
            TreeNode parent = t.parent;
            TreeNode R = t.right;
            TreeNode RL = R.left;
            R.parent = t.parent;

            if(parent != null)
            {
                if(parent.left != null)
                    if(parent.left.equals(t)) parent.left = R;

                if(parent.right != null)
                    if(parent.right.equals(t)) parent.right = R;
            }
            t.parent = R;
            if(RL != null) RL.parent = t;

            t.right = RL;
            R.left = t;
            return R;
        }

        public void insert(int n)
        {
            if(root == null) {
                this.root = new TreeNode(n);
                splay(root);
            }
            else if(root.key <= n)
            {
                splayTree rightT = new splayTree(root.right);
                rightT.insert(n);
                root.right = rightT.root;
                rightT.root.parent = root;
            }
            else if(root.key > n)
            {
                splayTree leftT = new splayTree(root.left);
                leftT.insert(n);
                root.left = leftT.root;
                leftT.root.parent = root;
            }
        }

        public void splay(TreeNode node)
        {
            if(node.parent != null) {
                if(node.parent.parent != null)
                {
                    if(node.parent.parent.left.equals(node.parent))
                    {
                        if(node.parent.left.equals(node)) {
                            rotationRight(node.parent.parent);
                            rotationLeft(node.parent);
                        }
                        else{
                            rotationLeft(node.parent);
                            rotationRight(node.parent);
                        }
                    } else{
                        if(root.parent.left.equals(node))
                        {
                            rotationRight(node.parent);
                            rotationLeft(node.parent);
                        }
                        else
                        {
                            rotationLeft(node.parent.parent);
                            rotationLeft(node.parent);
                        }
                    }
                    splay(node);
                }
                else
                {
                    if(root.parent.left.equals(root))
                        rotationRight(root.parent);
                    else
                        rotationLeft(root.parent);
                }
            }
        }

        public String inorder(TreeNode root)
        {
            String line = "";
            if (root != null)
            {
                line += inorder(root.left);
                line += root.key + " ";
                line += inorder(root.right);
            }
            return line;
        }

        public String splaySort() {
            return inorder(root);
        }
    }

    public static class TreeNode
    {
        TreeNode left;
        TreeNode right;
        TreeNode parent;
        int key;

        TreeNode(int n) {
            this.key = n;
        }
    }
}