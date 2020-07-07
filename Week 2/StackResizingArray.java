
public class StackResizingArray {
    static public String[] s = new String[1];
    public int N = 0;

    public void push(String item){
        if(s.length == N) resize(s.length * 2);
        s[N++] = item;
    }

    public String pop(){
        if(N>0){String item = s[--N];
            s[N] = null;
            if(s.length/4 == N) resize(s.length / 2);
            return item;
        }
        return null;
    }

    public boolean isEmpty(){
        return s.length == N;
    }

    public int size(){
        return N;
    }

    public void resize(int capacity){
        String[] c = new String[capacity];
        for (int i = 0; i < N; i++) {
            c[i] = s[i];
        }
        s = c;
    }

    public static void main(String[] args) {
        StackResizingArray stack = new StackResizingArray();
        stack.push("Jakub");
        stack.push("sa");
        stack.push("ma");
        stack.push("zle");
        stack.pop();
        stack.push("dobre");
        stack.push("dobre");
        stack.pop();
        stack.pop();


        for (String x : s) {
            System.out.print(x + " ");
        }
        
        System.out.println("\nNumber of elements " + stack.size() + ", actual size : " + s.length);
    }
}