import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class Permutation {
	public static void main(String[] args) {
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		int outCount = Integer.parseInt(args[0]);
		int inCount = 0;
		// StdOut.println(outCount);
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (item != null && item.length() > 0) {
				q.enqueue(item);
				inCount++;
			}
		}
		if (inCount < outCount) {
			throw new NoSuchElementException("incoming element < need to be print out");
		}
		while (outCount > 0) {
			StdOut.println(q.dequeue());
			outCount--;
		}
	}	
}
