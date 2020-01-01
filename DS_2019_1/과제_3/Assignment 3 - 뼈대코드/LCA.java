public class LCA {

    //2-a
    public static class ExtTNode {
        int value;
        ExtTNode left;
        ExtTNode right;
        ExtTNode parent;

        public ExtTNode(int value){
        }

        public void addLeft(ExtTNode node){
        }

        public void addRight (ExtTNode node){
        }


    }

    //2-b
    public static ExtTNode LCA(ExtTNode n1, ExtTNode n2 ){
        return null;
    }

    //2-c
    public static void PrintCommonPath(ExtTNode n1, ExtTNode n2 ){
        return ;
    }

    public static void main(String[] args) {

        /*
         * Tree construction with ExtNode
         *
         *       1
               /   \
              2     3
             / \   /  \
            4   5  6   7
               /    \
              8      9
         */

        ExtTNode root = new ExtTNode(1 );
        ExtTNode node2 =  new ExtTNode(2);
        ExtTNode node3 =  new ExtTNode(3);
        ExtTNode node4 =  new ExtTNode(4);
        ExtTNode node5 =  new ExtTNode(5);
        ExtTNode node6 =  new ExtTNode(6);
        ExtTNode node7 =  new ExtTNode(7);
        ExtTNode node8 =  new ExtTNode(8);
        ExtTNode node9 =  new ExtTNode(9);

        root.addLeft(node2);
        root.addRight(node3);
        root.left.addLeft(node4);
        root.left.addRight (node5);
        root.right.addLeft (node6);
        root.right.addRight (node7);
        root.left.right.addLeft(node8);
        root.right.left.addRight(node9);

        System.out.println("LCA : " + LCA(node8, node4));
        System.out.print("CommonPath : "); PrintCommonPath(node8, node4);

    }


}
