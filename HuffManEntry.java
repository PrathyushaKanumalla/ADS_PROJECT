

public class HuffManEntry {
	
	public HuffManEntry leftChild;
	public HuffManEntry rightChild;
	
	private int data;
	private int frequency;
	
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public HuffManEntry getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(HuffManEntry leftChild) {
		this.leftChild = leftChild;
	}
	public HuffManEntry getRightChild() {
		return rightChild;
	}
	public void setRightChild(HuffManEntry rightChild) {
		this.rightChild = rightChild;
	}
	
	public HuffManEntry(Integer freq,Integer data) {
		this.frequency = freq;
		this.data = data;
	}
	public HuffManEntry(int data, HuffManEntry left, HuffManEntry right) {
		this.data = data;
		this.leftChild = left;
		this.rightChild = right;
	}
	
	
	
}
