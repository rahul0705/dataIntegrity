import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BalancedSearchTree{
	ArrayList<String> data;
	String key;
	String searchScheme;
	
	
	public BalancedSearchTree(){
		data = new ArrayList<String>();
	}
	
	public void readData(String dataFile){
		Scanner scan;
		try {
			scan = new Scanner(new File(dataFile));
			while(scan.hasNext())
				data.add(scan.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void readKey(String keyFile){
		Scanner scan;
		try {
			scan = new Scanner(new File(keyFile));
			key = scan.nextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void readTree(String treeFile){
		Scanner scan;
		try {
			scan = new Scanner(new File(treeFile));
			searchScheme = scan.nextLine();
			while(scan.hasNext())
				data.add(scan.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
