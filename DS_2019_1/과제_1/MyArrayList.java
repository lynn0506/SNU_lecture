//2014-19498 Jung, Eunjoo

public class MyArrayList<T> implements LinearList {
    private int size;
    private Object[] element;
    
    public MyArrayList(int initialcapacity) {
    	if(initialcapacity<0) {
    		throw new IndexOutOfBoundsException
    		("index=" + initialcapacity + " size= " + size);
    	} element = new Object[initialcapacity];
    	size = 0;
    }
    
    public MyArrayList() {
    	this(5);
    }
    
    public boolean isEmpty() {
    	if(size == 0)
    		return true;
    	else 
    		return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object get(int index) {
    	return element[index];
    }

    @Override
    public int indexOf(Object elem) {
    	for(int i = 0; i<size; i++){
        	if(element[i].equals(elem))
        		return i;
        	}
    	return -1;   
    }

    @Override
    public Object remove(int index) {
    	if(index <0 || index >= size) { 
    		throw new IndexOutOfBoundsException
    		("index=" + index + " size= " + size);
    		}
    	Object removedElement = element[index];
    	for(int i = index+1; i<size; i++){
    			element[i-1] = element[i];
    	}
    	element[--size] = null;
        return removedElement;    
    }

    //it does not replace new added element with original element in same index.
    @Override
    public void add(int index, Object obj) {
    	if(index<0 || index > size) {
    		throw new IndexOutOfBoundsException("invalid");
    	}
    		if(size == element.length) {
    			Object[] newElement = new Object[element.length*2];
    			System.arraycopy(element, 0, newElement, 0, element.length);
    			element = newElement;
    		}
    		
    		if(index == size) {
    			element[index] = obj;
    			size++;
    		} else {
    			if(element[index] != null) {
    				for(int i = size; i>index; i--) {
    					element[i] = element[i-1];
    				} 
    				element[index] = obj;
    				size++;
    				
    				return;
    			} 
    			element[index] = obj;
    			size++;
    		}	
    	}
    }
   
