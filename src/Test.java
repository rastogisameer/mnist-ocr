import java.util.Arrays;

import com.syntel.ecm.mnist.input.LabelFileReader;
import com.syntel.ecm.ocr.classification.Neighbour;


public class Test {
	private final static String testLabelsPath = "D:\\Sameer\\workspace\\mnist\\test\\t10k-labels.idx1-ubyte";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LabelFileReader trainingReader = new LabelFileReader();
		String[] trainingLabels = trainingReader.readLabels(testLabelsPath);
		for(String s: trainingLabels)
			System.out.println(s);
		
		
//		Neighbour[] ns = new Neighbour[3];
//		ns[0] = new Neighbour("A", 1.0);
//		ns[1] = new Neighbour("A", 0.9);
//		ns[2] = new Neighbour("A", 2.9);
//		
//		Arrays.sort(ns);
//		
//		for(Neighbour n: ns){
//			System.out.println(n.getDistance());
//		}
	}

}
