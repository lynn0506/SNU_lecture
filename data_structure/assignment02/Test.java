//2014-19498 Jung eunjoo
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) throws  FileNotFoundException {
		final int BUCKETSIZE = 7393;
		final String GET = "get()";
		final String INSERT = "insert()";
		final String REMOVE = "remove()";

		LinearProbingHashTable arrayHashing;
		Hashingwithchain chainHashing;

		arrayHashing = new LinearProbingHashTable(BUCKETSIZE);
		chainHashing = new Hashingwithchain(BUCKETSIZE);

		Scanner textScan;
		String key, val, target;
		long startTime, endTime, enlapsedTime;

		textScan = new Scanner(new File("hash.txt"));

		while(textScan.hasNext()) {
			val = textScan.next();
			key = val;
			arrayHashing.insert(key, val);
			chainHashing.insert(key, val);
		}

		/**Hashing with Array print table **/
		System.out.println("<Hashing with Array>");
		arrayHashing.printHashTable();
		System.out.println();

		/** print Hashing with Chain table**/
		System.out.println("<Hashing with Chain>");
		chainHashing.printHashTable();

		File file2 = new File("indices.txt");
		textScan = new Scanner(file2);

		// get() text input execute
		if(textScan.next().equals(GET)) {
			while(textScan.hasNextInt()) {
				target = textScan.next();
				startTime = System.nanoTime();
				chainHashing.get(target);
				endTime = System.nanoTime();
				enlapsedTime = endTime - startTime;
				System.out.println("key is " + target + " method is get(), "
						+ "running time for LinearProbingHashtable is " + enlapsedTime + " nanoseconds");

				startTime = System.nanoTime();
				arrayHashing.get(target);
				endTime = System.nanoTime();
				enlapsedTime = endTime - startTime;
				System.out.println("key is " + target + " method is get(), "
						+ "running time for HashingWithChain is " + enlapsedTime + " nanoseconds");
			}

		// insert() text input execute
		if(textScan.next().equals(INSERT)) {
				while(textScan.hasNextInt()) {
					target = textScan.next();
					startTime = System.nanoTime();
					chainHashing.insert(target, target);
					endTime = System.nanoTime();
					enlapsedTime = endTime - startTime;
					System.out.println("key is " + target + " method is insert(), "
							+ "running time for LinearProbingHashtable is " + enlapsedTime + " nanoseconds");

					startTime = System.nanoTime();
					arrayHashing.insert(target, target);
					endTime = System.nanoTime();
					enlapsedTime = endTime - startTime;
					System.out.println("key is " + target + " method is insert(), "
							+ "running time for HashingWithChain is " + enlapsedTime + " nanoseconds");
				}
			}

		// remove() text input execute
		if(textScan.next().equals(REMOVE)) {
			while(textScan.hasNextInt()) {
				target = textScan.next();
				startTime = System.nanoTime();
				chainHashing.remove(target);
				endTime = System.nanoTime();
				enlapsedTime = endTime - startTime;
				System.out.println("key is " + target + " method is remove(), "
						+ "running time for LinearProbingHashtable is " + enlapsedTime + " nanoseconds");

				startTime = System.nanoTime();
				arrayHashing.remove(target);
				endTime = System.nanoTime();
				enlapsedTime = endTime - startTime;
				System.out.println("key is " + target + " method is remove(), "
						+ "running time for HashingWithChain is " + enlapsedTime + " nanoseconds");
				}
			}
		}
	}
}