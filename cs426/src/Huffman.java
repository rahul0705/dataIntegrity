import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import Helpers.HMAC;

public class Huffman {

	private static final int ALPHABET_COUNT = 256;
	private static final boolean INCLUDE_ZEROS = true;
	private static final String correct = "1111111111111111011111111111111111111111111111111111111111111000000000000000000000000000000000000000000000000000000001010001010111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000000000000000000000000010000000000000000000000000000000000000011111111111111111111111111111111111111111111111111111111111111110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
	
	private HashMap<Byte, String> code_book;
	
	private int input_count[];
	private int totalchars;
	
	private byte[] uncompressed_msg;
	
	private ArrayList<HuffmanNode> freqs;
	private ArrayList<Byte> msg;
	private ArrayList<HuffmanNode> leaves;
	
	private String key;
	private String hmac_bit_string;

	private HuffmanNode root;
	
	public static void main(String[] args) {
		String keyfile, inputfile;
		keyfile = "input/bst_test_key.txt";
		inputfile = "input/huff_test_mssg.txt";
		//inputfile = "input/huffrandom.txt";
		
		Huffman h = new Huffman(keyfile, inputfile);
	}
	
	public Huffman(String file1, String file2) {
		freqs = new ArrayList<HuffmanNode>();
		msg = new ArrayList<Byte>();
		
		input_count = new int[ALPHABET_COUNT];
		key = "";
		hmac_bit_string = "";
		leaves = new ArrayList<HuffmanNode>();
		code_book = new HashMap<Byte, String>();
		
		for (int i = 0; i < ALPHABET_COUNT; i++)
			input_count[i] = 0;
		
		readKey(file1);
		//System.out.println("Key  = " + key);
		readMsg(file2);
		calculateFrequency();
		createFrequencyTree();
		//root.print();
		
		int length = msg.size();
		uncompressed_msg = new byte[length];
		
		for (int i = 0; i < length; i++) {
			uncompressed_msg[i] = msg.get(i); 
		}
		
		byte[] hmac = HMAC.encode(uncompressed_msg, key);
		hmac_bit_string = HMAC.toBitString(hmac);
		//System.out.println("Hmac = " + hmac_bit_string);
		//System.out.println();
		markTree();
		printBFS();
		//System.out.println(correct);
		
		for (int i = 0; i < leaves.size(); i++) {
			String mark = getMark(leaves.get(i), "");
			char c = leaves.get(i).symbol.charAt(0);
			byte b = (byte)c;
			
			Byte val = new Byte(b);
			code_book.put(val, mark);
			//System.out.println(mark);
			System.out.println(b);
		}
		
		
		for (int i = 0; i < uncompressed_msg.length; i++) {
			System.out.println("" + code_book.get(uncompressed_msg[i]));
		}
		
	}

	private String getMark(HuffmanNode n, String mark) {
		if (n == null) {
			return mark;
		}
		
		return getMark(n.parent, mark + n.mark);
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
				leaves.add(tmp);
			}
		}
	}
	
	
	public void markTree() {
		LinkedList<HuffmanNode> queue = new LinkedList<HuffmanNode>();
		int index = 0;
		queue.add(root);
		
		while (queue.size() > 0) {
			HuffmanNode n = queue.removeFirst();
			char mark = hmac_bit_string.charAt(index);
			
			if (n.hasChildren()) {
				HuffmanNode least_weighty;
				HuffmanNode most_weighty;
				
				CustomComparator cmp = new CustomComparator();
				
				if (cmp.compare(n.leftChild, n.rightChild) < 0) {
					least_weighty = n.leftChild;
					most_weighty = n.rightChild;
				} else {
					least_weighty = n.rightChild;
					most_weighty = n.leftChild;
				}
				
				if (mark == '1') {
					n.rightChild = least_weighty;
					n.rightChild.mark = '1';
					n.leftChild = most_weighty;
					n.leftChild.mark = '0';
				} else if (mark == '0') {
					n.rightChild = most_weighty;
					n.rightChild.mark = '0';
					n.leftChild = least_weighty;
					n.rightChild.mark = '1';
				}
				
				n.leftChild.parent = n;
				n.rightChild.parent = n;
				
				queue.add(n.leftChild);
				queue.add(n.rightChild);	
				
				index += 1;
			}	
		}
	}

	
}
