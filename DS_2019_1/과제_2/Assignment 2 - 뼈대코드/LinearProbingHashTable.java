import java.util.Scanner;
 
 
/** Class LinearProbingHashTable **/
public class LinearProbingHashTable
{
    private int currentSize, maxSize;       
    private String[] keys;   
    private String[] vals;    
 
    /** Constructor **/
    public LinearProbingHashTable(int capacity) 
    {
    
    }  
 
    /** Function to clear hash table **/
    public void makeEmpty()
    {
         
    }
 
    /** Function to get size of hash table **/
    public int getSize() 
    {
         
    }
 
    /** Function to check if hash table is empty **/
    public boolean isEmpty() 
    {
        return getSize() == 0;
    }
 
    /** Function to get hash code of a given key **/
    private int hashCode(String key) 
    {


    }    
 
    /** Function to insert key-value pair **/
    public void insert(String key, String val) 
    {                


    }
 
    /** Function to get value for a given key **/
    public String get(String key) 
    {


    }
 
    /** Function to remove key and its value **/
    public void remove(String key) 
    {


    }       
 
    /** Function to print HashTable **/
    public void printHashTable()
    {
        System.out.println("\nHash Table: ");
        for (int i = 0; i < maxSize; i++)
            if (keys[i] != null)
              System.out.println("(",keys[i]+","+ vals[i]+")");
        System.out.println();
    }   
}
