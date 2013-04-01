package BST;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import Helpers.HMAC;
import Helpers.Node;

public class BalancedSearchTree{
	private ArrayList<String> data;
	private String key;
	private String searchScheme;
	private Node root;
	private byte[] mark;
	
	
	public BalancedSearchTree(){
		this.data = new ArrayList<String>();
		root = new Node();
	}
	
	public void readData(String dataFile){
		Scanner scan;
		try {
			scan = new Scanner(new File(dataFile));
			while(scan.hasNext())
				data.add(scan.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void readKey(String keyFile){
		Scanner scan;
		try {
			scan = new Scanner(new File(keyFile));
			this.key = scan.nextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void readTree(String treeFile){
		Scanner scan;
		try {
			scan = new Scanner(new File(treeFile));
			this.searchScheme = scan.nextLine();
			while(scan.hasNext())
				data.add(scan.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void buildTree(){
		mark = HMAC.encode(this.toString().getBytes(), key);
		int lowerBound = (int) Math.ceil((double)data.size()/4.0);
		int capacity = (int) Math.floor(Math.log10(Math.ceil((double)data.size()/2.0))/Math.log10(2));
		int offset;
		if (capacity == 0)
			offset = 0;
	}
	
	private void split(){
		
	}
	
	public String toString(){
		String s = new String();
		for(int i = 0; i < data.size(); i++){
			s = s + data.get(i);
		}
		return s;
	}
}
