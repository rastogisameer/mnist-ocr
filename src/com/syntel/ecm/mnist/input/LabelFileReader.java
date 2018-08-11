package com.syntel.ecm.mnist.input;
import java.io.IOException;

import com.syntel.ecm.ocr.utils.Constants;

public class LabelFileReader {

	public String [] readLabels(String filepath) {
	
		IDXFileReader reader = null;
		String [] labels = null;
		
		try {
			reader = new IDXFileReader(filepath);

			int magic = reader.readInt();

			if (magic != Constants.LABELFILE_MAGIC_NUMBER) {
				System.err.println("Invalid Magic Number: " + magic);
			}
			int cnt = reader.readInt();
 
			labels = new String[cnt];

			for (int i = 0; i < cnt; i++) {

				int label = reader.readUnsignedByte();

				labels[i] = "" + label;
				
				// System.out.println(label);
			}			

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return labels;
	}

}
