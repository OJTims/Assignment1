/* File: WordSalad.java - April 2018 */
package week09;

import java.util.*;

/**
 *  Skeleton implementation of the WordSalad class.
 *
 *  @author Michael Albert
 */
public class WordSalad implements Iterable<String> {

    private WordNode first;
    private WordNode last;
     
    public WordSalad() {
        this.first = null;
        this.last = null;
    }

    public WordSalad(java.util.List<String> words) {
        for (String word : words) {
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

    public int size() {
	int size = 1;
	for(WordNode n = first; n.next != null; n = n.next)
	    size++;     
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
  
    public java.util.Iterator<String> iterator() {
        return new java.util.Iterator<String>() {
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
        WordSalad[] result = new WordSalad[k];
        int g = 0;
        WordNode w = first;
	
	for (int c=0;c<k;c++){
            result[c] = new WordSalad();
        }
        while (w != null){
            result[g].addLast(w.word);
            w = w.next;
            g++;
            if (g == k){
                g = 0;
            }
    	}
	return result;	
    }
    
    
    public WordSalad[] chop(int k) {
	WordSalad[] result = new WordSalad[k];
	int size = this.size();

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
		result[ind].addLast(w.word);
		w = w.next;
		g-=1;
	    }while (g>0);
	    ind++;
	}
	return result;
    }
        
    public WordSalad[] split(int k) {
        return null;
    }
        
    public static WordSalad merge(WordSalad[] blocks) {
        return null;
    }
        
    public static WordSalad join(WordSalad[] blocks) {
        return null;
    }

    public static WordSalad recombine(WordSalad[] blocks, int k) {
        return null;
    }

}
