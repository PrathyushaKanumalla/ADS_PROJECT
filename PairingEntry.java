

public class PairingEntry {
	
	PairingEntry leftSibling;
	PairingEntry rightSibling;
	PairingEntry child;
	public PairingEntry leftChild;
	public PairingEntry rightChild;
	
	private int data;
	private int frequency;

	public void setLeftSibling(PairingEntry leftSibling) {
		this.leftSibling = leftSibling;
	}

	public PairingEntry getLeftSibling() {
		return leftSibling;
	}

	public PairingEntry getRightSibling() {
		return rightSibling;
	}

	public PairingEntry getChild() {
		return child;
	}

	public PairingEntry getLeftChild() {
		return leftChild;
	}

	public PairingEntry getRightChild() {
		return rightChild;
	}

	public void setRightSibling(PairingEntry rightSibling) {
		this.rightSibling = rightSibling;
	}

	public void setChild(PairingEntry child) {
		this.child = child;
	}

	public void setLeftChild(PairingEntry leftChild) {
		this.leftChild = leftChild;
	}

	public void setRightChild(PairingEntry rightChild) {
		this.rightChild = rightChild;
	}

	public PairingEntry(int frequency,int data) {
		this.frequency = frequency;
		this.data = data;
	}


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

}
