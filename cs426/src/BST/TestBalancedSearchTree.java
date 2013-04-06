package BST;

public class TestBalancedSearchTree {
	
	public static void main(String args[]){
		BalancedSearchTree bst = new BalancedSearchTree();
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-t")){
				bst.readTree(args[++i]);
			}
			if(args[i].equals("-k")){
				bst.readKey(args[++i]);
			}
			if(args[i].equals("-d")){
				bst.readData(args[++i]);
			}
		}
		bst.buildTree();
		bst.bfs();
	}
}
