package com.syntel.ecm.ocr.featureextraction;

public class HuMomentsExtractor implements IFeatureExtractor {

	/*
	 * 2-dimension cartesian moment. Mpq of order p + q
	 * P = order of x. Q = order of y
	 * Raw/General/Spatial moments RMpq = sum(sum(x^p * y^q * f(x,y)). 
	 * p, q = order of moment
	 * 
	 */	
	public double rawMoment(int[][] image, int p, int q){
		
		double moment = 0;
		
		for (int y = 0; y < image.length; y++) {

			for (int x = 0; x < image[y].length; x++) {
		
				int pix = image[y][x];
				
				moment += Math.pow(y, p) * Math.pow(x, q) * pix;
				
			}
		}
		return moment;
	}
	/*
	 * Central moments
	 */	
	public double centralMoment(int[][] image, int p, int q){
		
		double moment = 0;	
		/*
		 * 0th order - Mass/Area of image
		 */
		double rm00 = rawMoment(image, 0, 0);

		/*
		 * 1st order - Center of mass
		 */
		double rm10 = rawMoment(image, 1, 0);
		double rm01 = rawMoment(image, 0, 1);
		/*
		 * Coordinates of center of gravity of the image. Position of image
		 */
		double xMean = rm10 / rm00;
		double yMean = rm01 / rm00;
		
		for (int y = 0; y < image.length; y++) {

			for (int x = 0; x < image[y].length; x++) {
		
				int pix = image[y][x];
				
				moment += Math.pow(y - yMean, p) * Math.pow(x - xMean, q) * pix;
				
			}
		}
		return moment;
	}
	/*
	 * Normalization factor f = (p + q) / 2 + 1
	 */
	public double factor(int p, int q){
		
		return (p + q) / 2 + 1;
		
	}
	/*
	 * Normalized moments - normalized for the effects of change of scale
	 */
	public double normalizedMoment(int[][] image, int p, int q){
		
		double cm = centralMoment(image, p, q);
		double cm00 = centralMoment(image, 0, 0);

		double f = factor(p, q);
		
		double nm = cm / Math.pow(cm00, f);
		
		return nm;
	}
	/*
	 * Feature - specific structures in the image such as points, edges or
	 * objects. Moment - specific quantitative measure of the shape. Invariant -
	 * moments that are insensitive to deformations and used to distinguish
	 * objects belonging to different classes.
	 * Moment invariants
	 */
	public double[] featureVectorExtraction(int[][] image) {

		double moments[] = new double[7];

		/*
		 * 2nd order - Distribution of mass with respect to coordinate axis.
		 * Moments of inertia - Measure orientation, direction
		 */
		double nm20 = normalizedMoment(image, 2, 0);
		double nm02 = normalizedMoment(image, 0, 2);

		double nm11 = normalizedMoment(image, 1, 1);

		/*
		 * 3rd order - Projection skewness
		 */
		double nm30 = normalizedMoment(image, 3, 0);
		double nm03 = normalizedMoment(image, 0, 3);

		double nm21 = normalizedMoment(image, 2, 1);
		double nm12 = normalizedMoment(image, 1, 2);

		/*
		 * Hu's 7 Moment invariants - invariant under translation, changes in scale, and rotation
		 * Non-linear combination.
		 */
		moments[0] = nm20 + nm02; // Moment of inertia
		moments[1] = Math.pow(nm20 - nm02, 2) + 4 * Math.pow(nm11, 2);
		moments[2] = Math.pow(nm30 - 3 * nm12, 2)
				+ Math.pow(nm03 - 3 * nm21, 2);
		moments[3] = Math.pow(nm30 + nm12, 2) + Math.pow(nm03 + nm21, 2);
		moments[4] = (nm30 - 3 * nm12) * (nm30 + nm12)
				* (Math.pow(nm30 + nm12, 2) - 3 * Math.pow(nm21 + nm03, 2))
				+ (nm03 - 3 * nm21) * (nm21 + nm03)
				* (Math.pow(nm21 + nm03, 2) - 3 * (Math.pow(nm30 + nm12, 2)));
		moments[5] = (nm20 - nm02)
				* (Math.pow(nm30 + nm12, 2) - Math.pow(nm21 + nm03, 2)) + 4
				* nm11 * (nm30 + nm12) * (nm21 + nm03);
		// Skew moment - distinguish mirror images
		moments[6] = (3 * nm21 - nm03) * (nm12 + nm30)
				* (Math.pow(nm12 + nm30, 2) - 3 * Math.pow(nm03 + nm21, 2))
				- (3 * nm12 - nm30) * (nm21 + nm03)
				* (Math.pow(nm21 + nm03, 2) - 3 * Math.pow(nm30 + nm12, 2));

		return moments;
	}

}
