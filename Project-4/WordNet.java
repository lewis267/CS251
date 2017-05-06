import java.util.ArrayList;

/**
 * Created by lewis267 on 4/5/17.
 */
public final class WordNet {
    private boolean deal_with_collisions = true;
    private boolean try_to_save_memory = false;
    private Digraph G;
    private SAP sap;
    private TST<Integer> nouns;
    private String[][] lines;

    /**
     * Constructor takes the name of the two input files.
     * @param synsets the file that lists all the (noun) synsets.
     * @param hypernyms the file containing the set of hypernym relations.
     */
    public WordNet(String synsets, String hypernyms) {
        this.nouns = new TST<>();

        /**
         * Parse the Synsets File
         */

        ArrayList<String[]> noun_sets = new ArrayList<>();
        int graph_size = 0;

        In in = new In(synsets);
        while (in.hasNextLine()) {

            // parse the line in the list of noun synsets
            String raw = in.readLine();
            String[] line = raw.split(",");

            int synset_id = Integer.parseInt(line[0]);  // the synset id
            String[] synset = line[1].split(" ");       // the set of synonyms
            //String gloss = line[2];                   // the synset gloss (not used)

            noun_sets.add(synset);                      // add to the array of nouns

            if (!try_to_save_memory || deal_with_collisions) {

                for (String noun : synset) {                // add each noun in the synset to the TST

                    if (deal_with_collisions && nouns.contains(noun)) {                 // Deal with collisions
                        int noun_count = 1;                                             // one for the first and without a comma
                        for (String s : nouns.prefixMatch(noun + ",")) noun_count++;    // increment for each other

                        noun = noun + "," + Integer.toString(noun_count);               // Modify noun
                    }

                    nouns.put(noun, synset_id);
                }
            }
            else {
                // Maybe this might invoke a memory save through referencing
                for (int i = 0; i < synset.length; i++) {
                    nouns.put(noun_sets.get(synset_id)[i], synset_id);
                }
            }

            graph_size++;   // keep track of how big to make the graph
        }

        lines = new String[noun_sets.size()][];
        lines = noun_sets.toArray(lines);
        G = new Digraph(graph_size);

        /**
         * Parse the Hypernyms File
         */

        in = new In(hypernyms);
        while (in.hasNextLine()) {

            //parse the line in the list of hypernyms
            String rough = in.readLine();
            String[] line = rough.split(",");

            int synset_id = Integer.parseInt(line[0].trim());         // The first field
            for (int i = 1; i < line.length; i++) {
                int hypernym_id = Integer.parseInt(line[i].trim());   // One of the hypernyms
                G.addEdge(synset_id-1, hypernym_id-1);                // Add each hypernym as an edge
            }
        }

        // Verified Edges
        // Verified Commas
        //StdOut.println("commas: " + Integer.toString(commas) + ", G.E():" + Integer.toString(G.E()));

        sap = new SAP(this.G); // Create an instance of the SAP class
    }

    /**
     * Returns whether the given word is a noun in the WordNet.
     * @param word to check.
     * @return true if exists, false otherwise.
     */
    public boolean isNoun(String word) { return nouns.contains(word); }

    /**
     * Print the synset (second field of synsets.txt) that is the common ancestor
     * of nounA and nounB in a shortest ancestral path as well as the length of the path,
     * following this format: "sap<space>=<space><number>,<space>ancestor<space>=<space><synsettext>"
     * If no such path exists the sap should contain -1 and ancestor should say "null"
     * This method should use the previously defined SAP datatype.
     * @param nounA first child
     * @param nounB second child
     */
    public void printSap(String nounA, String nounB) {
        int number = -1;
        String synsettext = "null";

        if (isNoun(nounA) && isNoun(nounB)) {

            // Assume no duplicates
            int A = nouns.get(nounA);
            int B = nouns.get(nounB);

            if (deal_with_collisions) {
                // Consider collisions in TST
                for (String key : nouns.prefixMatch(nounA + ",")) {
                    int val = nouns.get(key);
                    A = (A < val) ? A : val;
                }
                for (String key : nouns.prefixMatch(nounB + ",")) {
                    int val = nouns.get(key);
                    B = (B < val) ? B : val;
                }
            }

            int ancestor_id = sap.ancestor(A-1, B-1);
            number          = sap.length(A-1, B-1);

            //Perform a reverse lookup of the ancestor number to get the ancestor word
            synsettext = lines[ancestor_id][0];
        }

        StdOut.println("sap = " + Integer.toString(number) + ", ancestor = " + synsettext);
    }

