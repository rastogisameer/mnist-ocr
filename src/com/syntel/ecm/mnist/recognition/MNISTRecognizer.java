package com.syntel.ecm.mnist.recognition;

import java.io.IOException;

import com.syntel.ecm.mnist.input.LabelFileReader;
import com.syntel.ecm.ocr.classification.IClassifier;
import com.syntel.ecm.ocr.classification.evaluation.Evaluator;
import com.syntel.ecm.ocr.featureextraction.IFeatureExtractor;
import com.syntel.ecm.mnist.segmentation.MNISTSegmentFeatureExtractor;

public class MNISTRecognizer {
	
	private String[] testLabels;
	
	private IFeatureExtractor extractor;
	private IClassifier classifier;
	
	public MNISTRecognizer(IFeatureExtractor extractor, IClassifier classifier){
		this.extractor = extractor;
		this.classifier = classifier;
	}
	public String[] classify(String testImagesPath) throws IOException{
	
		MNISTSegmentFeatureExtractor testExtractor = new MNISTSegmentFeatureExtractor(extractor);
		double[][] testFeatures = testExtractor.extractFeatures(testImagesPath);
		
		testLabels = new String[testFeatures.length];
		
		for (int test = 0; test < testFeatures.length; test++) {
			
			testLabels[test] = classifier.classify(testFeatures[test]);			
		}
		
		return testLabels;
	}
	
	public void train(String imagesPath, String labelsPath) throws IOException {
		
		MNISTSegmentFeatureExtractor trainingExtractor = new MNISTSegmentFeatureExtractor(extractor);
		double[][] trainingFeatures = trainingExtractor.extractFeatures(imagesPath);

		LabelFileReader trainingReader = new LabelFileReader();
		String[] trainingLabels = trainingReader.readLabels(labelsPath);
		
		classifier.train(trainingFeatures, trainingLabels);
	}
	public void evaluate(String knownLabelsPath){
		
		LabelFileReader testReader = new LabelFileReader();
		String[] knownLabels = testReader.readLabels(knownLabelsPath);
		
		Evaluator eval = new Evaluator(knownLabels);
		
		System.out.println("Error %: " + eval.errorRate(testLabels));
	}
}
