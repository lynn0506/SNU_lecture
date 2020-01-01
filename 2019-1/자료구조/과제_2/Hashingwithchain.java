//2014-19498 Jung eunjoo
import java.util.LinkedList;
import java.util.List;

public class Hashingwithchain {
    /**
     * Class LinearProbingHashTable
     **/
    private int currentSize, maxSize;
    private List<String> vals[];
    private List<String> keys[];
    List<String> list1 = new LinkedList<>();
    List<String> list2 = new LinkedList<>();

    /**
     * Constructor
     **/
    public Hashingwithchain(int capacity) {
        currentSize = 0;
        maxSize = capacity;
        vals = new List[capacity];
        keys = new List[capacity];
    }

    /**
     * Function to clear hash table
     **/
    public void makeEmpty() {
        for (int i = 0; i < maxSize; i++) {
            vals[i] = null;
            keys[i] = null;
        }
    }

    /**
     * Function to get size of hash table
     **/
    public int getSize() {
        return currentSize;
    }

    /**
     * Function to check if hash table is empty
     **/
    public boolean isEmpty() {
        return getSize() == 0;
    }

    /**
     * Function to get hash code of a given key
     **/
    private int hashCode(String key) {
        int length = key.length();
        int answer = 0;
        if(length % 2 == 1) {
            answer = key.charAt(length - 1);
            length--;
        }

        for(int i = 0; i< length; i += 2) {
            answer += key.charAt(i);
            answer += ((int) key.charAt(i + 1)) << 16;
        }
        answer = answer < 0 ? -answer : answer;
        return Math.abs(answer)%maxSize;
    }

    /**
     * Function to insert key-value pair
     **/
    public void insert(String key, String val) {
        currentSize++;
        int index = hashCode(key);

        if(keys[index] != null) {
            keys[index].add(key);
            vals[index].add(val);
        }

        else {
            list1 = new LinkedList<>();
            list1.add(key);
            list2 = new LinkedList<>();
            list2.add(val);

            keys[index] = list1;
            vals[index] = list2;
        }

    }

    /**
     * Function to get value for a given key
     **/
    public String get(String key) {
        if (currentSize == 0) {
            return null;
        }

        for (int i = 0; i < maxSize; i++) {
            if (keys[i] != null) {
                for (int j = 0; j < keys[i].size(); j++) {
                    if (keys[i].get(j).equals(key)) {
                        return vals[i].get(j);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Function to remove key and its value
     **/
    public void remove(String key) {
        if (currentSize == 0) {
            return;
        }

        for (int i = 0; i < maxSize; i++) {
            if (keys[i] != null) {
                for (int j = 0; j < keys[i].size(); j++) {
                    if (keys[i].get(j).equals((key))) {
                        keys[i].remove(j);
                        vals[i].remove(j);
                    }
                }
            }

        }
    }

    /**
     * Function to print HashTable
     **/
    public void printHashTable() {
        System.out.println("\nHash Table: ");
        for (int i = 0; i < maxSize; i++) {
            if (keys[i] != null) {
                for (int j = 0; j < keys[i].size(); j++) {
                    System.out.println("(" + keys[i].get(j) + "," + vals[i].get(j) + ")");
                }
            }
        }
    }
}

