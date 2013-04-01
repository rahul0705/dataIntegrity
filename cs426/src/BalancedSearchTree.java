import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BalancedSearchTree{
	private ArrayList<String> data;
	private byte[] key;
	private byte[] searchScheme;
	
	
	public BalancedSearchTree(){
		this.data = new ArrayList<String>();
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
			this.key = scan.nextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void readTree(String treeFile){
		Scanner scan;
		try {
			scan = new Scanner(new File(treeFile));
			this.searchScheme = scan.nextLine();
			while(scan.hasNext())
				data.add(scan.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		String s = new String();
		for(int i = 0; i < data.size(); i++){
			s = s + data.get(i);
		}
		return s;
	}
}
