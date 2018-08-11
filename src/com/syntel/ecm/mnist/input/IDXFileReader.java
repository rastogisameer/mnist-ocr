package com.syntel.ecm.mnist.input;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IDXFileReader {

	private BufferedInputStream in; 
	
	public IDXFileReader(String path){
		try {
			in = new BufferedInputStream(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	public void close() throws IOException{
		in.close();
	}

	/*
	 * MSB first. High Endian Format
	 */
	public int readInt() throws IOException {
		int x = 0;
		for (int i = 0; i < 4; i++) {
			int c = in.read();
			x <<= 8;
			x |= c;
		}
		return x;
	}
	public int readUnsignedByte() throws IOException{
		int c = in.read();
		
		return c & 0xff;
	}
}
