

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class encoder {
	
	Map<Integer, String> valueCodeMap = new HashMap<>();
	PairingEntry root = null;
	byte[] toWriteBytes = new byte[1024];
	Integer byteIndex = 0;
	byte tempByteBuffer = 0;
	Integer bufferIndex = 0;
	FileOutputStream fout = null;
	BufferedOutputStream bout = null;
	
	public encoder(String fileName) throws Exception {
		HashMap<Integer, Integer> freqTable = new HashMap<>();
		getInput(freqTable,fileName);
		//buildHuffManTree(freqTable);
		if(freqTable.isEmpty())
			return;
		PairingHeap pq = new PairingHeap();
		for (Entry<Integer, Integer> record : freqTable.entrySet()) {
			pq.add(new PairingEntry(record.getValue(), record.getKey()));
		}
		while(!pq.isEmpty() && pq.size() != 1) {
			PairingEntry he1 = pq.remove();
			
			PairingEntry he2 = pq.remove();
			PairingEntry entry = new PairingEntry(he2.getFrequency()+he1.getFrequency(), 0);
			entry.setLeftChild(he1);
			entry.setRightChild(he2);
			pq.add(entry);
			//pq.updateRoot(entry);
		}
		this.root = pq.remove();
		constructHuffManCodeMap(this.root,new StringBuilder());
		/*for (Entry<Integer, String> record : valueCodeMap.entrySet()) {
			System.out.println("Key: "+record.getKey()+ " Value: "+record.getValue());
		}*/
		writeCodeTableToFile();
		fout = new FileOutputStream("encoded.bin");    
		bout = new BufferedOutputStream(fout);
		writeEncodeDataToFile(fileName);
	}
	
	private void writeEncodeDataToFile(String fileName) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while (line != null && line.length() != 0) {
		 	Integer data = Integer.parseInt(line);
		   	if(valueCodeMap.containsKey(data)) {
		   		for (char c : valueCodeMap.get(data).toCharArray()) {
					if(bufferIndex < 8) {
						tempByteBuffer = (byte)((tempByteBuffer<<1)|(c=='0'?0:1));
						bufferIndex++;
						if(bufferIndex == 8) {
							toWriteBytes[byteIndex++] = tempByteBuffer;
							if(byteIndex == 1024) {
								//System.out.println("WROTE INTO FILE:");
								bout.write(toWriteBytes);
								byteIndex = 0;
							}
							bufferIndex = 0;
						}
					}
				}
		   	}
		   	line = br.readLine();
		}
		bout.write(toWriteBytes, 0, byteIndex);
		bout.close();
		br.close();
	}


	private void writeCodeTableToFile() throws Exception {
		fout = new FileOutputStream("code_table.txt");    
		bout = new BufferedOutputStream(fout);
		int i = 0;
		for (Entry<Integer, String> record : valueCodeMap.entrySet()) {
			if(i++ != valueCodeMap.size()-1)
				bout.write((record.getKey()+" "+record.getValue()+"\n").getBytes());
			else
				bout.write((record.getKey()+" "+record.getValue()).getBytes());
			
		}
		bout.close();
		fout.close();
	}

	private void constructHuffManCodeMap(PairingEntry node, StringBuilder stringBuilder) {
		if(node.leftChild == null && node.rightChild == null && stringBuilder != null) {
			valueCodeMap.put(node.getData(), stringBuilder.toString());
			return;
		}
		else if(node.leftChild == null && node.rightChild == null && stringBuilder == null) {
			valueCodeMap.put(node.getData(), "0");
			return;
		}
		constructHuffManCodeMap(node.leftChild, stringBuilder.append(0));
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		constructHuffManCodeMap(node.rightChild, stringBuilder.append(1));
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
	}

	public static void main(String[] args) throws Exception {
		try{
			long startTime = System.currentTimeMillis();
			encoder encoder = new encoder(args[0]);
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			System.out.println("Encoder Takes - "+elapsedTime);
		}
		catch(FileNotFoundException e){
			throw new Exception("The file "+args[0]+" is not found in my program. Please give complete path and run once again.");
		}
	}

	private static void getInput(HashMap<Integer, Integer> map, String fileName) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
	    String line = br.readLine();
	    while (line != null && line.length() != 0) {
	    	Integer data = Integer.parseInt(line);
	    	if(map.containsKey(data))
	    		map.put(data, map.get(data)+1);
	    	else
	    		map.put(data, 1);
	    	line = br.readLine();
	    }
	    br.close();
	}
	
	public void buildHuffManTree(HashMap<Integer, Integer> freqTable) {
		/*if(freqTable.isEmpty())
			return;
		PairingHeap pq = new PairingHeap();
		for (Entry<Integer, Integer> record : freqTable.entrySet()) {
			pq.add(new PairingEntry(record.getValue(), record.getKey()));
		}
		while(!pq.isEmpty() && pq.size() != 1) {
			PairingEntry he1 = pq.remove();
			
			PairingEntry he2 = pq.remove();
			PairingEntry entry = new PairingEntry(he2.getFrequency()+he1.getFrequency(), 0);
			entry.setLeftChild(he1);
			entry.setRightChild(he2);
			pq.add(entry);
			//pq.updateRoot(entry);
		}
		this.root = pq.remove();*/
	}
	
}
