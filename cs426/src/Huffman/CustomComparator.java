package Huffman;
import java.util.Comparator;

public class CustomComparator implements Comparator<HuffmanNode>{
	public int compare(HuffmanNode n1, HuffmanNode n2) {
		
		if (n1.frequency > n2.frequency) 
			return 1;
		else if (n1.frequency < n2.frequency)
			return -1;
		
		if (n1.symbol.length() > n2.symbol.length())
			return 1;
		else if (n1.symbol.length() < n2.symbol.length())
			return -1;
		
		if ((byte)n1.symbol.charAt(0) > (byte)n2.symbol.charAt(0))
			return 1;
		else if ((byte)n1.symbol.charAt(0) < (byte)n2.symbol.charAt(0))
			return -1;
		
		return 0;
	}
}
