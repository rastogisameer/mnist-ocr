package com.syntel.ecm.ocr.preprocessing;

import com.syntel.ecm.ocr.utils.Constants;

public class PreProcessor {

	public int[][] trim(int[][] image) {

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
		int[][] image2 = new int[endrow - startrow + 1][endcol - startcol + 1];

		for (int row = 0; row <= endrow - startrow; row++) {

			for (int col = 0; col <= endcol - startcol; col++) {

				image2[row][col] = image[startrow + row][startcol + col];

			}
		}
		return image2;
	}

	/*
	 * RBG color space - red, green, blue 8 bits, 8 bits, 8 bits = 24 bits
	 * (Java: int - 32 bites) 0-255, 0-255, 0-255: Decimal 256 * 256 * 256 =
	 * 16777216 possible colors 0 = Off, 255 = On, 0-255 = partial
	 * 
	 * Grayscale - intensity of light 0-1 0 = black, 255 = white 8 bits
	 */
	public int rgbToGrayScale(int pixel) {

		/*
		 * 0xff = 255 = 8 bits = 11111111 & = 1 if both 1, 0 otherwise
		 */
		int red = (pixel >> 16) & 0xff; // shift red bits to 16 bits left
		int green = (pixel >> 8) & 0xff; // pick only the right 8 bits
		int blue = (pixel) & 0xff;
		/*
		 * Linear Luminance/brightness = weighted average of red, green and blue
		 */
		int Y = ((red * 306) + (green * 601) + (blue * 117)) >> 10;

		if (Y > 128)
			Y = Constants.WHITE;
		else
			Y = Constants.BLACK;
		
		return Y;
	}

	public int[][] toGrayScale(int[][] img) {

		int[][] gimg = new int[img.length][img[0].length];

		for (int row = 0; row < img.length; row++) {

			for (int col = 0; col < img[row].length; col++) {

				gimg[row][col] = rgbToGrayScale(img[row][col]);
			}
		}
		return gimg;
	}

}
