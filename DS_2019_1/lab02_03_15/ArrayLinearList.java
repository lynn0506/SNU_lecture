import java.util*;

public class ArrayLinearList implements LinearList{ 
	protected Object[] element;
	protected int size;

	public boolean isEmpty() {
		return size == 0;
	}
	public int size() {
		return size;
	}
	public ArrayLinearList(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException ("initialCapacity must be >= 1");
			// 왜 여기에 일래갈 알규먼트익셉션이죠?
		} element = new Object[initialCapacity];
	}
	public  ArrayLinearList() {
		this(10);
	}

	public void checkIndex(int index) {
		if(index <0 || index >= size) {
			throw new IndexOutOfBoundsException ("index = " + index + "size = "+ size);
		}			
		}

	public Object get(int index) {
		checkIndex(index);
		return element[index];
		}
	public Object remove(int index)  {
		checkIndex(index);
		Object removedobject = element[index];
		for(int i = index+1; i<size; i++) {
			element[i-1] = element[i];
		} element[--size] = null;
		return removedobject;
	}
	public void add(int index, Object theElement) {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException ("indext = " + indext + "size = " + size);
			else if(size == element.length) {
				Onject[] newArray = new Object[2*elemnet.length];
				System.arraycopy(element, 0, newArray, 0, 2*element.length);
				for(int i = size-1; i>=index; i--) {
					element[i+1] = element[i]; 
				} element[index] = theElement;
				size++;
			} element = newArray;
		}
		public int indexOf(Onject theElement) {
			for(int i = 0; i<size; i++) {
				if(element[i].equals(theElement)) {
					return i;
				} return -1;
			}
		}
		public string toString() {
			StirngBuffer s = new StringBuffer("[");
			for(int i = 0; i<sizel i++) {
				if(element[i] == null) {
					s.append(" null, ");
				} else {
					s.append(element[i].toString() + ",");
				} if(size > 0) {
					s.delete(s.length() -2, s.length())
				}
			}
		}
	}
	}

	}

}