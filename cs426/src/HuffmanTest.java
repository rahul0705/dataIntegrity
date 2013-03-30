
public class HuffmanTest {
    public static void main(String[] args) throws Exception{
	Huffman h = new Huffman();
		
	if (args.length != 2) {
			
	} 
	else {
	    h.readMsg(args[0]);
	    h.readKey(args[1]);
	    //h.printCount();
	    h.generateFreq();
	    h.createTree();
	    h.displayFrequencyTree();
	    System.out.println();
	    h.markTree();
	    //h.displayMarkTree();
	    h.printBFS();
	}
    }
}
