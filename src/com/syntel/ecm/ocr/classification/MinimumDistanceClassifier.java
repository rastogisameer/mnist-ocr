package com.syntel.ecm.ocr.classification;

import java.io.IOException;

import com.syntel.ecm.ocr.classification.similaritymeasure.SimilarityMeasurer;
import com.syntel.ecm.ocr.utils.RasterUtils;

public class MinimumDistanceClassifier implements IClassifier {

	private double[][] trainingFeatures;
	private String[] trainingLabels;

	@Override
	public String classify(double[] testFeatures) throws IOException {

		System.out.println("MinimumDistanceClassifier: classify");		
		
		SimilarityMeasurer measurer = new SimilarityMeasurer();

		double minDist = Double.MAX_VALUE;
		double distance = 0;
		int trainSelected = 0;

		for (int train = 0; train < trainingFeatures.length; train++) {

			distance = measurer.euclideanDistance(trainingFeatures[train],
					testFeatures);

			if (distance < minDist) {
				minDist = distance;
				trainSelected = train;
			}
		}
		return trainingLabels[trainSelected];

	}

	public void train(Object otrainingFeatures, String[] newLabels) {

		double[][] newfeatures = (double[][]) otrainingFeatures;
		
		RasterUtils utils = new RasterUtils();
		
		this.trainingFeatures = utils.mergeFeatures(this.trainingFeatures, newfeatures);
		
		this.trainingLabels = utils.mergeLabels(trainingLabels, newLabels);
	}

}
