
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class PriorityQueueAnalysis {
	
	public HuffManEntry build2DegreeHeap(ArrayList<HuffManEntry> input) {
		if(input.isEmpty())
			return null;
		BinaryHeap pq = new BinaryHeap(input);
		while(!pq.isEmpty() && pq.size() != 1) {
			HuffManEntry he1 = pq.remove();
			
			HuffManEntry he2 = pq.peek();
			HuffManEntry entry = new HuffManEntry(he2.getFrequency()+he1.getFrequency(), 0);
			entry.setLeftChild(he1);
			entry.setRightChild(he2);
			//pq.add(entry);
			pq.updateRoot(entry);
		}
		return pq.remove();
	}
	
	public HuffManEntry build4DegreeHeap(ArrayList<HuffManEntry> input) {
		if(input.isEmpty())
			return null;
		FourCacheHeap pq = new FourCacheHeap(input);
		while(!pq.isEmpty() && pq.size() != 1) {
			HuffManEntry he1 = pq.remove();
			
			HuffManEntry he2 = pq.peek();
			HuffManEntry entry = new HuffManEntry(he2.getFrequency()+he1.getFrequency(), 0);
			entry.setLeftChild(he1);
			entry.setRightChild(he2);
			//pq.add(entry);
			pq.updateRoot(entry);
		}
		return pq.remove();
	}
	
	public PairingEntry buildPairingTree(ArrayList<PairingEntry> input, PairingHeap pq) {
		if(input.isEmpty())
			return null;
		for (PairingEntry pairingEntry : input) {
			pq.add(pairingEntry);
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
		return pq.remove();
	}
	public static void main(String[] args) throws Exception {
		PriorityQueueAnalysis.analyse();
	}
	
	private static void analyse() throws Exception {
		HashMap<Integer, Integer> map = new HashMap<>();
		getInput(map);
		PriorityQueueAnalysis analyse = new PriorityQueueAnalysis();
		ArrayList<HuffManEntry> input = new ArrayList<HuffManEntry>();
		ArrayList<PairingEntry> pairInput = new ArrayList<PairingEntry>();
		for (Entry<Integer, Integer> record : map.entrySet()) {
			input.add(new HuffManEntry(record.getValue(),record.getKey()));
			pairInput.add(new PairingEntry(record.getValue(), record.getKey()));
		}
		long startTime = System.currentTimeMillis();
		for(int i = 0; i<10;i++) {
			analyse.build2DegreeHeap(input);
		}
        long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Binary Heap Takes - "+elapsedTime/10);
		
	    
	    
	    startTime = System.currentTimeMillis();
	    for(int i = 0; i<10;i++) {
	    	analyse.build4DegreeHeap(input);
	    }
        stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.println("Four Cahe Heap Takes - "+elapsedTime/10);

	    
	    
	    startTime = System.currentTimeMillis();
	    for(int i = 0; i<10;i++) {
	    	analyse.buildPairingTree(pairInput, new PairingHeap());
	    }
        stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.println("Pairing Heap Takes - "+elapsedTime/10);
	}



	private static void getInput(HashMap<Integer, Integer> map) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("sample_input_large.txt"));
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
	
	
}
