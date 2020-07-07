import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] ar;
    private int n = 0;
    private int first = 0;
    private int last = 0;

    // construct an empty deque
    public Deque() {
        ar = (Item[]) new Object[1];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (first >= 0 && first < ar.length && ar[first] == null) {
            ar[first--] = item;
        }
        else {
            resize(ar.length * 2);
            ar[first--] = item;
        }
        last = first + n;
        last++;
        n++;

        // System.out.println("first " + first + ", last "+ last);
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (last >= 0 && last < ar.length - 1) {
            ar[++last] = item;
        }
        else {
            resize(ar.length * 2);
            // System.out.println("too small");
            if (last >= ar.length - 1) {
                resize(ar.length + 1);
            }
            ar[++last] = item;
        }
        last = first + n;
        last++;
        n++;

        // System.out.println("first " + first + ", last "+ last);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = ar[++first];
        ar[first] = null;
        n--;

        // System.out.println("removing first " + first + ", last "+ last);
        if (first >= 3*ar.length/4 && first > 2) {
            resize(ar.length/2);
        }

        // System.out.println("removing first " + first + ", last "+ last);

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // System.out.println("removing first " + first + ", last "+ last);
        
        Item item = ar[last];
        ar[last] = null;
        n--;

        
        if (last < ar.length / 4) {
            resize(ar.length / 2);
        }

        last = first + n;


        return item;
    }


//----------------------ITERATOR---------------------ITERATOR-----------------------ITERATOR------------------------

private class DequeIterator implements Iterator<Item> {
    // remain elements in the clone arr
    // can not use aCopy.length, 'cause it is a final, which always same with the initial x from 'object[x]'
    private int remain;
    private int no;
    public DequeIterator() {
        remain = n;
        no = first;
    }
    public Item next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Deque underflow");
        }
        no++;
        if (hasNext() && ar[no] != null) {
            Item ret = ar[no];
            remain--;
            return ret;
        }
        return null;
    }
    public boolean hasNext() {
        return remain > 0;
    }
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void resize(int capacity) {
        Item[] c = (Item[]) new  Object[capacity];
        first = capacity/2 - 1;
        int x = 0;
        for (int i = first; x < ar.length; i++, x++) {
            if (ar[x] == null) {
                i--;
            }
            else {
                c[i+1] = ar[x];
            }
        }
        ar = c;
        last = first + n;
    }

    // private void show() {
    //     for (Item item : ar) {
    //         System.out.print(item + ",");
    //     }
    // }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        
        deque.addFirst(2);
        deque.addFirst(4);
        
        Iterator<Integer> it = deque.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        
        deque.addFirst(2);
        deque.removeFirst();
        deque.addFirst(4);

        // deque.show();
    }

}