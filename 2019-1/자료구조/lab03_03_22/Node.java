
public class Node {
	int data;
	Node next;
	
	public Node() {
		next = null;
	}
	public Node(int input) {
		data = input;
		next = null;
	}
	public Node(int input, Node node) {
		data = input;
		next = node;
	}
	
}
