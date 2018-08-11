package com.syntel.ecm.mnist.test;

import com.syntel.ecm.mnist.recognition.MNISTRecognizer;
import com.syntel.ecm.ocr.classification.IClassifier;
import com.syntel.ecm.ocr.classification.KNearestNeighbourClassifier;
import com.syntel.ecm.ocr.classification.MinimumDistanceClassifier;
import com.syntel.ecm.ocr.featureextraction.IFeatureExtractor;
import com.syntel.ecm.ocr.featureextraction.ZoningDensityExtractor;

public class TestMNIST {

	private final static String trainingImagesPath = "D:\\Sameer\\workspace\\mnist\\training\\train-images.idx3-ubyte";
	private final static String trainingLabelsPath = "D:\\Sameer\\workspace\\mnist\\training\\train-labels.idx1-ubyte";
	
	private final static String testImagesPath = "D:\\Sameer\\workspace\\mnist\\test\\t10k-images.idx3-ubyte";
	private final static String testLabelsPath = "D:\\Sameer\\workspace\\mnist\\test\\t10k-labels.idx1-ubyte";

	public static void main(String[] args) {

		try {
			// IFeatureExtractor extractor = new HuMomentsExtractor();			
			

			// TemplateMatchingClassifier classifier = new TemplateMatchingClassifier();
			
			IFeatureExtractor extractor = new ZoningDensityExtractor(5);
			// IClassifier classifier = new MinimumDistanceClassifier();
			
			IClassifier classifier = new KNearestNeighbourClassifier(10);
			
			MNISTRecognizer recog = new MNISTRecognizer(extractor, classifier);
			
			recog.train(trainingImagesPath, trainingLabelsPath);
			
			recog.classify(testImagesPath);
		
			recog.evaluate(testLabelsPath);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
