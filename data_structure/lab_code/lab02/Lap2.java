import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Lap2{
	
	    int n;
	    int[] arr1;  //keep original unsorted array
	    int[] arr2;  //you will sort this array

	    public Lap2(int n, int[] arr1, int[] arr2) {
	        this.n = n;
	        this.arr1= arr1;
	        this.arr2 = arr2;
	    }

	    public void reinitializeArray() {
	        System.arraycopy(arr1, 0, arr2, 0 ,n);
	    }


	    /* TODO 1: Implement insertionSort(), which should sort "arr2" */
	    public void insertionSort() {
	    	
	    	for(int i = 0; i<arr2.length; i++) {
	    		int t = arr2[i];
	    		int j;
	    		for(j = i-1; j>= 0 && t< arr2[j]; j--) {
	    			if( t < arr2[j]) {
	    				arr2[j+1] = arr2[j];
	    			}}
	    				arr2[j+1] =t;
	    			
	    		}
	    	}
	    

	    /* TODO 2: implement printArr1() and printArr2(), which prints arr1 and arr2 respectively */
	    public void printArr1() {
	        System.out.println("Unsorted array: ");
	        for(int i = 0; i<arr1.length; i++) {
	        	System.out.print(arr1[i] + " ");
	        }
	        System.out.println();
	    }
	    public void printArr2() {
	        System.out.println("Sorted array: ");
	        for(int i = 0; i<arr2.length; i++) {
	        	System.out.print(arr2[i] + " ");
	        }
	        System.out.println();
	    }

	    /* TODO 3: - Performance tests in milliseconds for both insertionSort and quickSort.
	     *           Hint: keep a counter and run the test for 1000 miliseconds, then divide the elapsed time by the counter
	     *           Hint: you should use reinitialize()
	     *         - Print in new lines the results in the following format:
	     *           Insertion Sort: ___ ms
	     *           Quick Sort: ___ ms
	     */
	    public void performanceTest() {

	    	long startTime = System.currentTimeMillis();
			long counter = 0;

				do {
					counter++;
					reinitializeArray();
					insertionSort();
					}
				while(System.currentTimeMillis() - startTime < 1000);
					long enlapsedTime = System.currentTimeMillis() - startTime;
					
					float timeForMethod = ((float)enlapsedTime/counter);
					System.out.println("Insertion Sort: "+ timeForMethod + "ms");

			counter = 0;
			reinitializeArray();
			startTime = System.currentTimeMillis();
				do {
					counter++;
					reinitializeArray();
					quickSort(arr2, 0, n-1);
					}
				while(System.currentTimeMillis() - startTime < 1000);

					enlapsedTime = System.currentTimeMillis() - startTime;
					timeForMethod = ((float)enlapsedTime/counter);
					System.out.println("Quick Sort: " + timeForMethod + "ms");


	    }

	    /* TODO 4: - Read the first number in the file: n
	     *         - Read the following n numbers and store them in an array: arr1
	     *         - Make a copy of the array: arr2
	     *         - Use n, arr1, arr2 to initialize Lab2 class
	     *         - Then call methods insertionSort(), printArr1(), printArr2(), performanceTest()
	     */
	    public static void main(String[] args) throws FileNotFoundException {
	    		Scanner filescan = new Scanner(new File("input.txt"));
	    		int size_of_array = filescan.nextInt();

	    		int[] temp_array = new int[size_of_array];
	    		int[] temp_array2 = new int[size_of_array];
	    		for(int i = 0; i<size_of_array; i++) {
	    			temp_array[i] = filescan.nextInt();
	    			temp_array2[i] = temp_array[i];
	    		}
	    	Lap2 neu = new Lap2(size_of_array, temp_array, temp_array2);
	    	neu.printArr1();
	    	neu.insertionSort();
	    	neu.printArr2();
	    	neu.performanceTest();
	    	System.out.println("정은주, 2014-19498");

	    }

	    /* Given quickSort implementation.*/

	    public static void quickSort(int array[], int first, int last) {
	        if (first < last) {
	            int splitIndex = split(array, first, last);
	            quickSort(array, first, splitIndex - 1);
	            quickSort(array, splitIndex + 1, last);
	        }
	    }
	    private static int split(int array[], int first, int last) {

	        int pivot = array[last];

	        int i = (first - 1);

	        for (int j = first; j < last; j++) {
	            if (array[j] <= pivot) {
	                i++;
	                int temp = array[i];
	                array[i] = array[j];
	                array[j] = temp;
	            }
	        }

	        int temp = array[i + 1];
	        array[i + 1] = array[last];
	        array[last] = temp;

	        return i + 1;
	    }
}
