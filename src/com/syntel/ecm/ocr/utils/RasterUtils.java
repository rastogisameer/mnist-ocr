package com.syntel.ecm.ocr.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RasterUtils {
	private static int name = 1;

	public void save(int[][] img, String myname, String tmpdir)
			throws IOException {

		BufferedImage bimage = toBuffer(img);
		if (myname == null)
			myname = "" + name++;

		ImageIO.write(bimage, "tif", new File(tmpdir + "\\" + myname + ".tif"));

	}
	public BufferedImage toBuffer(int[][] img){
	
		BufferedImage bimage = new BufferedImage(img[0].length, img.length,
				BufferedImage.TYPE_BYTE_GRAY);

		int cnt = 0;

		for (int row = 0; row < img.length; row++) {

			for (int col = 0; col < img[row].length; col++) {

				bimage.setRGB(col, row, img[row][col]);

				cnt++;
			}
		}
		return bimage;
	}
	public int[][] toRaster(BufferedImage bimg){
		
		int [][] img = new int[bimg.getHeight()][bimg.getWidth()];
		
		for (int row = 0; row < bimg.getHeight(); row++) {

			for (int col = 0; col < bimg.getWidth(); col++) {

				img[row][col] = bimg.getRGB(col, row);
			}
		}
		return img;	
		
		
	}
	public void show(int[][] img) {

		for (int row = 0; row < img.length; row++) {

			for (int col = 0; col < img[row].length; col++) {

				if (img[row][col] > Constants.GRAY_THRESHOLD)
					System.out.print("*");
				else
					System.out.print(" ");
			}
			System.out.println("");
		}
	}

	/*
	 * x1, y1, x2, y2 are all inclusive
	 */
	public int[][] subset(int[][] img, int x1, int y1, int x2, int y2) {

		if (x1 < 0)
			x1 = 0;
		if (y1 < 0)
			y1 = 0;
		if (x2 <= 0)
			x2 = img[0].length - 1;
		if (y2 <= 0)
			y2 = img.length - 1;

		int w = x2 - x1 + 1;
		int h = y2 - y1 + 1;

		int[][] subimg = new int[h][w];

		for (int row = y1; row <= y2; row++) {
			for (int col = x1; col <= x2; col++) {

				subimg[row - y1][col - x1] = img[row][col];

				// System.arraycopy(img[row], col, subimg[row - y1], col - x1,
				// w);
			}
		}
		return subimg;
	}

	public void showBoundaries(int[][] image) {

		int startrow = image.length - 1;
		int endrow = 0;

		int startcol = image[0].length - 1;
		int endcol = 0;

		for (int row = 0; row < image.length; row++) {

			for (int col = 0; col < image[row].length; col++) {

				if (image[row][col] > Constants.GRAY_THRESHOLD) {

					startrow = Math.min(row, startrow);
					endrow = Math.max(row, endrow);

					startcol = Math.min(col, startcol);
					endcol = Math.max(col, endcol);

				}
			}

		}
		System.out.println("x1=" + startcol + ", y1=" + startrow + ", x2="
				+ endcol + ", y2=" + endrow + ", width="
				+ (endcol - startcol + 1) + ", height="
				+ (endrow - startrow + 1));
	}
	public double[][] mergeFeatures(double[][] oldfeatures, double[][] newfeatures){
		
		int oldheight =  oldfeatures != null? oldfeatures.length : 0;
		int oldwidth =  oldfeatures != null? oldfeatures[0].length : 0;
		
		double[][] combinedfeatures = new double[oldheight + newfeatures.length][newfeatures[0].length];
		
		if(oldfeatures != null)
			System.arraycopy(oldfeatures, 0, combinedfeatures, 0, oldheight);
		
		System.arraycopy(newfeatures, 0, combinedfeatures, oldheight, newfeatures.length);
		
		return combinedfeatures;
	}
	public String[] mergeLabels(String[] oldLabels, String[] newLabels){
		
		int oldlength = oldLabels != null? oldLabels.length : 0;
		
		String[] combinedLabels = new String[oldlength + newLabels.length];
		
		if(oldLabels != null)
			System.arraycopy(oldLabels, 0, combinedLabels, 0, oldlength);
		
		System.arraycopy(newLabels, 0, combinedLabels, oldlength, newLabels.length);
		
		return combinedLabels;
	}
}
