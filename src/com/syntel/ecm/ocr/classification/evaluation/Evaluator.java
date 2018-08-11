package com.syntel.ecm.ocr.classification.evaluation;

public class Evaluator {
	
	private String[] knownLabels;
	
	public Evaluator(String[] knownLabels){
		this.knownLabels = knownLabels;
	}
	
	public int errorRate(String[] testLabels){
		
		int errs = 0;
		for(int i = 0; i < knownLabels.length; i++){
			
			if(!testLabels[i].equalsIgnoreCase(knownLabels[i]))
				errs++;
		}		
		return errs * 100 / knownLabels.length;
	}
	
}
