package features.chunking;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GenerateBaselineTemplate {
	/*
	word
	lowerSmoothedWord
	pos
	for(String rep : representation) {
		sb.append(rep);
	}
	label;
	*/
	
	public static void main(String[] args) throws FileNotFoundException {
		String templateFile = "/home/anjan/work/chunk/features/baseline.template";
		StringBuffer content = new StringBuffer();
		int featureIndex = 0;
		//A: lowerSmoothedWord in window of +- 2
		for(int i=-2; i<=2; i++) {
			content.append(String.format("U%d:%%x[%d,1]\n", featureIndex, i));
			featureIndex++;
		}		
		//B: bigram +- 2
		for(int i=-1; i<=0; i++) {
			content.append(String.format("U%d:%%x[%d,1]/%%x[%d,1]\n", featureIndex, i, i+1));
			featureIndex++;
		}	
		//C: pos tag features
		for(int i=-2; i<=2; i++) {
			content.append(String.format("U%d:%%x[%d,2]\n", featureIndex, i));
			featureIndex++;
		}
		//D: pos tag bigram
		for(int i=-2; i<=1; i++) {
			content.append(String.format("U%d:%%x[%d,2]/%%x[%d,2]\n", featureIndex, i, i+1));
			featureIndex++;
		}
		//E: pos tag trigram
		for(int i=-2; i<=0; i++) {
			content.append(String.format("U%d:%%x[%d,2]/%%x[%d,2]/%%x[%d,2]\n", featureIndex, i, i+1, i+2));
			featureIndex++;
		}
		
		int bigramFeatureIndex = 0;
		
		//bigram
		content.append(String.format("B%d\n", bigramFeatureIndex));
		System.out.println(content.toString());
		PrintWriter pw = new PrintWriter(templateFile);
		pw.println(content.toString());
		pw.close();
		
	}
}
