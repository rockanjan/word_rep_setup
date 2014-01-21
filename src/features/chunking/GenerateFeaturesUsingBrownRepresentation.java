package features.chunking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import representation.BrownClusterRepresentation;
import data.DataRowBrownCluster;

public class GenerateFeaturesUsingBrownRepresentation {
	static String filename = "brown-rcv1.clean.tokenized-CoNLL03.txt-c1000-freq1.txt";
	public static void main(String[] args) throws IOException {
		BrownClusterRepresentation.createBrownClusterMap(filename);
		String root = "/home/anjan/work/ner/train_test_dev/withoutchunk/";
		String[] files = {"train_rep.txt", "testa_rep.txt", "testb_rep.txt", "muc7_rep.txt"};
		
		for(String file : files) {
			String absolutePathFile = root + file;
			PrintWriter outTrain = new PrintWriter(root + "features/"+ file + ".features");
			
			BufferedReader br = new BufferedReader(new FileReader(absolutePathFile));
			String line;
			
			while( (line = br.readLine()) != null) {
				line = line.trim();
				if(! line.isEmpty() ) {
					DataRowBrownCluster dr = new DataRowBrownCluster(line);
					outTrain.println(dr.getRowWithFeatureForNer());
				} else {
					outTrain.println();
				}
			}
			
			br.close();
			outTrain.close();
		}
		
	}
}
