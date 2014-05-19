package features.chunking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import representation.BrownClusterRepresentation;
import data.DataRowBrownCluster;

public class GenerateFeaturesUsingBrownRepresentation {
	//static String filename = "brown-rcv1.clean.tokenized-CoNLL03.txt-c1000-freq1.txt";
	static String filename = "/home/anjan/src/brown-cluster/rcv1.txt.SPL-c1000-p1.out/paths";
	public static void main(String[] args) throws IOException {
		BrownClusterRepresentation.createBrownClusterMap(filename);
		String root = "/home/anjan/work/chunk/";
		String[] files = {"train.rep.txt", "test.rep.txt"};
		
		for(String file : files) {
			String absolutePathFile = root + file;
			PrintWriter outTrain = new PrintWriter(root + "features/"+ file + ".features");
			
			BufferedReader br = new BufferedReader(new FileReader(absolutePathFile));
			String line;
			
			while( (line = br.readLine()) != null) {
				line = line.trim();
				if(! line.isEmpty() ) {
					DataRowBrownCluster dr = new DataRowBrownCluster(line);
					outTrain.println(dr.getRowWithFeatureForChunking());
				} else {
					outTrain.println();
				}
			}
			
			br.close();
			outTrain.close();
		}
		
	}
}
