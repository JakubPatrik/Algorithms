
public class StackLinkedList{

    private Node first = null;

    class Node{
        String item;
        Node next;
    }

    public void push(String s){
        Node oldfirst = first;
        first = new Node();
        first.next = oldfirst;
        first.item = s;
    }

    public String pop(){
        String item = first.item;
        first = first.next;
        return item;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public static void main(String[] args) {
        StackLinkedList stack = new StackLinkedList();
        stack.push("Jakub");
        stack.push("sa");
        stack.push("ma");
        stack.push("zle");
        stack.pop();
        stack.push("dobre");
        stack.push("dobre");
        stack.pop();
        stack.pop();
        System.out.println(stack.isEmpty());
    }
}