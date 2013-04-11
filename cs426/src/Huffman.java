import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import Helpers.HMAC;

public class Huffman {

	private static final int ALPHABET_COUNT = 256;
	
	private HashMap<Byte, String> code_book;
	
	private int input_count[];
	private int totalchars;
	
	private byte[] uncompressed_msg;
	
	private ArrayList<HuffmanNode> leaves;
	private ArrayList<HuffmanNode> freqs;
	private ArrayList<Byte> msg;
	private ArrayList<String> leaf_values;
	
	private String key;
	private String hmac_bit_string;
	private String compressed_msg;
	private String tree_structure;
	
	private HuffmanNode root;
	
	public static void main(String[] args) {
		String keyfile, inputfile, treefile;
		keyfile = "input/bst_test_key.txt";
		inputfile = "input/huff_test_mssg.txt";	
		treefile = "output/1b_dirty.txt";
		
		Huffman h = new Huffman(keyfile, inputfile);
		//Huffman h = new Huffman(keyfile, treefile, 1);
	}
	
	public Huffman(String file1, String file2) {
		msg = new ArrayList<Byte>();
		freqs = new ArrayList<HuffmanNode>();
		leaves = new ArrayList<HuffmanNode>();
		code_book = new HashMap<Byte, String>();
		
		key = "";
		hmac_bit_string = "";

		input_count = new int[ALPHABET_COUNT];
		for (int i = 0; i < ALPHABET_COUNT; i++)
			input_count[i] = 0;
		
		readKey(file1);
		readMsg(file2);
		calculateFrequency();
		createFrequencyTree();
		
		hmac_bit_string = HMAC.toBitString(HMAC.encode(uncompressed_msg, key));
		
		markTree();
		generateCodeBook();
		
		//Print Structure Based on file format
		printTreeStructure();
		printLeaves();
		printCompressedMsg();
	}
	
	public Huffman(String file1, String file2, int val) {
		freqs = new ArrayList<HuffmanNode>();
		msg = new ArrayList<Byte>();
		
		key = "";
		tree_structure = "";
		compressed_msg = "";
		
		leaves = new ArrayList<HuffmanNode>();
		leaf_values = new ArrayList<String>();
		
		input_count = new int[ALPHABET_COUNT];
		for (int i = 0; i < ALPHABET_COUNT; i++)
			input_count[i] = 0;
		
		readKey(file1);
		readTree(file2);	
		constructTree();

		hmac_bit_string = HMAC.toBitString(HMAC.encode(uncompressed_msg, key));
		String mark = extractMark();
		
		boolean modified = false;
		int length = mark.length();
		for (int i = 0; i < length; i++) {
			if (mark.charAt(i) != hmac_bit_string.charAt(i)) {
				modified = true;
				break;
			}
		}
		
		if (modified)
			System.out.println("Tree Modified");
		else 
			System.out.println("Tree Validated");
		
		//printUncompressedMsg();
	}

	//Read Key file
	//File must be one line long
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
	
	//Read original message
	//Message is read in by byte. Using an inputstream
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
		
