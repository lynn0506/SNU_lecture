public class Lab5 {

    public static void main(String[] args) {

        /*
         * Tower of Hanoi expected answer:

         *  Problem #1

            The moves are:
            Move top disk from tower 1 to top of tower 2
            Move top disk from tower 1 to top of tower 3
            Move top disk from tower 2 to top of tower 3
            Move top disk from tower 1 to top of tower 2
            Move top disk from tower 3 to top of tower 1
            Move top disk from tower 3 to top of tower 2
            Move top disk from tower 1 to top of tower 2
         */

        System.out.println(); System.out.println("Problem #1"); System.out.println();
        System.out.println("The moves are: ");
        TowersOfHanoi.towersOfHanoi(3, 1, 2, 3);


        /*
         * ParethesisMatching expected answer:

         * Problem #2

           The pairs of matching parentheses in (d+(a+b)*c*(d+e)-f))(() are (indexing begins at 0)
           3  7
           11  15
           0  18
           No match for right parenthesis at 19
           21  22
           No match for left parenthesis at 20
         */

        System.out.println(); System.out.println("Problem #2"); System.out.println();
        String keyboard = "(d+(a+b)*c*(d+e)-f))(()";
        System.out.print("The pairs of matching parentheses in ");
        System.out.print(keyboard+ " ");
        System.out.println("are (indexing begins at 0): ");
        ParenthesisMatching.printMatchedPairs(keyboard);

        /*
         * Queue expected answer:

         * Problem #3

           1055
           100
           1000
           Error
         */

        System.out.println(); System.out.println("Problem #3"); System.out.println();
        Queue q = new Queue();
        q.enQueue(1055);
        q.enQueue(100);
        q.enQueue(1000);
        System.out.println(q.deQueue());
        System.out.println(q.deQueue());
        System.out.println(q.deQueue());
        System.out.println(q.deQueue());

    }
}
