
import java.util.Stack;

public class ParenthesisMatching {


    /**
     * output the matched parenthesis pairs in the string expr
     */
    public static void printMatchedPairs(String expr) {
    	Stack<Integer> stack = new Stack<Integer>();
    	String left = "left";
    	String right = "right";
    	int n;
     
        for(int i=0; i < expr.length(); i++) {
            if(expr.charAt(i) == '(') {
                stack.push(i);
            } else if (expr.charAt(i) == ')') {
            	if(stack.isEmpty()) {
            		System.out.println("No match for" + right + "parenthesis at " + i);	
            	} else {
            		System.out.println(stack.pop() + " " + i);
            		}
            	} 
	
            }
        
        while(!stack.isEmpty()) {
        	System.out.println("No mathch for left parenthesis at " + stack.pop());
        }
        
    }
}
        
    
    
        



