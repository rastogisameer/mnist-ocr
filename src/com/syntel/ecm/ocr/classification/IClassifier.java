package com.syntel.ecm.ocr.classification;

import java.io.IOException;

public interface IClassifier {
	
	public String classify(double[] testFeatures) throws IOException;
		
	public void train(Object trainingFeatures, String[] trainingLabels);
}
