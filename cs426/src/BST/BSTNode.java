package BST;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BSTNode{
	private String data;
	private int start;
	private int end;
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
		return (this.lChild != null) ? true : false;
	}

	public boolean hasRightChild(){
		return (this.rChild != null) ? true : false;
	}

	public boolean hasChildren(){
		return (this.hasLeftChild() && this.hasLeftChild());
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
	
	public String toString(){
		/*if(data != null)
			return data.toString();
		else
			return "";*/
		if(this.hasChildren())
			return "1";
		return "0";
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