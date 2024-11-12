import dsa.LinkedQueue;
import dsa.LinkedStack;

import stdlib.StdIn;
import stdlib.StdOut;

public class Sort {
    // Entry point.
    public static void main(String[] args) {
        LinkedDeque<String> d = new LinkedDeque<>(); // Create a queue d.

        // For each word w read from standard input
        while (!StdIn.isEmpty()){
            String w = StdIn.readString();

            //Add w to the front of d if it is less than the first word in d
            if (d.isEmpty() || less(w, d.peekFirst())){
                d.addFirst(w);
            }
            // Add w to the back of d if it is greater than the last word in d.
            else if (less(d.peekLast(), w)){
                d.addLast(w);
            }

            // Otherwise, remove words that are less than w from the front of d and store them in a temporary stack s;
            // add w to the front of d; and add words from s also to the front of d.
            else {
                LinkedStack<String> s = new LinkedStack<String>();
                while (!d.isEmpty() && less(d.peekFirst(), w)){
                    s.push(d.removeFirst());
                }
                d.addFirst(w);
                while (!s.isEmpty()) {
                    d.addFirst(s.pop());
                }
            }

        }
        // Write the words from d to standard output.
        for (String w: d) {
            StdOut.println(w);
        }
    }

    // Returns true if v is less than w according to their lexicographic order, and false otherwise.
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
