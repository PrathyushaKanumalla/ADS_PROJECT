

import java.util.ArrayList;
import java.util.Stack;

public class PairingHeap {
	
	PairingEntry head;
	int size;

	public void add(PairingEntry node) {
		size++;
		if(head == null) {
			head = node;
			return;
		}
		head = meld(node,head);
	}
	
	public void addAll(ArrayList<PairingEntry> input) {
		for (PairingEntry pairingEntry : input) {
			add(pairingEntry);
		}
	}
	
	private PairingEntry meld(PairingEntry node,PairingEntry temp) {
		if(node == null && temp == null) {
			return null;
		}
		else if(node == null)
			return temp;
		else if(temp == null)
			return node;
		else {
			if(temp.getFrequency() <= node.getFrequency()) {
				PairingEntry parentchild = temp.getChild();
				if(parentchild != null){
					parentchild.setLeftSibling(node);
				}
				node.setRightSibling(parentchild);
				temp.setChild(node);
				node.setLeftSibling(temp);
				return temp;
			}
			else{
				PairingEntry parentchild = node.getChild();
				if(parentchild != null){
					parentchild.setLeftSibling(temp);
				}
				temp.setRightSibling(parentchild);
				node.setChild(temp);
				temp.setLeftSibling(node);
				return node;
			}
		}
	}

	public PairingEntry peek() {
		return head;
	}

	public PairingEntry remove() {
		if(head == null)
			return null;
		size--;
		Stack<PairingEntry> stack = new Stack<>();
		PairingEntry result = head,output = head;
		//firstchild
		PairingEntry leftSib = result.getChild();
		if(leftSib == null) {
			head = null;
			return result;
		}
		result = leftSib;
		while(leftSib.getRightSibling() != null){
			PairingEntry rightSib = leftSib.getRightSibling();
			//logic to get these out of heap;
			leftSib.setLeftSibling(null);
			leftSib.setRightSibling(null);
			PairingEntry temp = rightSib.getRightSibling();
			rightSib.setLeftSibling(null);
			rightSib.setRightSibling(null);
			stack.push(meld(leftSib,rightSib));
			
			leftSib = temp;
			if(leftSib == null)
				break;
			if(leftSib.getRightSibling() == null){
				leftSib.setLeftSibling(null);
				stack.push(leftSib);
				break;
			}
		}
		while(!stack.isEmpty()) {
			if(stack.size() == 1) {
				head = stack.pop();
				return output;
			}
			stack.push(meld(stack.pop(),stack.pop())); 
		}
		head = result;
		return output;
	}

	public void updateRoot(PairingEntry node) {
		if(node == null)
			return;
		head.setFrequency(node.getFrequency());
		head.setData(node.getData());
		head.setLeftChild(node.getLeftChild());
		head.setRightChild(node.getRightChild());
		update(head);
	}

	private void update(PairingEntry node) {
		while(node != null) {
			PairingEntry minChild = getMinChild(node);
			if(minChild != null && minChild.getFrequency() < node.getFrequency()) {
				
				int freq = node.getFrequency();
				PairingEntry right = node.getRightChild();
				PairingEntry left = node.getLeftChild();
				int data = node.getData();
				
				node.setFrequency(minChild.getFrequency());
				node.setData(minChild.getData());
				node.setLeftChild(minChild.getLeftChild());
				node.setRightChild(minChild.getRightChild());
				
				minChild.setFrequency(freq);
				minChild.setLeftChild(left);
				minChild.setRightChild(right);
				minChild.setData(data);
				
				node = minChild;
			}
			else break;
		}
		return;
	}

	private PairingEntry getMinChild(PairingEntry node) {
		PairingEntry leftSib = node.getChild();
		PairingEntry result = leftSib;
		while(leftSib != null) {
			result = leftSib;
			if(result.getFrequency() > leftSib.getFrequency()) {
				result = leftSib;
			}
			leftSib = leftSib.getRightSibling();
		}
		return result;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size==0)?true:false;
	}

	public void print() {
		print(head,1);
	}

	private void print(PairingEntry temp, int index) {
		while(temp != null) {
			System.out.print("Level- "+(index)+" -- ");
			System.out.print(temp.getFrequency()+" ");
			PairingEntry siblings = temp.getRightSibling();
			while(siblings != null) {
					print(siblings,index);
					siblings = siblings.getRightChild();
			}
			temp = temp.getChild();
			index++;
		}
		System.out.println();
	}
	
	

	
	
	
}
