package data;

import java.util.ArrayList;
import java.util.List;

import representation.BrownClusterRepresentation;
/*
 * stores each row data of a sentence
 */
public class DataRowBrownCluster {
	private String word;
	private String pos;
	private String label;
	public List<String> representation;
	
	public DataRowBrownCluster(){}
	public DataRowBrownCluster(String line) {
		processLine(line);
	}
	public void processLine(String line){
		//processes a string of line, stores the field
		String[] splitted = line.split("(\\s+|\\t+)");
		if(splitted.length < 3){
			System.err.println("DataRow: Cannot process line : " + line);
			System.exit(1);
		}
		word = splitted[0].trim();
		pos = splitted[1].trim();
		label = splitted[2].trim();
		
		representation = new ArrayList<String>();
		//generate representation
		representation.add(BrownClusterRepresentation.getRepresentation(word));
	}
	
	
	public String getRowWithFeatureForNer() {
		StringBuilder sb = new StringBuilder();
		sb.append(word); //word as it is
		sb.append(" ");
		String smoothedWord = TokenProcessor.getSmoothedWord(word);
		sb.append(smoothedWord);
		sb.append(" ");
		sb.append(smoothedWord.toUpperCase()); //all uppercase
		sb.append(" ");
		sb.append(isInitialCap()); //is init cap?
		sb.append(" ");
		sb.append(isAllCaps()); //is init cap?
		sb.append(" ");
		String[] prefixes = TokenProcessor.prefixes(smoothedWord);
		for(String prefix: prefixes) {
			sb.append(prefix);
			sb.append(" ");
		}
		
		String[] suffixes = TokenProcessor.suffixes(smoothedWord);
		for(String suffix: suffixes) {
			sb.append(suffix);
			sb.append(" ");
		}
		//try prefixes of length 4,6,10 and 20 if applicable
		int[] prefixLengths = {4, 6, 10, 20};
		String[] brownPrefixes = new String[prefixLengths.length];
		for(int i=0; i<brownPrefixes.length; i++) {
			brownPrefixes[i] = "_NA_";
		}
		for(String rep : representation) {
			for(int i=0; i<prefixLengths.length; i++) {
				if (rep.length() >= prefixLengths[i]) {
					brownPrefixes[i] = rep.substring(0, prefixLengths[i]);
				}
				sb.append(brownPrefixes[i] + " ");
			}
			//whole path
			sb.append(rep);
			sb.append(" ");
		}
		sb.append(label);
		return sb.toString();
	}
	
	public String getRowWithFeatureForChunking() {
		StringBuilder sb = new StringBuilder();
		sb.append(word); //word as it is
		sb.append(" ");
		String smoothedWord = TokenProcessor.getSmoothedWord(word);
		sb.append(smoothedWord.toLowerCase());
		sb.append(" ");
		sb.append(pos);
		sb.append(" ");
		//try prefixes of length 4,6,10 and 20 if applicable
		int[] prefixLengths = {4, 6, 10, 20};
		String[] brownPrefixes = new String[prefixLengths.length];
		for(int i=0; i<brownPrefixes.length; i++) {
			brownPrefixes[i] = "_NA_";
		}
		for(String rep : representation) {
			for(int i=0; i<prefixLengths.length; i++) {
				if (rep.length() >= prefixLengths[i]) {
					brownPrefixes[i] = rep.substring(0, prefixLengths[i]);
				}
				sb.append(brownPrefixes[i] + " ");
			}
			//whole path
			sb.append(rep);
			sb.append(" ");
		}		
		sb.append(label);
		return sb.toString();
	}
	
	
	
	public String isInitialCap() {
		if(TokenProcessor.isInitialCap(word)) {
			return "Y";
		}
		return "N";
	}
	
	public String isAllCaps() {
		if(TokenProcessor.isAllCaps(word)) {
			return "Y";
		}
		return "N";
	}
	
	
	public String getLabel() {
		return label;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}



	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String spaces(int size) {
		String returnString = "";
		for (int i=0; i<size; i++) {
			returnString += " ";
		}
		return returnString + " ";
	}
}
