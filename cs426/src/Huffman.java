import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.LinkedList;

public class Huffman {
	
    public static final int ALPHABET_N = 256;
    private ArrayList<NodeTest> freqs;
	
    private int msg[];
    private int count;
    NodeTest root;
    
    private int nodescount;

    public Huffman() {
	msg = new int[ALPHABET_N];
	freqs = new ArrayList<NodeTest>();
		
	count = 0;
	for (int i = 0; i < ALPHABET_N; i++) {
	    msg[i] = 0;
	}
    }
	
    public void readKey(String str) {	


    }

    
    public void printBFS() {
	printBFS(root);
	System.out.println();
    }    

    private void printBFS(NodeTest n) {
	LinkedList<NodeTest> queue = new LinkedList<NodeTest>();
	queue.add(n);
	while (queue.size() > 0) {
	    NodeTest tmp = queue.removeFirst();
	    if (tmp.hasChildren()) {
		System.out.print("1");
		queue.add(tmp.leftChild);
		queue.add(tmp.rightChild);
	    }
	    else {
		System.out.print("0");
	    }
	}
    }

    public void markTree() {
	root = freqs.get(0);
	nodescount = 0;
	markSubTree("0", root);
	System.out.println(nodescount);
    }

    public void markSubTree(String mark, NodeTest n) {
	nodescount += 1;
	
	n.mark = mark;
	
	if (n.leftChild != null && n.rightChild != null) {
	    
	    if (n.leftChild.frequency > n.rightChild.frequency) {
		markSubTree(n.mark + "1", n.leftChild);
		markSubTree(n.mark + "0", n.rightChild);
	    }
	    else {
		markSubTree(n.mark + "1", n.leftChild);
		markSubTree(n.mark + "0", n.rightChild);
	    }

	}
    }

    public void displayMarkTree() {
	root.printMark();
    }

    public void displayFrequencyTree() {
	freqs.get(0).print();
    }

    public void createTree(){
	while (freqs.size() > 1) {
	    Collections.sort(freqs, new CustomComparator());
	    NodeTest r = freqs.remove(0);
	    NodeTest l = freqs.remove(0);
	    NodeTest n = new NodeTest(-1, r.frequency + l.frequency);
	    n.rightChild = r;
	    n.leftChild = l;	
	    freqs.add(n);
	}
	System.out.println(freqs.get(0).frequency);

    }
	
    public void printCount() {		
	for (int i = 0; i < ALPHABET_N; i++) {
	    if (msg[i] != 0)
		System.out.println(i + ": " + msg[i]);
	}
	System.out.println(count);
    }
	
    public void printFreq() {
	int length = freqs.size();
	for (int i = 0; i < length; i++) {
	    System.out.println(freqs.get(i).value + ": " + freqs.get(i).frequency);
	}
    }
	
    public void generateFreq() {
	for (int i = 0; i < ALPHABET_N; i++) {
	    if (msg[i] != 0) {
		double f = (msg[i]) / ((double)count);	
		freqs.add(new NodeTest(i, f));
	    } /*else {
		freqs.add(new Node(i, 0));
		}	*/				
	}		
	//printFreq();
    }
	
    public void readMsg(String str) {
		
	System.out.println(str);
		
	try {
	    InputStream is = new FileInputStream(str);
			
	    while(is.available() > 0) {
		int b = is.read();
		msg[b]++;
		count++;
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
		
    }
}
