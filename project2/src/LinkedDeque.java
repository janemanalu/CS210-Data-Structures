import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;
import stdlib.StdRandom;

// A data type to represent a double-ended queue (aka deque), implemented using a doubly-linked
// list as the underlying data structure.
public class LinkedDeque<Item> implements Iterable<Item> {
    // Size for the deque
    private int n;
    // Front part of the deque
    private Node first;
    // Back part of the deque
    private Node last;



    // Constructs an empty deque.
    public LinkedDeque() {
        // Initialize instance variables to appropriate values.
        n = 0;
        first = null;
        last = null;
    }

    // Returns true if this deque is empty, and false otherwise.
    public boolean isEmpty() {
        // checking the deque is empty or not by returning the size of the deque
        return n == 0;
    }

    // Returns the number of items in this deque.
    public int size() {
        // Return the size of the deque by returning n
        return n;
    }

    // Adds item to the front of this deque.
    public void addFirst(Item item) {
        if (item == null){
            throw new NullPointerException("item is null");
        }
        // If this is the first item that is being added, both first and last must point to the same node.
        Node first_1 = first;
        first = new Node();
        first.item = item;
        first.next = first_1;
        first.prev = null;
        if (isEmpty()) {
            last = first;
        }
        else{
            first_1.prev = first;
        }
        n++; // Increment n by one.
    }

    // Adds item to the back of this deque.
    public void addLast(Item item) {
        if (item == null){
            throw new NullPointerException("item is null");
        }
        // If this is the first item that is being added, both first and last must point to the same node.
        Node last_1 = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = last_1;
        if (isEmpty()) {
            first = last;
        }
        else{
            last_1.next = last;
        }
        n++; // increment n by one
    }

    // Returns the item at the front of this deque.
    public Item peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return first.item; // return the first item of the deque
    }

    // Removes and returns the item at the front of this deque.
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        // If this is the last item that is being removed, both first and last must point to null.
        if (n == 1){
            Item item = first.item;
            first = null;
            last = null;
            n--;
            return item;
        }
        Item item = first.item;
        first = first.next;
        first.prev = null;
        n--; // Decrement n by one.
        return item;
    }

    // Returns the item at the back of this deque.
    public Item peekLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return last.item; // return the last item.
    }

    // Removes and returns the item at the back of this deque.
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        // If this is the last item that is being removed, both first and last must point to null.
        if (n == 1) {
            Item item = last.item;
            first = null;
            last = null;
            n--;
            return item;
        }
        Item item = last.item;
        last = last.prev;
        last.next = null;
        n--; // Decrement n by one
        return item;
    }

    // Returns an iterator to iterate over the items in this deque from front to back.
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Returns a string representation of this deque.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    // A deque iterator.
    private class DequeIterator implements Iterator<Item> {
        private Node current;

        // Constructs an iterator.
        public DequeIterator() {

            current = first; // Initialize instance variable appropriately
        }

        // Returns true if there are more items to iterate, and false otherwise.
        public boolean hasNext() {
            return current != null; //tells if the iterator has anymore iterator or nor
        }

        // Returns the next item.
        public Item next() {
            if (!hasNext()){
                throw new NoSuchElementException("Iterator is empty");
            }
            Item item = current.item;
            current = current.next; // advance current to the next node.
            return item; // Return the item in current
        }
    }

    // A data type to represent a doubly-linked list. Each node in the list stores a generic item
    // and references to the next and previous nodes in the list.
    private class Node {
        private Item item;  // the item
        private Node next;  // the next node
        private Node prev;  // the previous node
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its several powers, having " +
                "been originally breathed into a few forms or into one; and that, whilst this " +
                "planet has gone cycling on according to the fixed law of gravity, from so simple" +
                " a beginning endless forms most beautiful and most wonderful have been, and are " +
                "being, evolved. ~ Charles Darwin, The Origin of Species";
        int r = StdRandom.uniform(0, quote.length());
        StdOut.println("Filling the deque...");
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addFirst(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addLast(quote.charAt(r + i));
        }
        StdOut.printf("The deque (%d characters): ", deque.size());
        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        StdOut.println("Emptying the deque...");
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        }
        StdOut.println("deque.isEmpty()? " + deque.isEmpty());
    }
}
