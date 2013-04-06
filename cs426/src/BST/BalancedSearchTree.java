package BST;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import Helpers.HMAC;

public class BalancedSearchTree{

	private ArrayList<String> data;
	private String key;
	private String searchScheme;
	private BSTNode root;
	private String mark;
	private LinkedList<BSTNode> q;
	private int markIndex = 0;


	public BalancedSearchTree(){
		this.data = new ArrayList<String>();
		root = new BSTNode();
		q = new LinkedList<BSTNode>();
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

	private void buildTree(BSTNode n){
		int lowerBound = (int) Math.ceil((double)(n.getEnd()-n.getStart())/4.0);
		int capacity = (int) Math.floor(Math.log10(Math.ceil((double)(n.getEnd()-n.getStart())/2.0))/Math.log10(2));
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
				q.addLast(lChild);
			}else if((n.getStart() + offset + lowerBound) == n.getEnd() - 2){
				lChild.setData(data.get(n.getStart()));
				rChild.setStart(n.getStart() + lowerBound+offset);
				rChild.setEnd(n.getEnd());
				q.addLast(rChild);
			}else{
				lChild.setStart(n.getStart());
				lChild.setEnd(n.getStart() + lowerBound+offset);
				rChild.setStart(n.getStart() + lowerBound+offset);
				rChild.setEnd(n.getEnd());
				q.addLast(lChild);
				q.addLast(rChild);
			}
		}else{
			lChild.setData(data.get(n.getStart()));
			rChild.setData(data.get(n.getEnd()-1));
		}
		n.setLeftChild(lChild);
		n.setRightChild(rChild);
		return;
	}

	public void buildTree(){
		mark = HMAC.toBitString(HMAC.encode(this.toString().getBytes(), key));
		root.setStart(0);
		root.setEnd(data.size());
		q.addLast(root);
		while(!q.isEmpty()){
			buildTree(q.removeFirst());
		}
		root.print();
	}

	public void bfs(){
		Queue<BSTNode> queue = new LinkedList<BSTNode>();
		queue.add(root);
		System.out.print(root);
		root.visited = true;
		while(!queue.isEmpty()) {
			BSTNode node = queue.remove();
			BSTNode child=null;
			while((child = node.getNextChild()) != null) {
				child.visited = true;
				System.out.print(child);
				queue.add(child);
			}
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
