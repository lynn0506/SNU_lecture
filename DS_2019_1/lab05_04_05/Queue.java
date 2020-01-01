
import java.util.Stack;

public class Queue {

    private Stack<Integer> s1 = new Stack<Integer>();
    private Stack<Integer> s2 = new Stack<Integer>();

    public void enQueue(int x) {
    	s1.push(x);
    }

    public int deQueue() {
    	while(!s1.isEmpty()) {
    		s2.push(s1.pop());
    	} 
    	
    	if(s2.isEmpty()) {
    		System.out.println("Error");
			System.exit(0);
    	}
    		return s2.pop();

    }
}
