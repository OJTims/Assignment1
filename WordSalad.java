/* File: WordSalad.java - April 2018 */
package week09;

import java.util.*;
import java.util.ArrayList;

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

    //added this method:
    public int size() {
        if (this.first == null){
            return 0;
        }
        
        int size = 1;
        WordNode w = this.first;
        while (w != null){
            size++;
            w = w.next;
        }
        return size;
    }

    public void deleteNode(WordNode n){
        WordNode c = first;
        //if the first node is to be deleted
        if (c.equals(n)){
            this.first = this.first.next;
            return;
        }
        while (!(c.next.equals(n)) && (c.next != null)){ //!c.next.equals(n)
            c = c.next;
        }
        if (c.next == null){
            return;
        }
        c.next = n.next;
    }

    public WordNode moveNode(int num, WordNode nodeIn){
        WordNode n = nodeIn;
        for (int i=0;(i<num && n.next != null);i++){
            n = n.next;
        }
        return n;
    }

    private class WordNode {
        private String word;
        private WordNode next;

        private WordNode(String word, WordNode next) {
            this.word = word;
            this.next = next;
        }
    }

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


    public WordSalad[] distribute(int k) {
        if (k<=0){
            throw new NullPointerException("Enter a valid block size");
        }
        if (k > this.size()){
            return distribute(this.size());
        }

        WordSalad[] result = new WordSalad[k];
        int g = 0;
        WordNode w = first;
        while (w != null) {
            if (result[g] == null){
                result[g] = new WordSalad();
            }
            result[g].addLast(w.word);
            w = w.next;
            g++;
            if (g == k) {
                g = 0;
            }
        }
        return result;
    }


    public WordSalad[] chop(int k) {
	WordSalad[] result = new WordSalad[k];
	int size = this.size()-1;
        int rem = size % k;
	int s = size / k;
	List<Integer> nums = new ArrayList<>();
        
	if (k > size || k <= 0){
	    throw new NullPointerException("Enter a valid block size");
	}
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
		result[ind].addLast(w.word);
		w = w.next;
		g-=1;
	    }while (g>0);
	    ind++;
	}
        return result;
    }
    
    public WordSalad[] split(int k){
        WordSalad[] result = new WordSalad[this.size()];
        int saladLen = size();
        int ind = 0;
        WordSalad t = new WordSalad();
        
        while (saladLen > 0){
            System.out.println("ind: "+ind);
            WordNode n = this.first;
            int numW = (int) Math.ceil(saladLen / k);
            for (int i=0;i<numW;i++){
                System.out.println("\ni "+i);
                t.addLast(n.word);
                deleteNode(n);
                n = moveNode(k, n);
            }
            saladLen = size(); //-------------
            result[ind] = t;
            t = new WordSalad();
            ind++;
        }
        return result;
    }

    public static WordSalad merge(WordSalad[] blocks) {
        WordSalad result = new WordSalad();
        int c = 0;
        int len = 1;
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

    public static WordSalad join(WordSalad[] blocks) {
        WordSalad result = new WordSalad();
        List<String> r = new ArrayList<String>();
        for (WordSalad salad : blocks){
            WordNode n = salad.first;
            while (n != null){
                result.addLast(n.word);
                n = n.next;
            }
        }
        return result;
    }

    public static WordSalad recombine(WordSalad[] blocks, int k) {
        return null;
    }
}


