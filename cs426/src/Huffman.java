import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import Helpers.HMAC;

public class Huffman {

	private static final int ALPHABET_COUNT = 256;
	private static final boolean INCLUDE_ZEROS = false;
	
	private int input_count[];
	private int totalchars;
	
	
	private ArrayList<HuffmanNode> freqs;
	private ArrayList<Byte> msg;
	private String key;

	private HuffmanNode root;
	
	public static void main(String[] args) {
		String keyfile, inputfile;
		keyfile = "input/bst_test_key.txt";
		//inputfile = "input/huff_test_mssg.txt";
		inputfile = "input/huffrandom.txt";
		
		Huffman h = new Huffman(keyfile, inputfile);
	}
	
	public Huffman(String file1, String file2) {
		freqs = new ArrayList<HuffmanNode>();
		msg = new ArrayList<Byte>();
		
		input_count = new int[ALPHABET_COUNT];
		key = "";
		
		for (int i = 0; i < ALPHABET_COUNT; i++)
			input_count[i] = 0;
		
		readKey(file1);
		System.out.println("Key = " + key);
		readMsg(file2);
		calculateFrequency();
		createFrequencyTree();
		root.print();
		//byte[] key = HMAC.encode(msg.toArray(), key);
	}
	
	public void readKey(String filename) {
		
		File file = new File(filename);
		
		try {
			Scanner scr = new Scanner(file);
			if (!scr.hasNextLine()) {
				System.out.println("Invalid Key");
				System.exit(0);
			} else {
				key = scr.nextLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.exit(0);
		}
	}
	
	public void readMsg(String str) {
		try {
			InputStream is = new FileInputStream(str);
			while(is.available() > 0) {
				int b = is.read();
				input_count[b]++;
				totalchars += 1;
				msg.add((byte)b);	
			}
		} catch (IOException e) {
			System.out.println(e);
			System.exit(0);
		}
	}

	public void calculateFrequency() {
		for (int i = 0; i < ALPHABET_COUNT; i++) {
			double f = (input_count[i]) / ((double)totalchars);	
			
			if (INCLUDE_ZEROS) {
				freqs.add(new HuffmanNode((char)i + "", f));
			}
			
			if (!INCLUDE_ZEROS) {
				if (input_count[i] != 0)
					freqs.add(new HuffmanNode((char)i + "", f));
			}
		}
	}
	
	public void createFrequencyTree() {
		while (freqs.size() > 1) {
			Collections.sort(freqs, new CustomComparator());
			HuffmanNode right = freqs.remove(0);
			HuffmanNode left = freqs.remove(0);
			HuffmanNode parent = new HuffmanNode(right.symbol + left.symbol, right.frequency + left.frequency);
			
			parent.rightChild = right;
			parent.leftChild = left;
			
			freqs.add(parent);
		}
		root = freqs.get(0);
		System.out.println(freqs.get(0).frequency);
	}
	
	@SuppressWarnings("unused")
	private void printInputMsg() {
		for (int i = 0; i < ALPHABET_COUNT; i++) {
			if (INCLUDE_ZEROS) {
				System.out.println(i + ": " + input_count[i]);
			}
			
			if (!INCLUDE_ZEROS) {
				if (input_count[i] != 0)
					System.out.println(i + ": " + input_count[i]);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void printFrequency() {
		int length = freqs.size();
		for (int i = 0; i < length; i++) {
			System.out.println(freqs.get(i).symbol + ": " + freqs.get(i).frequency);
		}
	}
	
	
	public void printBFS() {
		printBFS(root);
		System.out.println();
	}    

	
	private void printBFS(HuffmanNode n) {
		LinkedList<HuffmanNode> queue = new LinkedList<HuffmanNode>();
		queue.add(n);
		while (queue.size() > 0) {
			HuffmanNode tmp = queue.removeFirst();
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

	/*
	public void markTree() {
		root = freqs.get(0);
		nodescount = 0;
		markSubTree("0", root);
		System.out.println(nodescount);
	}

	
	public void markSubTree(String mark, HuffmanNode n) {
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

	public void displayFrequencyTree() {
		freqs.get(0).print();
	}
	*/
}
