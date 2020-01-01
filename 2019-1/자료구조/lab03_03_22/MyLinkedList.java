
public class MyLinkedList implements LinkedList{
	Node head;
	int size;
	
	public void MyLinkedList() {
		head = null;
		size = 0;
		
	}
	public boolean isEmpty() {
		if(head == null)
			return true;
		else 
			return false;
	}
	
	public int size() {
		return size;
	}
	
	public int indexOf(int v) {
		int index = 0;
		Node temp = head;
		
		if(head == null) {
			return -1;
		} 
		if(head.data == v) {
			return 0;
		} 
		
			while(temp.next != null) {
				index++;
				if(temp.next.data == v) {
					return index;
					} 
					temp = temp.next;			
				}
			return -1;
	}
	
	public void print() {
		if(head == null) {
			System.out.println("this List is null");
			return;
		} else {
			System.out.print(head.data + " ");
			Node temp = head;
			while(temp.next!= null) {
				temp = temp.next;
				System.out.print(temp.data + " ");
			}
			System.out.println();
			return;
		}
	}
		
	public boolean remove(int v) {		
		if(head == null) {
			return false;
		} 
		if(head.data == v) {
			size--;
			if(head.next != null) {
				Node temp = head.next;
				head = temp;
				return true;
			}
			else {
				head = null;
				return true;
			}
		}
		Node temp = head;
		while(temp.next != null) {
			if(temp.next.data == v) {
				size--;
				if(temp.next.next != null) {
					temp.next = temp.next.next;
				} else {
				temp.next = null;
			} return true;
		} temp = temp.next;
	}
		return false;
	}
	
	public void add(int v) {
		Node temp;
		size++;
		if(head == null) {
			Node B = new Node(v); 
			head = B;
		} 
		
		else if(head.next == null) {
			if(head.data > v) {
				Node B = new Node(v);
				temp = head;
				head = B;
				head.next = temp;
			}
			else {
			Node B = new Node(v);
			head.next = B;
		}	
	} else {
		Node B;
		Node temp2;
		temp = head;
		while(temp.next != null) {
			if((temp.data <= v) && (v <= temp.next.data)) {
				B = new Node(v);
				temp2 = temp.next;
				temp.next = B;
				B.next = temp2;
				return;
			}
			temp = temp.next;
		}
		B = new Node(v);
		temp.next = B;
		}
	}
}