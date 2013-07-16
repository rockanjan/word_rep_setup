package features;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GenerateBaselineTemplate {
	/*
	word
	smoothedWord
	allcapSmoothedWord
	isInitCap
	isAllCap
	prefix3
	prefix4
	suffix1
	suffix2
	suffix3
	suffix4
	for(String rep : representation) {
		sb.append(rep);
	}
	label;
	*/
	
	public static void main(String[] args) throws FileNotFoundException {
		String templateFile = "/home/anjan/work/ner/train_test_dev/withoutchunk/features/baseline.template";
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
		
		/*
		//my: initial capitalization bigrams
		for(int i=-1; i<=0; i++) {
			content.append(String.format("U%d:%%x[%d,3]/%%x[%d,3]\n", featureIndex, i, i+1));
			featureIndex++;
		}
		*/
		
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
