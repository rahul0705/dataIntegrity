import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HuffmanNode {

	public double frequency;
	public String symbol;
	
	public HuffmanNode rightChild;
	public HuffmanNode leftChild;

	public HuffmanNode(String symbol, double freq) {
		this.frequency = freq;
		this.symbol = symbol;
		
		rightChild = null;
		leftChild = null;
	}

	public boolean hasChildren() {
		if (rightChild != null && leftChild != null)
			return true;
		return false;
	}
	
	public void print() {
		print("", true);
	}
	
	private void print(String prefix, boolean isTail) {
		List<HuffmanNode> children = new LinkedList<HuffmanNode>();
		
		if (leftChild != null) {
			children.add(leftChild);
		}
		
		if (rightChild != null) {
			children.add(rightChild);
		}
		
		System.out.println(prefix + (isTail ? "^-->" : "|-->") + frequency);
		for (Iterator<HuffmanNode> iterator = children.iterator(); iterator.hasNext(); ) {
			iterator.next().print(prefix + (isTail ? "    " : "|  "), !iterator.hasNext());
		}
	}

}
