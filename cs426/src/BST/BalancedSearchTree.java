package BST;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import Helpers.HMAC;

public class BalancedSearchTree{

	private ArrayList<String> data;
	private String key;
	private String searchScheme;
	private BSTNode root;
	private String mark;
	private LinkedList<BSTNode> buildTreeQueue;
	private LinkedList<BSTNode> BFSQueue;
	private int markIndex = 0;
	private ArrayList<BSTNode> leaves;


	public BalancedSearchTree(){
		this.data = new ArrayList<String>();
		this.leaves = new ArrayList<BSTNode>();
		this.BFSQueue = new LinkedList<BSTNode>();
		this.root = new BSTNode();
		this.buildTreeQueue = new LinkedList<BSTNode>();
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
		mark = HMAC.toBitString(HMAC.encode(this.toString().getBytes(), key));
		root.setStart(0);
		root.setEnd(data.size());
		buildTreeQueue.addLast(root);
		while(!buildTreeQueue.isEmpty()){
			buildTree(buildTreeQueue.removeFirst());
		}
	}
	
	private void buildTree(BSTNode n){
		int lowerBound = (int) Math.ceil((double)n.size()/4.0);
		int capacity = (int) Math.floor(Math.log10(Math.ceil((double)n.size()/2.0))/Math.log10(2));
		int offset = 0;
		BSTNode lChild = new BSTNode();
		BSTNode rChild = new BSTNode();
		if (capacity != 0){
			String bitString = new String();
			for(int i = 0; i < capacity; i++){
				bitString += mark.charAt(markIndex);
				markIndex = (markIndex+1)%mark.length();
			}
			offset = Integer.parseInt(bitString, 2);
			n.setMark(bitString);
			if((n.getStart() + offset + lowerBound) == n.getEnd() - 1){
				rChild.setData(data.get(n.getEnd()-1));
				lChild.setStart(n.getStart());
				lChild.setEnd(n.getStart() + lowerBound+offset);
				buildTreeQueue.addLast(lChild);
			}else if((n.getStart() + offset + lowerBound) == n.getStart() + 1){
				lChild.setData(data.get(n.getStart()));
				rChild.setStart(n.getStart() + lowerBound+offset);
				rChild.setEnd(n.getEnd());
				buildTreeQueue.addLast(rChild);
			}else{
				lChild.setStart(n.getStart());
				lChild.setEnd(n.getStart() + lowerBound+offset);
				rChild.setStart(n.getStart() + lowerBound+offset);
				rChild.setEnd(n.getEnd());
				buildTreeQueue.addLast(lChild);
				buildTreeQueue.addLast(rChild);
			}
		}else{
			lChild.setData(data.get(n.getStart()));
			rChild.setData(data.get(n.getEnd()-1));
		}
		n.setLeftChild(lChild);
		n.setRightChild(rChild);
		return;
	}

	public void printTree() {
		BFSQueue.addLast(root);
		while (BFSQueue.size() > 0) {
			printTree(BFSQueue.removeFirst());
		}
		System.out.println();
		for(BSTNode node : leaves){
			System.out.println(node.getData());
		}
	}    

	private void printTree(BSTNode n) {
		if (n.hasChildren()) {
			System.out.print("1");
			BFSQueue.add(n.getLeftChild());
			BFSQueue.add(n.getRightChild());
		}
		else {
			System.out.print("0");
			leaves.add(n);
		}
	}
	
	public String toString(){
		String s = new String();
		for(int i = 0; i < data.size(); i++){
			s = s + data.get(i);
		}
		return s;
	}
}
