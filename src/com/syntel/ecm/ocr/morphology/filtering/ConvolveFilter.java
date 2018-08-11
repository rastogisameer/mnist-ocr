package com.syntel.ecm.ocr.morphology.filtering;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;

import com.syntel.ecm.ocr.utils.RasterUtils;

public class ConvolveFilter {

	float[] sharpen = new float[] {
		     0.0f, -1.0f, 0.0f,
		    -1.0f, 5.0f, -1.0f,
		     0.0f, -1.0f, 0.0f
		};
	float[] smoothen = new float[] {
		     1/9f, 1/9f, 1/9f,
		     1/9f, 1/9f, 1/9f,
		     1/9f, 1/9f, 1/9f
		};
	
	float[] dilate = new float[] {
		     0f, 1f, 0f,
		     1f, 1f, 1f,
		     0f, 1f, 0f
		};
	public int[][] sharpen(int [][] arrimg) throws IOException{
		
		return filter(arrimg, sharpen);
	}
	public int[][] smoothen(int [][] arrimg) throws IOException{
		
		return filter(arrimg, sharpen);
	}
	public int[][] dilate(int [][] arrimg) throws IOException{
		return filter(arrimg, dilate);
	}
	public int[][] filter(int [][] arrimg, float[] kernel) throws IOException{
		
		ConvolveOp con = new ConvolveOp(new Kernel(3, 3, kernel), ConvolveOp.EDGE_NO_OP, null);
	
		RasterUtils utils = new RasterUtils();
		BufferedImage srcimg = utils.toBuffer(arrimg);
		
		BufferedImage destimg = con.filter(srcimg, null);
		
		int[][] destarr = utils.toRaster(destimg);
		
		// utils.save(destarr, null, Constants.tmppath);
		
		return utils.toRaster(destimg);
	}
}
