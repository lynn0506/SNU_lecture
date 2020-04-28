//2014-19498 Jung, eunjoo
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Switchbox {

    private int num1, num2, num3, num4;
    private String temp, temp1, temp2;

    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();
    private Stack<Integer> routerFail = new Stack<>();


    public static void main(String[] args) throws FileNotFoundException {
        Switchbox Control;
        Control = new Switchbox();

        /** output of Indext1.txt **/
        System.out.println("<Output of Index1.txt>");
        Scanner inputScan = new Scanner(new File("Index2.txt"));
        Control.stack(inputScan);

        System.out.println();
        Switchbox Control2;
        Control2 = new Switchbox();

        /** Output of Index2.txt **/
        System.out.println("<Output of Index2.txt>");
        Scanner inputScan2 = new Scanner(new File("Index1.txt"));
        Control2.stack(inputScan2);

    }


    public void switchCheck() {
        while(stack2.size() != 0) {
            stack.push(stack2.pop());
        }
        num1 = stack.pop();
        num2 = stack.pop();
        num3 = stack.pop();
        num4 = stack.pop();


        while(!stack.isEmpty()) {
            while(true) {
                if(!(biggerCheck())){
                    routerFail.push(num1);
                    routerFail.push(num2);
                    routerFail.push(num3);
                    routerFail.push(num4);
                    break;

                } else {
                    stack2.push(num3);
                    stack2.push(num4);
                    if(!stack.isEmpty()) {
                        num4 = stack.pop();
                        num3 = stack.pop();
                    } else {
                        break;
                    }
                }
            }

            stack = stack2;
            while(!stack2.isEmpty()) {
                stack2.pop();
            }
            if(!stack.isEmpty()) {
                num1 = stack.pop();
                num2 = stack.pop();
            }
        }
    }


    public void print() {
        if(routerFail.isEmpty()) {
            System.out.println("Routable");
        } else {
            num4 = routerFail.pop();
            num3 = routerFail.pop();
            num2 = routerFail.pop();
            num1 = routerFail.pop();

            System.out.println("(" + num1 + ", " + num2 + ")");
            System.out.println("(" + num3 + ", " + num4 + ")");
        }
    }

    public void stack(Scanner inputScan) {
        System.out.println("-------------------");
        while(inputScan.hasNextLine()) {
            temp = inputScan.nextLine();
            System.out.println(temp);

            for(int i = 0; i<temp.length(); i++) {
                if(temp.charAt(i) == ',') {
                    temp1 = temp.substring(0, i);
                    num1 = Integer.parseInt(temp1);

                    temp2 = temp.substring(i+1);
                    num2 = Integer.parseInt(temp2);

                    stack2.push(num1);
                    stack2.push(num2);
                    break;
                }
            }
        }
        System.out.println("--------------------");
        switchCheck();
        print();
    }

    public boolean biggerCheck() {
        if(num1 > num2) {
            return((num2 <= num3 && num3 <= num1)
                    && (num2 <= num4 && num4 <= num1));
        } else {
            return((num1 <= num3 && num3 <= num2)
                    && (num1 <= num4 && num4 <= num2));
        }
    }

}
