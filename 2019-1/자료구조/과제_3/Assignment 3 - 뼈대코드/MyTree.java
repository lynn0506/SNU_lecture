import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


@SuppressWarnings("Duplicates")
public class MyTree {


    // 1 - a
    MyTree(String preorder, String inorder){
        System.out.println("Unaccepted input");

    }

    // 1 - b
    String postorder(){
        return "";
    }

    //1 - c
    String levelorder(){
        return "";
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
