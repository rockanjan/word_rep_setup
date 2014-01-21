package features.chunking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import data.DataRow;

public class GenerateFeaturesForChunking {
	public static void main(String[] args) throws IOException {
		String root = "/home/anjan/work/chunk/";
		String[] files = {"train.txt", "test.txt"};
		
		for(String file : files) {
			String absolutePathFile = root + file;
			PrintWriter outTrain = new PrintWriter(root + "features/"+ file + ".features");
			
			BufferedReader br = new BufferedReader(new FileReader(absolutePathFile));
			String line;
			
			while( (line = br.readLine()) != null) {
				line = line.trim();
				if(! line.isEmpty() ) {
					DataRow dr = new DataRow(line);
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
