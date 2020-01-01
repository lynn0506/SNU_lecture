
//2014-19498 Jung, Eunjoo
import java.util.Iterator;

public class myLinkedList<T> implements LinearList, Iterable<T> {
	
	private int size;
	Node<T> head;

	public myLinkedList() {
		head = null;
		size =0;
	}
	    
	@Override
	public boolean isEmpty() {
		if(head == null) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object get(int index) {
		if(index < 0 || index >= size)
			return "invalid";

		Node<T> target = head;
		for(int i = 0; i<index; i++) {
			target = target.next;
		}
		return target.data;
	}


	@Override
	public int indexOf(Object elem) {
		Node<T> temp = head;
		for(int i = 0; i<size; i++) {
			if(temp.data == elem)
				return i;
			else
				temp = temp.next;
		}
		return -1;
	}

	@Override
	public Object remove(int index) {
		Node<T> temp = head;
		Node<T> temp2 = head.next;

		if(head == null || (index < 0 || index > size)) {
			throw new IndexOutOfBoundsException
			("index= " + index + "size= " + size);
		}

		size--;
		if(index == 0) {
			head = temp2;
			return temp.data;	
		}

		else {
			for(int i = 0; i< index-1; i++) {
				temp = temp.next;
			} temp2 = temp.next;
			temp.next = temp2.next;
			return temp2.data;	
		}
	}


	@Override
	public void add(int index, Object obj) {
		Node<T> temp = head;
		Node<T> temp2;
		Node<T> addition = new Node<T>((T)obj);

		if(index<0 || index > size) {
			throw new IndexOutOfBoundsException
			("index= " + index + " size= " + size);
		}

		if(head == null) {
			head = addition;
			size++;
			return;
		}

		if(index == 0) {
			temp2 = head.next;
			head = addition;
			head.next = temp2;
			return;
		}
		
		else if(index < size) {
			for(int i = 0; i< index-1; i++) {
				temp = temp.next;
			} 	
				temp2 = temp.next;
				temp.next = addition;
				addition.next = temp2;
				size++;
			
		}
		
		else {
			while(temp.next != null) {
				temp = temp.next;
			}
				temp2 = temp.next;
				temp.next= addition;
				addition.next = temp2;
				size++;
				return;
		}
	}
	
	public void lastAdd_(T setInfo) {
		lastAdd(head, setInfo);
	}
	 
	public void lastAdd(Node<T> source, T setInfo) {
		if(source.next != null){
			source = source.next;
			lastAdd(source,setInfo);
		} else {
			source.next = new Node<T>(setInfo);
			size++;
		}
	}

	public boolean duplicate() {
		Node<T> temp = head;
		Node<T> temp2 = head;
		boolean dupli = false;

		while(temp != null && temp.next != null) {
			temp2 = temp;
			while(temp2.next != null) {
				if(temp.compareAll(temp2.next)) {
					temp2.next = temp2.next.next;
					dupli = true;
					size--;
				} else {
					temp2 = temp2.next;
				}
			} temp = temp.next;
		}
		return dupli;
	}
	
	public void searchContact(int index) {
		Node<T> temp = head;
		ContactEntry tempInfo;
		for(int i = 0; i<index; i++) {
		   temp = temp.next;
		} tempInfo = (ContactEntry)temp.data;
		  System.out.println("contact at the given index is " + tempInfo.getName());
	}
	
	public void printName() {
		Node<T> temp = head;
		ContactEntry tempInfo;
		for(int i = 0; i<size; i++) {
			tempInfo = (ContactEntry) temp.data;
			tempInfo.printName();
			temp = temp.next;
		}
	}
	
	public void printContact(Object obj) {
		Node<T> temp = head;
		ContactEntry tempInfo;
		for(int i = 0; i<size; i++) {
			tempInfo = (ContactEntry)temp.data;
			
			if(tempInfo.getOccu().toString().equals(obj)) {
			  tempInfo.printInfo();
			} temp = temp.next;
		}
	}

	public void sort() {
		Node<T> temp = head;
		Node<T> temp2 = head;
		T tempData;

		while(temp != null) {
			temp2 = temp;
			while(true) {
				if(temp2.next == null) {
					break;
				}
				if(temp2.compareTo(temp2.next)>0) {
					tempData = temp2.data;
					temp2.data = temp2.next.data;
					temp2.next.data = tempData;
				} temp2 = temp2.next;
			} temp = temp.next;
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return new myLinkedListIterator<T>(this);
		}
	}

	class myLinkedListIterator<T> implements Iterator<T>{
		private Node<T> current;

		public myLinkedListIterator(myLinkedList<T> source) {
			current = source.head;
		}

	@Override
		public boolean hasNext() {
			return current.next != null;
		}

	@Override
		public T next() {
			current = current.next;
			return current.data;
			}
		}