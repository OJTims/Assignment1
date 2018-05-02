/* File: WordSalad.java - April 2018 */
package week09;

import java.util.*;

/**
 *  Skeleton implementation of the WordSalad class.
 *
 *  @author Michael Albert
 */
public class WordSalad implements Iterable <String> {

    private WordNode first;
    private WordNode last;

    public WordSalad() {
        this.first = null;
        this.last = null;
    }

    public WordSalad(List <String> words) {
        for (String word: words) {
            addLast(word);
        }
    }

    public void add(String word) {
        if (this.first == null) {
            this.first = new WordNode(word, null);
            this.last = this.first;
            return;
        }
        WordNode newFirst = new WordNode(word, this.first);
        this.first = newFirst;
    }

    public void addLast(String word) {
        if (this.first == null) {
            add(word);
            return;
        }
        WordNode newLast = new WordNode(word, null);
        this.last.next = newLast;
        this.last = newLast;
    }

    //added methods:
    public int size() {
        int size = 1;
        WordNode w = this.first;
        while (w != null){
            size++;
            w = w.next;
        }
        return size;
    }

    private class WordNode {
        private String word;
        private WordNode next;

        private WordNode(String word, WordNode next) {
            this.word = word;
            this.next = next;
        }
    }

    // for iterating through WordSalad's
    public Iterator<String> iterator() {
        return new Iterator <String> () {
            private WordNode current = first;

            public boolean hasNext() {
                return current != null;
            }

            public String next() {
                String result = current.word;
                current = current.next;
                return result;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public String toString() {
        StringBuilder result = new StringBuilder("[");
        WordNode node = first;
        while (node != null) {
            result.append(node.word);
            result.append(node.next == null ? "" : ", ");
            node = node.next;
        }
        return result.toString() + "]";
    }

    // Method stubs to be completed for the assignment.
    // See the assignment description for specification of their behaviour.

    public WordSalad[] distribute(int k) {
        //error checking
        if (k == 0 || k<0 || k>this.size()) {
            return null;
        }
        WordSalad[] s = new WordSalad[k];
        for (int c = 0; c<k; c++) {
            s[c] = new WordSalad();
        }
        int g = 0;
        WordNode w = first;
        while (w != null) {
            s[g].addLast(w.word);
            w = w.next;
            g++;
            if (g == k) {
                g = 0;
            }
        }
        return s;
    }


    public WordSalad[] chop(int k) {
	WordSalad[] result = new WordSalad[k];
	int size = this.size()-1;
	//error checking for more blocks than words or no blocks
	if (k > size || k == 0){
	    return null;
	}
	int rem = size % k;
	int s = size / k;
	List<Integer> nums = new ArrayList<>();
	for (int i=0;i<k;i++){
	    nums.add(s);
	}
	for (int i=0;i<rem;i++){
	    nums.set(i, nums.get(i)+1);
	}
	WordNode w = this.first;
	int ind = 0;
        
	for (int i:nums){
	    int g = i;
	    result[ind] = new WordSalad();
	    do{
		result[ind].addLast(w.word); //w.word
		w = w.next;
		g-=1;
	    }while (g>0);
	    ind++;
	}
        return result;
    }
    
    //not required
    //works as if distributing 
    public WordSalad[] split(int k) {
        WordSalad[] dist = this.distribute(k);
        WordSalad[] result = new WordSalad[k];
        List<String> r = new ArrayList<String>();
        int ind = 0;
        for (WordSalad salad : dist){
            WordNode n = salad.first;
            while (n != null){
                r.add(n.word);
                n = n.next;
            }
            result[ind] = new WordSalad(r);
            r.clear();
            ind++;
            //need to set dist to  a WordSalad and distribute it and repeat the above code
        }
        return result;
    }

    //required
    //opposite of distribute
    public static WordSalad merge(WordSalad[] blocks) {
        WordSalad result = new WordSalad();
        int c = 0;//overall counter
        //calculating length of blocks
        int len = 0;
        for (WordSalad s : blocks){
            for (String word : s){
                len++;
            }
        }
        while (result.size() < len){
            for (WordSalad salad : blocks){
                int v = 0;
                for (String w : salad){
                    if (v == c){
                        result.addLast(w);
                    }
                    v++;
                }
            }
            c++;
        }
        return result;
    }

    //required
    //opposite of chop
    public static WordSalad join(WordSalad[] blocks) {
        return null;
    }

    //not required
    public static WordSalad recombine(WordSalad[] blocks, int k) {
        return null;
    }

}



/**
   [A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R
   [A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R




 */
