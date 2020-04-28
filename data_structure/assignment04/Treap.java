//2014-19498 jung eunjoo
import java.util.Random;

class TreapNode {
    TreapNode left;
    TreapNode right;
    TreapNode parent;

    Object key;
    int priority;

    public TreapNode() {
        this.left = null;
        this.right = null;
        this.parent = null;
        this.key = null;
        this.priority  = 0;
    }
}

public class Treap {
    TreapNode root;

    Treap() {
        this.root = null;
    }

    Treap(TreapNode root) {
        this.root = root;
    }

    // inserting the Key from the TestTreap class
    public void insert(Object X) {
        Random rand = new Random();
        TreapNode temp = new TreapNode();

        temp.key = X;
        temp.priority = rand.nextInt(100);

        if(root == null) this.root = temp;
        else if(String.valueOf(X).compareTo(String.valueOf(root.key)) < 0)
        {
            Treap newTreap = new Treap(root.left);
            newTreap.insert(X);
            root.left = newTreap.root;
            newTreap.root.parent = root;

            if(root.left != null)
                if (root.left.priority > root.priority) root = rotationRight(root);
        }
        else
        {
            Treap newTreap = new Treap(root.right);
            newTreap.insert(X);
            root.right = newTreap.root;
            newTreap.root.parent = root;

            if(root.right != null)
                if (root.right.priority > root.priority) root = rotationLeft(root);
        }
    }

    // only for the union method when insertion the priorities and keys
    // from one tree to another tree
    public void insert(Object X, int priority) {
        TreapNode temp = new TreapNode();

        temp.key = X;
        temp.priority = priority;

        if(root == null) this.root = temp;
        else if(String.valueOf(X).compareTo(String.valueOf(root.key)) < 0)
        {
            Treap newTreap = new Treap(root.left);
            newTreap.insert(X, priority);
            root.left = newTreap.root;
            newTreap.root.parent = root;

            if(root.left != null)
                if(root.left.priority > root.priority) root = rotationRight(root);
        }
        else
        {
            Treap newTreap = new Treap(root.right);
            newTreap.insert(X, priority);
            root.right = newTreap.root;
            newTreap.root.parent = root;

            if (root.right != null)
                if (root.right.priority > root.priority) root = rotationLeft(root);
        }
    }

