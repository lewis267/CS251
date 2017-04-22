public class BurrowsWheeler {

    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode()
    {
        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        // create the table
        BW_String[] table = new BW_String[input.length];
        for (int shift = 0; shift < input.length; shift++) {
            table[shift] = new BW_String(shift, s);
        }

        // sort the table
        Quick3String q3s = new Quick3String();
        int row = q3s.sort(table);

        // output the result
        BW_String.outputResult(table, input, row);
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
        //encode();

        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new RuntimeException("Illegal command line argument");
    }



    public static class BW_String {
        private int index;
        private int length;
        String str; // untouched string so that it can be huge and used by reference.

        BW_String(int i, String s, int length) {
            this.index = i;
            this.str = s;
            this.length = length;
        }
        BW_String(int i, String s) {
            this.index = i;
            this.str = s;
            this.length = s.length();
        }

        public int compareTo(BW_String other) {
            for (int i = 0; i < this.length() && i < other.length(); i++) {
                if (this.charAt(i) > other.charAt(i)) return 1;
                if (this.charAt(i) < other.charAt(i)) return -1;
            }
            if (this.length() > other.length()) return 1;
            if (this.length() < other.length()) return -1;
            return 0;
        }

        public int length() {
            return this.length;
        }

        public char charAt(int idx) {
            assert(idx < this.length);
            return str.charAt((idx+index) % str.length());
        }

        public BW_String substring(int offset) {
            assert(offset < this.length && offset > -1);

            int new_idx = (offset + index) % str.length(); // % to wrap around string
            int new_len = this.length - offset;

            return new BW_String(new_idx, this.str, new_len);
        }
        public BW_String substring(int offset, int limit) {
            assert(offset > -1 && limit > -1);
            assert(offset <= limit && limit < this.length);

            int new_idx = (offset + index) % str.length();
            int new_len = (limit - offset);

            return new BW_String(new_idx, this.str, new_len);
        }

        @Override
        public boolean equals(Object o) {
            BW_String other = (BW_String)o;
            if (this.length() != other.length()) return false;

            for (int i = 0; i < this.length(); i++) {
                if (this.charAt(i) != other.charAt(i)) return false;
            }

            return true;
        }

        static void outputResult(BW_String[] table, char[] s, int row) {
            BinaryStdOut.write(row);

            for (BW_String st : table) {
                char c = s[st.index==0 ? s.length - 1 : st.index - 1];
                BinaryStdOut.write(c);
            }
            BinaryStdOut.flush();
        }
    }
}

