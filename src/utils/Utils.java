package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	public static int getNumberOfLines(String filename) throws IOException {
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(filename));
		int length = 0;
		while( (br.readLine()) != null) {
			length++;
		}			 
		br.close();
		return length;
	}
	
	public static String spaces(int size) {
		String space = "";
		for(int i=0; i<size; i++){
			space += " ";
		}
		return space;
	}
}
