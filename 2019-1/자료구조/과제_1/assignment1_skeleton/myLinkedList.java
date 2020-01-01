public class myLinkedList implements LinearList {
    @Override
    public boolean isEmpty() {

        if(size == 0) {
            return false;
        }

        return true;
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public Object get(int index)
    { if(head == 0) {

    }
        return null;
    }

    @Override
    public int indexOf(Object elem) {
        return 0;
    }

    @Override
    public Object remove(int index) {
        return null;
    }

    @Override
    public void add(int index, Object obj) {

    }


    // add more methods as needed.
}
