import java.util.Comparator;

public class CustomComparator implements Comparator<Node>{
	public int compare(Node n1, Node n2) {
		return ((Double) n1.frequency).compareTo(n2.frequency);
	}
}
