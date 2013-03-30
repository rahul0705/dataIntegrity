import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NodeTest {

    int value;
    double frequency;
    NodeTest rightChild;
    NodeTest leftChild;
    String mark;

    public NodeTest(int v, double f) {
	value = v;
	frequency = f;
	rightChild = null;
	leftChild = null;
	mark = "";
    }

    public void print() {
        print("", true);
    }

    public boolean hasChildren() {
	if (rightChild != null && leftChild != null)
	    return true;

	return false;
    }

    private void print(String prefix, boolean isTail) {
	List<NodeTest> children = new LinkedList<NodeTest>();
	if (rightChild != null)
		children.add(rightChild);
	if (leftChild != null)
		children.add(leftChild);
	
        System.out.println(prefix + (isTail ? "^-->" : "|-->") + frequency);
        for (Iterator<NodeTest> iterator = children.iterator(); iterator.hasNext(); ) {
            iterator.next().print(prefix + (isTail ? "    " : "|  "), !iterator.hasNext());
        }
    }

    public void printMark() {
	printMark("", true);
    }

    private void printMark(String prefix, boolean isTail) {
	List<NodeTest> children = new LinkedList<NodeTest>();
	if (rightChild != null)
		children.add(leftChild);
	if (leftChild != null)
		children.add(rightChild);
	
        System.out.println(prefix + (isTail ? "^-->" : "|-->") + mark);
        for (Iterator<NodeTest> iterator = children.iterator(); iterator.hasNext(); ) {
            iterator.next().printMark(prefix + (isTail ? "    " : "|  "), !iterator.hasNext());
        }
    }
}
