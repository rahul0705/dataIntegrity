import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Node<T> {
	private T data;
	private Node<T> lChild;
	private Node<T> rChild;

	public Node(){
		this.data = null;
		this.lChild = null;
		this.rChild = null;
	}
	public Node(T data){
		this.data = data;
		this.lChild = null;
		this.rChild = null;
	}

	public Node<T> getLeftChild(){
		return this.lChild;
	}

	public Node<T> getRightChild(){
		return this.rChild;
	}

	public void setLeftChild(Node<T> lChild){
		this.lChild = lChild;
	}

	public void setRightChild(Node<T> rChild){
		this.rChild = rChild;
	}

	public T getData(){
		return this.data;
	}

	public void setData(T data){
		this.data = data;
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

	public void print() {
		print("", true);
	}

	private void print(String prefix, boolean isTail) {
		List<Node<T>> children = new LinkedList<Node<T>>();
		if (this.hasChildren()){
			children.add(this.lChild);
			children.add(this.rChild);
		}

		System.out.println(prefix + (isTail ? "^-->" : "|-->") + data.toString());
		for (Iterator<Node<T>> iterator = children.iterator(); iterator.hasNext(); ) {
			iterator.next().print(prefix + (isTail ? "    " : "|  "), !iterator.hasNext());
		}
	}
}