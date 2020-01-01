//2014-19498 eunjoo jung
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class MyTree {
    MyTreeNode root;

    // 1 - a
    MyTree(String preorder, String inorder) {
        if(preorder.equals("") || preorder.length() != inorder.length())
            this.root = null;

        else {
            this.root = new MyTreeNode(preorder.substring(0, 1));
            constructor(preorder, inorder);
        }

        if(preorder.length() == levelorder().length() && root != null) {
            if(preorder.equals(preorder()) && inorder.equals(inorder()))
                System.out.println("Done");
            else {
                System.out.println("Unaccepted input");
                this.root = null;
            }
        }
        else
        {
            this.root = null;
            System.out.println("Unaccepted input");
        }
    }

    MyTree(String preorder, String inorder, int check)
    // this constructor is for adding sub trees. (check is for overloading)
    {
        if(preorder.equals("") || preorder.length() != inorder.length())
            this.root = null;
        else {
            this.root = new MyTreeNode(preorder.substring(0, 1));
            constructor(preorder, inorder);
        }
    }

    void constructor(String preorder, String inorder) {
        String leftPre = "";
        String rightPre = "";
        String leftIn = "";
        String rightIn = "";

        if(preorder.length() == 0 || inorder.length() == 0)
            return ;

        else {
           for (int i = 0; i < inorder.length(); i++) {
                if (inorder.substring(i, i + 1).equals(this.root.key)) {
                    if (i != 0)
                        leftIn = inorder.substring(0, i);
                    if (i != inorder.length() - 1)
                        rightIn = inorder.substring(i + 1);
                }
            }

           if(leftIn.length() != 0)
                leftPre = preorder.substring(1, leftIn.length()+1);

           if(rightIn.length() != 0)
                rightPre = preorder.substring(preorder.length() - rightIn.length());
        }

        MyTree rightT = new MyTree(rightPre, rightIn, 1);
        MyTree leftT = new MyTree(leftPre, leftIn, 1);
        this.root.right = rightT.root;
        this.root.left = leftT.root;
    }

    String inorder() {
       String line = getInorder(root);
       return line;
    }

    String getInorder(MyTreeNode root) {
       String line = "";
       if(root != null){
           line += getInorder(root.left);
           line += root.key;
           line += getInorder(root.right);
       }
       return line;
    }

    String preorder() {
       String line = getPreorder(root);
       return line;
    }

    String getPreorder(MyTreeNode root) {
       String line = "";
       if(root != null){
           line += root.key;
           line += getPreorder(root.left);
           line += getPreorder(root.right);
       }
       return line;
    }

    // 1 - b
    String postorder(){
        String line = getPostorder(root);
        return line;
    }

    String getPostorder(MyTreeNode root) {
        String line = "";

        if(root != null){
            line += getPostorder(root.left);
            line += getPostorder(root.right);
            line += root.key;
        }
        return line;
    }

    // 1 - c
    String levelorder() {
        String line = "";

        Queue<MyTreeNode> queue = new LinkedList<>();

        if(root == null)
            return line;
        else
            queue.add(root);

        while(!queue.isEmpty())
        {
            MyTreeNode tree = queue.poll();
            line = line.concat(tree.key);

            if(tree.left != null)
                queue.add(tree.left);
            
            if(tree.right != null)
                queue.add(tree.right);
        }
        return line;
    }

    class MyTreeNode {
    MyTreeNode right;
    MyTreeNode left;
    String key;

    MyTreeNode(String key){
        this.right = this.left = null;
        this.key = key;
    }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("myTreeInput.txt");
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            String[] inputs = input.split("\t");

            System.out.println("Input : " + inputs[0] + ", " + inputs[1]);
            System.out.print("Making tree...  ");
            MyTree myTree = new MyTree(inputs[0], inputs[1]);
            System.out.print("Report tree postorder : ");
            System.out.println(myTree.postorder());
            System.out.print("Report tree level : ");
            System.out.println(myTree.levelorder());
            System.out.println();
        }
    }
}