    public TreapNode rotationRight(TreapNode t) {
        TreapNode parent = t.parent;
        TreapNode L = t.left;
        TreapNode LR = L.right;
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

    public TreapNode rotationLeft(TreapNode t) {
       TreapNode parent = t.parent;
       TreapNode R = t.right;
       TreapNode RL = R.left;
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

    public void search(Object X) {
        if(search(root, X) == null)
            System.out.println(X + " Not Found");
        else
            System.out.println(X + " Found");
    }


    public TreapNode search(TreapNode node, Object X) {
        if(node == null || node.key.equals(X))
            return node;
        else if(node.key.toString().compareTo(X.toString()) < 0)
            return search(node.right, X);
        else
            return search(node.left, X);
    }

    public void remove(Object X) {
        System.out.println("Delete " + X);
        if(this.root == null)
            return;

        TreapNode target = search(root, X);
        //target is null;
        if(target == null)
            return;

        if(root.key.toString().equals(X) && root.parent == null
                && root.right == null && root.left == null)
        {
            this.root = null;
            return;
        }

        // when parent is null;
        if(target.parent == null)
        {
            if (target.left == null && target.right != null)
            {
                this.root = target.right;
                root.parent = null;
                return;
            }
            else if(target.left != null && target.right == null)
            {
                this.root = target.left;
                root.parent = null;
                return;
            }

            if(root.left != null && root.right != null)
            {
                TreapNode left = target.left;
                TreapNode right = target.right;
                Treap leftT = new Treap(left);
                Treap rightT = new Treap(right);
                this.root = union(leftT, rightT).root;
            }
            return;
        }

        // else if parent is not null;
       if(target != null)
       {
            target.priority = -19999;
            while(target.right != null || target.left != null)
            {
                if(target.right != null)
                {
                    if(target.priority < target.right.priority)
                        rotationLeft(target);

                        target = search(root, X);
                }
                if(target.left != null)
                {
                    if(target.priority < target.left.priority)
                        rotationRight(target);

                        target = search(root, X);
                }
            }
            remove(search(root, X), X);
        }
    }

    public void remove(TreapNode root, Object X)
    {   // this method is only when parent is not null!
        if(root == null) return;
        TreapNode p = root.parent;

            if (root.key.equals(X))
            {
                if (p.left != null)
                {
                    if (p.left.equals(root))
                    {
                        p.left = null;
                        return;
                    }
                }
                if (p.right != null)
                    if (p.right.equals(root)) p.right = null;
            }
            else if (root.key.toString().compareTo(X.toString()) >= 0)
                remove(root.left, X);
            else if (root.key.toString().compareTo(X.toString()) < 0)
                remove(root.right, X);
       return;
    }

    public void build(Object[] X)
    {
        for(int i = 0; i< X.length; i++)
        {
            if(X[i] != null)
            this.insert(X[i]);
        }
    }

    public Treap union(Treap T1, Treap T2) {
        // when one of Treap trees is empty
        if(T1 == null) return T2;
        else if(T2 == null) return T1;

        int index = 0;
        int j = 0;

        // when T1's key comes to root, inserting the keys and priorities of T2 to T1
        if(T1.root.key.toString().compareTo(T2.root.key.toString()) > 0) {
            String inputLine = getString(T1.root);
            String priorityLine = getPriority(T1.root);

            T1.root = null;
            int[] priorityArr = new int[inputLine.length()];
            for(int i = 0; i< priorityLine.length(); i++)
            {
                if(priorityLine.substring(i, i+1).equals(" "))
                {
                    priorityArr[j++] = Integer.parseInt(priorityLine.substring(index, i));
                    index = i+1;
                }
            }

            if(T2.root.parent != null) T2.root.parent = null;
            for(int i = 0; i < inputLine.length(); i++)
            {
                String key = inputLine.substring(i, i+1);
                T2.insert(key, priorityArr[i]);
            }
            return T2;

        }
        // when T2's key comes to root, inserting the keys and priorities of T1 to T2
        else {
            String inputLine = getString(T2.root);
            String priorityLine = getPriority(T2.root);
            T2.root = null;
            int[] priorityArr = new int[inputLine.length()];

            for(int i= 0; i < priorityLine.length(); i++)
            {
                if(priorityLine.substring(i, i+1).equals(" "))
                {
                    priorityArr[j++] = Integer.parseInt(priorityLine.substring(index, i));
                    index = i+1;
                }
            }
            if(T1.root.parent != null) T1.root.parent = null;
            for(int i = 0; i < inputLine.length(); i++)
            {
                String key = inputLine.substring(i, i+1);
                T1.insert(key, priorityArr[i]);
            }
            return T1;
        }
    }

    // for the union method to keep using same priority.
    public String getPriority(TreapNode root)
    {
        return InorderPriority(root);
    }

    // for the union method to keep using same priority.
    public String InorderPriority(TreapNode root)
    {
        String line = "";
        if(root != null)
        {
            line += InorderPriority(root.left);
            line += root.priority + " ";
            line += InorderPriority(root.right);
        }
        return line;
    }

    // for the union method
    public String getString(TreapNode root)
    {
        return inorderString(root);
    }

    // for the union method
    public String inorderString(TreapNode root)
    {
        String line = "";
        if(root != null)
        {
            line += inorderString(root.left);
            line += root.key;
            line += inorderString(root.right);
        }
        return line;
    }

    public void Inorder() {
        Inorder(this.root);
        System.out.println("\n");
    }

    public void Inorder(TreapNode root) {
        if(root != null)
        {
            Inorder(root.left);
            System.out.print("(key, priority): (" + root.key + " , " + root.priority + ") ");
            if(root.left != null)
                System.out.print(" left child " + root.left.key);

            if(root.right != null)
                System.out.print(" , right child " + root.right.key + "\n");

            else 
                System.out.println();

            Inorder(root.right);
        }

    }
}
