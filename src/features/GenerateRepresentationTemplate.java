package features;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GenerateRepresentationTemplate {
	/*
	0 word
	1 smoothedWord
	2 allcapSmoothedWord
	3 isInitCap
	4 isAllCap
	5 prefix3
	6 prefix4
	7 suffix1
	8 suffix2
	9 suffix3
	10 suffix4
	for(String rep : representation) {
		sb.append(rep);
	}
	label;
	*/
	
	public static void main(String[] args) throws FileNotFoundException {
		int REP_LENGTH = 1; // 0 means baseline
		String templateFile = "/home/anjan/work/ner/train_test_dev/withoutchunk/features/representation.template";
		StringBuffer content = new StringBuffer();
		int featureIndex = 0;
		//A: allcapWord in window of +- 2
		for(int i=-2; i<=2; i++) {
			content.append(String.format("U%d:%%x[%d,2]\n", featureIndex, i));
			featureIndex++;
		}		
		//B: token themselves in window of +- 2
		//use smoothed token
		for(int i=-2; i<=2; i++) {
			content.append(String.format("U%d:%%x[%d,1]\n", featureIndex, i));
			featureIndex++;
		}		
		//bigrams
		for(int i=-1; i<=0; i++) {
			content.append(String.format("U%d:%%x[%d,1]/%%x[%d,1]\n", featureIndex, i, i+1));
			featureIndex++;
		}		
		//D: initial capitalization of tokens in a window of +- 2
		for(int i=-2; i<=2; i++) {
			content.append(String.format("U%d:%%x[%d,3]\n", featureIndex, i));
			featureIndex++;
		}		
		//E: more elaborate word type info
		//all caps
		for(int i=-1; i<=1; i++) {
			content.append(String.format("U%d:%%x[%d,4]\n", featureIndex, i));
			featureIndex++;
		}
		
		//F: token prefix and suffix
		for(int j=5; j<11; j++) {
			for(int i=-1; i<=1; i++) {
				content.append(String.format("U%d:%%x[%d,%d]\n", featureIndex, i, j));
				featureIndex++;
			}
		}		
		
		/*** representation features starts ***/
		//Add representations
		for(int d=0; d<REP_LENGTH; d++) { //rep dimension
			for(int i=-2; i<=2; i++) {
				content.append(String.format("U%d:%%x[%d,%d]\n", featureIndex, i, (11+d)));
				featureIndex++;
			}
		}
		/*** representaiton features complete***/
		
		
		//C: previous two tags and current token combined with previous tag
				int bigramFeatureIndex = 0;
				content.append(String.format("B%d:%%x[0,1]\n", bigramFeatureIndex));
				bigramFeatureIndex++;
				
		//bigram
		content.append(String.format("B%d\n", bigramFeatureIndex));
		System.out.println(content.toString());
		PrintWriter pw = new PrintWriter(templateFile);
		pw.println(content.toString());
		pw.close();
	}
}
