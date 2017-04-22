public class BurrowsWheeler {

    class BW_String implements Comparable {
        int index;

    }

    //<editor-fold desc="Globals">



    //</editor-fold>


    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode()
    {
        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();


    }
    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode()
    {
        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();


    }

    /**
     * if args[0] is '-', apply Burrows-Wheeler encoding
     * if args[0] is '+', apply Burrows-Wheeler decoding
     * @param args
     */
    public static void main(String[] args)
    {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new RuntimeException("Illegal command line argument");
    }
}