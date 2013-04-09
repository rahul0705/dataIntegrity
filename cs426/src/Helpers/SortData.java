package Helpers;

import java.util.Comparator;

public class SortData implements Comparator<String>{
	@Override
	public int compare(String x, String y) {
		int a = Integer.parseInt(x);
		int b = Integer.parseInt(y);
		return a-b;
	}

}
