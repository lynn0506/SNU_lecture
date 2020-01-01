import java.util.*;

public class Lab03 {
	public static void main(String[] args) {
	MyLinkedList a = new MyLinkedList();
	
	if(a.isEmpty()) {
		System.out.println("isEmpty? true");
		System.out.println("size is " + a.size());
	} else {
		System.out.println("isEmpty? false");
	} 
	
	if(a.remove(12)== false) {
	 System.out.println("False");
	} else {
		System.out.println("True");
	}
	
	System.out.println("Index of 0: " + a.indexOf(0));
	
	a.add(5);
	a.add(2);
	a.add(4);
	a.add(10);
	a.add(7);
	a.add(4);
	a.print();
	
	System.out.println("Size is " + a.size());
	System.out.println("Index of 2: " + a.indexOf(2) );
	System.out.println("Indext of 7: " + a.indexOf(7));
	System.out.println("Index of 10: " + a.indexOf(10));
	System.out.println(a.remove(4));
	System.out.println(a.remove(2));
	System.out.println(a.remove(2));
	a.print();
	System.out.println("isEmpty? " + a.isEmpty());
	System.out.println("size is " + a.size());
	
	}
	
}
