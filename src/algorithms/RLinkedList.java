package algorithms;

public class RLinkedList {
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.add(0);
		list.add(100);
		list.add(12);
		list.add(10);
		list.add(114);
		
	}
}

/**
 * @author raphe Anytime this method is invoked, it holds creates objects of
 *         itself
 */
class Node {
	
	private int size = 0;
	
	public Node() {
		size++;
	}
	
	int data;
	Node next;
	
	int getSize() {
		return size;
	}
	
}

class LinkedList {
	private Node head;

	public void add(int input) {
		Node n = new Node();
		n.data = input;
		n.next = null;

		// inserting at the end of the node
		if (head == null) {
			System.out.println("Head Null");
			head = n;
		} else {	
			Node node = head;
			
			while (node!= null) {
				node = node.next;
				System.out.println("Checking For null ");
				
				
			}

			node = n;
			System.out.println("Check Over "+node.getSize());
		}
	}

	public void show() {
//		Node n = head;'
	}
}
