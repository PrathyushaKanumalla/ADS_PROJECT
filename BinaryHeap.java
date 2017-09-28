

import java.util.ArrayList;;

public class BinaryHeap {
	
	HuffManEntry[] array;
	int size = 0;
	int maxSize = 0;
	
	/*Initialise the heap with array List*/
	public BinaryHeap(ArrayList<HuffManEntry> input) {
		this(input.size());
		addAll(input);
	}
	
	public void addAll(ArrayList<HuffManEntry> input) {
		for (int i = 0; i < input.size() && size < maxSize;i++) {
			array[size++] = input.get(i);
		}
		for(Integer i = input.size()/2+1; i >= 0; i--) {
			//heapify(i);
			Integer index = i;
			while(index != null && index >= 0 && index < size) {
				Integer minIndex = minimum(getLeftChild(index), getRightChild(index));
				if(minIndex == null)
					break;
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

	public BinaryHeap(int si){
		maxSize = si;
		array = new HuffManEntry[si];
	}

	public void add(HuffManEntry node) {
		if(size+1 <= maxSize) {
			array[size++] = node;
			//perculateToTop(size-1);
			Integer index = size-1;
			while(index != null && index >= 0 && index < size) {
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
	}
	
	public HuffManEntry peek() {
		return (size != 0)?array[0]:null;
	}

	public HuffManEntry remove() {
		if(size > 0) {
			HuffManEntry node = array[0];
			if(size > 1)
				array[0] = array[size-1];
			size--;
			//heapify(0);
			Integer index = 0;
			while(index != null && index >= 0 && index < size) {
				Integer minIndex = minimum(getLeftChild(index), getRightChild(index));
				if(minIndex == null)
					break;
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
		array[0] = node;
		//heapify(0);
		Integer index = 0;
		while(index != null && index >= 0 && index < size) {
			Integer minIndex = minimum(getLeftChild(index), getRightChild(index));
			if(minIndex == null)
				break;
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
		return size;
	}

	public boolean isEmpty() {
		return (size==0);
	}
	
	private void heapify(Integer index) {
		while(index != null && index >= 0 && index < size) {
			Integer minIndex = minimum(getLeftChild(index), getRightChild(index));
			if(minIndex == null)
				return;
			Integer swapIndex = minimum(minIndex,index);
			if(swapIndex != index) {
				HuffManEntry temp = array[index];
				array[index] = array[swapIndex];
				array[swapIndex] = temp;
				index = swapIndex;
			}
			else return;
		}
		return;		
	}
	
	private Integer getLeftChild(Integer index) {
		int outindex = 2*index+1;
		return (outindex >= 0 && outindex < size)?outindex:null;
	}
	
	private Integer getRightChild(Integer index) {
		int outindex = 2*index+2;
		return (outindex >= 0 && outindex < size)?outindex:null;
	}
	
	private Integer getParent(Integer index) {
		int outindex = (index-1)/2;
		return (index != 0 && outindex >= 0 && outindex < size)?outindex:null;
	}
	
	private void perculateToTop(Integer index) {
		while(index != null && index >= 0 && index < size) {
			Integer parent = getParent(index);
			if(parent!=null && array[index].getFrequency() < array[parent].getFrequency()) {
				HuffManEntry temp = array[parent];
				array[parent] = array[index];
				array[index] = temp;
				index = parent;
			}
			else return;
		}
		return;		
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