		//Store message into byte array which can be used later for calculating the HMAC
		int length = msg.size();
		uncompressed_msg = new byte[length];
		for (int i = 0; i < length; i++)
			uncompressed_msg[i] = msg.get(i); 
	}
	
	//Read Complete Tree File
	//File Format:
	//TreeStructure \n
	//Leaves \n
	//Compressed Message \n
	public void readTree(String filename) {
		File file = new File(filename);
		try {
			Scanner scr = new Scanner(file);
			if (!scr.hasNextLine()) {
				System.out.println("Invalid File");
				System.exit(0);
			} else {
				tree_structure = scr.nextLine();
				int length = tree_structure.length();
				int count = 0;
				for (int i = 0; i < length; i++) {
					if (tree_structure.charAt(i) == '0')
						count++;
				}
				while (count > 0) {
					if (!scr.hasNextLine()) {
						System.out.println("Invalid Tree Format: Not Enough Leaves");
						System.exit(0);
					}
					String leaf = scr.nextLine();
					byte b = Byte.valueOf(leaf);
					leaf_values.add((char)b + "");
					count--;
				}
				if (!scr.hasNextLine()) {
					System.out.println("Invalid Tree Format: No Compressed Message");
					System.exit(0);
				}
				compressed_msg = scr.nextLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.exit(0);
		}
	}
	
	//Generates all compressed value for each possible bytes
	public void generateCodeBook() {
		getCode(root, "");
	}
	
	//Helper used to generating the codebook
	private void getCode(HuffmanNode n, String code) {
		if (!n.hasChildren()) {
			byte b = (byte)n.symbol.charAt(0);
			code_book.put(b, code);
			return;
		}
		getCode(n.leftChild, code + "0");
		getCode(n.rightChild, code + "1");
	}
	
	//Construct tree using Structure/leaves/compressed message
	public void constructTree() {
		LinkedList<HuffmanNode> queue = new LinkedList<HuffmanNode>();
		root = new HuffmanNode("", 0);
		queue.add(root);
		int tree_index = 0;
		int leaf_index = 0;
		while (queue.size() > 0) {
			HuffmanNode tmp = queue.removeFirst();
			if (tree_structure.charAt(tree_index) == '1') {
				HuffmanNode l = new HuffmanNode("", 0);
				HuffmanNode r = new HuffmanNode("", 0);
				tmp.leftChild = l;
				tmp.rightChild = r;
				l.parent = tmp;
				r.parent = tmp;
				queue.add(l);
				queue.add(r);
			}
			else {
				tmp.symbol = leaf_values.get(leaf_index);
				leaves.add(tmp);
				leaf_index++;
			}
			tree_index++;
		}
	
		HuffmanNode current = root;
		for (int i = 0; i < compressed_msg.length(); i++) {
			char mark = compressed_msg.charAt(i);
			if (mark == '0') {
				current = current.leftChild;
			} else {
				current = current.rightChild;
			}
			if (!current.hasChildren()) {
				byte b = (byte)current.symbol.charAt(0);
				msg.add(b);
				current = root;
			}
		}
		
		//Calculate Frequencies counts based off compressed message
		int length = msg.size();
		uncompressed_msg = new byte[length];
		totalchars = length;
		for (int i = 0; i < length; i++) {
			uncompressed_msg[i] = msg.get(i); 
			input_count[(int)msg.get(i)]++;
		}
		
		//Calculate frequencies and store then in freqs
		calculateFrequency();
		
		//For each Leaf check the frequency for the same symbol and copy the value
		for (HuffmanNode n: leaves) {
			for (HuffmanNode f : freqs) {
				if (n.symbol.equals(f.symbol)) {
					n.frequency = f.frequency;
					break;
				}
			}
		}
		
		buildUp();
	}
	
	//Build up from the leaves of the tree until you reach the node. Populating with frequencies and symbols.
	public void buildUp() {
		LinkedList<HuffmanNode> nodes;
		CustomComparator cmp = new CustomComparator();
		LinkedList<HuffmanNode> queue = new LinkedList<HuffmanNode>();
		ArrayList<HuffmanNode> excludelist = new ArrayList<HuffmanNode>();
		while (true) {
			nodes = new LinkedList<HuffmanNode>();
			queue.add(root);
			while (queue.size() > 0) {
				HuffmanNode tmp = queue.removeFirst();
				if (tmp.hasChildren() && tmp.symbol.equals("")) {
					queue.add(tmp.leftChild);
					queue.add(tmp.rightChild);
				}
				else {
					if (!excludelist.contains(tmp)) {
						nodes.addFirst(tmp);
					}
				}
			}
			
			HuffmanNode r = nodes.removeFirst();
			HuffmanNode l = nodes.removeFirst();
			
			if (cmp.compare(l, r) < 0) {
				r.parent.symbol = l.symbol + r.symbol;
			} else {
				r.parent.symbol = r.symbol + l.symbol;
			}
			
			r.parent.frequency = r.frequency + l.frequency;
			
			excludelist.add(r);
			excludelist.add(l);
			
			if (r.parent == root)
				break;
		}
	}

	//Extract Mark from a tree that is already built.
	//Done with DFS traversal
	public String extractMark() {
		String mark = "";
		CustomComparator cmp = new CustomComparator();
		LinkedList<HuffmanNode> queue = new LinkedList<HuffmanNode>();
		queue.add(root);
		while (queue.size() > 0) {
			HuffmanNode n = queue.removeFirst();
			if (n.hasChildren()) {
				queue.add(n.leftChild);
				queue.add(n.rightChild);
				
				if (cmp.compare(n.leftChild, n.rightChild) < 0) {
					mark += 0;
				}
				else {
					mark += 1;
				}
			}
		}
		return mark;
	}
	
	//Mark tree populated with frequencies and symbols
	//Swap children in DFS fashion based on custom comparer
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
					n.leftChild = most_weighty;
				} else if (mark == '0') {
					n.rightChild = most_weighty;
					n.leftChild = least_weighty;
				}
				
				queue.add(n.leftChild);
				queue.add(n.rightChild);	
				
				index += 1;
			}	
		}
	}
	
	//Calculate frequencies based on input_count and totalchars
	//Fill global Arraylist freqs
	public void calculateFrequency() {
		for (int i = 0; i < ALPHABET_COUNT; i++) {
			double f = (input_count[i]) / ((double)totalchars);	
			freqs.add(new HuffmanNode((char)i + "", f));
		}
	}
	
	//Add the two lowest frequencies repeatedly until 1 node exists
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
	
	public void printUncompressedMsg() {
		for (int i = 0; i < uncompressed_msg.length; i++) {
			System.out.print((char)uncompressed_msg[i]);
		}
	}
	
	//I Print Compressed Msg
	public void printCompressedMsg() {
		for (int i = 0; i < uncompressed_msg.length; i++) {
			System.out.print(code_book.get(uncompressed_msg[i]));
		}
	}
	
	//I Print the leaves
	public void printLeaves() {
		int length = leaves.size();
		for (int i = 0; i < length; i++) {
			byte b = (byte) leaves.get(i).symbol.charAt(0);
			System.out.println(b);
		}
	}
	
	//Print the inputmessage
	@SuppressWarnings("unused")
	private void printInputMsg() {
		for (int i = 0; i < ALPHABET_COUNT; i++) {
			System.out.println(i + ": " + input_count[i]);
		}
	}
	
	//Print the public call tree structure in a DFS fashion
	public void printTreeStructure() {
		printTreeStructure(root);
		System.out.println();
	}    

	//Print the tree helper function
	private void printTreeStructure(HuffmanNode n) {
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
	

	
}
