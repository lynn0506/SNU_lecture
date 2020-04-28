public class MyHash {
	private int Capacity;
	private String keyVal;
	private int index;
	private int val;
	private int[] Data;
	private String[] Keys;
	
	
	public MyHash(int initialCapacity)
	{
		Capacity = initialCapacity;
		Data = new int[Capacity];
		Keys = new String[Capacity];
		
		for(int i = 0; i< Capacity; i++) {
			Keys[i] = null;
			Data[i] = -1;
		}
		index = -1;
	}
	
	public void add(int value)
	{
		keyVal = Integer.toString(value);
		index = value % Capacity;
		
		while(Keys[index] != null) {
			index++;
			
			if(index == Capacity) {
				index = 0;
			}
		}
		Data[index] = value;
		Keys[index] = keyVal;
	}
	
	public void remove(int value)
	{
		index = value % Capacity;
		for(int i = 0; i< Capacity; i++) {
			if(Data[i] == value) {
				Data[i] = -1;
				Keys[i] = null;
			}
		}
	
	}
	
	

	public String toString() 
	{
		index = 0;
		String line = "";
		for(int i = 0; i< Capacity; i++) {
			if(Keys[i] != null) {
			line = line.concat(" ");
			line = line.concat(Keys[i]);
			}
		}
		return line;
	}
	
	public String toString2()
	{
		index = 0;
		String line = "";
		
		for(int i = 0; i< Capacity; i++) {
			if(Keys[i] != null) {
				line = line.concat("1 ");
			} else {
				line = line.concat("0 ");
			}
		}
		return line;
		
	}
	
}

