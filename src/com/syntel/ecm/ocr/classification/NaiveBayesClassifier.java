package com.syntel.ecm.ocr.classification;

import java.util.Map;

/* Probabilistic classifier. Conditional probability.
 * 
 * Since the pixel values and hence, successive features are dependent on the character label, 
 * the Naive Bayes Assumption (features being conditionally independent of output labels) may not hold.
 * All the Hu moments contribute independently to the probability that prediction success.
 * 
 * p(C|F1,F2,...Fn) = p(C) * p(F1,F2,...Fn|C) / p(F1,F2...Fn)
 * posterior = prior * likelihood / evidence.
 * evidence = p(F1,F2...Fn) = constant = Z = scaling factor
 * class prior = p(C)
 * feature probability distributions = p(Fi|C)
 * Naive = Fi is conditionally independent of Fj
 * p(C,F1,...Fn) ~ p(C) * p(F1|C) * p(F2|C)... = 1/Z * p(C) * PROD(p(Fi|C))  
 */
public class NaiveBayesClassifier implements Classifier{

	@Override
	public Character classify(Map<Character, RasterImage> trainingMap,
			RasterImage glyph) {

		int evidence = 1; // ignored because it is a constant
		
		double maxPosterior = Double.MIN_VALUE;
		double posterior = 0;
		Character selected = 'Z';		
		double[] momentInvariants = null;
		int inv = 0;
		
		for (Character ch : trainingMap.keySet()) {
		
		
			
			posterior = probability(trainingMap, ch) * probabilityDensity(trainingMap, ch, momentInvariants[inv]) * probabilityDensity(trainingMap, ch, momentInvariants[inv]) / evidence;
			
			if (posterior > maxPosterior) {
				maxPosterior = posterior;
				selected = ch;
			}
		}
		
		return null;
	}
	public double probability(Map<Character, RasterImage> trainingMap, Character ch){
		return 1 / trainingMap.size();
	}
	/*
	 * Normal/Gaussian probability distribution/bell shaped curve - probability that observation would fall between any 2 real numbers.
	 * mu = mean = where peak of density occurs. 
	 * sig = standard deviation = spread of bell curve. 
	 * 
	 */
	public double probabilityDensity(Map<Character, RasterImage> trainingMap, Character ch, double invariant){
		
		return 1; //1 / Math.sqrt(2 * Math.PI * Math.pow(sig, sig));
		
	}
}
