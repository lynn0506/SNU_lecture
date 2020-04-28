
import java.util.Scanner;

public class myLZW {
    public static void main(String[] args) {
        Scanner InputScan = new Scanner(System.in);
        System.out.print("The input for testing encode is: ");
       String line =  InputScan.next();
       LZWtest LZW  = new LZWtest();


       String encodedLine = LZW.encode(line);
       System.out.println("Encoded output is: " + encodedLine);

        System.out.print("The input for testing decode is: ");

        String decodedNum = InputScan.next();
        String decodedLine = LZW.decode(decodedNum);
        System.out.println("The original string is: " + decodedLine);


    }
}

