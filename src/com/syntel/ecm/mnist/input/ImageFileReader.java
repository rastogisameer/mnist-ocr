package com.syntel.ecm.mnist.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.syntel.ecm.ocr.utils.Constants;

public class ImageFileReader {

	private IDXFileReader reader;
	private int currentImage;
	private int magic, imageCnt, rowCnt, colCnt;

	public void open(String filepath) throws IOException {

		reader = new IDXFileReader(filepath);

		magic = reader.readInt();

		if (magic != Constants.IMAGEFILE_MAGIC_NUMBER) {
			System.err.println("Invalid Magic Number: " + magic);
		}
		imageCnt = reader.readInt();
		rowCnt = reader.readInt();
		colCnt = reader.readInt();
	}

	public void close() throws IOException {
		reader.close();
	}

	public int[][] next() throws IOException {

		int[][] image = null;

		if (currentImage < imageCnt) {

			image = new int[colCnt][rowCnt];

			for (int row = 0; row < rowCnt; row++) {

				for (int col = 0; col < colCnt; col++) {

					int pixel = reader.readUnsignedByte();

					image[row][col] = pixel;

				}

			}
			currentImage++;
		}
		return image;
	}
	public int[][][] readAll(String filepath) throws IOException{
		
		open(filepath);
		
		int[][] trainimage = next();
		
		List<int[][]>trainimages = new ArrayList<int[][]>(); 
		
		while(trainimage != null){

			// int[][] prunedimage = proc.prune(trainimage);
			
			// RasterUtils.show(prunedimage);
			
			trainimages.add(trainimage);
			
			trainimage = next();
		}
		close();
		return trainimages.toArray(new int[trainimages.size()][][]);
	}
}
