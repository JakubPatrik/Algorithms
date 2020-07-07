import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] ar;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        ar = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == ar.length-1) { resize(ar.length*2, true); }
        else { resize(ar.length, true); }
        ar[0] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randIndex = StdRandom.uniform(0, n);
        Item item = ar[randIndex];
        // System.out.println("removed = " + ar[randIndex]);
        ar[randIndex] = null;
        resize(n, false);
        n--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randIndex = StdRandom.uniform(0, n);
        Item item = ar[randIndex];
        return item;
    }

//----------------------ITERATOR---------------------ITERATOR-----------------------ITERATOR------------------------

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] aCopy;
		// remain elements in the clone arr
		// can not use aCopy.length, 'cause it is a final, which always same with the initial x from 'object[x]'
		private int remain;
		public RandomizedQueueIterator() {
			remain = n;
			aCopy = (Item[]) new Object[n];
			for (int i = 0; i < n; i++) {
				aCopy[i] = ar[i];
			}
		}
		public boolean hasNext() {
			return remain > 0;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Deque underflow");
			}
			int no = StdRandom.uniform(remain);
			Item ret = aCopy[no];
			aCopy[no] = aCopy[--remain];
			return ret;
		}
	}
	// return an independent iterator over items in random order 
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

    private void resize(int capacity, boolean shift) {
        int x = 0;
        x = shift ? 1 : 0; 
        Item[] c = (Item[]) new Object[capacity];
        int count = 0;

        for (int i = 0; i < ar.length; i++) {
            if (ar[i] != null) { 
                c[count+x] = ar[i]; 
                count++;
            }
        }
        ar = c;
    }

    // private void show() {
    //     for (Item item : ar) {
    //         System.out.print(item + ",");
    //     }
    // }

    // unit testing (required)
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> deque = new RandomizedQueue<>();
        deque.enqueue(10);
        deque.enqueue(20);
        deque.enqueue(30);
        deque.enqueue(40);
        deque.enqueue(100);
        deque.enqueue(200);
        deque.enqueue(300);
        deque.enqueue(400);

        Iterator<Integer> it = deque.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        
        // deque.dequeue();
        // deque.dequeue();
        // deque.dequeue();
        // deque.dequeue();
        // deque.dequeue();
        // deque.dequeue();

        // deque.show();

    }

}