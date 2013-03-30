import java.util.Comparator;

public class CustomComparator implements Comparator<NodeTest>{
	public int compare(NodeTest n1, NodeTest n2) {
		return ((Double) n1.frequency).compareTo(n2.frequency);
	}
}
