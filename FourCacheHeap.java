

import java.util.ArrayList;;

public class FourCacheHeap{
	
	HuffManEntry[] array ;
	int size = 3;
	int maxSize = 3;
	
	/*Initialise the heap with array List*/
	public FourCacheHeap(ArrayList<HuffManEntry> input) {
		this(input.size());
		addAll(input);
	}
	
	
	
	public void addAll(ArrayList<HuffManEntry> input) {
		for (int i = 0; i < input.size() && (size < maxSize); i++) {
			array[size++] = input.get(i);
		}
		for(int i = size/2+1; i >= 3; i--) {
			//heapify(i);
			Integer index = i;
			while(index != null && index >= 3 && index < maxSize) {
				Integer minIndex = minimum(getFirstChild(index), getSecondChild(index));
				if(minIndex == null)
					break;
				if(minIndex != null)
					minIndex = minimum(minIndex,getThirdChild(index));
				if(minIndex != null)
					minIndex = minimum(minIndex,getFourthChild(index));
				Integer swapIndex = minimum(minIndex,index);
				if(swapIndex != index) {
					HuffManEntry temp = array[index];
					array[index] = array[swapIndex];
					array[swapIndex] = temp;
					index = swapIndex;
				}
				else break;
			}
		}
	}



	public FourCacheHeap(int size){
		maxSize += size;
		array = new HuffManEntry[size+3];
		array[0] = new HuffManEntry(0,0);
		array[1] = new HuffManEntry(0,0);
		array[2] = new HuffManEntry(0,0);
	}

	public void add(HuffManEntry node) {
		if(size+1 <= maxSize) {
			array[size++] = node;
			//perculateToTop(size-1);
			Integer index = size-1;
			while(index != null && index >= 3 && index < size) {
				Integer parent = getParent(index);
				if(parent!=null && array[index].getFrequency() < array[parent].getFrequency()) {
					HuffManEntry temp = array[parent];
					array[parent] = array[index];
					array[index] = temp;
					index = parent;
				}
				else break;
			}
		}
		return;
	}
	
	private void perculateToTop(Integer index) {
		while(index != null && index >= 3 && index < size) {
			Integer parent = getParent(index);
			if(parent!=null && array[index].getFrequency() < array[parent].getFrequency()) {
				HuffManEntry temp = array[parent];
				array[parent] = array[index];
				array[index] = temp;
				index = parent;
			}
			else break;
		}
		return;
	}



	public HuffManEntry peek() {
		return (size() > 0)?array[3]:null;
	}

	public HuffManEntry remove() {
		if(size > 3) {
			HuffManEntry node = array[3];
			if(size > 4)
				array[3] = array[size-1];
			size--;
			//heapify(3);
			Integer index = 3;
			while(index != null && index >= 3 && index < maxSize) {
				Integer minIndex = minimum(getFirstChild(index), getSecondChild(index));
				if(minIndex == null)
					break;
				if(minIndex != null)
					minIndex = minimum(minIndex,getThirdChild(index));
				if(minIndex != null)
					minIndex = minimum(minIndex,getFourthChild(index));
				Integer swapIndex = minimum(minIndex,index);
				if(swapIndex != index) {
					HuffManEntry temp = array[index];
					array[index] = array[swapIndex];
					array[swapIndex] = temp;
					index = swapIndex;
				}
				else break;
			}
			return node;
		}
		return null;
	}

	public void updateRoot(HuffManEntry node) {
		if(node == null)
			return;
		array[3] = node;
		//heapify(3);
		Integer index = 3;
		while(index != null && index >= 3 && index < maxSize) {
			Integer minIndex = minimum(getFirstChild(index), getSecondChild(index));
			if(minIndex == null)
				break;
			if(minIndex != null)
				minIndex = minimum(minIndex,getThirdChild(index));
			if(minIndex != null)
				minIndex = minimum(minIndex,getFourthChild(index));
			Integer swapIndex = minimum(minIndex,index);
			if(swapIndex != index) {
				HuffManEntry temp = array[index];
				array[index] = array[swapIndex];
				array[swapIndex] = temp;
				index = swapIndex;
			}
			else break;
		}
	}

	public int size() {
		return size-3;
	}
	
	public int arraySize() {
		return size;
	}


	public boolean isEmpty() {
		return (size()==0);
	}
	
	private void heapify(Integer index) {
		while(index != null && index >= 3 && index < maxSize) {
			Integer minIndex = minimum(getFirstChild(index), getSecondChild(index));
			if(minIndex == null)
				break;
			if(minIndex != null)
				minIndex = minimum(minIndex,getThirdChild(index));
			if(minIndex != null)
				minIndex = minimum(minIndex,getFourthChild(index));
			Integer swapIndex = minimum(minIndex,index);
			if(swapIndex != index) {
				HuffManEntry temp = array[index];
				array[index] = array[swapIndex];
				array[swapIndex] = temp;
				index = swapIndex;
			}
			else break;
		}
		return;		
	}
	
	private Integer getFirstChild(Integer index) {
		int outindex = 4*index-8;
		return ((outindex > 3) && outindex < arraySize())?outindex:null;
	}
	
	private Integer getSecondChild(Integer index) {
		int outindex = 4*index-7;
		return ((outindex > 3) && outindex < arraySize())?outindex:null;
	}
	
	private Integer getThirdChild(Integer index) {
		int outindex = 4*index-6;
		return ((outindex > 3) && outindex < arraySize())?outindex:null;
	}
	
	private Integer getFourthChild(Integer index) {
		int outindex = 4*index-5;
		return ((outindex > 3) && outindex < arraySize())?outindex:null;
	}
	
	private Integer getParent(Integer index) {
		int outindex = index/4;
		return (index > 3 && outindex+2 < arraySize())?outindex+2:null;
	}
	
	private Integer minimum(Integer index1, Integer index2) {
		if(index1 == null && index2 == null)
            return null;
        else if(index1 == null)
            return index2;
        else if(index2 == null)
            return index1;
        else {
            if(array[index1].getFrequency() <= array[index2].getFrequency())
                return index1;
            else
                return index2;
        }
	}
	
	public void print(){
		System.out.println();
		for (int i =0;i<size;i++) {
			System.out.print(array[i].getFrequency()+" ");
		}
		System.out.println();
	}
}
