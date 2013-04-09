package BST;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BSTNode{
	private String data;
	private int start;
	private int end;
	private int count;
	private String mark;
	public boolean visited = false;
	private BSTNode lChild;
	private BSTNode rChild;

	public BSTNode(){
		this.lChild = null;
		this.rChild = null;
	}

	public BSTNode getLeftChild(){
		return this.lChild;
	}

	public BSTNode getRightChild(){
		return this.rChild;
	}

	public void setLeftChild(BSTNode lChild){
		this.lChild = lChild;
	}

	public void setRightChild(BSTNode rChild){
		this.rChild = rChild;
	}

	public boolean hasLeftChild(){
		if(this.lChild != null)
			return true;
		return false;
	}

	public boolean hasRightChild(){
		if(this.rChild != null)
			return true;
		return false;
	}

	public boolean hasChildren(){
		if (this.rChild != null && this.lChild != null)
			return true;
		return false;
	}
	
	public void setMark(String mark){
		this.mark = mark;
	}
	
	public void setData(String data){
		this.data = data;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	
	public String getMark(){
		return this.mark;
	}
	
	public String getData() {
		return this.data;
	}
	
	public int getStart() {
		return this.start;
	}
	
	public int getEnd() {
		return this.end;
	}
	
	public int getCount(){
		return this.count;
	}
	
	public int size() {
		return this.end - this.start;
	}
	
	public BSTNode getNextChild(){
		BSTNode ret = this.getLeftChild();
		if(this.getLeftChild().visited)
			ret = this.getRightChild();
		else if (this.getRightChild().visited)
			ret = null;
		return ret;
	}

	public void print() {
		print("", true);
	}

	private void print(String prefix, boolean isTail) {
		List<BSTNode> children = new LinkedList<BSTNode>();
		if (this.hasChildren()){
			children.add(this.lChild);
			children.add(this.rChild);
		}

		System.out.println(prefix + (isTail ? "^-->" : "|-->") + ((mark != null) ? mark : data));
		for (Iterator<BSTNode> iterator = children.iterator(); iterator.hasNext(); ) {
			iterator.next().print(prefix + (isTail ? "    " : "|  "), !iterator.hasNext());
		}
	}
}