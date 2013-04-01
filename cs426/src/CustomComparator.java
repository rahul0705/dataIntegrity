import java.util.Comparator;

public class CustomComparator implements Comparator<HuffmanNode>{
	public int compare(HuffmanNode n1, HuffmanNode n2) {
		
		if (n1.frequency != n2.frequency) {
			return ((Double) n1.frequency).compareTo(n2.frequency);
		}
		
		return (n1.symbol).compareTo(n2.symbol);
	}
}
