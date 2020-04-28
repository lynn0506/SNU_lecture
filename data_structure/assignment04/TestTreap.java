//2014-19498 jung eunjoo
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestTreap {
	public static void main(String[] args) throws FileNotFoundException {
		//the input.txt needs reading and processing
		String path = args[0];
		Scanner reader = new Scanner(new FileInputStream(path));

		String temp[] = new String[100];
		int index = 0;

		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			Scanner lineScan = new Scanner(line);
			while(lineScan.hasNext()){
				temp[index++] = lineScan.next();
			}
		}

		String S [] = new String[index];
		System.arraycopy( temp, 0, S, 0, index);
		// U can use your own method for input file size

		Treap mytreap = new Treap();
		mytreap.build(S);
		System.out.println("Treap");
		mytreap.Inorder();
		mytreap.remove("C");
		System.out.println("New Treap");
		mytreap.Inorder();
		mytreap.remove("H");
		System.out.println("New Treap");
		mytreap.Inorder();
		mytreap.remove("A");
		System.out.println("New Treap");
		mytreap.Inorder();
		mytreap.search("A");

	}
}