//2014-19498 eunjoo jung
public class LCA {

    //2-a
    public static class ExtTNode{
        int value;
        ExtTNode left;
        ExtTNode right;
        ExtTNode parent;

        public ExtTNode() {
            this.value = 0;
            this.left = this.right = this.parent = null;
        }

        public ExtTNode(int value) {
            this.value = value;
            this.left = this.right = this.parent = null;
        }


        public void addLeft(ExtTNode node) {
            this.left = node;
            node.parent = this;
        }

        public void addRight (ExtTNode node) {
            this.right = node;
            node.parent = this;
        }

        public String toString() {
            return Integer.toString(this.value);
        }
    }

    //2-b
    public static ExtTNode LCA(ExtTNode n1, ExtTNode n2) {
        ExtTNode[] n1Arr = new ExtTNode[1000];
        ExtTNode[] n2Arr = new ExtTNode[1000];

        int i = 0;
        while (n1.parent != null) {
            n1Arr[i++] = n1.parent;
            n1 = n1.parent;
        }

        int j = 0;
        while (n2.parent != null) {
            n2Arr[j++] = n2.parent;
            n2 = n2.parent;
        }

        ExtTNode target = new ExtTNode();
        for(int k = 0; k < i; k++) {
            for(int m = 0; m < j; m++) {
                if(n1Arr[k].value == n2Arr[m].value) {
                    target = n1Arr[k];
                    return target;
                }
            }
        }
        return target;
    }

    //2-c
    public static void PrintCommonPath(ExtTNode n1, ExtTNode n2 ){
        ExtTNode[] arr1 = new ExtTNode[1000];
        ExtTNode[] arr2 = new ExtTNode[1000];
        String line = "";

        int i = 0;
        while(true) {
            if(n1.parent != null) {
                arr1[i++] = n1.parent;
                n1 = n1.parent;
            }
            else
                break;
        }

        int j = 0;
        while(true) {
            if(n2.parent != null) {
                arr2[j++] = n2.parent;
                n2 = n2.parent;
            }
            else
                break;
        }

        for(int m = 0; m<i; m++){
            for(int n = 0; n<j; n++){
                if(arr1[m].value == arr2[n].value){
                    line += arr1[m].value + ("->");
                }
            }
        }
        System.out.println(line.substring(0, line.length()-2));
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