    /**
     * Runs the WordNet with the given input file
     * @param wordnettestfile input file
     */
    private void run(String wordnettestfile) {
        In in = new In(wordnettestfile);

        while (in.hasNextLine()) {
            String line = in.readLine();
            try {
                String first = line.split(" ")[0];
                String second = line.split(" ")[1];
                this.printSap(first, second);
            }
            catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid input, needs space between two words per line.");
            }
        }
    }

    public static void main(String[] args) {
        String synsets          = args[0];
        String hypernyms        = args[1];
        String wordnettestfile  = args[2];

        WordNet wn = new WordNet(synsets, hypernyms);
        wn.run(wordnettestfile);
    }
}

class TST<Value> {
    private int N;       // size
    private Node root;   // root of TST

    private class Node {
        private char c;                 // character
        private Node left, mid, right;  // left, middle, and right subtries
        private Value val;              // value associated with string
    }

    // return number of key-value pairs
    public int size() {
        return N;
    }

    /**************************************************************
     * Is string key in the symbol table?
     **************************************************************/
    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get(String key) {
        if (key == null || key.length() == 0) throw new RuntimeException("illegal key");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    // return subtrie corresponding to given key
    private Node get(Node x, String key, int d) {
        if (key == null || key.length() == 0) throw new RuntimeException("illegal key");
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }


    /**************************************************************
     * Insert string s into the symbol table.
     **************************************************************/
    public void put(String s, Value val) {
        if (!contains(s)) N++;
        root = put(root, s, val, 0);
    }

    private Node put(Node x, String s, Value val, int d) {
        char c = s.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if      (c < x.c)             x.left  = put(x.left,  s, val, d);
        else if (c > x.c)             x.right = put(x.right, s, val, d);
        else if (d < s.length() - 1)  x.mid   = put(x.mid,   s, val, d+1);
        else                          x.val   = val;
        return x;
    }


    /**************************************************************
     * Find and return longest prefix of s in TST
     **************************************************************/
    public String longestPrefixOf(String s) {
        if (s == null || s.length() == 0) return null;
        int length = 0;
        Node x = root;
        int i = 0;
        while (x != null && i < s.length()) {
            char c = s.charAt(i);
            if      (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else {
                i++;
                if (x.val != null) length = i;
                x = x.mid;
            }
        }
        return s.substring(0, length);
    }

    // all keys in symbol table
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, "", queue);
        return queue;
    }

    // all keys starting with given prefix
    public Iterable<String> prefixMatch(String prefix) {
        Queue<String> queue = new Queue<String>();
        Node x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != null) queue.enqueue(prefix);
        collect(x.mid, prefix, queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, String prefix, Queue<String> queue) {
        if (x == null) return;
        collect(x.left,  prefix,       queue);
        if (x.val != null) queue.enqueue(prefix + x.c);
        collect(x.mid,   prefix + x.c, queue);
        collect(x.right, prefix,       queue);
    }


    // return all keys matching given wilcard pattern
    public Iterable<String> wildcardMatch(String pat) {
        Queue<String> queue = new Queue<String>();
        collect(root, "", 0, pat, queue);
        return queue;
    }

    public void collect(Node x, String prefix, int i, String pat, Queue<String> q) {
        if (x == null) return;
        char c = pat.charAt(i);
        if (c == '.' || c < x.c) collect(x.left, prefix, i, pat, q);
        if (c == '.' || c == x.c) {
            if (i == pat.length() - 1 && x.val != null) q.enqueue(prefix + x.c);
            if (i < pat.length() - 1) collect(x.mid, prefix + x.c, i+1, pat, q);
        }
        if (c == '.' || c > x.c) collect(x.right, prefix, i, pat, q);
    }



    /*// test client
    public static void main(String[] args) {
        // build symbol table from standard input
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }


        // print results
        for (String key : st.keys()) {
            StdOut.println(key + " " + st.get(key));
        }
    }*/
}
