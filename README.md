Dylan Fistrovic
Nicholas Bachman
Rahul Mohandas

Eclipse Project - Compile

Running Huffman
Either modify arguments in run configurations
the arguments for Huffman are
-k keyfile
-d messagefile
-t treefile
-uncom

-uncom is used for displaying the decrompressed message when verifying the tree.

example usage:
	java -classpath bin/ Huffman/Huffman -k keyfile -d datafile
	java -classpath bin/ Huffman/Huffman -k keyfile -t treefile
	java -classpath bin/ Huffman/Huffman -k keyfile -t treefile -uncom

Running BalancedSearchTree
Either modify arugments to run configurations
the arguments for BalancedSearchTree are
-k keyfile
-d datafile
-t treefile
-cgt

-cgt is used for combinatorial group testing

example usage:
	java -classpath bin/ BST/BalancedSearchTree -k keyfile -d datafile
	java -classpath bin/ BST/BalancedSearchTree -k keyfile -d datafile -cgt
	java -classpath bin/ BST/BalancedSearchTree -k keyfile -t treefile
	java -classpath bin/ BST/BalancedSearchTree -k keyfile -t treefile -cgt

Included is a build.xml to compile using ant
