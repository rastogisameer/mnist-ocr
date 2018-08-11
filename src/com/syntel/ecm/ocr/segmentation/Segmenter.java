package com.syntel.ecm.ocr.segmentation;

import java.util.ArrayList;
import java.util.List;

import com.syntel.ecm.ocr.utils.Constants;

import com.syntel.ecm.ocr.utils.RasterUtils;

public class Segmenter {

	public int[][][] lineSegmentation(int[][] img) {

		RasterUtils utils = new RasterUtils();

		int startrow = 0;

		List<int[][]> lines = new ArrayList<int[][]>();

		boolean isPrevWhiteSpace = true;

		for (int row = 0; row < img.length; row++) {

			boolean isWhiteSpace = true;

			for (int col = 0; col < img[row].length; col++) {

				if (img[row][col] > Constants.GRAY_THRESHOLD) {

					isWhiteSpace = isWhiteSpace && false;

				}
			}
			if (isPrevWhiteSpace != isWhiteSpace) {

				if (isWhiteSpace) {
					int[][] line = utils.subset(img, 0, startrow,
							img[startrow].length - 1, row - 1);
					lines.add(line);

				} else {
					startrow = row;

				}
				isPrevWhiteSpace = isWhiteSpace;
			}
			if (row == img.length - 1) {
				if (!isWhiteSpace) {
					int[][] line = utils.subset(img, 0, startrow,
							img[startrow].length - 1, row);
					lines.add(line);
				}
			}
		}

		return lines.toArray(new int[lines.size()][][]);
	}

	public int[][][] charSegmentation(int[][] img) {

		RasterUtils utils = new RasterUtils();
		int startcol = 0;

		List<int[][]> chars = new ArrayList<int[][]>();

		boolean isPrevWhiteSpace = true;

		for (int col = 0; col < img[0].length; col++) {

			boolean isWhiteSpace = true;

			for (int row = 0; row < img.length; row++) {

				if (img[row][col] > Constants.GRAY_THRESHOLD) {

					isWhiteSpace = isWhiteSpace && false;
				}
			}
			if (isPrevWhiteSpace != isWhiteSpace) {

				if (isWhiteSpace) { // end of character
					int[][] chr = utils.subset(img, startcol, 0, col - 1,
							img.length - 1);
					chars.add(chr);

				} else { // begining of character
					startcol = col;

				}
				isPrevWhiteSpace = isWhiteSpace;
			}
			if (col == img[0].length - 1) {
				if (!isWhiteSpace) {

					int[][] chr = utils.subset(img, startcol, 0,
							col, img.length - 1);
					chars.add(chr);
				}

			}
		}
		return chars.toArray(new int[chars.size()][][]);
	}
}
