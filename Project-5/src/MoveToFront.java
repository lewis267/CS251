public class MoveToFront {
    /**
     *  apply move-to-front encoding, reading from standard input and writing to standard output
     */
    public static void encode()	{

        // initialize the sequence
        char[] sequence = new char[256];
        for (char i = 0; i < sequence.length; i++) {
            sequence[i] = i;
        }

        while (!BinaryStdIn.isEmpty()) {

            // read in character
            char c = BinaryStdIn.readChar();

            // get value to output
            char o = sequence[c];

            // move to front
            for (int i = 0; i < sequence.length; i++) {
                if (sequence[i] < o) sequence[i]++;
            }
            sequence[c] = 0;

            // output char
            BinaryStdOut.write(o);
        }
        BinaryStdOut.flush();
    }

    /**
     *  apply move-to-front decoding, reading from standard input and writing to standard output
     */
    public static void decode()	{

        // initialize the sequence
        char[] sequence = new char[256];
        for (char i = 0; i < sequence.length; i++) {
            sequence[i] = i;
        }

        while (!BinaryStdIn.isEmpty()) {

            // read in character
            char c = BinaryStdIn.readChar();

            // get the value
            char o = sequence[c];

            // output char
            BinaryStdOut.write(o);

            // move to front
            char temp = o;
            for (int i = 0; i <= c; i++) {
                char temp2 = sequence[i];
                sequence[i] = temp;
                temp = temp2;
            }
        }
        BinaryStdOut.flush();
    }

    /**
     * if args[0] is '-', apply move-to-front encoding
     * if args[0] is '+', apply move-to-front decoding
     * @param args
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new RuntimeException("Illegal command line argument");
    }
}