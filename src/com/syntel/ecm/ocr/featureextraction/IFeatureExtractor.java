package com.syntel.ecm.ocr.featureextraction;

public interface IFeatureExtractor {
	public double[] featureVectorExtraction(int[][] image);
}
