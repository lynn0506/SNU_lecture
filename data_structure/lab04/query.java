import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class query {
	private static Scanner inputScan;

	public static void main(String[] args) throws FileNotFoundException {
		inputScan = new Scanner(new File("input.txt"));
		int inputNum = inputScan.nextInt();
		String[] strings = new String[inputNum];
		int count = 0;
		
		for(int i = 0; i<inputNum; i++) {
			String String = inputScan.next();
			strings[i] = String;
		}
		
		inputNum = inputScan.nextInt();
		String[] queries = new String[inputNum];
		int[] output = new int[inputNum];
		for(int i = 0; i<inputNum; i++) {
			String query = inputScan.next();
			queries[i] = query;
		}
		
		for(int i = 0; i<queries.length; i++) {
			for(int j = 0; j<strings.length; j++) {
				if(queries[i].equals(strings[j])) {
					count++;
				} }
				System.out.println(count);
				count = 0;
			}
		}
		
	}


