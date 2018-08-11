package com.syntel.ecm.mnist.segmentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.syntel.ecm.mnist.input.ImageFileReader;
import com.syntel.ecm.ocr.featureextraction.IFeatureExtractor;
import com.syntel.ecm.ocr.morphology.filtering.ConvolveFilter;
import com.syntel.ecm.ocr.utils.Constants;
import com.syntel.ecm.ocr.utils.RasterUtils;

public class MNISTSegmentFeatureExtractor {
	
	private IFeatureExtractor extractor;
	
	public MNISTSegmentFeatureExtractor(IFeatureExtractor extractor){ 
		this.extractor = extractor;
	}

	public double[][] extractFeatures(String filepath) throws IOException {

		System.out.println("MNISTSegmentFeatureExtractor: extractFeatures");

		ImageFileReader reader = new ImageFileReader();
		reader.open(filepath);

		List<double[]> moments = new ArrayList<double[]>();
		
		int[][] image = reader.next();
		
		RasterUtils utils = new RasterUtils();
		
		ConvolveFilter filter = new ConvolveFilter();
		
		while (image != null) {

			// image = filter.smoothen(image);
			// image = filter.dilate(image);
			// utils.save(image, null, Constants.tmppath);
			double[] features = extractor.featureVectorExtraction(image);

			moments.add(features);

			image = reader.next();
		}
		reader.close();

		return moments
				.toArray(new double[moments.size()][]);
	}
}
