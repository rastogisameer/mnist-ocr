package com.syntel.ecm.invoice.test;

import com.syntel.ecm.invoice.recognition.InvoiceRecognizer;
import com.syntel.ecm.ocr.classification.IClassifier;
import com.syntel.ecm.ocr.classification.KNearestNeighbourClassifier;
import com.syntel.ecm.ocr.classification.MinimumDistanceClassifier;
import com.syntel.ecm.ocr.featureextraction.HuMomentsExtractor;
import com.syntel.ecm.ocr.featureextraction.IFeatureExtractor;
import com.syntel.ecm.ocr.featureextraction.ZoningDensityExtractor;

public class TestInvoice {

	private final static String trainingImagesPath = "D:\\Sameer\\workspace\\mnist\\training\\train-images.idx3-ubyte";
	private final static String trainingLabelsPath = "D:\\Sameer\\workspace\\mnist\\training\\train-labels.idx1-ubyte";
	
	// private final static String testImagesPath = "D:\\Sameer\\workspace\\mnist\\test\\CI1.tiff";
	private final static String testImagesPath = "D:\\Sameer\\workspace\\mnist\\test\\CI3.tiff";
	
	private final static String[] knownLabels = new String[]{"0", "0", "0", "0", "0", "4", "0", "4"};
	
	// private final static String trainingImagesPath2 = "D:\\Sameer\\workspace\\mnist\\training\\train1.png";
	private final static String trainingImagesPath2 = "D:\\Sameer\\workspace\\mnist\\training\\train2.png";
	
	// private static int x1 = 827, x2 = 990, y1 = 225, y2 = 260;
	private static int x1 = 1042, y1 = 631, x2 = 1181, y2 = 661;
	
	public static void main(String[] args) {

		try {
			// IFeatureExtractor extractor = new HuMomentsExtractor();			

			IFeatureExtractor extractor = new ZoningDensityExtractor(5);
			IClassifier classifier = new MinimumDistanceClassifier();
			
			// IClassifier classifier = new KNearestNeighbourClassifier(10);
			
			InvoiceRecognizer recog = new InvoiceRecognizer(extractor, classifier);
			
			recog.trainMNIST(trainingImagesPath, trainingLabelsPath);
			recog.trainInvoice(trainingImagesPath2);
			
			String[] testLabels = recog.classify(testImagesPath, x1, y1, x2, y2);
			
			for(String l: testLabels){
				System.out.println(l);
			}
			recog.evaluate(knownLabels);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
