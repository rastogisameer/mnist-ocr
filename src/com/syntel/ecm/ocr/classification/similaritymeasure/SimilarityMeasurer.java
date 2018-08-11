package com.syntel.ecm.ocr.classification.similaritymeasure;

import com.syntel.ecm.ocr.utils.Constants;

public class SimilarityMeasurer {

	/*
	 * Euclidean distance is the distance between 2 points in Cartesian space
	 * using Pythagoras theorum d(p,q) = SQRT({q1 - p1)^2 + (q2 - p2)^2 + ... +
	 * (qn - pn)^2) Distance between Continuous variables
	 */
	public double euclideanDistance(double[] trainclass, double[] querypoint) {

		double sum = 0;

		for (int i = 0; i < querypoint.length; i++) {

			sum += Math.pow(querypoint[i] - trainclass[i], 2);
		}
		double distance = Math.sqrt(sum);

		return distance;
	}

	public double[] euclideanDistances(double[][] classFeatures, double[] querypoint) {

		double[] distances = new double[classFeatures.length];

		for (int i = 0; i < classFeatures.length; i++) {

			double[] trainMoments = classFeatures[i];

			distances[i] = euclideanDistance(trainMoments, querypoint);
		}
		return distances;
	}

	public double rootMeanSquareError(int[][] trainimage, int[][] testimage) {

		double sumError = 0;

		for (int row = 0; row < testimage.length; row++) {

			for (int col = 0; col < testimage[row].length; col++) {

				double residual = Double.MAX_VALUE;

				if (trainimage.length < row + 1 || trainimage[row].length < col + 1)
					residual = testimage[row][col] - Constants.WHITE;
				else
					residual = testimage[row][col] - trainimage[row][col];

				sumError += Math.pow(residual, 2);

			}
		}
		double numPixels = testimage.length * testimage[0].length;
		double meanError = sumError / numPixels;

		return Math.sqrt(meanError);
	}
}
