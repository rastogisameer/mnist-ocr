package com.syntel.ecm.ocr.featureextraction;

import com.syntel.ecm.ocr.utils.Constants;

public class ZoningDensityExtractor implements IFeatureExtractor{

	private int z;
	public ZoningDensityExtractor(int z){
		this.z = z;
	}
	@Override
	public double[] featureVectorExtraction(int[][] image) {
		
		
		// TODO Auto-generated method stub
				
		// Divide image into z columns
		int h = image.length % z == 0 ? image.length / z : image.length / z + 1;
		// and z rows
		double features[] = new double[z * z]; // number of cells = z*z
		int i = 0;
		
		for(int y = 0; y < image.length - 1; y = y + h){
		
			if(y + h > image.length - 1)
				h = image.length - 1 - y;
			// Divide image into z rows
			int w = image[y].length % z == 0 ? image[y].length / z : image[y].length / z + 1;
			
			for(int x = 0; x < image[0].length - 1; x = x + w){
				
				if(x + w > image[0].length - 1)
					w = image[0].length - 1 - x;	
				
				features[i++] = density(image, x, y, w, h);			
			}
		}
		
		return features;
	}
	public double density(int [][] image, int x, int y, int w, int h){
		
		int sum = 0;
		
		for(int row = y; row <= y + h; row++){
			
			for(int col = x; col <= x + w; col++){
				
				if(image[row][col] > Constants.GRAY_THRESHOLD){
					sum++;
				}
				
			}
		}	
		return (double)sum / (w * h);
	}
}
