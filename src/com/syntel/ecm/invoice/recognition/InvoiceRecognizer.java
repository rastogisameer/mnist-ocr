package com.syntel.ecm.invoice.recognition;

import java.io.IOException;

import com.syntel.ecm.mnist.input.LabelFileReader;
import com.syntel.ecm.mnist.segmentation.MNISTSegmentFeatureExtractor;
import com.syntel.ecm.ocr.classification.IClassifier;
import com.syntel.ecm.ocr.classification.evaluation.Evaluator;
import com.syntel.ecm.ocr.featureextraction.IFeatureExtractor;
import com.syntel.ecm.ocr.segmentation.SegmentFeatureExtractor;

public class InvoiceRecognizer {

	private String[] testLabels;

	private IFeatureExtractor extractor;
	private IClassifier classifier;

	public InvoiceRecognizer(IFeatureExtractor extractor, IClassifier classifier) {
		this.extractor = extractor;
		this.classifier = classifier;
	}

	public void trainMNIST(String imagesPath, String labelsPath)
			throws IOException {

		MNISTSegmentFeatureExtractor trainingExtractor = new MNISTSegmentFeatureExtractor(
				extractor);
		double[][] trainingFeatures = trainingExtractor.extractFeatures(imagesPath);

		LabelFileReader trainingReader = new LabelFileReader();
		String[] trainingLabels = trainingReader.readLabels(labelsPath);

		classifier.train(trainingFeatures, trainingLabels);
	}

	public void trainInvoice(String imagePath) throws IOException,
			InterruptedException {

		SegmentFeatureExtractor trainingExtractor = new SegmentFeatureExtractor(extractor);
		double[][] trainingFeatures = trainingExtractor.extractFeatures(imagePath, 0, 0, 0, 0);
		
		String[] trainingLabels = new String[] { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9" };
		
		classifier.train(trainingFeatures, trainingLabels);
	}

	public String[] classify(String imagePath, int startx, int starty,
			int endx, int endy) throws IOException, InterruptedException {

		SegmentFeatureExtractor testExtractor = new SegmentFeatureExtractor(extractor);
		
		double[][] testFeatures = testExtractor.extractFeatures(imagePath, startx, starty,
				endx, endy);
		
		testLabels = new String[testFeatures.length];
		
		for (int test = 0; test < testFeatures.length; test++) {
			
			testLabels[test] = classifier.classify(testFeatures[test]);			
		}
		
		return testLabels;		
	}
	public void evaluate(String[] knownLabels){
		
		Evaluator eval = new Evaluator(knownLabels);
		
		System.out.println("Error %: " + eval.errorRate(testLabels));
	}
}
