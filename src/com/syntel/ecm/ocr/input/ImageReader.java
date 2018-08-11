package com.syntel.ecm.ocr.input;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;

import org.geotoolkit.image.io.plugin.RawTiffImageReader;


public class ImageReader {
	
	public int[][] read(String imagefile) throws InterruptedException, IOException{
		
		return readPixels(readImage(imagefile));
	}

	public Image readImage(String imagefile) throws IOException {

		IIORegistry registry = IIORegistry.getDefaultInstance();
		registry.registerServiceProvider(new RawTiffImageReader.Spi()); // PixelGrabber does not support TIFF

		Image image = ImageIO.read(new File(imagefile));

		return image;
	}
	public int[][] readPixels(Image image) throws InterruptedException {

		int width = image.getWidth(null);
		int height = image.getHeight(null);

		int npix = height * width;
		int[] pixels = new int[npix];

		PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height,
				pixels, 0, width); // RGB model

		grabber.grabPixels();
		
		int[][] img = new int[height][width];
		
		for(int row = 0; row < height; row++){
			for(int col = 0; col < width; col++){
			
				img[row][col] = pixels[row * width + col];
			}
		}

		return img;

	}
}
