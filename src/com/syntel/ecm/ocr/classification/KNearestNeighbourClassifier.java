package com.syntel.ecm.ocr.classification;

import java.io.IOException;
import java.util.Arrays;

import com.syntel.ecm.ocr.classification.similaritymeasure.SimilarityMeasurer;
import com.syntel.ecm.ocr.utils.RasterUtils;

public class KNearestNeighbourClassifier implements IClassifier {

	private int k;

	private double[][] trainingFeatures;
	private String[] trainingLabels;

	public KNearestNeighbourClassifier(int k) {
		this.k = k;
		
	}

	public void train(Object otrainingFeatures, String[] newLabels) {

		double[][] newfeatures = (double[][]) otrainingFeatures;
		
		RasterUtils utils = new RasterUtils();
		
		this.trainingFeatures = utils.mergeFeatures(this.trainingFeatures, newfeatures);
		
		this.trainingLabels = utils.mergeLabels(trainingLabels, newLabels);
	}

	public String classify(double[] testFeatures) throws IOException {

		SimilarityMeasurer measurer = new SimilarityMeasurer();

		double[] distances = measurer.euclideanDistances(trainingFeatures,
				testFeatures);

		Neighbour[] neighbours = new Neighbour[distances.length];

		for (int train = 0; train < distances.length; train++) {

			Neighbour node = new Neighbour(trainingLabels[train],
					distances[train]);

			neighbours[train] = node;
		}
		Arrays.sort(neighbours);

		Neighbour[] kneighbours = Arrays.copyOfRange(neighbours, 0, k);

		for (int i = 0; i < k; i++) {

			for (int j = 0; j < k; j++) {

				if (kneighbours[i].equals(kneighbours[j])) {
					kneighbours[i].setVote(kneighbours[i].getVote() + 1);
				}
			}
		}
		Arrays.sort(kneighbours);

		return kneighbours[0].getLabel();
	}

}
