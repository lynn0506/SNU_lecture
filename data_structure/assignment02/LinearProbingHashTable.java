//2014-19498 Jung eunjoo
/** Class LinearProbingHashTable **/
public class LinearProbingHashTable
{
    private int currentSize, maxSize;
    private String[] keys;
    private String[] vals;

    /** Constructor **/
    public LinearProbingHashTable(int capacity)
    {
        maxSize = capacity;
        currentSize = 0;
        keys = new String[maxSize];
        vals = new String[maxSize];
    }

    /** Function to clear hash table **/
    public void makeEmpty()
    {
        for(int i = 0; i < maxSize; i++) {
            keys[i] = null;
            vals[i] = null;
        }
        currentSize = 0;
    }

    /** Function to get size of hash table **/
    public int getSize()
    {
        return currentSize;
    }

    /** Function to check if hash table is empty **/
    public boolean isEmpty()
    {
        return getSize() == 0;
    }

    /** Function to get hash code of a given key **/
    private int hashCode(String key)
    {
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

    /** Function to insert key-value pair **/
    public void insert(String key, String val)
    {
        currentSize++;
        int index = hashCode(key);

        while(keys[index] != null) {
            index++;

            if (index == maxSize)
                index = 0;

        } keys[index] = key;
        vals[index] = val;
    }

    /** Function to get value for a given key **/
    public String get(String key)
    {
        int targetIndex = -1;

        if(this.isEmpty()) {
            return null;
        }


        for(int i = 0; i < maxSize; i++) {
            if (keys[i] != null) {
                if (key.compareTo(keys[i]) == 0)
                    targetIndex = i;
            }
        }

        if(targetIndex == -1)
            return null;

        else
            return vals[targetIndex];

    }

    /** Function to remove key and its value **/
    public void remove(String key)
    {
        if(this.isEmpty()) {
            return;
        }

        int searchIndex = -1;
        for(int i = 0; i< maxSize; i++) {
            if (keys[i]!=null) {
                if (keys[i].equals(key)) {
                    searchIndex = i;
                    break;
                }
            }
        }

        if(searchIndex == -1) {
            return;
        }

        else {
            keys[searchIndex] = null;
            vals[searchIndex] = null;

            for (int i = 0; i < maxSize; i++) {
                searchIndex++;
                if (searchIndex == maxSize) {
                    searchIndex = 0;
                }

                if (keys[searchIndex] == null) {
                    return;
                } else {
                    int newSearchIndex = hashCode(keys[searchIndex]);
                    int tempInt = newSearchIndex;

                    for (int j = 0; j < maxSize; j++) {
                        if (keys[newSearchIndex] == null) {
                            keys[newSearchIndex] = keys[searchIndex];
                            keys[searchIndex] = null;
                            vals[newSearchIndex] = vals[searchIndex];
                            vals[searchIndex] = null;
                            break;
                        } else {
                            if (hashCode(keys[newSearchIndex]) != newSearchIndex)
                                newSearchIndex++;
                        }
                        if (newSearchIndex == maxSize) {
                            newSearchIndex = 0;
                        }
                    }

                }
            }
            }
        }





    /** Function to print HashTable **/
    public void printHashTable()
    {
        System.out.println("\nHash Table: ");
        for (int i = 0; i < maxSize; i++) {
            if (keys[i] != null)
                System.out.println("(" + keys[i] + "," + vals[i] + ")");
        }
    }
}

