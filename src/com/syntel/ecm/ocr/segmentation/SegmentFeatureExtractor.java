package com.syntel.ecm.ocr.segmentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.syntel.ecm.ocr.featureextraction.IFeatureExtractor;
import com.syntel.ecm.ocr.input.ImageReader;
import com.syntel.ecm.ocr.morphology.filtering.ConvolveFilter;
import com.syntel.ecm.ocr.preprocessing.PreProcessor;
import com.syntel.ecm.ocr.utils.Constants;
import com.syntel.ecm.ocr.utils.RasterUtils;

public class SegmentFeatureExtractor {
	
	private IFeatureExtractor extractor;
	
	public SegmentFeatureExtractor(IFeatureExtractor extractor){ 
		this.extractor = extractor;
	}

	public double[][] extractFeatures(String filepath, int startx, int starty,
			int endx, int endy) throws IOException, InterruptedException {

		System.out.println("SegmentFeatureExtractor: extractFeatures");
		
		ImageReader reader = new ImageReader();

		RasterUtils utils = new RasterUtils();

		int[][] img = reader.read(filepath);

		PreProcessor proc = new PreProcessor();

		img = proc.toGrayScale(img);

		img = utils.subset(img, startx, starty, endx, endy);

		Segmenter segmenter = new Segmenter();
		ConvolveFilter filter = new ConvolveFilter();
		
		List<double[]> moments = new ArrayList<double[]>();

		int[][][] lines = segmenter.lineSegmentation(img);

		for (int l = 0; l < lines.length; l++) {

			utils.save(lines[l], null, Constants.tmppath);

			int[][][] chars = segmenter.charSegmentation(lines[l]);

			for (int c = 0; c < chars.length; c++) {

				chars[c] = proc.trim(chars[c]);
				chars[c] = filter.smoothen(chars[c]);
				chars[c] = filter.dilate(chars[c]);
				
				utils.save(chars[c], null, Constants.tmppath);

				double[] features = extractor.featureVectorExtraction(chars[c]);

				moments.add(features);
			}			
		}
		return moments
		.toArray(new double[moments.size()][]);
	}

}
