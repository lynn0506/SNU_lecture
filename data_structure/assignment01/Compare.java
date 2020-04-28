//2014-19498 Jung, eunjoo

import java.util.Random;
import java.lang.String;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;
import java.util.Arrays;


public class Compare {
	private long enlapsedTime1;
	private long enlapsedTime2;
	private long startTime;
	private long endTime;
	public myLinkedList<String> myLinked = new myLinkedList<String>();
	public MyArrayList<String> myArray = new MyArrayList<String>();
	
	public static void main(String[] args) {
		Compare compare = new Compare();
		byte[] array = new byte[100000];	
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));

	
		for(int i = 0; i<100000; i++) {
			compare.myLinked.add(i, generatedString);
			compare.myArray.add(i, generatedString);
		}
			compare.print(1);
			compare.print(49999);
			compare.print(99999);
	}
	
	public void print(int index) {
		System.out.println("\tArrayList\tLinkedList\tIndex = " + index);
		System.out.println("-----------------------------------------------------");
		
		//add
			startTime = System.nanoTime();
			myArray.add(index,"Stirng");
			endTime = System.nanoTime();
			enlapsedTime1 = endTime - startTime;
			
			startTime = System.nanoTime();
			myLinked.add(index, "String");
			endTime = System.nanoTime();
			enlapsedTime2 = endTime - startTime;
		
		System.out.println("Add" + "\t\t|" + enlapsedTime1 + "\t\t|" + enlapsedTime2);
		System.out.println();
		
		//get
			startTime = System.nanoTime();
			myArray.get(index);
			endTime = System.nanoTime();
			enlapsedTime1 = endTime - startTime;
		
			startTime = System.nanoTime();
			myLinked.get(index);
			endTime = System.nanoTime();
			enlapsedTime2 = endTime - startTime;
		
		System.out.println("Get" + "\t\t|" + enlapsedTime1 + "\t\t|" + enlapsedTime2);
		System.out.println();
		
		//remove
			startTime = System.nanoTime();
			myArray.remove(index);
			endTime = System.nanoTime();
			enlapsedTime1 = endTime - startTime;
		
			startTime = System.nanoTime();
			myLinked.remove(index);
			endTime = System.nanoTime();
			enlapsedTime2 = endTime - startTime;
			
		System.out.println("Remove" + "\t\t|"+ enlapsedTime1 + "\t\t|" + enlapsedTime2);
		System.out.println();
		
		}
	}

