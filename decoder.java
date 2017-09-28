

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;



public class decoder {
	
	DecoderNode root = new DecoderNode(0);
	FileInputStream fin = null;
	BufferedInputStream bin = null;
	FileOutputStream fout = null;
	BufferedOutputStream bout = null;
	byte[] readBytes = new byte[1024];
	Integer byteIndex = 0;
	int tempByteBuffer = 1<<7;
	int bytesSize = 0;
	 public class DecoderNode {
	        int data;
	        public DecoderNode leftChild, rightChild;
	        
	        public DecoderNode(int data) {
	            this.data = data;
	        }

	        public DecoderNode() {

	        }

	        public void setData(int data) {
	            this.data = data;
	        }

	        public int getData() {
	            return this.data;
	        }
	        
	    }
	
	public decoder(String encoderFile, String cdeTableFile) throws Exception {
		Map<Integer, String> valueCodeMap = new HashMap<>();
		getInput(valueCodeMap,cdeTableFile);
		for (Integer data : valueCodeMap.keySet()) {
			String code = valueCodeMap.get(data);
			int index = 0;
			DecoderNode node = root;
			while(index < code.length()) {
				if(code.charAt(index) == '0') {
					if (node.leftChild == null) 
						node.leftChild = new DecoderNode(0);
					node = node.leftChild;
				}
				else  {
					if(node.rightChild == null)
						node.rightChild = new DecoderNode(1);
					node = node.rightChild;
				}
				index++;
				if(index == code.length()) {
					node.setData(data);
					break;
				}
			}
		}
		//print start
		// inorder(root);
		//print end
		
		fin = new FileInputStream(encoderFile);	
		bin = new BufferedInputStream(fin);
		fout = new FileOutputStream("decoded.txt");    
		bout = new BufferedOutputStream(fout);
		decodeEncodedData();
		bout.close();
		bin.close();
		fout.close();
		fin.close();
	}
	
	public static void inorder(HuffManEntry node) {
       /* if(node == null) {
            return;
        }
        inorder(node.leftChild);
        if(node.leftChild == null && node.rightChild == null)
        	System.out.println(node.getData());
        inorder(node.rightChild);*/
    } 

	private void decodeEncodedData() throws Exception {
		bytesSize = bin.read(readBytes);
		if(bytesSize != -1) {
			writeBytesToDecoder();
		}
		bout.close();
		return;
	}

	private void writeBytesToDecoder() throws IOException {
		DecoderNode node = root;
		while(true) {
			if(tempByteBuffer == 0  && byteIndex < bytesSize-1) {
				byteIndex++;
				tempByteBuffer = 1<<7;
			}
			else if(tempByteBuffer == 0 && byteIndex == bytesSize-1) {
				bytesSize = bin.read(readBytes);
				if(bytesSize != -1) {
					tempByteBuffer = 1<<7;
					byteIndex = 0;
				}
				else {
					/*System.out.println(node.getData());
					bout.write(Integer.toString(node.getData()).getBytes());
					bout.write("\n".getBytes());*/
					break;
				}
			}
			if((readBytes[byteIndex] & tempByteBuffer) == 0){
				if(node.leftChild != null) {
					tempByteBuffer >>>= 1;
					node = node.leftChild;
				}
				if(node.leftChild == null && node.rightChild == null) {
					bout.write(Integer.toString(node.getData()).getBytes());
					bout.write("\n".getBytes());
					node = root;
				}
			}
			else {
				if(node.rightChild != null) {
					tempByteBuffer >>>= 1;
					node = node.rightChild;
				}
				if(node.leftChild == null && node.rightChild == null) {
					bout.write(Integer.toString(node.getData()).getBytes());
					bout.write("\n".getBytes());
					node = root;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		try{
			long startTime = System.currentTimeMillis();
			if(args.length != 2) {
				throw new IllegalArgumentException("Please provide two file names as arguments");
			}
			decoder decoder = new decoder(args[0],args[1]);
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			System.out.println("Decoder Takes - "+elapsedTime);
		}
		catch(FileNotFoundException e){
			throw new Exception("The file is not found in my program. Please give complete path and run once again.");
		}
	}

	private static void getInput(Map<Integer, String> valueCodeMap, String fileName) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
	    String line = br.readLine();
	    while (line != null && line.length() != 0) {
	    	String[] dataBitsCombo = line.split(" ");
	    	Integer data = Integer.parseInt(dataBitsCombo[0]);
	    	valueCodeMap.put(data,dataBitsCombo[1]);
	    	line = br.readLine();
	    }
	    br.close();
		
	}
	
}
