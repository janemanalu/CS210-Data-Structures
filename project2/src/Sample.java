import stdlib.StdOut;

public class Sample {
    // Entry point.
    public static void main(String[] args) {
        // Accept lo (int), hi (int), k (int), and mode (String) as command-line arguments.
        int lo = Integer.parseInt(args[0]);
        int hi = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        String mode = args[3];

        // Create a random queue q containing integers from the interval [lo, hi].
        ResizingArrayRandomQueue<Integer>q = new ResizingArrayRandomQueue<>();
        for (int i = lo; i <= hi; i++){
            q.enqueue(i); //Enqueue integers from lo to hi into the queue
        }
        // If mode is “+” (sampling with replacement), sample and write k integers from q to standard output.
        if (mode.equals("+")){
            for (int i = 0; i< k; i++){ // If mode is "+", print k samples from the queue
                StdOut.println(q.sample());
            }
        }
        //If mode is “-” (sampling without replacement), dequeue and write k integers from q to standard.
        else if (mode.equals("-")) { // If mode is "-", print k samples from the queue
            if (k > q.size()) {
                throw new IllegalArgumentException("Illegal mode");// throw exception if item is empty
            }
            for (int i =0; i < k; i++){
                StdOut.println(q.dequeue());
            }
        }
        else{
            throw new IllegalArgumentException("Illegal mode");// throw exception if item is empty

        }
    }

}
